(ns maze.misc)

(defn between [v x y]
  (and (>= v x) (<= v y)))

(defn mget [x y matrix]
  (-> matrix (nth y) (nth x)))

(defn mset [v x y matrix]
  (assoc matrix y (-> matrix (nth y) (update x #(+ % v)))))
