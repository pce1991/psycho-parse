(ns psycho-parse.core
  (:require [opennlp.nlp :refer :all]
            [opennlp.treebank :refer :all]
            [clj-wordnet.core :refer :all])
  (:gen-class))

;;; use opennlp to get a parse of the sentence, then go to each word, get its
;;; WN entry, and then find its hypernyms and replace each word with it to
;;; the requested depth, so you could get "the dog ran" as "the canine moved"
;;; or "the animal acted" 

(def get-sentences (make-sentence-detector "resources/models/en-sent.bin"))
(def tokenize (make-tokenizer "resources/models/en-token.bin"))
(def pos-tag (make-pos-tagger "resources/models/en-pos-maxnet.bin"))
(def chunker (make-treebank-chunker "resources/models/en-chunker.bin"))
(def treebank-parser (make-treebank-parser
                      "resources/models/en-parser-chunking.bin"))

(defn hypernyms [word]
  ;; recursively get the related-synsets of words, then of those
  ;; but how do I know what sense/entry-number they are?
  )

(defn word->hypernym [word depth]
  ;; if depth is greater than count of hypernyms then just get last.
  )

;;; hard part here is diving into the dense tree and changing values 
(defn replace-words [treebank]
  )


