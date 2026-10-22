package irrgarten;

public abstract class LabyrinthCharacter {
    private String name;
    private float intelligence;
    private float strength;
    private float health;
    private int row;
    private int col;

    public LabyrinthCharacter(String name, float intelligence, float strength, float health){
        this.name = name;
        this.intelligence = intelligence;
        this.strength = strength;
        this.health = health;
        this.row = -1;
        this.col = -1;
    }
    
    public LabyrinthCharacter(LabyrinthCharacter other){
        this.name = other.name;
        this.intelligence = other.intelligence;
        this.strength = other.strength;
        this.health = other.health;
        this.row = other.row;
        this.col = other.col;
    }
    
    public boolean dead(){
        return this.health <= 0;
    }
    
    public int getRow(){
        return this.row;
    }
    
    public int getCol(){
        return this.col;
    }
    
    protected float getIntelligence(){
        return this.intelligence;
    }
    
    protected float getStrength(){
        return this.strength;
    }
    
    protected float getHealth(){
        return this.health;
    }
    
    protected void setHealth(float health){
        this.health = health;
    }
    
    public void setPos(int row, int col){
        this.row = row;
        this.col = col;
    }
    
    public String toString(){
        return "Nombre: " + this.name + ", Inteligencia: " + this.intelligence + ", Fuerza: " + this.strength + ", Vida: " + this.health + ", Posicion: (" + this.row + ", " + this.col + ")\n";
    }
    
    protected void gotWounded(){
        this.health--;
    }
    
    public abstract float attack();
    
    public abstract boolean defend(float attack);
    
}
