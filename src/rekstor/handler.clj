(ns rekstor.handler
  (:gen-class)
  (:require [rekstor.codec :as codec]
            [rekstor.spec :as spec]
            [clojure.spec.alpha :as s]
            [compojure.api.sweet :refer :all]
            [ring.util.http-response :refer :all]
            [ring.adapter.jetty :refer [run-jetty]]))

(def ^:private state
  (atom []))

(defn api-routes
  "create application routes"
  [state]
  (api
    {:swagger
     {:ui "/"
      :spec "/swagger.json"
      :data {:info {:title "rekstor api"
                    :description "person record store api"}
             :tags [{:name "records", :description "record apis"}]}}}

    (context "/records" []
      :tags ["records"]

      (GET "/gender" []
        :summary "returns records sorted by gender"
        (ok (sort-by :gender @state)))

      (GET "/birthdate" []
        :summary "returns records sorted by birth-date"
        (ok (sort-by :birth-date @state)))

      (GET "/name" []
        :summary "returns records sorted by name"
        (ok (sort-by (juxt :last-name :first-name) @state)))

      (POST "/" []
        :body-params [line :- String, format :- String]
        :summary "stores a record"
        (let [record (codec/decode-person format line)]
          (if (s/valid? ::spec/person record)
            (do
              (swap! state conj record)
              (ok record))
            (let [error (str (s/explain-str ::spec/person record))]
              (-> (bad-request error)
                (header "Content-Length" (count error))
                (header "Content-Type" "text/plain")))))))))

(def app
  (api-routes state))

(defn -main
  [& _args]
  (run-jetty app {:port (Integer/valueOf (or (System/getenv "PORT") "80"))}))