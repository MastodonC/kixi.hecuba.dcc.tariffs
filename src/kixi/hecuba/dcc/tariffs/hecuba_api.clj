(ns kixi.hecuba.dcc.tariffs.hecuba-api
  (:require [cheshire.core :as json]
            [clj-http.client :as http]
            [taoensso.timbre :as timbre]))

(defn get-entities [{:keys [endpoint username password]} programme-id entity-type max-entries-per-page]
  (let [url-to-get (str endpoint
                        "entities/?q=property_type:\""
                        entity-type
                        "\" AND programme_id:"
                        programme-id
                        "&page=0&size="
                        max-entries-per-page
                        "&sort_key=programme_name.lower_case_sort&sort_order=asc")]
    (try (let [response-json (-> (:body (http/get
                                         url-to-get
                                         {:basic-auth [username
                                                       password]
                                          :headers {"X-Api-Version" "2"}
                                          :content-type :json
                                          :socket-timeout 20000
                                          :conn-timeout 20000}))
                                 (json/parse-string true))]
           response-json)
         (catch Exception e (timbre/error e)))))

(defn post-tariff
  "posts current tariff https://github.com/MastodonC/kixi.hecuba/blob/master/doc/api.md#tariff-examples"
  [{:keys [endpoint username password]} json-payload entity-id]
  (let [json-to-send (json/generate-string json-payload)
        endpoint (str endpoint "entities/" entity-id "/profiles/")]
    (timbre/infof "Using endpoint: %s" endpoint)

    (try (http/post
          endpoint
          {:basic-auth [username password]
           :body json-to-send
           :headers {"X-Api-Version" "2"}
           :content-type :json
           :socket-timeout 20000
           :conn-timeout 20000
           :accept "application/json"})
         (catch Exception e (doall (str "Caught Exception " (.getMessage e))
                                   (timbre/error e "> There was an error during the upload to entity " entity-id)))
         (finally {:message "post tariff complete."})))  )
