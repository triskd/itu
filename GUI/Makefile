download:
	wget https://www.stud.fit.vutbr.cz/~xtrisk05/ITU_BALIKY/json-simple-1.1.jar
	wget https://www.stud.fit.vutbr.cz/~xtrisk05/ITU_BALIKY/javafx-mx.jar

compile:
	javac -cp .:javafx-mx.jar:json-simple-1.1.jar controler/*.java model/*.java view/*.java

run:
	java -cp .:javafx-mx.jar:json-simple-1.1.jar view.Main

clear:
	rm *.jar controler/*.class view/*.class model/*.class