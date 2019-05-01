(ns rekstor.data-test
  (:require [clojure.test :refer :all]
            [rekstor.data :refer :all])
  (:import [java.time LocalDate]))

(deftest person-record-test
  (testing "validate person record"
    (let [dob (LocalDate/parse "2018-01-01")
          person (->Person "Smith" "Jane" "F" "Blue" dob)]
      (is (= (:last-name person) "Smith"))
      (is (= (:first-name person) "Jane"))
      (is (= (:gender person) "F"))
      (is (= (:favorite-color person) "Blue"))
      (is (= (:date-of-birth person) dob)))))
