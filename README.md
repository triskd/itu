ITU PROJEKT 2020
autor: David Tříska, xtrisk05
-------------------------------------------------------------------------------------------------
- skolni projekt pro predmet ITU - Tvorba uzivatelskych rozhrani.
- sudoku aplikace (Java + javafx) slouzici jako front-end ziskava data ze simulovaneho back-end (PHP)
- seznam souboru:
	```
	.
	├── API
	│   └── api
	│       └── game
	│           ├── games.php
	│           ├── generate
	│           │   └── index.php
	│           ├── help
	│           │   └── index.php
	│           └── check
	│               └── index.php
	├── GUI
	│   ├── build.xml
	│   ├── lib
	│   │   └── get-libs.sh
	│   ├── Makefile
	│   └── src
	│       ├── controler
	│       │   └── Controler.java
	│       ├── main.css
	│       ├── model
	│       │   ├── Cell.java
	│       │   ├── GameBoard.java
	│       │   ├── GameTask.java
	│       │   ├── HTTP.java
	│       │   └── Server.java
	│       └── view
	│           ├── BoardView.java
	│           ├── ControlsView.java
	│           └── Main.java
	└── REAMDE.md
	```
- API:
	- autorske soubory, tvorici backend aplikace v soucasne dobe bezi na domene: https://www.stud.fit.vutbr.cz/~xtrisk05/itu_proj/api/
	- ktera slouzi jako spolecny backend k nasim aplikacim
	- 

- GUI:
	- autorske sobory uzivatelskeho rozhrani, desktopova aplikace napsana v jave + javafx
	- preklad a spusteni:
		- k prekladu i spusteni slouzi $ ant
		- pred samotnym prekladem je potreba stahnout zavisle baliky z nasledujicich domen:
			- https://www.stud.fit.vutbr.cz/~xtrisk05/ITU_BALIKY/json-simple-1.1.jar
			- https://gluonhq.com/download/javafx-11-0-2-sdk-linux/
			- stazeni a potrebne upravy provede script get-libs.sh
		- $ ant run 
		

----------------------------------------------------------------------------------------------------