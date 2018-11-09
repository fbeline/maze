(ns maze.quil
  (require [quil.core :as q]
           [maze.core :as maze]
           [maze.misc :refer :all]))

;; imperative rendering style
(defn draw-maze! [maze w margin]
  (q/line margin margin (- (q/width) margin) margin)
  (dotimes [y (count maze)]
    (let [cell-size (/ (- w (* 2 margin)) (count maze))
          yv        (+ margin (* y cell-size))
          yv2       (+ cell-size yv)]
      (q/line margin yv margin yv2)
      (dotimes [x (count maze)]
        (let [xv  (+ margin (* x cell-size))
              xv2 (+ cell-size xv)]
          (if (= 0 (bit-and (mget x y maze) 2)) (q/line xv yv2 xv2 yv2))
          (if (not= 0 (bit-and (mget x y maze) 4))
            (when (= 0 (bit-and (bit-or (mget x y maze) (mget (inc x) y maze)) 2)) (q/line xv yv2 xv2 yv2))
            (q/line xv2 yv xv2 yv2)))))))

(defn setup []
  (q/background 255)
  (q/stroke-weight 2)
  (draw-maze! (maze/create 20) (q/width) 10))

(q/defsketch gen-maze
           :title "Maze"
           :setup setup
           :size [500 500])
