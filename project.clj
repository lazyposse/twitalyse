(defproject twitalyse "1.0.0-SNAPSHOT"
  :description "Analyse your tweets"
  :dependencies [[org.clojure/clojure "1.3.0"]
                 [org.twitter4j/twitter4j-core "[2.1,)"]
                 [midje "1.3.1"]]
  :dev-dependencies [[appengine-magic "0.4.1"]
                     [lein-midje "1.0.9"]
                     [com.intelie/lazytest "1.0.0-SNAPSHOT" :exclusions [swank-clojure]]
                     [lein-marginalia "0.7.0"]])

{:ok [0 127]
 :ko [128]}
