(ns twitalyse.twitter
  (:import [twitter4j TwitterFactory Query])
  (:use [midje.sweet]))

(defn group-by-count
  "Takes a seq and return a map of {values, count}"
  [s]
  (reduce #(assoc %1 %2 (if-let [cnt (%1 %2)] (inc cnt) 1)) {} s))

(fact "group-by-count"
  (group-by-count ["a" "a" "b" "a"]) => {"a" 3
                                         "b" 1})

(defn make-query
  "Build the query for a hashtag and a page number"
  [hashtag pagenumber]
  (doto (Query. (str "#" hashtag))
    (.setRpp 100)
    (.setPage pagenumber)))

(fact "make-query" ;; java not easy to test
  (let [q (make-query "test" 10)]
    (.getPage q) => 10
    (.getRpp q) => 100
    (.getQuery q) => "#test"))

(defn raw-results
  "Raw results from twitter"
  [hashtag pagenumber]
  (.search (.getInstance (TwitterFactory.))
           (make-query hashtag pagenumber)))

(defn results-page
  "Reworked results, so we have a count of tweets by user"
  [hashtag pagenumber]
  (map #(.getFromUser %)
       (.getTweets (raw-results hashtag pagenumber))))

(defn results
  "Given a hashtag, return the seq list of pair [user count-tweets-hashtag]"
  [hashtag]
  (flatten
   (take-while seq
               (map #(results-page hashtag %)
                    (iterate inc 1)))))

;; use to play with the repl
'(ns user
   (:import (twitter4j TwitterFactory Query))
   (:require [twitalyse.test.twitter])
  (:use [twitalyse.twitter])
  (:use [clojure.test :only [run-tests]])
  (:use [clojure.contrib.repl-utils :only [show]]))

