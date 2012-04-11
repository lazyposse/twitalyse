(ns twitalyse.core
  (:require [appengine-magic.core :as ae])
  (:require [twitalyse.twitter :as twitter]))


(defn twitalyse-app-handler [request]
  {:status 200
   :headers {"Content-Type" "text/plain"}
   :body (str
          (java.util.Date.)
          "\nBest #sfeir twitters (last 5-7 days):\n"
          (twitter/results "sfeir")
          "\n\n"
          " [powered by AppEngine and clojure]")})

(ae/def-appengine-app twitalyse-app #'twitalyse-app-handler)
