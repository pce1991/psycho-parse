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

;; just do the basics here of getting the ENTRIES of just the toplevel
;; hypernyms. I definetly dont want the lemmas
;; do I know that the hypernyms of those words are the next thing up from
;; previous ones.
;; the related synset returns a map of synset ids which can keyed to
;; lists of words, an example being that a certain synset has both candid
;; and canine as lemmas
(defn nearest-hypernyms [entry]
  ;; recursively get the related-synsets of words, then of those
  ;; but how do I know what sense/entry-number they are?
  ;; worry about that later, right now just get the hypernyms
  ;; need to call perl from here or implement algorithm
  ;; maybe just execute a perl script

  ;; i think what im actually worried about is how to know which sense
  ;; of a synset to look up. do i know that members of a synset have the
  ;; same hypernym
  ;; i want to build up a tree of these, maybe just branches at synsets
  ;; and then vectors showing the order of supremacy
  ;; keep in mind tho that any synset could split up at any point
  ;; so at every point I must check to see if there's a split
  (let [synsets (related-synsets entry :hypernym)
        hypernyms (map #(into [] %) (vals synsets))]
    ;; i want to build up the trees for each synset, 
    (into [] hypernyms)))


;; THIS IS RIDICULOUS! 129646! I must be looking up too many redundant words. how do I know which to ignore? if the synset id is already in cumulative; this might not work for the branching hierarchies
;; i want divide these up based on where they branch, but do synsets have
;; an order so that synset of canine is less than synset of entity?
(defn hypernyms [entry]
  (loop [hypernyms (flatten (nearest-hypernyms entry))
         cumulative []]
    ;;just loop thru moving the front cumulative and add its hyps to hypernyms
    (if (empty? hypernyms)
      cumulative
      (if (empty? (nearest-hypernyms (first hypernyms)))
        ;; this is when we know its the end of that tree
        (recur (rest hypernyms) (conj cumulative (first hypernyms)))
        (recur
         (concat (rest hypernyms)
                 (flatten (nearest-hypernyms (first hypernyms))))
         (conj cumulative (first hypernyms)))))))

(defn word->hypernym [word depth]
  ;; if depth is greater than count of hypernyms then just get last.
  )

;;; hard part here is diving into the dense tree and changing values 
(defn replace-words [treebank]
  )


