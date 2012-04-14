(ns twitalyse.twitter
  (:import [twitter4j TwitterFactory Query])
  (:use [midje.sweet]))

(fact "frequencies"
  (frequencies ["a" "a" "b" "a"]) => {"a" 3 "b" 1})

(defn make-query
  "Build the query for a hashtag and a page number"
  [hashtag pagenumber]
  (doto (Query. (str "#" hashtag))
    (.setRpp 100)
    (.setPage pagenumber)))

(fact "make-query"
  (bean (make-query "test" 10)) => {:rpp 100, :until nil, :class twitter4j.Query, :page 10, :locale nil,
                                    :geocode nil, :lang nil, :since nil, :maxId -1, :resultType nil,
                                    :query "#test", :sinceId -1})

(defn raw-results
  "Given a hashtag and a page number, return the raw results."
  [hashtag pagenumber]
  (.search (.getInstance (TwitterFactory.))
           (make-query hashtag pagenumber)))

(defn results-page
  "Given a hashtag and a page number, return the count of tweets by users on this page number"
  [hashtag pagenumber]
  (map #(.getFromUser %)
       (.getTweets (raw-results hashtag pagenumber))))

(fact
  (results-page "ecole" 1) => nil)

(defn results
  "Given a hashtag, return all the results for this hashtag (aggregate all the pages)."
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

