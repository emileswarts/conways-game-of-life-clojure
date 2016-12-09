(ns conways-gol.core 
  (:require [conways-gol.console-renderer :refer :all]
            [conways-gol.start-patterns :refer :all]))

(defn new-state
  [live-neighbour-count cell]
  (cond
    (< live-neighbour-count 2) 0
    (= live-neighbour-count 2) (if (= (cell :state) 1) 1 0)
    (= live-neighbour-count 3) 1
    (> live-neighbour-count 3) 0))

(defn cell-state [cell neighbours] (new-state (count (filter #(= (%1 :state) 1) neighbours)) cell))

(def neighbour-offsets
  (let [digits (range -1 2)]
    (for [x digits y digits :let [value [x y]] :when (not= value [0 0]) ] value)))

(defn offset-match?
  [axis cell other-cell]
  (some #(= true %) (map #(= (+ (axis other-cell) (first %)) (axis cell)) neighbour-offsets)))

(defn neighbour?
  [other-cell cell]
  (and (offset-match? :x cell other-cell) (offset-match? :y cell other-cell) (not (= cell other-cell))))

(defn neighbours [grid cell] (filter #(neighbour? % cell) grid))

(defn tick-cell [cell grid] (assoc cell :state (cell-state cell (neighbours grid cell))))

(defn y-rows [dimensions] (map #(hash-map :y %) (take (dimensions :y) (range))))

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
  [defined-cells dimensions]
  (with-existing-cells
    (map (fn [y-row] (map #(assoc y-row :x %) (take (dimensions :x) (range))))
         (y-rows dimensions))
    defined-cells))

(defn presentable
  [world]
  (println (render world dimensions)) (Thread/sleep 50)
  (recur (rendered-world (map #(tick-cell %1 world) world) dimensions)))

(defn -main [] (let [world (rendered-world glider1 dimensions)] (presentable world)))
