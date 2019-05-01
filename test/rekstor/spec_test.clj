(ns rekstor.spec-test
  (:require [clojure.test :refer :all]
            [rekstor.spec :as spec]
            [rekstor.data :refer [->Person]]
            [clojure.spec.alpha :refer [valid?]])
  (:import [java.time LocalDate]))

(deftest spec-test
  (testing "validate non-empty-string"
    (is (valid? ::spec/non-empty-string "foobar"))
    (is (not (valid? ::spec/non-empty-string nil)))
    (is (not (valid? ::spec/non-empty-string "")))
    (is (not (valid? ::spec/non-empty-string (Object.)))))

  (testing "validate first-name"
    (is (valid? ::spec/non-empty-string "John"))
    (is (not (valid? ::spec/non-empty-string nil)))
    (is (not (valid? ::spec/non-empty-string "")))
    (is (not (valid? ::spec/non-empty-string (Object.)))))

  (testing "validate last-name"
    (is (valid? ::spec/non-empty-string "Smith"))
    (is (not (valid? ::spec/non-empty-string nil)))
    (is (not (valid? ::spec/non-empty-string "")))
    (is (not (valid? ::spec/non-empty-string (Object.)))))

  (testing "validate gender"
    (is (valid? ::spec/gender "F"))
    (is (valid? ::spec/gender "M"))
    (is (not (valid? ::spec/gender nil)))
    (is (not (valid? ::spec/gender "")))
    (is (not (valid? ::spec/gender "Z")))
    (is (not (valid? ::spec/gender (Object.)))))

  (testing "validate favorite-color"
    (is (valid? ::spec/favorite-color "Blue"))
    (is (not (valid? ::spec/gender nil)))
    (is (not (valid? ::spec/gender "")))
    (is (not (valid? ::spec/gender (Object.)))))

  (testing "validate birth-date"
    (is (valid? ::spec/birth-date (LocalDate/parse "2000-02-03")))
    (is (not (valid? ::spec/birth-date nil)))
    (is (not (valid? ::spec/birth-date "2018-01-01")))))

(deftest person-spec-test
  (testing "validate person"
    (let [rec (->Person "Smith" "John" "M" "Green", (LocalDate/parse "1999-01-01"))]
      (is (valid? ::spec/person rec))
      (is (true? (spec/validate-person rec)))
      (is (not (valid? ::spec/person nil)))
      (is (false? (spec/validate-person nil)))
      (is (not (valid? ::spec/person {})))
      (is (false? (spec/validate-person {}))))))