import java.lang.Math;

public class MineEngine {
    private int numCols;
    private int numRows;
    private int numMines;
    private int safeTilesRemaining;
    private int[][] tiles;
    private boolean[][] isFlag;
    private boolean[][] hasRevealed;
    private int numFlagsRemaining;

    private boolean gameLost;
    private boolean gameWon;

    public MineEngine(int cols, int rows, int mines){
        setBoard(cols, rows, mines);
    }

    /**
     * setBoard will create a new board of MineSweeper. It'll put minds in random tiles and then count those mines on every tile around them. Call this anytime you want to start a new game.
     */
    public void setBoard(int cols, int rows, int mines){
        numCols = cols;
        numRows = rows;
        numMines = mines;
        numFlagsRemaining = mines;

        tiles = new int[numCols][numRows];
        isFlag = new boolean[numCols][numRows];
        hasRevealed = new boolean[numCols][numRows];

        fillTiles();
        putMines();
        countMines();
        gameLost = false;
        gameWon = false;
        safeTilesRemaining = (numRows*numCols) - numMines;
    }

    private void fillTiles(){
        int col, row;
        for (col = 0; col<numCols; col++){
            for(row = 0; row<numRows; row++){
                tiles[col][row] = 0;
                isFlag[col][row] = false;
                hasRevealed[col][row] = false;
            }
        }
    }

    private void putMines(){
        int col, row;
        int rand;
        int mineCounter = numMines;

        while(mineCounter>0){
            for(col = 0; col < numCols; col++){
                for(row = 0; row < numRows; row++){
                    rand = (int)(Math.random() * (numCols*numRows));
                    if(rand==0 && tiles[col][row]!=9){
                        tiles[col][row] = 9;
                        mineCounter--;
                    }

                    if(mineCounter==0){
                        return;
                    }
                }
            }
        }
        
    }

    private void countMines(){
        int surroundingMines;
        int col, row, checkCol, checkRow, leftCol, rightCol, topRow, bottomRow;

        for(col = 0; col<numCols; col++){
            for(row = 0; row<numRows; row++){
                surroundingMines = 0;

                if(tiles[col][row] == 9){
                    continue;
                }

                leftCol = col-1;
                rightCol = col+1;
                topRow = row-1;
                bottomRow = row+1;
                if(col == 0){
                    leftCol = col;
                }
                if(col == numCols-1){
                    rightCol = col;
                }
                if(row == 0){
                    topRow = row;
                }
                if(row == numRows-1){
                    bottomRow = row;
                }

                for(checkCol = leftCol; checkCol<=rightCol; checkCol++){
                    for(checkRow = topRow; checkRow<=bottomRow; checkRow++){
                        if(!(checkCol==col && checkRow==row) && tiles[checkCol][checkRow] == 9){
                            surroundingMines++;
                        }
                    }
                }

                tiles[col][row] = surroundingMines;
            }
        }
    }

    public int getMines(){
        return numMines;
    }

    public int getRows(){
        return numRows;
    }

    public int getCols(){
        return numCols;
    }

    public boolean hasFlag(int col, int row){
        return isFlag[col][row];
    }

    public void changeFlag(int col, int row){
        if(isFlag[col][row] == false){
            if(numFlagsRemaining==0){
                return;
            }
            isFlag[col][row] = true;
            numFlagsRemaining--;
        } else{
            isFlag[col][row] = false;
            numFlagsRemaining++;
        }
    }

    public int getFlags(){
        return numFlagsRemaining;
    }

    public boolean isMine(int col, int row){
        if(tiles[col][row] == 9){
            return true;
        }
        return false;
    }

    public int getTile(int col, int row){
        return tiles[col][row];
    }

    public boolean isRevealed(int col, int row){
        return hasRevealed[col][row];
    }

    public void revealTile(int col, int row){
        if(tiles[col][row]!=9){
            safeTilesRemaining--;
        }
        hasRevealed[col][row] = true;
    }

    public int getRemainingSafeTiles(int col, int row){
        return safeTilesRemaining;
    }

    public boolean isGameWon(){
        return gameWon;
    }

    public void winGame(){
        gameWon = true;
    }
    
    public boolean isGameLost(){
        return gameLost;
    }

    public void loseGame(){
        gameLost = true;
    }
}
