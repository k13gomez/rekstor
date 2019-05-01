(ns rekstor.core
  (:require [rekstor.codec :as codec]
            [rekstor.spec :as spec]
            [clojure.java.io :as io]
            [clojure.pprint :refer [print-table]])
  (:gen-class)
  (:import [java.time.format DateTimeFormatter]))

(def ^:private birth-date-formatter
  (DateTimeFormatter/ofPattern "M/d/yyyy"))

(defn- read-person-records
  "read person records from input file and format"
  [file format]
  (let [reader (io/reader file)
        lines (line-seq reader)
        decoder (partial codec/decode-person format)
        records (map decoder lines)]
    (filter spec/validate-person records)))

(defn- format-person-record
  "formats a person record for printing"
  [{:keys [birth-date] :as record}]
  (assoc record
    :birth-date (.format birth-date birth-date-formatter)))

(defn- print-person-records
  "prints person records in various formats"
  [file records]
  (let [sorted-by-gender-and-last-name-ascending (->> records
                                                   (sort-by (juxt :gender :last-name))
                                                   (map format-person-record))
        sorted-by-birth-date-ascending (->> records
                                         (sort-by :birth-date)
                                         (map format-person-record))
        sorted-by-last-name-descending (->> records
                                         (sort-by :last-name (comp (partial * -1) compare))
                                         (map format-person-record))
        columns [:last-name :first-name :gender :favorite-color :birth-date]]
    (println file "records sorted by :gender and :last-name ascending:")
    (print-table columns sorted-by-gender-and-last-name-ascending)
    (println)
    (println file "records sorted by :birth-date ascending:")
    (print-table columns sorted-by-birth-date-ascending)
    (println)
    (println file "records sorted by :last-name descending:")
    (print-table columns sorted-by-last-name-descending)
    (println)))

(defn -main
  [& {:as input}]
  (doseq [[file format] input]
    (let [records (read-person-records file format)]
      (print-person-records file records))))
