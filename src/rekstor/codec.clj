(ns rekstor.codec
  (:require [rekstor.spec :as spec]
            [rekstor.data :refer [->Person]]
            [clojure.spec.alpha :as s]
            [clojure.string :as string])
  (:import [java.time LocalDate]
           [java.time.format DateTimeParseException]))

(def pipe-delimited-format
  :pipe-delimited)

(def comma-delimited-format
  :comma-delimited)

(def space-delimited-format
  :space-delimited)

(def format-to-pattern
  {pipe-delimited-format #"\|"
   comma-delimited-format #","
   space-delimited-format #" "})

(defn to-local-date
  "parses a local date in the format yyyy-MM-dd or
  returns nil if blank or invalid"
  [^String str]
  (when (s/valid? ::spec/non-empty-string str)
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
  "converts a line of text by splitting the fields using the split pattern,
  then converts it to a Person record"
  [^String text format]
  (->> (format-to-pattern format)
    (string/split text)
    (map string/trim)
    (apply to-person)))