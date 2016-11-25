(ns conways-gol.core
  (:gen-class))

(defn state-from-neighbours
  [live-neighbour-count]
  (cond
    (< live-neighbour-count 2) 0
    (= live-neighbour-count 2) 1
    (= live-neighbour-count 3) 1
    (> live-neighbour-count 3) 0))

(defn cell-state
  [cell cell-neighbours]
  (state-from-neighbours (count (filter #(= (%1 :state) 1) cell-neighbours))))

(defn neighbour?
  [potential-neighbour-cell cell]
  (and
    (and
      (or (= (cell :x) (potential-neighbour-cell :x))
          (= (cell :x) (- (potential-neighbour-cell :x) 1))
          (= (cell :x) (+ (potential-neighbour-cell :x) 1)))
      (or (= (cell :y) (potential-neighbour-cell :y))
          (= (cell :y) (- (potential-neighbour-cell :y) 1))
          (= (cell :y) (+ (potential-neighbour-cell :y) 1))))
    (not= cell potential-neighbour-cell)))

(defn neighbours
  [grid cell]
  (filter #(neighbour? %1 cell) grid))

(defn tick-cell
  [cell grid]
  (assoc cell :state (cell-state cell (neighbours grid cell))))

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

(defn rendered-world
  [defined-cells world-dimensions]
  (with-existing-cells (map (fn [x-row]
         (map
           (fn [y] (assoc x-row :y y))
           (take (world-dimensions :y) (range)))) (x-rows world-dimensions)) defined-cells))

(defn presentational
  [world]
  (println (map #(if (= 1 (% :state)) "*" " ") world))
  (Thread/sleep 1000)
  (presentational (rendered-world (map #(tick-cell %1 world) world) {:x 8 :y 8})))

(defn -main [& args]
  (let [world (rendered-world [{:x 3 :y 2 :state 1}
                               {:x 4 :y 3 :state 1}
                               {:x 4 :y 4 :state 1}
                               {:x 3 :y 4 :state 1}
                               {:x 2 :y 4 :state 1}] {:x 8 :y 8})]
    (presentational world)))
