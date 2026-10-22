
package irrgarten;
import java.util.ArrayList;


public class Game {
    private static final int MAX_ROUNDS = 10;
    
    private int currentPlayerIndex;
    private String log;
    private Player currentPlayer;
    private ArrayList<Player> players;
    private ArrayList<Monster> monsters;
    private Labyrinth labyrinth;
    
   
    public Game(int nplayers){
        this.players = new ArrayList<>();
       
        this.log = "Comienza la partida \n";
        this.labyrinth = new Labyrinth(10,10,Dice.randomPos(10),Dice.randomPos(10));
        this.monsters = new ArrayList<>();
        for(int i = 0; i < nplayers; i++){
            this.players.add(new Player((char) ('0' + i), Dice.randomIntelligence(), Dice.randomStrength()));
        }
        this.currentPlayerIndex = Dice.whoStarts(nplayers);
        this.currentPlayer = this.players.get(this.currentPlayerIndex);
        
        this.configureLabyrinth();
        this.labyrinth.spreadPlayers(players);
    }
    
    public boolean finished(){
        return this.labyrinth.haveAWinner();
    }
    
    public GameState getGameState(){
        
        String labyrinthState = this.labyrinth.toString(); 
        String playersState = this.players.toString();
        String monstersState = this.monsters.toString();
        int currentPlayer = this.currentPlayerIndex;
        boolean winner = this.finished();
        String logState = this.log;

        return new GameState(
            labyrinthState, 
            playersState, 
            monstersState, 
            currentPlayer, 
            winner, 
            logState
        );
    }
    
  
    private void configureLabyrinth(){
        int row = 0, col = 0;
       
        for(int i = 0; i < Dice.randomPos(labyrinth.getRow() - 1); i++){
            boolean libre = false;
            while(!libre){
                row = Dice.randomPos(this.labyrinth.getRow() - 1);
                col = Dice.randomPos(this.labyrinth.getCol() - 1);
                if(this.labyrinth.getCasilla(row, col) == '-')
                    libre = true;
            }
            
            String nombre = "Monster" + i;
            Monster monster = new Monster(nombre, Dice.randomIntelligence(), Dice.randomStrength());
            monster.setPos(row, col);
            this.monsters.add(monster);
            this.labyrinth.addMonster(row, col, monster);
        }
        
        for(int i = 0; i < 10; i++){
            Orientation orientation;
            if(i % 2 == 0)
                orientation = Orientation.HORIZONTAL;
            else
                orientation = Orientation.VERTICAL;
           
            boolean libre = false;
            while(!libre){
                row = Dice.randomPos(this.labyrinth.getRow() - 1);
                col = Dice.randomPos(this.labyrinth.getCol() - 1);
                if(this.labyrinth.getCasilla(row, col) == '-')
                    libre = true;
            }
            this.labyrinth.addBlock(orientation, row, col, 1);
        }
        
        
    }
    
    private void nextPlayer(){
        this.currentPlayerIndex = (this.currentPlayerIndex + 1) % this.players.size();
        this.currentPlayer = this.players.get(this.currentPlayerIndex);
    }
    
    private GameCharacter combat(Monster monster){
        int rounds = 0;
        GameCharacter winner = GameCharacter.PLAYER;
        float playerAttack = this.currentPlayer.attack();
        boolean lose = monster.defend(playerAttack);
        
        while (!lose && rounds < Game.MAX_ROUNDS){
            winner = GameCharacter.MONSTER;
            rounds++;
            float monsterAttack = monster.attack();
            lose = this.currentPlayer.defend((monsterAttack));
           
            if(!lose){
               playerAttack = this.currentPlayer.attack();
               winner = GameCharacter.PLAYER;
               monster.defend(playerAttack);
            }
        } 
        this.logRounds(rounds, Game.MAX_ROUNDS);
        
        return winner;
    }
   
    private void manageResurrection(){
        boolean resurrect = Dice.resurrectPlayer();
        if(resurrect){
            this.currentPlayer.resurrect();
            FuzzyPlayer fp = new FuzzyPlayer(this.currentPlayer);
            this.currentPlayer = fp;
            this.players.set(this.currentPlayerIndex,fp);
            this.labyrinth.putFuzzyPlayer(fp);
            this.logResurrected();
        }
        else
            this.logPlayerSkipTurn();
    }
    
    private void manageReward(GameCharacter winner){
        if(winner == GameCharacter.PLAYER){
            this.currentPlayer.receiveReward();
            this.logPlayerWon();
        }
        else
            this.logMonsterWon();
    }
    
   public boolean nextStep(Directions preferredDirecton){
        this.log = "";
        boolean dead = this.currentPlayer.dead();
        if(!dead){
            Directions direction = this.actualDirection(preferredDirecton);
            if(direction != preferredDirecton)
                this.logPlayerNoOrders();
            
            Monster monster = this.labyrinth.putPlayer(direction, this.currentPlayer);
            if(monster == null)
                this.logNoMonster();
            else{
                GameCharacter winner = this.combat(monster);
                this.manageReward(winner);
            }
        }
        else
            this.manageResurrection();
        
        boolean endGame = this.finished();
        if(!endGame)
            this.nextPlayer();
        
        return endGame;
    }
    
    private Directions actualDirection(Directions preferredDirection){
        int currentRow = this.currentPlayer.getRow();
        int currentCol = this.currentPlayer.getCol();
        ArrayList<Directions> validMoves = this.labyrinth.validMoves(currentRow, currentCol);
        return this.currentPlayer.move(preferredDirection, validMoves);
    }
    
    
    private void logPlayerWon(){
        log += "El jugador ha ganado el combate\n";
    }
    
    private void logMonsterWon(){
        log += "El monstruo ha ganado el combate\n";
    }
    
    private void logResurrected(){
        log += "El jugador ha resucitado ahora es un fuzzyplayer\n";
    }
    
    private void logPlayerSkipTurn(){
        log += "El jugador ha perdido el turno por estar muerto\n";
    }
    
    private void logPlayerNoOrders(){
        log += " El jugador no ha seguido las instrucciones del jugador humano\n";
    }
    
    private void logNoMonster(){
        log += " El jugador se ha movido a una celda vacia o no le ha sido posible moverse\n";
    }
    
    private void logRounds(int rounds,int max){
        log += " Se han producido" + rounds + "de" + max + "rondas de combate\n";
    }
}
