package irrgarten;
import java.util.ArrayList;

public class Player extends LabyrinthCharacter{
    
    //Atributos de clase
    private static final int MAX_WEAPONS = 2;
    private static final int MAX_SHIELD = 3;
    private static final int INITIAL_HEALTH = 1;
    private static final int HITS2LOSE = 3;
    
    //Atributos de instancia
    private char number;
    private int consecutiveHits;
    
    private ArrayList<Weapon> weapons;
    private ArrayList<Shield> shields;
    
    private WeaponCardDeck weaponCardDeck;
    private ShieldCardDeck shieldCardDeck;
    
    //Metodos de instancia
    
    public Player(char number , float intelligence , float strength){
        super("Player #" + number, intelligence, strength, Player.INITIAL_HEALTH);
        this.number = number;
        this.consecutiveHits = 0;
      
        this.weapons = new ArrayList<>();
            //this.weapons.add(new Weapon(3.0f,2));
            //this.weapons.add(new Weapon(4.5f,3));
        this.shields = new ArrayList<>();
      
        this.weaponCardDeck = new WeaponCardDeck();
        this.shieldCardDeck = new ShieldCardDeck();
    }
    
    public Player(Player other){
        super(other);
        this.number = other.number;
        this.consecutiveHits = 0;
        
        this.weapons = other.weapons;
        this.shields = other.shields;
        
        this.weaponCardDeck = other.weaponCardDeck;
        this.shieldCardDeck = other.shieldCardDeck;
    }
    
    public void resurrect(){
        this.weapons.clear();  
        this.shields.clear(); 
        this.setHealth(Player.INITIAL_HEALTH);
        this.consecutiveHits = 0;
   }
    
    public char getNumber(){
        return this.number;
    }
    
    public Directions move(Directions direction, ArrayList<Directions> validMoves){
        int size = validMoves.size();
        boolean contained = validMoves.contains(direction);
        
        if(size > 0 && !contained)
            return validMoves.get(0);
        else
            return direction;     
    }
    
    @Override
    public float attack(){
        return this.getStrength() + this.sumWeapons();
    }
    
    @Override
    public boolean defend(float receivedAttack){
        return this.manageHit(receivedAttack);  
    }
    
    public void receiveReward(){
        int wReward = Dice.weaponsReward();
        int sReward = Dice.shieldsReward();
        
        for(int i=0; i< wReward; i++){
            Weapon wnew = this.newWeapon();
            this.receiveWeapon(wnew);
        }
        
        for(int i=0; i< sReward; i++){
            Shield snew = this.newShield();
            this.receiveShield(snew);
        }
        
        int extraHealth = Dice.healthReward();
        this.setHealth(this.getHealth() + extraHealth);
    }
    
    @Override
    public String toString(){
        return "Numero: " + this.number + ", " + super.toString() +", Golpes consecutivos: " + this.consecutiveHits;
    }
    
    private void receiveWeapon(Weapon w){
       for(int i = weapons.size()-1; i >= 0; i--){
           Weapon wi = this.weapons.get(i);
           boolean discard = wi.discard();
           if(discard)
               this.weapons.remove(i);
       }
       
       int size = this.weapons.size();
       if(size < Player.MAX_WEAPONS)
           this.weapons.add(w);
    }
    
    private void receiveShield(Shield s){
       for(int i = this.shields.size()-1; i >= 0; i--){
           Shield si = this.shields.get(i);
           boolean discard = si.discard();
           if(discard)
               this.shields.remove(i);
       }
       
       int size = this.shields.size();
       if(size < Player.MAX_SHIELD)
           this.shields.add(s);
    }
    
    private Weapon newWeapon(){
        return this.weaponCardDeck.nextCard();
    }
    
    private Shield newShield(){
        return this.shieldCardDeck.nextCard();
    }
    
    protected float sumWeapons(){
        float sum = 0.0f;
        for (Weapon arma: this.weapons)
            sum += arma.attack();
        
        return sum;
    }
    
    protected float sumShields(){
        float sum = 0.0f;
        for (Shield escudo: this.shields)
            sum += escudo.protect();
        
        return sum;
    }
    
    protected float defensiveEnergy(){
        return this.getIntelligence() + this.sumShields();
    }
    
    private boolean manageHit(float receivedAttack){
        float defense = this.defensiveEnergy();
        
        if(defense < receivedAttack){
            this.gotWounded();
            this.incConsecutiveHits();
        }
        else
            this.resetHits();
        
        if(this.consecutiveHits == Player.HITS2LOSE || this.dead()){
            this.resetHits();
            return true;
        }
        else
            return false;
    }
    
    private void resetHits(){
        this.consecutiveHits = 0;
    }
    
    private void incConsecutiveHits(){
        this.consecutiveHits++;  
    }
 }
