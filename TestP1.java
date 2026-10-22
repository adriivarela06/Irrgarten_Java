package irrgarten;

import java.util.ArrayList;

public class TestP1 {
    public static void main(String[] args){     
        Directions up = Directions.UP, 
                   down = Directions.DOWN,
                   right = Directions.RIGHT, 
                   left = Directions.LEFT;
        
        Orientation vertical = Orientation.VERTICAL,
                    horizontal = Orientation.HORIZONTAL;
        
        GameCharacter jugador = GameCharacter.PLAYER,
                      monstruo = GameCharacter.MONSTER;
        
        Weapon arma1 = new Weapon(2.5f, 5),
               arma2 = new Weapon(3.2f, 0);
        
        Shield escudo1 = new Shield(4.3f, 0),
               escudo2 = new Shield(2.5f, 1);
        
        float ataque = arma1.attack();
        String arma = arma1.toString();
        boolean descartar = arma1.discard();
        
        Dice dice = new Dice();
        
        GameState Game1 = new GameState("Laberinto", "Player", "Monstruo", 18, true, "CR7");
        
        System.out.println(arma1.toString());
        arma1.attack();
        arma1.discard();
        
        System.out.println(arma2.toString());
        arma2.attack();
        arma2.discard();
        
        System.out.println(escudo1.toString());
        escudo1.protect();
        escudo1.discard();
        
        System.out.println(escudo2.toString());
        escudo2.protect();
        escudo2.discard();
        
        for(int i = 0; i < 100; i++)
            System.out.println(dice.randomPos(2));
        for(int i = 0; i < 100; i++)
            System.out.println(dice.randomIntelligence());
        for(int i = 0; i < 100; i++){
            if(dice.resurrectPlayer())
                System.out.println("V");
            else
                System.out.println("F");
        }
        for(int i = 0; i < 100; i++){
            if(dice.discardElement(1))
                System.out.println("V");
            else
                System.out.println("F");
        }    
        
        Monster m = new Monster("Kraken", 3.0f, 7.0f);
        System.out.println("Monster creado (Constructor y toString()): " + m.toString());
        
        m.setPos(10, 5);
        System.out.println("Posición seteada (setPos()): " + m.toString());
        
        System.out.println("¿Monster muerto (dead())? " + m.dead());
        
        float mAttack = m.attack();
        System.out.println("Valor de ataque (attack()): " + mAttack);
        
        
        Player p = new Player('A', 2.5f, 5.5f);
        System.out.println("Player creado (Constructor y toString()): " + p.toString());
        
        System.out.println("Número (getNumber()): " + p.getNumber());
        
        p.setPos(2, 3);
        System.out.println("Posición seteada (setPos()): " + p.toString());
        System.out.println("Fila (getRow()): " + p.getRow() + ", Columna (getCol()): " + p.getCol()); 
        
        System.out.println("¿Player muerto (dead())? " + p.dead());
        
        float pAttack = p.attack();
        System.out.println("Valor de ataque (attack()): " + pAttack);
        
        p.setPos(99, 99); 
        p.resurrect();
        System.out.println("Player resucitado (resurrect()): " + p.toString());
        
        ArrayList<Directions> validos = new ArrayList <>();
        validos.add(Directions.DOWN);
        validos.add(Directions.LEFT);
        p.move(Directions.UP, validos);
        p.move(Directions.DOWN, validos);
        
    }
}