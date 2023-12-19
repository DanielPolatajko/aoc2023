build-task:
	kotlinc $(day)/task$(problem)/task$(problem).kt -include-runtime -d $(day)/task$(problem)/task$(problem).jar 

build-day:
	make build-task day=$(day) problem=1
	make build-task day=$(day) problem=2

run-task:
	java -jar $(day)/task$(problem)/task$(problem).jar $(day)/task$(problem)/$(file)

run-day:
	java -jar $(day)/task1/task1.jar $(day)/task1/example.txt
	java -jar $(day)/task1/task1.jar $(day)/task1/input.txt

	java -jar $(day)/task2/task2.jar $(day)/task2/example.txt
	java -jar $(day)/task2/task2.jar $(day)/task2/input.txt

build-and-run-day:
	make build-day $(day)
	make run-day $(day)