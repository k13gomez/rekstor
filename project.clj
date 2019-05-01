(defproject rekstor "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [metosin/compojure-api "1.1.11"]
                 [compojure "1.6.1"]
                 [ring/ring-defaults "0.3.2"]]
  :ring {:handler rekstor.handler/app}
  :uberjar-name "rekstor.jar"
  :profiles {:dev {:dependencies [[javax.servlet/javax.servlet-api "3.1.0"]
                                  [ring/ring-mock "0.3.2"]]
                   :plugins [[lein-ring "0.12.5"]
                             [lein-cloverage "1.1.1"]]}
             :uberjar {:aot :all}}
  :main ^:skip-aot rekstor.core
  :target-path "target/%s")
