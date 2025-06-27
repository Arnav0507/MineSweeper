Minesweeper
A Java Swing implementation of the classic Minesweeper game.

📋 Features
Beginner, Intermediate, and Expert difficulty levels

Automatically places mines after the first click (never lose on your first move)

Timer with custom digital font

Left-click to reveal cells

Right-click to place or remove flags

Automatic expansion of empty cells

Win/loss detection with pop-up dialogs

Resizable grid and mine counts

🛠 Requirements
Java Development Kit (JDK) 8 or higher

Java Swing libraries (included in standard JDK)

Minesweeper images (1.png through 8.png, flag.png, mine0.png)

digital-7.ttf font file

📂 Project Structure
css
Copy
Edit
Minesweeper/
├── src/
│   ├── Minesweeper.java
│   └── Minesweeper Images/
│       ├── 1.png
│       ├── 2.png
│       ├── ...
│       ├── 8.png
│       ├── flag.png
│       └── mine0.png
│   └── digital-7.ttf


🚀 Running the Game
Compile and run the program:

bash
Copy
Edit
javac Minesweeper.java
java Minesweeper
The main window will appear with the default Beginner grid.

🎮 Controls
Left-click a cell to reveal it.

Right-click a cell to place/remove a flag.

Menu bar:

Beginner: 9×9 grid, 10 mines

Intermediate: 16×16 grid, 40 mines

Expert: 16×40 grid, 99 mines

Reset: Restart the current difficulty

✨ Customization
You can change:

Grid size (rows, cols)

Mine count (numMines)

Cell icon size (currently scaled to 60×60 pixels)

Timer font and color

Modify these in the Minesweeper constructor and setGrid() as needed.

📖 License
This project is provided for educational purposes. Feel free to adapt or expand it.
