(ns kixi.hecuba.dcc.tariffs.parser
  (:require [clojure.data.xml :as xml]
            [com.rpl.specter :as specter]))

(defn- has-tag?
  [tag element]
  (= (:tag element) tag))

(defn- parse
  [data-in]
  (xml/parse (java.io.StringReader. data-in)))

(defn process
  "parse measurement xml and extract relevant data"
  [data-in]
  (let [parsed (parse data-in)]
    parsed))
