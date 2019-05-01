(ns rekstor.spec
  (:require [clojure.spec.alpha :as s]
            [clojure.string :as string])
  (:import [java.time LocalDate]))

(s/def ::non-empty-string (s/and string? (comp not string/blank?)))

(s/def ::first-name ::non-empty-string)

(s/def ::last-name ::non-empty-string)

(s/def ::gender #{"M" "F"})

(s/def ::favorite-color ::non-empty-string)

(s/def ::date-of-birth
  (partial instance? LocalDate))

(s/def ::person
  (s/keys :req-un [::first-name
                   ::last-name
                   ::gender
                   ::favorite-color
                   ::date-of-birth]))

(def validate-person
  (partial s/valid? ::person))