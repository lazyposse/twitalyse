(ns twitalyse.twitter
  (:require [twitalyse.secret :as secret])
  (:require [twitter :as twitter]
            [oauth.client :as oauth]))

;; Make a OAuth consumer
(def oauth-consumer
  (oauth/make-consumer "dbS2QoccyeGdIlFfPypmQw" ;; consumer key
                       secret/consumer-secret ;; consumer secret
                       "http://api.twitter.com/oauth/request_token"
                       "http://api.twitter.com/oauth/access_token"
                       "http://api.twitter.com/oauth/authorize"
                       :hmac-sha1))

(def oauth-access-token
  "16343678-qCTcjFpEnAcQuxukRMCcHd1xnHtNsCaCvqkqVutoI")
(def oauth-access-token-secret
  secret/oauth-access-token-secret)

(defn latest-tweets [count]
  (twitter/with-oauth
    oauth-consumer
    oauth-access-token
    oauth-access-token-secret
    (twitter/home-timeline :count (str count))))

(println "*****     " *ns* " loaded     *****")
