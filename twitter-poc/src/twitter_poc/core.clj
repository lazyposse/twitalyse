(ns twitter-poc.core
  (:require [twitter-poc.secret])
  (:require [twitter :as twitter]
            [oauth.client :as oauth]))

;; Make a OAuth consumer
(def oauth-consumer
  (oauth/make-consumer "dbS2QoccyeGdIlFfPypmQw" ;; consumer key
                       twitter-poc.secret/consumer-secret ;; consumer secret
                       "https://api.twitter.com/oauth/request_token"
                       "https://api.twitter.com/oauth/access_token"
                       "https://api.twitter.com/oauth/authorize"
                       :hmac-sha1))

(def oauth-access-token
  "16343678-qCTcjFpEnAcQuxukRMCcHd1xnHtNsCaCvqkqVutoI")
(def oauth-access-token-secret
  twitter-poc.secret/oauth-access-token-secret)

(defn latest-tweets [count]
  (twitter/with-https
    (twitter/with-oauth
      oauth-consumer
      oauth-access-token
      oauth-access-token-secret
      (twitter/home-timeline :count (str count)))))
