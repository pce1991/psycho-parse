(ns psycho-parse.core
  (:require [opennlp.nlp :refer :all]
            [clj-wordnet.core :refer :all])
  (:gen-class))

;;; use opennlp to get a parse of the sentence, then go to each word, get its
;;; WN entry, and then find its hypernyms and replace each word with it to
;;; the requested depth, so you could get "the dog ran" as "the canine moved"
;;; or "the animal acted" 

(def get-sentences (make-sentence-detector "models/ent-sent.bin"))
(def tokenize (make-tokenizer "models/en-token.bin"))
(def pos-tag (mage-pos-tagger "models/en-pos-maxent.bin"))
(def chunker (make-treebank-chunker "models/en-chunker.bin"))




(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
