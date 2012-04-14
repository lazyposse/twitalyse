(ns twitalyse.twitter
  (:import [twitter4j TwitterFactory Query])
  (:use [midje.sweet]))

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
  "Given a hashtag and a page number, return the users that tweets with this hashtag for this page."
  [hashtag pagenumber]
  (map #(.getFromUser %)
       (.getTweets (raw-results hashtag pagenumber))))

;; As the twitter api limits to 5 days, this will only count the results for these 5 days.
;; Furthermore, as there is pagination, this function may take some time as this will query
;; as long as there is page, then aggregate the results.

(defn results
  "Given a hashtag, return the number of tweets per user that tweet this hashtag."
  [hashtag]
  (frequencies
   (flatten
    (take-while seq
                (map #(results-page hashtag %)
                     (iterate inc 1))))))

(fact "results"
  (results :some-hashtag) => {:user1 3
                              :user2 2
                              :user3 1}
  (provided
    (results-page :some-hashtag 1) => [:user1 :user1 :user2]
    (results-page :some-hashtag 2) => [:user1 :user2 :user3]))

;; use to play with the repl
'(ns user
   (:import (twitter4j TwitterFactory Query))
   (:require [twitalyse.test.twitter])
  (:use [twitalyse.twitter])
  (:use [clojure.test :only [run-tests]])
  (:use [clojure.contrib.repl-utils :only [show]]))

