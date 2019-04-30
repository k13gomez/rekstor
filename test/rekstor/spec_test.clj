(ns rekstor.spec-test
  (:require [clojure.test :refer :all]
            [rekstor.spec :as spec]
            [rekstor.data :refer [->Person]]
            [clojure.spec.alpha :as s])
  (:import [java.time LocalDate]))

(deftest spec-test
  (testing "validate non-empty-string"
    (is (s/valid? ::spec/non-empty-string "foobar"))
    (is (not (s/valid? ::spec/non-empty-string nil)))
    (is (not (s/valid? ::spec/non-empty-string "")))
    (is (not (s/valid? ::spec/non-empty-string (Object.)))))

  (testing "validate first-name"
    (is (s/valid? ::spec/non-empty-string "John"))
    (is (not (s/valid? ::spec/non-empty-string nil)))
    (is (not (s/valid? ::spec/non-empty-string "")))
    (is (not (s/valid? ::spec/non-empty-string (Object.)))))

  (testing "validate last-name"
    (is (s/valid? ::spec/non-empty-string "Smith"))
    (is (not (s/valid? ::spec/non-empty-string nil)))
    (is (not (s/valid? ::spec/non-empty-string "")))
    (is (not (s/valid? ::spec/non-empty-string (Object.)))))

  (testing "validate gender"
    (is (s/valid? ::spec/gender "F"))
    (is (s/valid? ::spec/gender "M"))
    (is (not (s/valid? ::spec/gender nil)))
    (is (not (s/valid? ::spec/gender "")))
    (is (not (s/valid? ::spec/gender "Z")))
    (is (not (s/valid? ::spec/gender (Object.)))))

  (testing "validate favorite-color"
    (is (s/valid? ::spec/favorite-color "Blue"))
    (is (not (s/valid? ::spec/gender nil)))
    (is (not (s/valid? ::spec/gender "")))
    (is (not (s/valid? ::spec/gender (Object.)))))

  (testing "validate date-of-birth"
    (is (s/valid? ::spec/date-of-birth (LocalDate/parse "2000-02-03")))
    (is (not (s/valid? ::spec/date-of-birth nil)))
    (is (not (s/valid? ::spec/date-of-birth "2018-01-01")))))

(deftest person-spec-test
  (testing "validate person"
    (is (s/valid? ::spec/person
          (->Person "Smith" "John" "M" "Green", (LocalDate/parse "1999-01-01"))))
    (is (not (s/valid? ::spec/person nil)))
    (is (not (s/valid? ::spec/person {})))))