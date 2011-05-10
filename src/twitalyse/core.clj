(ns twitalyse.core
  (:require [appengine-magic.core :as ae]))


(defn twitalyse-app-handler [request]
  {:status 200
   :headers {"Content-Type" "text/plain"}
   :body "Hello from Twitalyse. [powered by AppEngine and clojure]"})

(ae/def-appengine-app twitalyse-app #'twitalyse-app-handler)
