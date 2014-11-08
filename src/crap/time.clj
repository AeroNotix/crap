(ns crap.time)


(defn right-now
  "Get the time right now in unix timestamp."
  []
  (quot (System/currentTimeMillis) 1000))
