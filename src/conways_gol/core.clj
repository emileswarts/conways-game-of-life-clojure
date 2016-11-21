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

(defn with-existing-cells
  [generated-cells defined-cells]
    (map (fn [generated-cell]
           (let [row (#(filter (fn [cell-row] (and
                                                (= (cell-row :x) (generated-cell :x))
                                                (= (cell-row :y) (generated-cell :y)))) defined-cells))]
            (println (flatten row))
           (if (not (empty? row))
             (assoc generated-cell :state ((first row) :state))
             (assoc generated-cell :state 0)))) (flatten generated-cells)))

(defn rendered-world
  [defined-cells world-dimensions]
  (with-existing-cells (map (fn [x-row]
         (map
           (fn [y] (assoc x-row :y y))
           (take (world-dimensions :y) (range)))) (x-rows world-dimensions)) defined-cells))
