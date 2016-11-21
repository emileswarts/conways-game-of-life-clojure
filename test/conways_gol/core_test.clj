(ns conways-gol.core-test
  (:require [clojure.test :refer :all]
            [conways-gol.core :refer :all]))

(deftest tick-cell-test
  (testing "Fewer than 2 neighbors"
    (let [cell { :x 0 :y 0 :state 1 :neighbors [0 0 1]}]
    (is (= 0 ((tick-cell cell) :state)))))
  (testing "2 neighbors"
    (let [cell { :x 0 :y 0 :state 1 :neighbors [0 0 1 1]}]
    (is (= 1 ((tick-cell cell) :state)))))
  (testing "3 neighbors"
    (let [cell { :x 0 :y 0 :state 1 :neighbors [0 1 1 1]}]
    (is (= 1 ((tick-cell cell) :state)))))
  (testing "More than 3 neighbors"
    (let [cell { :x 0 :y 0 :state 1 :neighbors [1 1 1 1]}]
    (is (= 0 ((tick-cell cell) :state))))))

(deftest rendered-world-test
  (testing "Render world grid"
    (let [cells [{ :x 0 :y 0 :state 1}]
          world-dimensions {:x 5 :y 5}
          expected-result [
                           { :x 0 :y 0 :neighbors [0 0 0] :state 1}
                           { :x 1 :y 0 :neighbors [1 0 0 0 0] :state 0}
                           { :x 0 :y 1 :neighbors [1 0 0 0 0] :state 0}
                           { :x 1 :y 1 :neighbors [1 0 0 0 0 0 0 0] :state 0}
                           ]]
      (is (= expected-result (rendered-world cells world-dimensions))))))
