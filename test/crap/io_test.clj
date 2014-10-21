(ns crap.io-test
  (:require [crap.io :refer :all]
            [clojure.test :refer :all]))

(deftest unknown-io-reader
  "Can't get an io reader on any random object."
  (is (thrown? UnsupportedOperationException (io-reader "banana"))))
