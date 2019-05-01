(ns rekstor.spec
  (:require [clojure.spec.alpha :as s]
            [clojure.string :as string])
  (:import [java.time LocalDate]))

(s/def ::non-empty-string (s/and string? (comp not string/blank?)))

(s/def ::first-name ::non-empty-string)

(s/def ::last-name ::non-empty-string)

(s/def ::gender #{"M" "F"})

(s/def ::favorite-color ::non-empty-string)

(s/def ::birth-date
  (partial instance? LocalDate))

(s/def ::person
  (s/keys :req-un [::first-name
                   ::last-name
                   ::gender
                   ::favorite-color
                   ::birth-date]))

(def validate-person
  (partial s/valid? ::person))