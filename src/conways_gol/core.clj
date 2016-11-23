(ns conways-gol.core
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(defn state-from-neighbours
  [live-neighbor-count]
  (cond
    (< live-neighbor-count 2) 0
    (= live-neighbor-count 2) 1
    (= live-neighbor-count 3) 1
    (> live-neighbor-count 3) 0))

(defn cell-state
  [cell cell-neighbours]
  (state-from-neighbours (count (filter #(= (%1 :state) 1) cell-neighbours))))

(defn tick-cell
  [cell grid]
  (assoc cell :state (cell-state (neighbours cell grid) grid)))

(defn x-rows
  [world-dimensions]
  (map (fn [x-row] (hash-map :x x-row)) (take (world-dimensions :x) (range))))

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

(defn neighbour?
  [potential-neighbour-cell cell]
  (and (or
    (= (cell :x) (potential-neighbour-cell :x))
    (= (cell :y) (potential-neighbour-cell :y))
    (= (cell :x) (- (potential-neighbour-cell :x) 1))
    (= (cell :y) (- (potential-neighbour-cell :y) 1))
    (= (cell :x) (+ (potential-neighbour-cell :x) 1))
    (= (cell :y) (+ (potential-neighbour-cell :y) 1)))
    (not= cell potential-neighbour-cell)))

(defn neighbours
  [grid cell]
  (filter #(neighbour? %1 cell) grid))

(defn rendered-world
  [defined-cells world-dimensions]
  (with-existing-cells (map (fn [x-row]
         (map
           (fn [y] (assoc x-row :y y))
           (take (world-dimensions :y) (range)))) (x-rows world-dimensions)) defined-cells))
