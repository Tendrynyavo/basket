package mouse;

import graphical.Basket;
import joueur.Joueur;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Click implements MouseListener {
    
    Joueur joueur;
    Basket basket;

    public Joueur getJoueur() {
        return joueur;
    }
    
    public void setJoueur(Joueur joueur) {
        this.joueur = joueur;
    }

    public Click(Joueur joueur, Basket basket) {
        setJoueur(joueur);
        setBasket(basket);
    }

    public Basket getBasket() {
        return basket;
    }

    public void setBasket(Basket basket) {
        this.basket = basket;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        getBasket().getTypeListener().setJoueur(getJoueur());
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}