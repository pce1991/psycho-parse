(ns psycho-parse.core
  (:gen-class))

;;; use opennlp to get a parse of the sentence, then go to each word, get its
;;; WN entry, and then find its hypernyms and replace each word with it to
;;; the requested depth, so you could get "the dog ran" as "the canine moved"
;;; or "the animal acted" 

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
