(ns twitalyse.twitter
  (use [clojure.pprint :only [pprint]])
  (import (twitter4j TwitterFactory Query)))

(defn group-by-count
  "Takes a seq and return a map of {values, count}"
  [s] (reduce #(assoc %1 %2 (if-let [cnt (%1 %2)]
                              (inc cnt)
                              1))
              {}
              s))

(defn make-query
  [hashtag pagenumber] (doto (Query. (str "#" hashtag))
                         (.setRpp 100)
                         (.setPage pagenumber)))

;; raw results from twitter
(defn raw-results
  [hashtag pagenumber] (.search (.getInstance (TwitterFactory.))
                                (make-query hashtag pagenumber)))

;; reworked results, so we have a count of the tweets by user
(defn results-page [hashtag pagenumber]
  (map #(.getFromUser %)
       (.getTweets (raw-results hashtag pagenumber))))

(defn results
  [hashtag] (flatten (take-while seq
                                 (map #(results-page hashtag %)
                                      (iterate inc 1)))))

;; use to play with the repl
'(ns user
   (:import (twitter4j TwitterFactory Query))
   (:require [twitalyse.test.twitter])
  (:use [twitalyse.twitter])
  (:use [clojure.test :only [run-tests]])
  (:use [clojure.contrib.repl-utils :only [show]]))

