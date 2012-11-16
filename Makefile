# Work around for "lein run" not working w/ read-line on Windows (?), and not
# being able to get "lein trampoline run" to work either.

default:
	lein uberjar
	java -jar target/tictactoe-0.1.0-SNAPSHOT-standalone.jar
