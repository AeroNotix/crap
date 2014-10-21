(ns crap.io
  (:require [crap.predicates :refer [byte-array?]]))


(defmulti io-reader
  "Coerces things into a java.io.Reader"
  (fn [obj]
    (if (byte-array? obj)
      :byte-array
      (throw (UnsupportedOperationException.
               "Don't know how to convert to java.io.Reader")))))

(defmethod io-reader :byte-array [ba]
  "Implementation for a [B (ByteArray)"
  (-> ba
    (java.io.ByteArrayInputStream.)
    (java.io.InputStreamReader.)))
