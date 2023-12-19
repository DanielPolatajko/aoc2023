.PHONY
build-task:
	kotlinc $(day)/task$(problem)/task$(problem).kt -include-runtime -d $(day)/task$(problem)/task$(problem).jar 

.PHONY
build-day:
	build-task day=$(day) problem=1
	build-task day=$(day) problem=2

.PHONY
run-task:
	java -jar $(day)/task$(problem)/task$(problem).jar $(day)/task$(problem)/$(file)

.PHONY
run-day:
	echo "Running task 1 example"
	java -jar $(day)/task1/task1.jar $(day)/task1/example.txt
	echo "Running task 1 input"
	java -jar $(day)/task1/task1.jar $(day)/task1/input.txt

	echo "Running task 2 example"
	java -jar $(day)/task2/task2.jar $(day)/task2/example.txt
	echo "Running task 2 input"
	java -jar $(day)/task2/task2.jar $(day)/task2/input.txt