(ns crap.logging
  (:require [crap.exceptions :refer [log-stack-trace]]
            [metrics.histograms :refer [update!]]))


(defmacro logging-future [& body]
  `(future
     (try
       ~@body
       (catch Exception e#
         (log-stack-trace)
         (log/info "Error while waiting for future:" (str e#))))))

(defmacro with-histogram [name & body]
  `(let [begin# (System/currentTimeMillis)]
     ~@body
     (update! ~name
       (- (System/currentTimeMillis) begin#))))
