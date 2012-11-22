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

(def empty-board [0 0 0 0 0 0 0 0 0])

(def winning-lines
  [[0 1 2] [6 7 8][0 3 6][2 5 8][1 4 7][3 4 5][0 4 8][2 4 6]])

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

(defn is-space-empty? [board index]
  (= 0 (nth board index)))

(defn find-winner [board]
  (first (filter #(not (= 0 %))
                 (map #(win-on-line board %)
                      winning-lines))))

(defn is-board-solved? [board]
  (not (nil? (find-winner board))))


(defn is-board-full? [board]
  (nil? (first (filter #(= 0 %) board))))

;; 0, 1, or 2
(defn who-won-board [board]
  (let [winner (find-winner board)]
    (if (nil? winner) 0 winner)))

(defn is-board-complete? [board]
  (or (is-board-full? board)
      (is-board-solved? board)))


(defn play-move [board index move]
  (if (or (< index 0) (> index 8))
    (throw (new IllegalArgumentException "INDEX out of bounds")))
  (if (not (or (= move 1) (= move 2)))
    (throw (new IllegalArgumentException "MOVE out of bounds")))
  (concat (take index board) (list move) (nthrest board (+ index 1))))

;; takes a board, a set of moves, and who's turn it is, applies moves
;; alternating between players.
;;
;; Returns a list of the resulting board, and who's turn is next
(defn play-turn [board moves whos-turn]
  (let [winner (who-won-board board)]
    (if (not (= 0 winner))
      (list board whos-turn)
      (if (empty? moves)
        (list board whos-turn)
        (play-turn (play-move board (first moves) whos-turn)
                   (nthrest moves 1)
                   (if (= 1 whos-turn) 2 1))))))

;; input-func: returns list w/ player's input, function for next input
(defn play-game [board player input-func output]
  (if (not (nil? output)) (output board))
  (if (is-board-complete? board)
    board
    (do
      (let [[input next-input-func] (input-func player board)
            [board player] (play-turn board [input] player)]
        (recur board player next-input-func output)))))
