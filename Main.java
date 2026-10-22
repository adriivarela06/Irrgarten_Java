

package irrgarten;
import irrgarten.UI.TextUI;
import irrgarten.controller.Controller;


public class Main {
    
    public static void main(String[] args) {
        TextUI ui = new TextUI();
        Game game = new Game(1);
        Controller control = new Controller(game ,ui);
        control.play();
        
        
    }
    
}

        