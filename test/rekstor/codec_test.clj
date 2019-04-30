(ns rekstor.codec-test
  (:require [clojure.test :refer :all]
            [rekstor.codec :refer :all])
  (:import [java.time LocalDate]))

(deftest to-local-date-test
  (testing "validate to-local-date"
    (is (= (to-local-date "2018-01-01") (LocalDate/parse "2018-01-01")))
    (is (nil? (to-local-date nil)))
    (is (nil? (to-local-date "Jan 1st, 2018")))))
