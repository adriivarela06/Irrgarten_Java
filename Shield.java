package irrgarten;

public class Shield extends CombatElement{
    public Shield(float effect, int uses){
        super(effect, uses);
    }
    
    public float protect(){
        return this.produceEffect();
    }
    
    public String toString(){
        return "S" + super.toString();
    }
}
