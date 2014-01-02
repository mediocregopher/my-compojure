(ns my-compojure.core
  (:require [clojure.tools.reader.edn :as edn]
            [clojure.tools.cli :refer [cli]]
            [ring.adapter.jetty :refer [run-jetty]]))

(def config-atom (atom {}))

(defn conf-get
    "Given key you want from the config, returns that key. Key can be multiple layers deep, for
    instance: (cget :redis :host)"
    [& keys]
    (get-in @config-atom keys))

(defn conf-load-str
    "Given a string, loads it in as configuration"
    [config-str]
    (->> config-str
         (edn/read-string)
         (reset! config-atom)))

(defn conf-load-file
    "Given a filename, loads it in as configuration"
    [filename]
    (-> filename
        (slurp)
        (conf-load-str)))

(def default-config-msg
  "Using the default configuration. You can generate this config using the -d
  flag. You can change the configuration by piping the default one to a file,
  editing, and passing that file in using the -c flag")

(defn conf-load-cli
  "Given a description of the app and a default configuration (and the arguments
  passed in on the command-line) parses the arguments to try and load in a
  configuration file, output the default one to stdout and exit, or use the
  default one"
  [description default-config-str args]
  (let [[opts _ halp]
          (cli args ["-c" "--config" "Configuration file"]
                    ["-d" "--dump" "Dump default configuration to stdout"
                      :flag true]
                    ["-h" "--help" "Print help" :default false])]

    (cond
      (not (false? (opts :help))) (do (print description "\n\n" halp "\n")
                                      (flush)
                                      (System/exit 0))

      (opts :dump) (do (print default-config-str)
                       (flush)
                       (System/exit 0))

      (get opts :config false) (conf-load-file (opts :config))
      :else
        (do
          (println default-config-msg)
          (flush)
          (conf-load-str default-config-str)))))

(defn run
  "Runs the compojure app with the given jetty conf
  (http://mmcgrana.github.io/ring/ring.adapter.jetty.html)"
  [app conf]
  (run-jetty app conf))
