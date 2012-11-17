(ns tictactoe.console
  (:gen-class)
  (:use tictactoe.core
        [clojure.string :only [join]]))

(def printfln (comp println format))

(defn player-mark [player-num]
  (case player-num 1 "X" 2 "O" " "))

(defn player-name [player-num]
  (str "Player " (player-mark player-num)))

(defn print-board [board]
  (doseq [line (partition 3 board)]
    (printfln "+---+---+---+")
    (printfln "| %s |" (join " | " (map #(player-mark %1) line))))
  (printfln "+---+---+---+"))

(defn parse-int [s]
   (Integer. (re-find  #"\d+" s )))

(defn console-input [player]
  (printfln "%s's turn: " (player-name player))
  (let [input (parse-int (read-line))]
    (printfln "Player picked: %s" input)
    input))


(defn play-console [board player & input-func]
  (let [input-func (if (nil? input-func) console-input input-func)]
  (print-board board)
  (if (is-board-solved? board)
    board
    (do
      (let [input (input-func player)
            result (play-turn board [input] player)]
        (recur (first result) (second result) input-func))))))


(defn -main [& args]
  (let [final-board (play-console empty-board 1)
        winner (who-won-board final-board)]
    (printfln "*** %s won! ***" (player-name winner))))
