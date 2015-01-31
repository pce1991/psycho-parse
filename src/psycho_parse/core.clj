 (ns psycho-parse.core
  (:require [opennlp.nlp :refer :all]
            [opennlp.treebank :refer :all]
            [clj-wordnet.core :refer :all]
            [lex.dictionary :as dict])
  (:gen-class))

;;; use opennlp to get a parse of the sentence, then go to each word, get its
;;; WN entry, and then find its hypernyms and replace each word with it to
;;; the requested depth, so you could get "the dog ran" as "the canine moved"
;;; or "the animal acted" 

(def get-sentences (make-sentence-detector "resources/models/en-sent.bin"))
(def tokenize (make-tokenizer "resources/models/en-token.bin"))
(def pos-tag (make-pos-tagger "resources/models/en-pos-maxnet.bin"))
(def chunker (make-treebank-chunker "resources/models/en-chunker.bin"))
;; (def treebank-parser (make-treebank-parser
;;                       "resources/models/en-parser-chunking.bin"))

(defn disambiguate [])

(defn nearest-hypernyms [entry]
  (let [synsets (related-synsets entry :hypernym)
        hypernyms (map #(into [] %) (vals synsets))]
    ;; i want to build up the trees for each synset, 
    (into [] hypernyms)))

;;its important that this not go thru so many tries as the other which
;;added way to mayn redundant cases to hypernyms
;; create a tree structure
;;look at current hypernyms of a word, and then update that into the set
(defn hypernyms [entry]
  (loop [hypernyms {}             ;(related-synsets entry :hypernym)
         to-search [entry]             ;(map #(into [] %) (vals hypernyms))
         synset-ids []]
;   (println (map :lemma to-search))
;    (println (count synset-ids))
    (clojure.pprint/pprint hypernyms)
    (if (empty? to-search)
      hypernyms
      (let [e (first to-search)
            synset-id (:synset-id e)
            ;; sometimes this may need to be reset, when we return to an
            ;; earlier synset, but which one? if we get a synset thats
            ;;already appeared I think we know to reset to that point
            synset-ids (conj synset-ids synset-id)
            hyprnms (related-synsets e :hypernym)
            hyp-entries (flatten (vals hyprnms))
            ]
        (if (empty? hyprnms)
          (recur hypernyms (rest to-search) synset-ids) ;;wrong?!
          ;;i may need to build up a vector of synset-ids, but it couldnt just accumlate
          ;; 
          (recur
           ;;right now synset is going out of bounds?
           ;;i think i definitely need update in, its losing all the
           ;;the early entries, keeping only synset. think of its idealform
           ;;each id needs to hold its hyps, and also its childre
           (assoc-in  hypernyms synset-ids hyprnms)
                 (concat hyp-entries (rest to-search))
                 ;;this is so it follows one path all the way before backing up.
                 synset-ids
                 ))

        ))
    ))

;; how do i know which synset to go down?
(defn word->hypernym [word depth]
  ;; if depth is greater than count of hypernyms then just get last.
  )

;;; hard part here is diving into the dense tree and changing values 
(defn replace-words [treebank]
  )


