Minesweeper
A Java Swing implementation of the classic Minesweeper game.

Features
Beginner, Intermediate, and Expert difficulty levels

First click is always safe

Timer with custom digital font

Left-click to reveal cells

Right-click to place/remove flags

Automatic expansion of empty cells

Win/loss detection dialogs

Resizable grids

Requirements
Java Development Kit (JDK) 8 or higher

Java Swing (included with JDK)

Images:

1.png through 8.png

flag.png

mine0.png

Font file:

digital-7.ttf

Project Structure
css
Copy
Edit
Minesweeper/
├── src/
│   ├── Minesweeper.java
│   ├── Minesweeper Images/
│   │   ├── 1.png
│   │   ├── 2.png
│   │   ├── ...
│   │   ├── 8.png
│   │   ├── flag.png
│   │   └── mine0.png
│   └── digital-7.ttf
Important: Update the file paths in Minesweeper.java if needed.

Example snippet:

java
Copy
Edit
numbers[x-1] = new ImageIcon("path/to/Minesweeper Images/" + x + ".png");
flag = new ImageIcon("path/to/Minesweeper Images/flag.png");
mine = new ImageIcon("path/to/Minesweeper Images/mine0.png");
clockFont = Font.createFont(Font.TRUETYPE_FONT, new File("path/to/digital-7.ttf"));
How to Run
Compile:

bash
Copy
Edit
javac Minesweeper.java
Run:

bash
Copy
Edit
java Minesweeper
Controls
Left-click: Reveal a cell

Right-click: Flag/unflag a cell

Menu bar:

Beginner (9×9 grid, 10 mines)

Intermediate (16×16 grid, 40 mines)

Expert (16×40 grid, 99 mines)

Reset current game

Customization
You can adjust:

Grid size (rows, cols)

Number of mines (numMines)

Cell size (default: 60×60 px)

Timer font and color

These can be changed in the Minesweeper constructor and setGrid().

License
This project is provided for educational use. Feel free to modify and extend it.
