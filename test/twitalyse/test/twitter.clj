(ns twitalyse.test.twitter
  (:use [twitalyse.twitter])
  (:use [clojure.test]))

(deftest test-group-by-count
  (is (= {"a" 1 "b" 2} (group-by-count ["b" "a" "b"]))))
