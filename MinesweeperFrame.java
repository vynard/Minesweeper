import javax.swing.*;
import java.awt.*;

public class MinesweeperFrame extends JFrame {
    private MineEngine board;
    private int numMines=-1;
    private int boardWidth=-1;
    private int boardHeight=-1;

    public MinesweeperFrame() {
        setTitle("Minesweeper");
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screensize = kit.getScreenSize();
        setSize(screensize.width/2, screensize.height*2/3);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createBoard();
        board = new MineEngine(boardWidth, boardHeight, numMines);
        setLayout(new BorderLayout());
        //this.add()
        MineFlagPanel flagPanel = new MineFlagPanel(this);
        add(flagPanel, BorderLayout.NORTH);
        add(new MinesweeperPanel(this, flagPanel), BorderLayout.CENTER);
        setVisible(true);
    }

    private void createBoard(){
        while(boardWidth<3 || boardWidth>30){
            try{
                boardWidth = Integer.parseInt(JOptionPane.showInputDialog(this, "Please input the width of the game board (between 3 and 30): "));
            }
            catch(NumberFormatException e){
                continue;
            }
        }
        while(boardHeight<3 || boardHeight>30){
            try{
            boardHeight = Integer.parseInt(JOptionPane.showInputDialog(this, "Please input the height of the game board (between 3 and 30): "));
            }
            catch(NumberFormatException e){
                continue;
            }

        }
        while(numMines < 1 || numMines > boardWidth*boardHeight-1){
            try{
            numMines = Integer.parseInt(JOptionPane.showInputDialog(this, "Please input the number of mines on the game board (between 1 and " + (boardWidth*boardHeight-1) + ")\nRecommended " + (int)(boardWidth*boardHeight*.2) + ": "));
            }
            catch(NumberFormatException e){
                continue;
            }

        }
    }

    public MineEngine getBoard(){
        return board;
    }

    public void setCols(int cols){
        boardWidth = cols;
    }

    public void setRows(int rows){
        boardHeight = rows;
    }

    public void setMines(int mines){
        numMines = mines;
    }
}
