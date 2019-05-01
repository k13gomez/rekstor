(ns rekstor.core-test
  (:require [clojure.test :refer :all]
            [rekstor.core :refer :all]
            [clojure.data :refer [diff]]
            [clojure.string :as string]))

(deftest test-core-main-end-to-end
  (testing "test core main class comma delimited"
    (let [actual-output
                          (string/trim
                            (with-out-str
                              (-main "comma-delimited.txt" ",")))
          expected-output (string/trim
                            (slurp "comma-delimited-expected-output.txt"))]
      (is (= actual-output expected-output))))
  (testing "test core main class pipe delimited"
    (let [actual-output
                          (string/trim
                            (with-out-str
                              (-main "pipe-delimited.txt" "\\|")))
          expected-output (string/trim
                            (slurp "pipe-delimited-expected-output.txt"))]
      (is (= actual-output expected-output))))
  (testing "test core main class space delimited"
    (let [actual-output
                          (string/trim
                            (with-out-str
                              (-main "space-delimited.txt" " ")))
          expected-output (string/trim
                            (slurp "space-delimited-expected-output.txt"))]
      (is (= actual-output expected-output)))))
