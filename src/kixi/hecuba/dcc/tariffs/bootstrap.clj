(ns kixi.hecuba.dcc.tariffs.bootstrap
  (:require [kixi.hecuba.dcc.tariffs.processor :as m]
            [taoensso.timbre :as log])
  (:gen-class))

(defn -main [& args]
  (log/info "Starting Kafka Stream Backup")
  (.start (m/start-stream)))
