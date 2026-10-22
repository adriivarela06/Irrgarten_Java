package irrgarten;

public class WeaponCardDeck extends CardDeck<Weapon>{
    @Override
    protected void addCards(){
        for(int i = 0; i < WeaponCardDeck.TAM_CARDDECK; i++)
            this.addCard(new Weapon(Dice.weaponPower(), Dice.usesLeft()));
    }
}
