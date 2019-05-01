(ns rekstor.codec
  (:require [rekstor.spec :as spec]
            [rekstor.data :refer [->Person]]
            [clojure.spec.alpha :refer [valid?]]
            [clojure.string :as string])
  (:import [java.time LocalDate]
           [java.time.format DateTimeParseException]))

(defn to-local-date
  "parses a local date in the format yyyy-MM-dd or
  returns nil if blank or invalid"
  [^String str]
  (when (valid? ::spec/non-empty-string str)
    (try
      (LocalDate/parse str)
      (catch DateTimeParseException _
        nil))))

(defn to-person
  "creates a Person record from last-name, first-name, gender, favorite-color and date-of-birth"
  [& [last-name first-name gender favorite-color date-of-birth]]
  (->> (to-local-date date-of-birth)
    (->Person last-name first-name gender favorite-color)))

(defn decode-person
  "converts a line of text by splitting the fields using the split format,
  then converts it to a Person record"
  [format ^String text]
  (->> (str "\\" format)
    (re-pattern)
    (string/split text)
    (map string/trim)
    (apply to-person)))