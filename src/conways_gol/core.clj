(ns conways-gol.core 
  (:require [conways-gol.console-renderer :refer :all]
            [conways-gol.start-patterns :refer :all]))

(def board-dimensions {:x 25 :y 25})

(defn state-from-neighbours
  [live-neighbour-count cell]
  (cond
    (< live-neighbour-count 2) 0
    (= live-neighbour-count 2) 
      (if (= (cell :state) 1) 1 0)
    (= live-neighbour-count 3) 1
    (> live-neighbour-count 3) 0))

(defn cell-state
  [cell cell-neighbours]
  (state-from-neighbours (count (filter #(= (%1 :state) 1) cell-neighbours)) cell))

(defn neighbour?
  [other-cell cell]
  (and
    (or 
      (and (= (cell :y) (- (other-cell :y) 1)) (= (cell :x) (- (other-cell :x) 1)))
      (and (= (cell :x) (other-cell :x)) (= (cell :y) (- (other-cell :y) 1)))
      (and (= (cell :y) (other-cell :y)) (= (cell :x) (- (other-cell :x) 1)))
      (and (= (cell :y) (other-cell :y)) (= (cell :x) (+ (other-cell :x) 1)))
      (and (= (cell :x) (+ (other-cell :x) 1)) (= (cell :y) (- (other-cell :y) 1)))
      (and (= (cell :x) (+ (other-cell :x) 1)) (= (cell :y) (+ (other-cell :y) 1)))
      (and (= (cell :x) (other-cell :x)) (= (cell :y) (+ (other-cell :y) 1)))
      (and (= (cell :y) (+ (other-cell :y) 1)) (= (cell :x) (- (other-cell :x) 1))))
    (not= cell other-cell)))

(defn neighbours [grid cell] (vec (filter (fn [grid-item] (neighbour? grid-item cell)) grid)))

(defn tick-cell [cell grid] (assoc cell :state (cell-state cell (neighbours grid cell))))

(defn y-rows
  [world-dimensions]
  (map (fn [y-row] (hash-map :y y-row)) (take (world-dimensions :y) (range))))

(defn with-existing-cells
  [generated-cells defined-cells]
  (map (fn [generated-cell]
         (let [row (#(filter
                       (fn [cell-row]
                         (and
                           (= (cell-row :x) (generated-cell :x))
                           (= (cell-row :y) (generated-cell :y)))) defined-cells))]
           (if (not (empty? row))
             (assoc generated-cell :state ((first row) :state))
             (assoc generated-cell :state 0)))) (flatten generated-cells)))

(defn rendered-world
  [defined-cells world-dimensions]
  (with-existing-cells
    (map (fn [y-row] (map (fn [x] (assoc y-row :x x)) (take (world-dimensions :x) (range))))
         (y-rows world-dimensions))
    defined-cells))

(defn presentable
  [world]
  (println (render world board-dimensions)) (Thread/sleep 50)
  (recur (rendered-world (map #(tick-cell %1 world) world) board-dimensions)))

(defn -main [& args] (let [world (rendered-world glider1 board-dimensions)] (presentable world)))
