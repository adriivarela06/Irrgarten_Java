
package irrgarten;
import java.util.ArrayList;

public class FuzzyPlayer extends Player{
    public FuzzyPlayer(Player other){
        super(other);
        
    }
    
    public Directions move(Directions direction, ArrayList<Directions> validMoves){
        return Dice.nextStep(direction,validMoves,getIntelligence());
    }
    
    public float attack(){
        return sumWeapons()+(Dice.intensity(getStrength()));
    }
    
    protected float defensiveEnergy(){
        return Dice.intensity(getIntelligence())+ sumShields();
    }
    
    @Override
    public String toString(){
        return "Fuzzy "+super.toString();
    }
}
