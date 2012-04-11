(ns twitalyse.twitter
  (:import [twitter4j TwitterFactory Query])
  (:use [midje.sweet]))

(defn group-by-count
  "Takes a seq and return a map of {values, count}"
  [s] (reduce #(assoc %1 %2 (if-let [cnt (%1 %2)]
                              (inc cnt)
                              1))
              {}
              s))

(fact "group-by-count"
  (group-by-count ["a" "a" "b" "a"]) => {"a" 3
                                         "b" 1})

;; raw results from twitter
(def raw-results (.search (.getInstance (TwitterFactory.))
                          (Query. "#sfeir")))



;; reworked results, so we have a count of the tweets by user
(def results (group-by-count
              (map #(.getFromUser %)
                   (.getTweets raw-results))))


;; use to play with the repl
'(ns user
   (:import (twitter4j TwitterFactory Query))
   (:require [twitalyse.test.twitter])
  (:use [twitalyse.twitter])
  (:use [clojure.test :only [run-tests]])
  (:use [clojure.contrib.repl-utils :only [show]]))

