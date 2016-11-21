(ns conways-gol.core-test
  (:require [clojure.test :refer :all]
            [conways-gol.core :refer :all]))

(deftest cell-state-test
  (testing "Fewer than 2 neighbors"
    (let [cell { :x 0 :y 0 :state 1 :neighbors [0 0 1]}]
    (is (= :dead (cell-state cell)))))
  (testing "2 neighbors"
    (let [cell { :x 0 :y 0 :state 1 :neighbors [0 0 1 1]}]
    (is (= :alive (cell-state cell)))))
  (testing "3 neighbors"
    (let [cell { :x 0 :y 0 :state 1 :neighbors [0 1 1 1]}]
    (is (= :alive (cell-state cell)))))
  (testing "More than 3 neighbors"
    (let [cell { :x 0 :y 0 :state 1 :neighbors [1 1 1 1]}]
    (is (= :dead (cell-state cell))))))
