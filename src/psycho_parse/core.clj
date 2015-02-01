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

;; may want this in a vector instead for indexing
;; this can be used by hypernyms to find the right position to reset the path to by finding the position of the current synset in all paths you could take, and each synset is guaranteed to come up only one time. the only uncertainty is if we know where to reset it to, so we may actually want to be able to follow the path to a certain synse
(defn nested-keys [map]
  (filter keyword? (tree-seq map? #(interleave (keys %) (vals %)) map)))

;;all i want is the path
;;use this keys-in function
;;returns the ending key too often when what i want is just the synset val
;; thanks to whoeber wrote this
(defn keys-in [m]
  (if (map? m)
    (vec (mapcat (fn [[k v]]
                   (let [sub (keys-in v)
                         nested (map #(into [k] %) (filter (comp not empty?) sub))]
                     (if (seq nested)
                       nested
                       [[k]])))
                 m))
    []))

;;record the depth at which the key was found to know when two are =
(defn path-to [map key]
  
  )

(defn hypernym [entry]
  "Returns the hypernyms for an entry in a tree form where the synset-id is mapped to all the entries in that synset and to any potential hypernyms of that set."
  (let [m (related-synsets entry :hypernym)
        synsets (keys m)
        hypernyms (vals m)
        hyp-nodes (map (fn [s] {:synset s :hypernyms nil}) hypernyms)
        tree-node (zipmap synsets hyp-nodes)]
    tree-node))

;; only add the first of each synest to to-search
;;need to reset path, and make sure hypernyms is appended for each
;;it'll come after it tho, so chidren new-id
;;its overwriting valuse cause theyre not getting put in hypernyms
(defn hypernyms [entry]
  (loop [to-search [entry]
         hypernyms {}
         path []]
    (clojure.pprint/pprint hypernyms)
    (if (empty? to-search)
      hypernyms
      (let [e (if (map? (first to-search))
                (first to-search)
                ;if its a synset need only one exemplar entry
                (first (first to-search))) 
            synset-id (:synset-id e)
            ;;need to add hypernyms before id so its a child
            path (conj path synset-id)
            ;;will need to append :hypernyms most of the time 
            ;;still need to determine how to reset this
            
            hyprnms (hypernym e)
            hyp-entries (map :synset (vals hyprnms))] ;;instead of this just go thru entries as whole synsets checking to see 
        (if (empty? hyprnms)
          ;;if its empty then path needs to be updated
          (recur (rest to-search) hypernyms path)
          (recur (concat hyp-entries (rest path))
                 ;;wrong: hyprnms lack keys for entries and hyeprnyms!
                 ;;probably need to use updatein so that I can add children to a node 
                 (assoc-in hypernyms path hyprnms)
                 path))
        ))
    )
  )


;; how do i know which synset to go down?
(defn word->hypernym [word depth]
  ;; if depth is greater than count of hypernyms then just get last.
  )

;;; hard part here is diving into the dense tree and changing values 
(defn replace-words [treebank]
  )


