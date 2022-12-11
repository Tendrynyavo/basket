import javax.swing.JFrame;
import formulaire.Formulaire;
import match.Match;

public class Main {

    public static void main(String[] args) throws Exception {
        Formulaire formulaire = Match.getFormulaire();
        JFrame frame = new JFrame();
        formulaire.initFrame(frame);
        frame.setSize(600, 300);
    }
}