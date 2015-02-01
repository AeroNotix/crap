(ns crap.control)


(defn retry* [times wait-period fun]
  (when (zero? times)
    (throw (Exception. "Max retries exceeded.")))
  (when-not (fun)
    (Thread/sleep wait-period)
    (recur (dec times) wait-period fun)))

(defmacro retry [{:keys [retries wait-period]} & body]
  `(retry* ~retries ~wait-period (fn [] ~@body)))

(defmacro with-ns
  ;; Taken from clojure.contrib
  "Evaluates body in another namespace.  ns is either a namespace
  object or a symbol.  This makes it possible to define functions in
  namespaces other than the current one."
  [ns & body]
  `(binding [*ns* (the-ns ~ns)]
     ~@(map (fn [form] `(eval '~form)) body)))

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

(defn throttler [max in-period]
  (let [last (ref 0)
        count (ref 0)
        throttled? (ref false)]
    (fn []
      (dosync
        (let [this-time (System/currentTimeMillis)]
          (alter count inc)
          (if (< (- this-time @last) in-period)
            (ref-set throttled? (>= @count max))
            (do
              (ref-set count 0)
              (ref-set throttled? false)))
          (ref-set last this-time)
          @throttled?)))))
