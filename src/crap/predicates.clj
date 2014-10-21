(ns crap.predicates)


(defn byte-array? [obj]
  (= Byte/TYPE (.getComponentType (class obj))))

(defn numeric? [s]
  (try
    (bigdec s)
    true
    (catch NumberFormatException e false)
    (catch IllegalArgumentException e false)))

(def not-nil? (complement nil?))

(defn seq-not-nil?
  [seek]
  (every? not-nil? seek))

(defmulti uuid? class)

(defmethod uuid? java.util.UUID [_] true)

(defmethod uuid? String [uuid]
  (try
    (let [_ (java.util.UUID/fromString uuid)]
      true)
    (catch IllegalArgumentException e
      false)))

(defmethod uuid? :default [_] false)
