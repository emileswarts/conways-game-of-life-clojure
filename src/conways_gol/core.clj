(ns conways-gol.core
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(defn cell-state
  [cell]
  (let [live-neighbor-count (count (filter #(= 1 %) (cell :neighbors)))]
  (cond
    (< live-neighbor-count 2) 0
    (= live-neighbor-count 2) 1
    (= live-neighbor-count 3) 1
    (> live-neighbor-count 3) 0)))

(defn tick-cell
  [cell]
  (assoc cell :state (cell-state cell)))

(defn x-rows
  [world-dimensions]
  (map (fn [x-row] (hash-map :x x-row)) (take (world-dimensions :x) (range))))

(defn y-rows-from-x-rows
  [x-row world-dimensions]
  (fn [x-row]
    (map
      (fn [y] (assoc x-row :y y))
      (take (world-dimensions :y) (range)))))

(defn with-neighbors
  [cells]
  cells)

(defn with-existing-cells
  [generated-cells defined-cells]
  (with-neighbors
    (map (fn [cell]
         (if (not= nil #(filter (fn [cell-row] (and (= (cell-row :x) (= (cell-row :y)))) [(cell :x) (cell :y)]) defined-cells))
               (assoc cell :state (cell :state))
               (assoc cell :state 0))) defined-cells)))

(defn rendered-world
  [defined-cells world-dimensions]
  (with-existing-cells (y-rows-from-x-rows (x-rows world-dimensions) world-dimensions) defined-cells))

; (def world-dimensions
;   {:x 9 :y 9})
