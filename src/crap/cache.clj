(ns crap.cache
  (:require [clojure.core.cache :as cache]))


(defmacro defncached
  "Creates a function which is cached for the specified time

  (defncached foo [x y z] 5000
    (Thread/sleep 1000)
    (println x y z)
    (rand))
  "
  [name args time & body]
  `(let [a-cache# (atom (cache/ttl-cache-factory {} :ttl ~time))]
     (defn ~name ~args
       (if (cache/has? @a-cache# ~args)
         (do
           (swap! a-cache# cache/hit ~args)
           (cache/lookup @a-cache# ~args))
         (let [ret# (do ~@body)]
           (reset! a-cache#
             (cache/miss @a-cache# ~args ret#))
           ret#)))))
