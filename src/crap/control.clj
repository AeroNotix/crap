(ns crap.control)


(defn retry* [times wait-period fun]
  (when (zero? times)
    (throw (Exception. "Max retries exceeded.")))
  (when-not (fun)
    (Thread/sleep wait-period)
    (recur (dec times) wait-period fun)))

(defmacro retry [{:keys [retries wait-period]} & body]
  `(retry* ~retries ~wait-period (fn [] ~@body)))

(defmacro with-temp-ns
  ;; Taken from clojure.contrib
  "Evaluates body in an anonymous namespace, which is then immediately
  removed.  The temporary namespace will 'refer' clojure.core."
  [& body]
  `(try
    (create-ns 'sym#)
    (let [result# (with-ns 'sym#
                    (clojure.core/refer-clojure)
                    ~@body)]
      result#)
    (finally (remove-ns 'sym#))))
