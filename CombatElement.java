package irrgarten;

public abstract class CombatElement{
    private float effect;
    private int uses;

    public CombatElement(float effect, int uses) {
        this.effect = effect;
        this.uses = uses;
    }
    
    protected float produceEffect(){
        float pow;
        if(this.uses > 0){
            this.uses--;
            pow = this.effect;
        }
        else
            pow = 0;
        return pow;
    }
    
    public boolean discard(){
        return Dice.discardElement(this.uses);
    }
    
    public String toString(){
        return "[" + this.effect + ", " + this.uses + "]";
    }
}
