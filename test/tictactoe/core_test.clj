(ns tictactoe.core-test
  (:use clojure.test
        tictactoe.core))


(def empty-board                      '( 0 0 0
                                         0 0 0
                                         0 0 0 ))

(def partially-complete-board         '( 0 2 0
                                         0 1 0
                                         0 0 0 ))

(def partially-complete-1wins-board   '( 1 0 2
                                         0 1 2
                                         0 0 1 ))

(def partially-complete-2wins-board   '( 1 0 2
                                         0 1 2
                                         0 1 2 ))

(def draw-board                       '( 1 2 2
                                         2 1 1
                                         1 1 2 ))

;; top       1 2 3
;; bottom                7 8 9
;; left      1     4     7
;; right         3     6     9
;; vert        2     5     8
;; horz            4 5 6
;; criss     1       5       9
;; cross         3   5   7

;; ;; draw-board
;; top       1 2 2
;; bottom                1 1 2
;; left      1     2     1
;; right         2     1     2
;; vert        2     1     1
;; horz            2 1 1
;; criss     1       1       2
;; cross         2   1   1

(def winning-lines ['(0 1 2)
                    '(6 7 8)
                    '(0 3 6)
                    '(2 5 8)
                    '(1 4 7)
                    '(3 4 5)
                    '(0 4 8)
                    '(2 4 6)])


(defn win-on-line [board line]
  (let [marks (distinct (map #(nth board %) line))]
    (if (= 1 (count marks))
         (first marks)
         0)))

(defn is-solved? [board]

  (not (nil? (first (filter #(not (= 0 %))
                         (map #(win-on-line board %)
                              winning-lines))))))

(deftest test-win-on-line
  (is (= 0 (win-on-line empty-board '(0 1 2))))
  (is (= 1 (win-on-line partially-complete-1wins-board '(0 4 8))))
  (is (= 2 (win-on-line partially-complete-2wins-board '(2 5 8)))))

(deftest test-is-solved?
  (is (= false (is-solved? empty-board)))
  (is (= false (is-solved? draw-board)))
  (is (= true (is-solved? partially-complete-1wins-board)))
  (is (= true (is-solved? partially-complete-2wins-board)))
  )
