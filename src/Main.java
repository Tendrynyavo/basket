
import javax.swing.JFrame;

import equipe.Equipe;
import formulaire.Formulaire;
import match.Match;

public class Main {

    public static void main(String[] args) throws Exception {
        Formulaire formulaire = Match.getFormulaire();
        formulaire.initFrame(new JFrame());
    }
}