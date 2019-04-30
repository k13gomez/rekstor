(ns rekstor.codec
  (:require [rekstor.spec :as spec]
            [clojure.spec.alpha :as s])
  (:import [java.time LocalDate]
           [java.time.format DateTimeParseException]))

(defn to-local-date
  "parses a local date in the format yyyy-MM-dd or
  returns nil if blank or invalid"
  [^String str]
  (when (s/valid? ::spec/non-empty-string str)
    (try
      (LocalDate/parse str)
      (catch DateTimeParseException _
        nil))))