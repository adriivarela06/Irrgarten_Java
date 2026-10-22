package irrgarten;
import java.util.Random;
import java.util.ArrayList;

public class Dice {
    private static final int MAX_USES = 5;
    private static final float MAX_INTELLIGENCE = 10.0f;
    private static final float MAX_STRENGTH = 10.0f;
    private static final float RESURRECT_PROB = 0.3f;
    private static final int WEAPONS_REWARD = 2;
    private static final int SHIELDS_REWARD = 3;
    private static final int HEALTH_REWARD = 5;
    private static final int MAX_ATTACK = 3;
    private static final int MAX_SHIELD = 2;
    private static final Random generator = new Random();
    
    public static int randomPos(int max){
        return Dice.generator.nextInt(max);
    }
    
    public static int whoStarts(int nplayers){
        return Dice.generator.nextInt(nplayers);
    }
    
    public static float randomIntelligence(){
        return Dice.generator.nextFloat(MAX_INTELLIGENCE);
    }
    
    public static float randomStrength(){
        return Dice.generator.nextFloat(MAX_STRENGTH);
    }
    
    public static boolean resurrectPlayer(){
        return Dice.generator.nextFloat(1) <= RESURRECT_PROB;
    }
    
    public static int weaponsReward(){
        return Dice.generator.nextInt(WEAPONS_REWARD);
    }
    
    public static int shieldsReward(){
        return Dice.generator.nextInt(SHIELDS_REWARD);
    }
    
    public static int healthReward(){
        return Dice.generator.nextInt(HEALTH_REWARD);
    }
    
    public static float weaponPower(){
        return Dice.generator.nextInt(MAX_ATTACK);
    }
    
    public static float shieldPower(){
        return Dice.generator.nextInt(MAX_SHIELD);
    }
    
    public static int usesLeft(){
        return Dice.generator.nextInt(MAX_USES);
    }
    
    public static float intensity(float competence){
        return Dice.generator.nextFloat(competence);
    }
    
    public static boolean discardElement(int usesLeft){
        boolean discard;
        if(usesLeft == MAX_USES)
            discard = false;
        else if(usesLeft == 0)
            discard = true;
        else
            discard = Dice.generator.nextInt(MAX_USES) > usesLeft;
        return discard;
    }
    
    public static Directions nextStep(Directions preference, ArrayList<Directions> validMoves, float intelligence) {
        
        if (validMoves.contains(preference)) {
            
            float probPreference = intelligence / MAX_INTELLIGENCE;
            
            if (probPreference > generator.nextFloat()) {                
                return preference; // Ruby: preference
            } else {
                int randIndex = randomPos(validMoves.size());
                return validMoves.get(randIndex);
            }
        } else {
            int randIndex = randomPos(validMoves.size());
            return validMoves.get(randIndex);
        }
    }
}