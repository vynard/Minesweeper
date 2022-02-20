import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.plaf.ColorUIResource;
//import java.util.*;


public class MinesweeperPanel extends JPanel {

    private MineEngine board;
    private MinesweeperFrame frame;
    private MineLabel[][] buttons;
    private Toolkit kit;
    private String flagIcon;
    private MineFlagPanel boardFlagPanel;

    public MinesweeperPanel(MinesweeperFrame setframe, MineFlagPanel flagPanel) {
        frame = setframe;
        boardFlagPanel = flagPanel;
        board = frame.getBoard();
        buttons = new MineLabel[board.getCols()][board.getRows()];
        setLayout(new GridLayout(board.getRows(), board.getCols()));
        kit = Toolkit.getDefaultToolkit();
        flagIcon = "flag.png";

        board.setBoard(board.getCols(), board.getRows(), board.getMines());
        createLabels();
    }

    private void createSingleLabel(int col, int row){
        MineLabel button = new MineLabel("", SwingConstants.CENTER, col, row);
        button.addMouseListener(new MouseHandler());
        Border border = BorderFactory.createLineBorder(Color.BLACK, 2);
        button.setBorder(border);
        button.setForeground(Color.GRAY);
        button.setBackground(new ColorUIResource(126, 200, 80));
        button.setOpaque(true);
        button.setFont(new Font("Caslon", Font.BOLD, 18));
        buttons[col][row] = button;
        add(buttons[col][row]);
    }

    private void createLabels(){
        int col, row;
        for(row = 0; row<board.getRows(); row++){
            for(col = 0; col<board.getCols(); col++){
                createSingleLabel(col, row);
            }
        }
    }

    //was originally trying to make the game replayable with wins or loses, but couldn't figure it out.
    //private void removeLabels(){
        //int col, row;
        //for(row = 0; row<board.getRows(); row++){
            //for(col = 0; col<board.getCols(); col++){
                //remove(buttons[col][row]);
                //createSingleLabel(col, row);
            //}
        //}
    //}


    private void leftClick(MineLabel button){
        int col = button.getCol();
        int row = button.getRow();

        if(board.hasFlag(col, row)){
            return;
        }
        if(board.isRevealed(col, row)){
            return;
        }
        
        button.setBackground(new ColorUIResource(138, 107, 61));
        if(board.getTile(col, row) >= 0 && board.getTile(col, row) <= 2){
            button.setForeground(Color.BLUE);
        }
        else if(board.getTile(col, row) <= 5){
            button.setForeground(Color.RED);
        }
        else{
            button.setForeground(new ColorUIResource(128, 0, 128));
        }
        kit.sync();
        button.setText("" + board.getTile(col, row));
        if(board.getTile(col, row)==9){
            button.setText("m");
            loseWindow();;
        }
        board.revealTile(col, row);
        kit.sync();
        if(board.getRemainingSafeTiles(col, row) == 0){
            winWindow();
        }

        //int checkLeft, checkRight, checkTop, checkBottom;
        if(board.getTile(col, row) == 0){
            removeZeros(col, row, button);
        }
    }

    private void removeZeros(int col, int row, MineLabel button){
        int checkLeft, checkRight, checkTop, checkBottom;
        checkLeft = col - 1;
        checkRight = col + 1;
        checkTop = row - 1;
        checkBottom = row + 1;

        if(col==0 && row!=0 && row!= board.getRows()-1){
            leftClick(buttons[checkRight][row]);
            leftClick(buttons[checkRight][checkTop]);
            leftClick(buttons[checkRight][checkBottom]);
            leftClick(buttons[col][checkTop]);
            leftClick(buttons[col][checkBottom]);
        }
        else if(col == 0 && row == 0){
            leftClick(buttons[checkRight][row]);
            leftClick(buttons[checkRight][checkBottom]);
            leftClick(buttons[col][checkBottom]);
        }
        else if(col == 0 && row == board.getRows()-1){
            leftClick(buttons[checkRight][row]);
            leftClick(buttons[checkRight][checkTop]);
            leftClick(buttons[col][checkTop]);
        }
        else if(col==board.getCols()-1 && row!=0 && row!= board.getRows()-1){
            leftClick(buttons[checkLeft][row]);
            leftClick(buttons[checkLeft][checkTop]);
            leftClick(buttons[checkLeft][checkBottom]);
            leftClick(buttons[col][checkTop]);
            leftClick(buttons[col][checkBottom]);
        }
        else if(col == board.getCols()-1 && row == 0){
            leftClick(buttons[checkLeft][row]);
            leftClick(buttons[checkLeft][checkBottom]);
            leftClick(buttons[col][checkBottom]);
        }
        else if(col == board.getCols()-1 && row == board.getRows()-1){
            leftClick(buttons[checkLeft][row]);
            leftClick(buttons[checkLeft][checkTop]);
            leftClick(buttons[col][checkTop]);
        }
        else if(row==0 && col!=0 && col!= board.getCols()-1){
            leftClick(buttons[checkLeft][checkBottom]);
            leftClick(buttons[col][checkBottom]);
            leftClick(buttons[checkRight][checkBottom]);
            leftClick(buttons[checkLeft][row]);
            leftClick(buttons[checkRight][row]);
        }
        else if(row==board.getRows()-1 && col!=0 && col!= board.getCols()-1){
            leftClick(buttons[checkLeft][checkTop]);
            leftClick(buttons[col][checkTop]);
            leftClick(buttons[checkRight][checkTop]);
            leftClick(buttons[checkLeft][row]);
            leftClick(buttons[checkRight][row]);
        }
        if(col <= 0 || row <= 0 || col >= board.getCols()-1 || row >= board.getRows()-1){
            return;
        }

        leftClick(buttons[checkLeft][row]);
        leftClick(buttons[checkRight][row]);
        leftClick(buttons[checkLeft][checkTop]);
        leftClick(buttons[checkRight][checkTop]);
        leftClick(buttons[checkLeft][checkBottom]);
        leftClick(buttons[checkRight][checkBottom]);
        leftClick(buttons[col][checkTop]);
        leftClick(buttons[col][checkBottom]);
    }


    private void loseWindow(){
        JOptionPane.showMessageDialog(frame, "Boom! You lost! Thanks for playing!", "You lost :(", JOptionPane.PLAIN_MESSAGE);
        System.exit(0);
    }

    private void winWindow(){
        JOptionPane.showMessageDialog(frame, "Congradulations! You win! Thanks for playing!", "Good job :)", JOptionPane.PLAIN_MESSAGE);
        System.exit(0);
    }

    private class MouseHandler implements MouseListener{
        public void mousePressed(MouseEvent me) { }
        public void mouseReleased(MouseEvent me) { }
        public void mouseEntered(MouseEvent me) { }
        public void mouseExited(MouseEvent me) { }

        public void mouseClicked(MouseEvent me) { 
            MineLabel source = (MineLabel)me.getSource();
            int tile_x = source.getCol();
            int tile_y = source.getRow();
            //int checkLeft, checkRight, checkTop, checkBottom, checkCols, checkRows;

            if(me.getButton() == MouseEvent.BUTTON1) {
                leftClick(buttons[tile_x][tile_y]);
                //if(board.isGameWon()){
                  //  winWindow();
                //}
                //if(board.isGameLost()){
                   // loseWindow();
                //}
            }

            if(me.getButton() == MouseEvent.BUTTON3) {
                if(board.isRevealed(tile_x, tile_y)){
                    return;
                }
                board.changeFlag(tile_x, tile_y);
                boardFlagPanel.updatesFlags();

                if(board.hasFlag(tile_x, tile_y)){
                    source.setIcon(new ImageIcon(flagIcon));
                    return;
                }
                source.setIcon(null);
            }
        }

    }

}
