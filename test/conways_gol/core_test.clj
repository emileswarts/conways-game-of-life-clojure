(ns conways-gol.core-test
  (:require [clojure.test :refer :all]
            [conways-gol.core :refer :all]
            [conways-gol.console-renderer :refer :all]))

(deftest cell-state-test
  (testing "Dying: No live neighbours"
    (let [grid [{:x 1 :y 2 :state 1}
                {:x 0 :y 1 :state 0}
                {:x 1 :y 1 :state 0}
                {:x 2 :y 1 :state 0}]
          cell {:x 1 :y 2 :state 1}]
      (is (= 0 ((tick-cell cell grid) :state)))))
  (testing "Dying: More than 3 neighbours"
    (let [grid [{:x 1 :y 2 :state 1}
                {:x 0 :y 1 :state 1}
                {:x 1 :y 1 :state 1}
                {:x 2 :y 1 :state 1}
                {:x 1 :y 3 :state 1}]
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
                {:x 0 :y 1}
                {:x 1 :y 1}]
          cell-neighbours [{:x 1 :y 0}
                           {:x 0 :y 1}
                           {:x 1 :y 1}]
          cell {:x 0 :y 0}]
      (is (= cell-neighbours (neighbours grid cell)))))

  (testing "neighbour top left"
    (let [grid [{:x 0 :y 0}
                {:x 1 :y 1}]
          cell-neighbours [{:x 0 :y 0}]
          cell {:x 1 :y 1}]
      (is (= cell-neighbours (neighbours grid cell)))))

  (testing "neighbour top"
    (let [grid [{:x 1 :y 0}
                {:x 1 :y 1}]
          cell-neighbours [{:x 1 :y 0}]
          cell {:x 1 :y 1}]
      (is (= cell-neighbours (neighbours grid cell)))))

  (testing "neighbour top right"
    (let [grid [{:x 2 :y 0}
                {:x 1 :y 1}]
          cell-neighbours [{:x 2 :y 0}]
          cell {:x 1 :y 1}]
      (is (= cell-neighbours (neighbours grid cell)))))

  (testing "neighbour right"
    (let [grid [{:x 2 :y 1}
                {:x 1 :y 1}]
          cell-neighbours [{:x 2 :y 1}]
          cell {:x 1 :y 1}]
      (is (= cell-neighbours (neighbours grid cell)))))

  (testing "neighbour bottom right"
    (let [grid [{:x 2 :y 2}
                {:x 1 :y 1}]
          cell-neighbours [{:x 2 :y 2}]
          cell {:x 1 :y 1}]
      (is (= cell-neighbours (neighbours grid cell)))))

  (testing "neighbour bottom"
    (let [grid [{:x 1 :y 2}
                {:x 1 :y 1}]
          cell-neighbours [{:x 1 :y 2}]
          cell {:x 1 :y 1}]
      (is (= cell-neighbours (neighbours grid cell)))))

  (testing "neighbour bottom left"
    (let [grid [{:x 0 :y 2}
                {:x 1 :y 1}]
          cell-neighbours [{:x 0 :y 2}]
          cell {:x 1 :y 1}]
      (is (= cell-neighbours (neighbours grid cell)))))

  (testing "neighbour left"
    (let [grid [{:x 0 :y 1}
                {:x 1 :y 1}]
          cell-neighbours [{:x 0 :y 1}]
          cell {:x 1 :y 1}]
      (is (= cell-neighbours (neighbours grid cell)))))

  )

  (testing "5 neighbours"
    (let [grid [{:x 0 :y 0}
                {:x 1 :y 0}
                {:x 2 :y 0}
                {:x 0 :y 1}
                {:x 1 :y 1}
                {:x 2 :y 1}
                {:x 0 :y 2}
                {:x 1 :y 2}
                {:x 2 :y 2}]
          cell-neighbours [{:x 0 :y 0}
                           {:x 1 :y 0}
                           {:x 2 :y 0}
                           {:x 0 :y 1}
                           {:x 2 :y 1}
                           {:x 0 :y 2}
                           {:x 1 :y 2}
                           {:x 2 :y 2}]
          cell {:x 1 :y 1}]
      (is (= cell-neighbours (neighbours grid cell)))))

(deftest console-render-test
  (testing "console-renderer"
    (let [world [{:x 0 :y 0 :state 1}
                 {:x 1 :y 0 :state 0}
                 {:x 0 :y 1 :state 1}
                 {:x 1 :y 1 :state 0} ]

          world-dimensions {:x 2 :y 2}
          expected-result "🔥 . 
🔥 . \n\n"]
    (is (= (render world world-dimensions) expected-result)))))

(deftest neighbour-offsets-test
  (testing "neighbour offsets"
    (let [expected-result '([-1 -1]
                            [-1 0]
                            [-1 1]
                            [0 -1]
                            [0 1]
                            [1 -1]
                            [1 0]
                            [1 1])]
      (is (= neighbour-offsets expected-result)))))
