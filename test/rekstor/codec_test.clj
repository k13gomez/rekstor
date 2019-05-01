(ns rekstor.codec-test
  (:require [clojure.test :refer :all]
            [rekstor.codec :refer :all]
            [rekstor.data :refer [->Person]])
  (:import [java.time LocalDate]))

(deftest to-local-date-test
  (testing "validate to-local-date"
    (is (= (to-local-date "2018-01-01") (LocalDate/parse "2018-01-01")))
    (is (nil? (to-local-date nil)))
    (is (nil? (to-local-date "Jan 1st, 2018")))))

(deftest to-person-test
  (testing "validate to-person"
    (let [dob (LocalDate/parse "2018-01-01")
          expected (->Person "Smith" "John" "M" "Blue" dob)]
      (is (= (to-person "Smith" "John" "M" "Blue" "2018-01-01") expected))
      (is (not= (to-person) expected)))))

(deftest decode-person-test
  (testing "validate decode person record"
    (let [expected (->Person "Smith" "John" "M" "Blue" (LocalDate/parse "2018-01-01"))]
      (is (= (decode-person "," "Smith, John, M, Blue, 2018-01-01") expected))
      (is (= (decode-person "|" "Smith | John | M | Blue | 2018-01-01") expected))
      (is (= (decode-person " " "Smith John M Blue 2018-01-01") expected))
      (is (not= (decode-person "," "Smith, John, M, Blue") expected)))))