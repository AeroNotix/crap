(ns crap.exceptions-test
  (:require [crap.exceptions :refer :all]
            [clojure.test :refer :all]))

(deftest log-stack-trace-test
  (let [f1 (fn [] (log-stack-trace))
        f2 (fn [] (f1))
        f3 (fn [] (f2))
        f4 (fn [] (f3))
        f5 (fn [] (f4))]
    (f5)))

(deftest loop-exceptions-test
  (let [count (atom 0)]
    (is
      (thrown-with-msg?
        Exception #"foo"
        (loop-exceptions 0 0 0 50
          (swap! count inc)
          (throw (Exception. "foo")))))
    (is @count 50)))
