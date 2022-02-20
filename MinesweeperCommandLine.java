import java.util.*;

public class MinesweeperCommandLine {
    public static void main(String[] args){
        int cols = Integer.parseInt(args[0]);
        int rows = Integer.parseInt(args[1]);
        int mines = Integer.parseInt(args[2]);
        int col, row;
        Scanner input = new Scanner(System.in);

        MineEngine board = new MineEngine(cols, rows, mines);

        while(!board.isGameLost() && !board.isGameWon()){
            for(col = 0; col<cols; col++){
                if(rows<10 && col==0){
                    System.out.print("    " + (col+1));
                }
                else if(rows>= 10 && col==0){
                    System.out.print("     " + (col+1));
                }
                else{
                    System.out.print(" " + (col+1));
                }
            }
            System.out.print("\n    ");
            for(col = 0; col<cols; col++){
                System.out.print("--");
            }
            System.out.println("");

            for(row = 0; row<rows; row++){
                if(rows<10){
                    System.out.print((row+1) + " |");
                }
                else{
                    if (row<9){
                        System.out.print((row+1) + "  |");
                    }
                    else{
                        System.out.print((row+1) + " |");
                    }
                }

                for(col = 0; col<cols; col++){
                    if(board.hasFlag(col, row)){
                        System.out.print(" f");
                        continue;
                    }
                    if(!board.isRevealed(col, row)){
                        System.out.print(" ?");
                        continue;
                    }
                    if(board.getTile(col, row) == 9){
                        System.out.print(" m");
                    }

                    System.out.print(" " + board.getTile(col, row));
                }
                System.out.println("");
            }

            System.out.print("\nInput in form (g/f) x y: ");
            String action = input.next();
            col = input.nextInt()-1;
            row = input.nextInt()-1;
            System.out.println("");

            if(action.equals("g")){
                if(!board.hasFlag(col, row)){
                    board.revealTile(col, row);
                    if(board.isMine(col, row)){
                        board.loseGame();
                    }
                    if(board.getRemainingSafeTiles(col, row) == 0){
                        board.winGame();
                    }
                }
            }

            if(action.equals("f")){
                if(!board.isRevealed(col, row)){
                    board.changeFlag(col, row);
                }
            }

            if(board.isGameLost()){
                System.out.println("Boom! You hit a mine! You lost!");
            }
            if(board.isGameWon()){
                System.out.println("All safe tiles uncovered! You win! Congradulations!");
            }

        }

        input.close();
    }
}
