import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;
public class Minesweeper extends JPanel implements MouseListener, ActionListener
{
    Timer timer;
    int timePassed = 0;
    JTextField timeField;
    GraphicsEnvironment ge;
    Font clockFont;
    JFrame frame;
    JMenuBar bar;
    JMenu menu;
    JMenuItem beginner, inter, expert;
    JButton reset;
    JPanel panel;
    JToggleButton[][] buttons;
    int numMines = 10;
    boolean firstClick = true;
    boolean gameOver = false;
    int rows = 9, cols = 9;
    int numClicks = 0;
    int clickR, clickC;
    ImageIcon[] numbers = new ImageIcon[8];
    ImageIcon flag, mine;
    public Minesweeper(){
        menu  = new JMenu("Menu");
        beginner = new JMenuItem("Beginner");
        inter = new JMenuItem("Intermediate");
        expert = new JMenuItem("Expert");
        reset = new JButton("Reset");
        reset.setFocusable(false);
        reset.addActionListener(this);
        beginner.addActionListener(this);
        inter.addActionListener(this);
        expert.addActionListener(this);
        bar = new JMenuBar();
        menu.add(beginner);
        menu.add(inter);
        menu.add(expert);
        bar.add(menu);
        bar.add(reset);
        frame = new JFrame("Minesweeper");
        frame.setSize(1000, 600);
        frame.setLayout(new BorderLayout());
        for(int x = 1; x<=8; x++){
            numbers[x-1] = new ImageIcon("/Users/arnavgoel/IdeaProjects/Minesweeper/src/Minesweeper Images/"+x+".png");
            numbers[x-1] = new ImageIcon(numbers[x-1].getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH));
        }

        flag = new ImageIcon("/Users/arnavgoel/IdeaProjects/Minesweeper/src/Minesweeper Images/flag.png");
        flag = new ImageIcon(flag.getImage().getScaledInstance(60,60,Image.SCALE_SMOOTH));

        mine = new ImageIcon("/Users/arnavgoel/IdeaProjects/Minesweeper/src/Minesweeper Images/mine0.png");
        mine = new ImageIcon(mine.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH));
        panel = new JPanel();
        setGrid(rows, cols);

        frame.add(panel, BorderLayout.CENTER);
        frame.add(bar, BorderLayout.NORTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        try{
            ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            clockFont = Font.createFont(Font.TRUETYPE_FONT, new File("/Users/arnavgoel/IdeaProjects/Minesweeper/src/digital-7.ttf"));
            ge.registerFont(clockFont);
        } catch(IOException | FontFormatException e) {
            System.out.println("Clock Font Error");
        }
        timeField = new JTextField(timePassed+"");
        timeField.setFont(clockFont.deriveFont(18f));
        timeField.setForeground(Color.BLUE);
        timer = new Timer();
        bar.add(timeField);
        bar.setLayout(new GridLayout(1,3));
    }

    public void setGrid(int rows, int cols){
        if(panel!=null)
            frame.remove(panel);
        buttons = new JToggleButton[rows][cols];
        panel=new JPanel();
        panel.setLayout(new GridLayout(rows, cols));
        for(int r = 0; r<rows; r++){
            for(int c = 0; c<cols; c++){
                buttons[r][c] = new JToggleButton();
                buttons[r][c].addMouseListener(this);
                buttons[r][c].putClientProperty("row",r);
                buttons[r][c].putClientProperty("col",c);
                buttons[r][c].putClientProperty("mineVal",0);
                buttons[r][c].setFocusable(false);
                panel.add(buttons[r][c]);
            }
        }
        frame.add(panel,BorderLayout.CENTER);
        frame.setSize(cols*60,rows*60);
        frame.revalidate();
    }

    public void dropMines(int rows, int cols){
        int count=numMines;
        while(count>0){
            int r = (int)(Math.random()*buttons.length);
            int c = (int)(Math.random()*buttons[0].length);
            if(r!=rows && c!=cols && (int)(buttons[r][c].getClientProperty("mineVal"))==0){
                buttons[r][c].putClientProperty("mineVal",-1);
                count--;
            }
        }

        for(int r = 0; r<buttons.length; r++){
            for(int c = 0; c<buttons[0].length; c++){
                int state = (int)(buttons[r][c].getClientProperty("mineVal"));
                if(state!=-1){
                    count = 0;
                    for(int rr=r-1; rr<=r+1; rr++){
                        for(int cc = c-1; cc<=c+1; cc++){
                            try{
                                state = (int)(buttons[rr][cc].getClientProperty("mineVal"));
                                if(state==-1)
                                    count++;
                            }catch(ArrayIndexOutOfBoundsException e){

                            }
                        }

                    }
                    buttons[r][c].putClientProperty("mineVal", count);
                }
            }
        }

    }

    public void disableBoard(){
        for(int r = 0; r<buttons.length; r++){
            for(int c = 0; c<buttons[0].length; c++){
                ImageIcon icon = (ImageIcon)buttons[r][c].getIcon();
                buttons[r][c].setDisabledIcon(icon);
                buttons[r][c].setEnabled(false);
                int state = (int)(buttons[r][c].getClientProperty("mineVal"));
                if(state==-1){
                    buttons[r][c].setIcon(mine);
                    buttons[r][c].setDisabledIcon(mine);
                }
            }
        }
    }

    public void mouseReleased(MouseEvent e){
        clickR = (int)((JToggleButton)e.getComponent()).getClientProperty("row");
        clickC = (int)((JToggleButton)e.getComponent()).getClientProperty("col");
        if(!gameOver){
            if(e.getButton()==MouseEvent.BUTTON1 && buttons[clickR][clickC].isEnabled())//leftClick
            {
                if(firstClick){
                    timer = new Timer();
                    timer.schedule(new UpdateTimer(), 0, 1000);
                    dropMines(clickR,clickC);
                    firstClick = false;
                }
                int state = (int)(buttons[clickR][clickC].getClientProperty("mineVal"));
                if(state==-1){
                    gameOver = true;
                    timer.cancel();
                    disableBoard();
                    JOptionPane.showMessageDialog(null, "You are a loser!");
                }
                else{
                    expand(clickR, clickC);
                    numClicks++;
                    if(numClicks==buttons.length*buttons[0].length-numMines){
                        timer.cancel();
                        JOptionPane.showMessageDialog(null, "You are a winner!");
                    }

                }
            }
            if(!firstClick && e.getButton()==MouseEvent.BUTTON3)
            {
                if(!buttons[clickR][clickC].isSelected()){
                    if(buttons[clickR][clickC].getIcon()==null){
                        buttons[clickR][clickC].setIcon(flag);
                        buttons[clickR][clickC].setDisabledIcon(flag);
                        buttons[clickR][clickC].setEnabled(false);
                    }else{
                        buttons[clickR][clickC].setIcon(null);
                        buttons[clickR][clickC].setDisabledIcon(null);
                        buttons[clickR][clickC].setEnabled(true);
                    }
                }
            }
        }
    }

    public void expand(int row, int col){
        if(!buttons[row][col].isSelected()){
            numClicks++;
            buttons[row][col].setSelected(true);
        }
        int state = (int)(buttons[row][col].getClientProperty("mineVal"));
        if(state>0){
            buttons[row][col].setIcon(numbers[state-1]);
            buttons[row][col].setDisabledIcon(numbers[state-1]);
            buttons[row][col].setEnabled(false);
        }
        else{
            for(int r = row-1; r<=row+1; r++){
                for(int c = col-1; c<=col+1; c++){
                    try{
                        if(!buttons[r][c].isSelected())
                            expand(r,c);
                    }catch(ArrayIndexOutOfBoundsException e){

                    }
                }
            }
        }
    }
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()== beginner){
            numMines = 10;
            rows = 9;
            cols = 9;
            numClicks = 0;
            clear();
        }
        if(e.getSource()== inter){
            numMines = 40;
            rows = 16;
            cols = 16;
            numClicks = 0;
            clear();
        }
        if(e.getSource()== expert){
            numMines = 99;
            rows = 16;
            cols = 40;
            numClicks = 0;
            clear();
        }
        if(e.getSource()== reset){
            clear();
        }
        if(timer!=null){
            timer.cancel();
            timePassed = 0;
            timeField.setText(" "+timePassed);
        }
        frame.revalidate();

    }
    public void clear(){
        timer.cancel();
        timePassed = 0;
        timeField.setText(" "+timePassed);
        numClicks = 0;
        setGrid(rows, cols);
        gameOver = false;
        firstClick = true;
    }
    public void mouseExited(MouseEvent e){}
    public void mouseEntered(MouseEvent e){}
    public void mousePressed(MouseEvent e){}
    public void mouseClicked(MouseEvent e){}
    public class UpdateTimer extends TimerTask{
        public void run(){
            if(!gameOver){
                timePassed++;
                timeField.setText(" "+timePassed);
            }
        }
    }
    public static void main(String[] args)
    {
        Minesweeper game = new Minesweeper();
    }
}
