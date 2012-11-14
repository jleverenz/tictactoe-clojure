(ns tictactoe.core)

;; A Board is represented by a list of 9 positions, 0-based.
;;    0 1 2
;;    3 4 5
;;    6 7 8
;;
;; The winning combinations are then:
;;
;;    top       0 1 2
;;    bottom                6 7 8
;;    left      0     3     6
;;    right         2     5     8
;;    vert        1     4     7
;;    horz            3 4 5
;;    criss     0       4       8
;;    cross         2   4   6

(def winning-lines
  ['(0 1 2) '(6 7 8)'(0 3 6)'(2 5 8)'(1 4 7)'(3 4 5)'(0 4 8)'(2 4 6)])

;; Returns 1 or 2 indicating which player won on LINE of BOARD, or 0 if no
;; player won
(defn win-on-line [board line]
  ;; the non-distinct map will be set to the 'marks' on that row.  Think "X X
  ;; O" or "X . ." or "O O O".  Except it'll be 0,1,2 values.  Distinct
  ;; collapses the list.  I win is found when the distinct symbol list for a
  ;; winline reduces to 1 (X) or 2 (o).
  (let [marks (distinct (map #(nth board %) line))]
    (if (= 1 (count marks))
         (first marks)                  ; only one type, it's XXX or OOO
         0)))                           ; everything else is a loser

(defn is-board-solved? [board]
  (not (nil? (first (filter #(not (= 0 %))
                         (map #(win-on-line board %)
                              winning-lines))))))
