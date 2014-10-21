(ns crap.exceptions
  (:require [clojure.tools.logging :as log]))


(defn log-stack-trace []
  (let [st (with-out-str
             (mapv #(print (str %) " ")
               (rest (.. (Thread/currentThread) (getStackTrace)))))]
    (log/warn st)))
