package graphical;

import equipe.Equipe;
import formulaire.Button;
import formulaire.Champ;
import formulaire.Formulaire;
import joueur.Joueur;
import match.Match;
import mouse.Click;
import type.TypeListener;
import javax.swing.*;
import java.awt.*;

public class Basket extends JFrame {

    int width=950, height=350;
    Match match;
    JPanel[] panels = new JPanel[2];
    TypeListener type;

    @Override
    public int getWidth() {
        return width;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Basket() {
        initFrame();
    }

    public JPanel[] getPanels() {
        return panels;
    }

    public void paint(Graphics g) {
        super.paint(g);
        Joueur joueur = getMatch().havePossession();
        if (joueur != null) this.setTitle(joueur.getNom());
        this.repaint();
    }

    public void setPanels(JPanel[] panels) {
        this.panels = panels;
    }

    public TypeListener getTypeListener() {
        return type;
    }

    public void setTypeListener(TypeListener type) {
        this.type = type;
    }

    public Basket(Match match) throws Exception {
        setMatch(match);
        setLayout(new GridLayout(2, 1));
        setTypeListener(new TypeListener(getMatch()));
        setFocusable(true);
        requestFocus();
        TypeListener type = getTypeListener();
        addKeyListener(type);
        match.setType(type);
        for (int i = 0; i < 2; i++)
            panels[i] = createTeam(match.getEquipes()[i]);
        add(panels[0]);
        panels[1].setBackground(Color.lightGray);
        add(panels[1]);
        initFrame();
    }

    public void initFrame() {
        setSize(this.getWidth(), this.getHeight());
        setTitle("Basket");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocation(100, 100);
        setVisible(true);
    }

    public JPanel createTeam(Equipe equipe) throws Exception {
        Formulaire panel = Formulaire.createFormulaire(new Joueur());
        for (Champ champ : panel.getListeChamp())
            champ.setVisible(false, "");
        equipe.setJoueurs();
        Joueur[] joueurs = equipe.getJoueurs();
        equipe.setPG(joueurs[0]);
        for (Joueur joueur: joueurs) {
            joueur.setEquipe(equipe);
            Button button = new Button(new Click(joueur, this), joueur.getNom());
            button.setFocusable(false);
            panel.addButton(button);
        }
        panel.setPosition();
        return panel;
    }
}