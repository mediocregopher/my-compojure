(ns my-compojure.example
  (:gen-class)
  (:require [my-compojure.core :refer [conf-load-cli conf-get run]]
            [compojure.core :refer :all]
            compojure.handler
            compojure.route))

(def description "An example usage for my-compojure")

(def default-config-str "
;; This is an example configuration for a simple compojure app. It contains all
;; the default values for the various options, change them as needed.
{

    ;; Options for the HTTP REST API (OMG CAPS). These are the most common ones
    ;; that'll be used, you can find a full list of available options at:
    ;; http://mmcgrana.github.io/ring/ring.adapter.jetty.html
    :rest {
            :host \"0.0.0.0\"
            :port 3000
          }

}
")

(def app
  (compojure.handler/api
    (routes
      (GET "/" [] "Hello World!")
      (compojure.route/not-found "Not found :("))))

(defn -main [& args]
  (conf-load-cli description default-config-str args)
  (run app (conf-get :rest)))
