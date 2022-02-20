import javax.swing.*;

public class MineLabel extends JLabel{
    private int row;
    private int col;

    public MineLabel(String arg0, int arg1, int cols, int rows){
        super(arg0, arg1);
        col = cols;
        row = rows;
    }

    public int getCol(){
        return col;
    }
    public int getRow(){
        return row;
    }
}