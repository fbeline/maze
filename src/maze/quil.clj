(ns maze.quil
  (require [quil.core :as q]
           [maze.core :as maze]
           [maze.misc :refer :all]))

;; imperative rendering style
(defn draw-maze! [maze margin]
  (q/line margin margin (- (q/width) margin) margin)
  (let [y-cell-size (/ (- (q/height) (* 2 margin)) (count maze))
        x-cell-size (/ (- (q/width) (* 2 margin)) (-> maze first count))]
    (dotimes [y (count maze)]
      (let [yv  (+ margin (* y y-cell-size))
            yv2 (+ y-cell-size yv)]
        (q/line margin yv margin yv2)
        (dotimes [x (-> maze first count)]
          (let [xv  (+ margin (* x x-cell-size))
                xv2 (+ x-cell-size xv)]
            (if (= 0 (bit-and (mget x y maze) 2)) (q/line xv yv2 xv2 yv2))
            (if (not= 0 (bit-and (mget x y maze) 4))
              (when (= 0 (bit-and (bit-or (mget x y maze) (mget (inc x) y maze)) 2)) (q/line xv yv2 xv2 yv2))
              (q/line xv2 yv xv2 yv2))))))))

(defn setup []
  (q/background 255)
  (q/stroke-weight 2)
  (draw-maze! (maze/create 40 20) 10))

(q/defsketch gen-maze
           :title "Maze"
           :setup setup
           :size [500 1000])
