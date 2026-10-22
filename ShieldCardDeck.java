
package irrgarten;

public class ShieldCardDeck extends CardDeck<Shield>{
    @Override
    protected void addCards(){
        for(int i = 0; i < ShieldCardDeck.TAM_CARDDECK; i++)
            this.addCard(new Shield(Dice.shieldPower(), Dice.usesLeft()));
    }
}
