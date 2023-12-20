build-task:
	kotlinc $(day)/task$(task)/task$(task).kt -include-runtime -d $(day)/task$(task)/task$(task).jar 

build-day:
	make build-task day=$(day) task=1
	make build-task day=$(day) task=2

run-task:
	java -jar $(day)/task$(task)/task$(task).jar $(day)/task$(task)/$(file)

run-day:
	java -jar $(day)/task1/task1.jar $(day)/task1/example.txt
	java -jar $(day)/task1/task1.jar $(day)/task1/input.txt

	java -jar $(day)/task2/task2.jar $(day)/task2/example.txt
	java -jar $(day)/task2/task2.jar $(day)/task2/input.txt

build-and-run-day:
	make build-day $(day)
	make run-day $(day)