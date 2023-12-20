build-task:
	kotlinc $(day)-12-2023/task$(task)/task$(task).kt -include-runtime -d $(day)-12-2023/task$(task)/task$(task).jar 

build-day:
	make build-task day=$(day) task=1
	make build-task day=$(day) task=2

run-task:
	java -jar $(day)-12-2023/task$(task)/task$(task).jar $(day)-12-2023/task$(task)/$(file)

run-day:
	java -jar $(day)-12-2023/task1/task1.jar $(day)-12-2023/task1/example.txt
	java -jar $(day)-12-2023/task1/task1.jar $(day)-12-2023/task1/input.txt

	java -jar $(day)-12-2023/task2/task2.jar $(day)-12-2023/task2/example.txt
	java -jar $(day)-12-2023/task2/task2.jar $(day)-12-2023/task2/input.txt

build-and-run-day:
	make build-day $(day)
	make run-day $(day)