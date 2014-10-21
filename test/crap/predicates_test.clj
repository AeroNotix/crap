(ns crap.predicates-test
  (:require [crap.predicates :refer :all]
            [clojure.test :refer :all]))

(deftest seq-not-nil?-test
  (let [s (repeat 3 nil)
        ts (repeat 3 true)
        fs (repeat 3 false)
        both (interpose ts fs)]
    (is (not (seq-not-nil? s)))
    (is (seq-not-nil? ts))
    (is (seq-not-nil? fs))
    (is (seq-not-nil? both))))

(deftest numeric?-test
  "Testing if our numeric? function works."
  (is (= false (numeric? "banana")))
  (is (= false (numeric? "123banana")))
  (is (= false (numeric? "banana123")))
  (is (= false (numeric? ["123"])))
  (is (= true (numeric? "123120387128397123987123897")))
  (is (= true (numeric? "123")))
  (is (= true (numeric? "123123098712398172389123.1237912387123897123")))
  (is (= true (numeric? "1.0"))))

(deftest uuid?-test
  (let [a-uuid (java.util.UUID/randomUUID)
        str-uuid (.toString a-uuid)]
    (is (uuid? a-uuid))
    (is (uuid? str-uuid))
    (is (not (uuid? "banana")))
    (is (not (uuid? 123)))
    (is (not (uuid? [])))))
