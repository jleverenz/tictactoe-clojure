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
  (printfln "")
  (doseq [line (partition 3 board)]
    (printfln "+---+---+---+")
    (printfln "| %s |" (join " | " (map #(player-mark %1) line))))
  (printfln "+---+---+---+"))

(defn parse-int [s]
   (Integer. (re-find  #"\d+" s )))

(defn console-input [player board]
  (printfln "%s's turn: " (player-name player))
  (let [input (parse-int (read-line))]
    (printfln "Player picked: %s" input)
    (list input #(console-input %1 %2))))

(defn -main [& args]
  (let [final-board (play-game empty-board 1 console-input print-board)
        winner (who-won-board final-board)]
    (printfln "*** %s won! ***" (player-name winner))))
