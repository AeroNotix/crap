(ns crap.logging
  (:require [crap.exceptions :refer [log-stack-trace]]))


(defmacro logging-future [& body]
  `(future
     (try
       ~body
       (catch Exception e#
         (log-stack-trace)
         (log/info "Error while waiting for future:" (str e#))))))

