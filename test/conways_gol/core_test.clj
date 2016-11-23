(ns conways-gol.core-test
  (:require [clojure.test :refer :all]
            [conways-gol.core :refer :all]))

; (deftest tick-cell-test
;   (testing "Fewer than 2 neighbors"
;     (let [cell { :x 0 :y 0 :state 1 :neighbors [0 0 1]}]
;     (is (= 0 ((tick-cell cell) :state)))))
;   (testing "2 neighbors"
;     (let [cell { :x 0 :y 0 :state 1 :neighbors [0 0 1 1]}]
;     (is (= 1 ((tick-cell cell) :state)))))
;   (testing "3 neighbors"
;     (let [cell { :x 0 :y 0 :state 1 :neighbors [0 1 1 1]}]
;     (is (= 1 ((tick-cell cell) :state)))))
;   (testing "More than 3 neighbors"
;     (let [cell { :x 0 :y 0 :state 1 :neighbors [1 1 1 1]}]
;     (is (= 0 ((tick-cell cell) :state))))))

(deftest rendered-world-test
  (testing "Render world grid"
    (let [cells [{ :x 0 :y 0 :state 1}]
          world-dimensions {:x 5 :y 5}
          defined-cell (some #{{ :x 0 :y 0 :state 1}} (rendered-world cells world-dimensions))
          untouched-cell (some #{{ :x 1 :y 0 :state 0}} (rendered-world cells world-dimensions))]
      (is (= { :x 0 :y 0 :state 1} defined-cell))
      (is (= { :x 1 :y 0 :state 0} untouched-cell)))))

(deftest cell-neighbours-test
  (testing "3 neighbours"
    (let [grid [{ :x 0 :y 0 :state 1 }
                { :x 1 :y 0 :state 1 }
                { :x 1 :y 1 :state 1 }
                { :x 0 :y 1 :state 1 }]
          cell-neighbours [{ :x 1 :y 0 :state 1 }
                           { :x 1 :y 1 :state 1 }
                           { :x 0 :y 1 :state 1 }]
           cell { :x 0 :y 0 :state 1 }]
    (is (= cell-neighbours (neighbours grid cell))))))

; (deftest cell-state-test
;   (testing "3 neighbours"
;     (let [cell-family [{ :x 0 :y 0 :state 1 }
;                 { :x 1 :y 0 :state 1 }
;                 { :x 1 :y 1 :state 1 }
;                 { :x 0 :y 1 :state 1 }]]
;     (is (= 0 ((mutated-cell cell-family { :x 0 :y 0 :state 1 }) :state))))))
