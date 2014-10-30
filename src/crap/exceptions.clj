(ns crap.exceptions
  (:require [clojure.tools.logging :as log]))


(defn log-stack-trace []
  (let [st (with-out-str
             (mapv #(print (str %) " ")
               (rest (.. (Thread/currentThread) (getStackTrace)))))]
    (log/warn st)))

(defn gen-exception-clause [exception as exception-clause]
  `(catch ~exception ~as
     ~exception-clause))

(defmacro with-exceptions [{:keys [exceptions as]} body exception-clause]
  `(try
     ~body
     ~@(mapv #(gen-exception-clause % as exception-clause) exceptions)))

(defn parse-try-body [body]
  (letfn [(catch-expr? [expr]
            (if (sequential? expr)
              (= 'catch (first expr))
              false))]
    (partition-by catch-expr? body)))

(defn gen-catch [f exceptions var & body]
  (if (sequential? exceptions)
    (mapcat #(apply gen-catch 'catch % var body) exceptions)
    `((catch ~exceptions ~var ~@body))))
        
(defmacro try+ [& body]
  (let [[code catch-code] (parse-try-body body)]
    `(try
       ~@code
       ~@(apply gen-catch (first catch-code)))))
