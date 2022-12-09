package mouse;

import formulaire.Formulaire;
import graphical.Basket;
import match.Match;
import time.Chrono;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Date;

public class Start implements MouseListener {

    Formulaire formulaire;
    public Start(Formulaire formulaire) {
        setFormulaire(formulaire);
    }

    public Formulaire getFormulaire() {
        return formulaire;
    }

    public void setFormulaire(Formulaire formulaire) {
        this.formulaire = formulaire;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        String[] values = getFormulaire().getText();
        try {
            Match match = new Match(values[1], values[2], new Date(System.currentTimeMillis()));
            match.insert(null);
            match.setEquipes();
            match.setChrono(new Chrono());
            new Basket(match);
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(new JFrame(), exception.getMessage());
        }
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