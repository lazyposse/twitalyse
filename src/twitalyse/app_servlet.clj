(ns twitalyse.app_servlet
  (:gen-class :extends javax.servlet.http.HttpServlet)
  (:use twitalyse.core)
  (:use [appengine-magic.servlet :only [make-servlet-service-method]]))


(defn -service [this request response]
  ((make-servlet-service-method twitalyse-app) this request response))
