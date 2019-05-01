(ns rekstor.handler-test
  (:require [clojure.test :refer :all]
            [rekstor.handler :refer :all]
            [cheshire.core :as json]
            [ring.mock.request :as mock]))

(defn- slurp-body
  [{:keys [body] :as ring-map}]
  (assoc ring-map
    :body (slurp body)))

(deftest your-json-handler-test
  (let [state (atom [])
        handler (api-routes state)
        req1-line "Gomez, Jose, M, Green, 1987-04-12"
        req1-body {:last-name "Gomez",
                   :first-name "Jose",
                   :gender "M",
                   :favorite-color "Green",
                   :birth-date "1987-04-12"}
        req2-line "Smith, Jane, F, Blue, 2000-02-01"
        req2-body {:last-name "Smith",
                   :first-name "Jane",
                   :gender "F",
                   :favorite-color "Blue",
                   :birth-date "2000-02-01"}
        req3-line "Seymour, Jane, F, Blue, 2000-02-01"
        req3-body {:last-name "Seymour",
                   :first-name "Jane",
                   :gender "F",
                   :favorite-color "Blue",
                   :birth-date "2000-02-01"}
        invalid-line "Gomez, Jose, Z, Green, 1987-04-12"]
    (testing "post various valid records"
      (is (= (-> (mock/request :post "/records")
               (mock/json-body {:line req1-line :format ","})
               (handler)
               (slurp-body))
            (let [expected-body (json/generate-string req1-body)]
              {:status  200
               :headers {"Content-Type" "application/json; charset=utf-8"
                         "Content-Length" (-> expected-body count str)}
               :body expected-body})))
      (is (= (-> (mock/request :post "/records")
               (mock/json-body {:line req2-line :format ","})
               (handler)
               (slurp-body))
            (let [expected-body (json/generate-string req2-body)]
              {:status  200
               :headers {"Content-Type" "application/json; charset=utf-8"
                         "Content-Length" (-> expected-body count str)}
               :body expected-body})))
      (is (= (-> (mock/request :post "/records")
               (mock/json-body {:line req3-line :format ","})
               (handler)
               (slurp-body))
            (let [expected-body (json/generate-string req3-body)]
              {:status  200
               :headers {"Content-Type" "application/json; charset=utf-8"
                         "Content-Length" (-> expected-body count str)}
               :body expected-body})))
      (is (= 3 (count @state))))

    (testing "post an invalid record"
      (is (= (-> (mock/request :post "/records")
               (mock/json-body {:line invalid-line :format ","})
               (handler))
            (let [expected-body "\"Z\" - failed: #{\"M\" \"F\"} in: [:gender] at: [:gender] spec: :rekstor.spec/gender\n"]
              {:status  400
               :headers {"Content-Type" "text/plain"
                         "Content-Length" (-> expected-body count str)}
               :body expected-body})))
      (is (= 3 (count @state))))

    (testing "get records sorted by gender"
      (is (= (-> (mock/request :get "/records/gender")
               (handler)
               (slurp-body))
            (let [expected-body (json/generate-string [req2-body req3-body req1-body])]
              {:status  200
               :headers {"Content-Type" "application/json; charset=utf-8"
                         "Content-Length" (-> expected-body count str)}
               :body expected-body}))))

    (testing "get records sorted by birth-date"
      (is (= (-> (mock/request :get "/records/birthdate")
               (handler)
               (slurp-body))
            (let [expected-body (json/generate-string [req1-body req2-body req3-body])]
              {:status  200
               :headers {"Content-Type" "application/json; charset=utf-8"
                         "Content-Length" (-> expected-body count str)}
               :body expected-body}))))

    (testing "get records sorted by name"
      (is (= (-> (mock/request :get "/records/name")
               (handler)
               (slurp-body))
            (let [expected-body (json/generate-string [req1-body req3-body req2-body])]
              {:status  200
               :headers {"Content-Type" "application/json; charset=utf-8"
                         "Content-Length" (-> expected-body count str)}
               :body expected-body}))))))