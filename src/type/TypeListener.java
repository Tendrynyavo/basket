package type;

import joueur.Joueur;
import match.Match;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class TypeListener implements KeyListener {

    Joueur joueur;
    Joueur previous;
    Match match;
    int debut;

    public int getDebut() {
        return debut;
    }

    public void setDebut(int debut) {
        this.debut = debut;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public Joueur getJoueur() {
        return joueur;
    }

    public void setJoueur(Joueur joueur) {
        this.joueur = joueur;
    }

    public Joueur getPrevious() {
        return previous;
    }

    public void setPrevious(Joueur previous) {
        this.previous = previous;
    }

    public TypeListener(Match match) {
        setMatch(match);
    }

    public void keyTyped(KeyEvent e) {
        if (getJoueur() != null) {
            try {
                switch (e.getKeyChar()) {
                    case 'b':
                        if (getPrevious() == null) {
                            getMatch().getChrono().start();
                            getMatch().setDebut(0);
                            getJoueur().changePossession(getJoueur(), getMatch());
                        } else getPrevious().changePossession(getJoueur(), getMatch());
                        break;
                    case 't':
                        getJoueur().shoot(getMatch());
                        break;
                    case 'm':
                        getJoueur().marque(getMatch());
                        break;
                }
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(new JFrame(), exception.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(new JFrame(), "Selectionner un Joueur");
        }
    }

    public void keyPressed(KeyEvent e) {

    }

    public void keyReleased(KeyEvent e) {

    }
}