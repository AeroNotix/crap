(ns crap.agents)


(defn exception-throttler [{:keys [max-restarts max-time]}]
  (let [max-time (or max-time 5000)
        exception-count (atom 0)
        thrown?         (atom false)
        last-exception  (atom 0)]
    (fn [an-agent ex]
      (when thrown? (throw ex))
      (swap! exception-count inc)
      (if (<= @exception-count max-restarts)
        (do
          (restart-agent an-agent @an-agent)
          (reset! last-exception (System/currentTimeMillis)))
        (do
          (let [this-time (System/currentTimeMillis)]
            (if (< (- this-time @last-exception) max-time)
              (reset! thrown? true)
              (do
                (reset! exception-count 0)
                (reset! last-exception this-time)
                (restart-agent an-agent @an-agent)))))))))
