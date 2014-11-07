(ns crap.random)


(defn random-string [n]
  (apply str
    (take n
      (repeatedly #(rand-nth "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ")))))
