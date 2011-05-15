(ns twitalyse.core
  (:require [appengine-magic.core :as ae])
  (:use [twitalyse.twitter]))
'(ns twitalyse.core
  (:require [appengine-magic.core :as ae]))


(defn twitalyse-app-handler [request]
  {:status 200
   :headers {"Content-Type" "text/plain"}
   :body (str "Last tweet at " (java.util.Date.)  ".\n"
              '(first (map :text (latest-tweets 1)))
              "\n"
              "[powered by AppEngine and clojure]")})

(ae/def-appengine-app twitalyse-app #'twitalyse-app-handler)
