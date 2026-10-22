package irrgarten;

public class Weapon extends CombatElement{    
    public Weapon(float effect, int uses){
        super(effect, uses);
    }
    
    public float attack(){
        return this.produceEffect();
    }
    
    public String toString(){
        return "W" + super.toString();
    }
}
