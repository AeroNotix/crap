(ns crap.control)


(defn retry* [times wait-period fun]
  (when (zero? times)
    (throw (Exception. "Max retries exceeded.")))
  (when-not (fun)
    (Thread/sleep wait-period)
    (recur (dec times) wait-period fun)))

(defmacro retry [{:keys [retries wait-period]} & body]
  `(retry* ~retries ~wait-period (fn [] ~@body)))
