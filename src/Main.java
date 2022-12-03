import connection.BddObject;
import equipe.Equipe;
import formulaire.Button;
import formulaire.Formulaire;
import graphical.Basket;
import joueur.Joueur;
import match.Match;
import mouse.Start;
import javax.swing.*;
import java.sql.Date;

public class Main {
    public static void main(String[] args) throws Exception {
        Formulaire formulaire = Match.getFormulaire();
        formulaire.initFrame(new JFrame());
    }
}