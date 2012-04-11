(ns twitalyse.test.twitter
  (:use [twitalyse.twitter]
        [midje.sweet]))

(fact "group-by-count"
  (group-by-count ["b" "a" "b"]) => {"a" 1 "b" 2})
