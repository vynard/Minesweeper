import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;

public class MineFlagPanel extends JPanel {
    private MineEngine board;
    JLabel flagCount;

    public MineFlagPanel(MinesweeperFrame f){
        board = f.getBoard();
        setBackground(new ColorUIResource(135, 206, 235));
        setLayout(new BorderLayout());
        flagCount = new JLabel("Flags: " + board.getFlags() + "  ");
        flagCount.setFont(new Font("Caslon", Font.BOLD, 14));
        flagCount.setForeground(new ColorUIResource(231, 84, 128));
        add(flagCount, BorderLayout.EAST);
    }

    public void updatesFlags(){
        flagCount.setText("Flags: " + board.getFlags() + "  ");
    }
}
