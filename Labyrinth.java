package irrgarten;

import java.util.ArrayList;

public class Labyrinth {

    private static final char BLOCK_CHAR = 'X';
    private static final char EMPTY_CHAR = '-';
    private static final char MONSTER_CHAR = 'M';
    private static final char COMBAT_CHAR = 'C';
    private static final char EXIT_CHAR = 'E';
    private static final int ROW = 0;
    private static final int COL = 1;

    private int nRows;
    private int nCols;
    private int exitRow;
    private int exitCol;

    private char labyrinth[][];

    private Monster monsters[][];
    private Player players[][];

    public Labyrinth(int nRows, int nCols, int exitRow, int exitCol) {
        this.nRows = nRows;
        this.nCols = nCols;
        this.exitRow = exitRow;
        this.exitCol = exitCol;

        this.labyrinth = new char[this.nRows][this.nCols];
        this.monsters = new Monster[this.nRows][this.nCols];
        this.players = new Player[this.nRows][this.nCols];
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {
                this.labyrinth[i][j] = Labyrinth.EMPTY_CHAR;
                this.monsters[i][j] = null;
                this.players[i][j] = null;
            }
        }
        this.labyrinth[exitRow][exitCol] = Labyrinth.EXIT_CHAR;
    }

    public int getRow() {
        return this.nRows;
    }

    public int getCol() {
        return this.nCols;
    }

    public char getCasilla(int row, int col) {
        return this.labyrinth[row][col];
    }

    public boolean haveAWinner() {
        return (this.players[this.exitRow][this.exitCol] != null);
    }

    public void spreadPlayers(ArrayList<Player> players) {
        for (int i = 0; i < players.size(); i++) {
            Player p = players.get(i);
            int[] pos = this.randomEmptyPos();
            this.putPlayer2D(-1, -1, pos[Labyrinth.ROW], pos[Labyrinth.COL], p);
        }
    }

    @Override
    public String toString() {
        String cadena = "";
        for (int i = 0; i < this.nRows; i++) {
            cadena += "|";
            for (int j = 0; j < this.nCols; j++) {
                cadena += " " + this.labyrinth[i][j] + " ";
            }
            cadena += "|\n";
        }
        return cadena;
    }

    public void addMonster(int row, int col, Monster monster) {
        this.labyrinth[row][col] = Labyrinth.MONSTER_CHAR;
        this.monsters[row][col] = monster;
    }

    public void addBlock(Orientation orientation, int startRow, int startCol, int length) {
        int incRow, incCol;
        if (orientation == Orientation.VERTICAL) {
            incRow = 1;
            incCol = 0;
        } else {
            incRow = 0;
            incCol = 1;
        }
        int row = startRow,
                col = startCol;

        while (this.posOK(row, col) && this.emptyPos(row, col) && length > 0) {
            this.labyrinth[row][col] = Labyrinth.BLOCK_CHAR;
            length--;
            row += incRow;
            col += incCol;
        }
    }

    public ArrayList<Directions> validMoves(int row, int col) {
        ArrayList<Directions> output = new ArrayList<>();

        if (this.canStepOn(row + 1, col)) {
            output.add(Directions.DOWN);
        }

        if (this.canStepOn(row - 1, col)) {
            output.add(Directions.UP);
        }

        if (this.canStepOn(row, col + 1)) {
            output.add(Directions.RIGHT);
        }

        if (this.canStepOn(row, col - 1)) {
            output.add(Directions.LEFT);
        }

        return output;
    }

    public Monster putPlayer(Directions direction, Player player) {
        int oldRow = player.getRow();
        int oldCol = player.getCol();
        int newPos[] = this.dir2Pos(oldRow, oldCol, direction);
        Monster monster = putPlayer2D(oldRow, oldCol, newPos[Labyrinth.ROW], newPos[Labyrinth.COL], player);

        return monster;
    }

    private Monster putPlayer2D(int oldRow, int oldCol, int row, int col, Player player) {
        Monster output = null;
        if (this.canStepOn(row, col)) {
            if (this.posOK(oldRow, oldCol)) {
                Player p = this.players[oldRow][oldCol];
                if (p == player) {
                    this.updateOldPos(oldRow, oldCol);
                    this.players[oldRow][oldCol] = null;
                }
            }

            boolean monsterPos = this.monsterPos(row, col);
            if (monsterPos) {
                this.labyrinth[row][col] = Labyrinth.COMBAT_CHAR;
                output = this.monsters[row][col];
            } else {
                char number = player.getNumber();
                this.labyrinth[row][col] = number;
            }

            player.setPos(row, col);
            this.players[row][col] = player;

        }

        return output;
    }

    private boolean posOK(int row, int col) {
        boolean output = true;
        if (row >= nRows || row < 0 || col >= nCols || col < 0) {
            output = false;
        }

        return output;
    }

    private boolean emptyPos(int row, int col) {
        return this.labyrinth[row][col] == Labyrinth.EMPTY_CHAR;
    }

    private boolean monsterPos(int row, int col) {
        return this.labyrinth[row][col] == Labyrinth.MONSTER_CHAR;
    }

    private boolean exitPos(int row, int col) {
        return this.labyrinth[row][col] == Labyrinth.EXIT_CHAR;
    }

    private boolean combatPos(int row, int col) {
        if (this.monsters[row][col] != null && this.players[row][col] != null) {
            this.labyrinth[row][col] = this.COMBAT_CHAR;
            return true;
        } else {
            return false;
        }
    }

    private boolean canStepOn(int row, int col) {
        return (this.posOK(row, col) && (this.emptyPos(row, col) || this.monsterPos(row, col) || this.exitPos(row, col)));
    }

    private void updateOldPos(int row, int col) {
        if (this.posOK(row, col)) {
            if (this.labyrinth[row][col] == Labyrinth.COMBAT_CHAR) {
                this.labyrinth[row][col] = Labyrinth.MONSTER_CHAR;
            } else {
                this.labyrinth[row][col] = Labyrinth.EMPTY_CHAR;
            }
        }
    }

    private int[] dir2Pos(int row, int col, Directions direction) {

        switch (direction) {
            case RIGHT:
                col++;
                break;
            case LEFT:
                col--;
                break;
            case UP:
                row--;
                break;
            case DOWN:
                row++;
                break;
        }
        // Simplemente devuelve la nueva coordenada, sin comprobar nada
        return new int[]{row, col};
    }

    private int[] randomEmptyPos() {
        boolean salir = false;
        int row = Dice.randomPos(this.nRows),
                col = Dice.randomPos(this.nCols);
        while (!salir) {
            if (this.emptyPos(row, col)) {
                salir = true;
            } else {
                row = Dice.randomPos(this.nRows);
                col = Dice.randomPos(this.nCols);
            }
        }

        return new int[]{row, col};
    }
    
    public void putFuzzyPlayer(FuzzyPlayer fp){
        int row = fp.getRow();
        int col = fp.getCol();
        if(posOK(row,col)){
            this.players[row][col] = fp; 
            
            this.labyrinth[row][col] = fp.getNumber();
        }
    }

}
