(ns conways-gol.core-test
  (:require [clojure.test :refer :all]
            [conways-gol.core :refer :all]))

(deftest cell-state-test
  (testing "Dying: No live neighbours"
    (let [grid [{:x 1 :y 2 :state 1}
                {:x 0 :y 1 :state 0}
                {:x 1 :y 1 :state 0}
                {:x 2 :y 1 :state 0}]
          cell {:x 1 :y 2 :state 1}]
      (is (= 0 ((tick-cell cell grid) :state)))))
  (testing "Dying: Too many neighbours"
    (let [grid [{:x 1 :y 2 :state 1}
                {:x 0 :y 1 :state 1}
                {:x 1 :y 1 :state 1}
                {:x 2 :y 1 :state 1}]
          cell {:x 1 :y 2 :state 1}]
      (is (= 0 ((tick-cell cell grid) :state)))))
  (testing "Live: 2 neighbours"
    (let [grid [{:x 1 :y 2 :state 1}
                {:x 0 :y 1 :state 1}
                {:x 1 :y 1 :state 1}
                {:x 2 :y 1 :state 0}]
          cell {:x 1 :y 2 :state 1}]
      (is (= 1 ((tick-cell cell grid) :state)))))
  (testing "Live: 3 neighbours"
    (let [grid [{:x 1 :y 2 :state 1}
                {:x 0 :y 1 :state 1}
                {:x 1 :y 1 :state 1}
                {:x 2 :y 1 :state 1}]
          cell {:x 1 :y 2 :state 1}]
      (is (= 1 ((tick-cell cell grid) :state))))))

(deftest rendered-world-test
  (testing "Render world grid"
    (let [cells [{ :x 0 :y 0 :state 1}]
          world-dimensions {:x 5 :y 5}
          defined-cell (some #{{ :x 0 :y 0 :state 1}} (rendered-world cells world-dimensions))
          untouched-cell (some #{{ :x 1 :y 0 :state 0}} (rendered-world cells world-dimensions))]
      (is (= {:x 0 :y 0 :state 1} defined-cell))
      (is (= {:x 1 :y 0 :state 0} untouched-cell)))))

(deftest cell-neighbours-test
  (testing "3 neighbours"
    (let [grid [{:x 0 :y 0}
                {:x 1 :y 0}
                {:x 1 :y 1}
                {:x 0 :y 1}]
          cell-neighbours [{:x 1 :y 0}
                           {:x 1 :y 1}
                           {:x 0 :y 1}]
           cell {:x 0 :y 0}]
    (is (= cell-neighbours (neighbours grid cell)))))

  (testing "5 neighbours"
    (let [grid [{:x 1 :y 2}
                {:x 0 :y 1}
                {:x 1 :y 1}
                {:x 2 :y 1}
                {:x 0 :y 2}
                {:x 2 :y 2}
                {:x 0 :y 3}
                {:x 1 :y 3}
                {:x 2 :y 3}]
          cell-neighbours [{:x 0 :y 1}
                           {:x 1 :y 1}
                           {:x 2 :y 1}
                           {:x 0 :y 2}
                           {:x 2 :y 2}
                           {:x 0 :y 3}
                           {:x 1 :y 3}
                           {:x 2 :y 3}]
           cell {:x 1 :y 2}]
    (is (= cell-neighbours (neighbours grid cell))))))
