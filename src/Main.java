import javax.swing.JFrame;
import formulaire.Formulaire;
import match.Match;

public class Main {

    public static void main(String[] args) throws Exception {
        Formulaire formulaire = Match.getFormulaire();
        formulaire.initFrame(new JFrame());
        // Chrono chrono = new Chrono();
        // chrono.start();
        // Thread.sleep(2000);
        // chrono.pause();
        // System.out.println(chrono.getDureeSec());
        // chrono.resume();
        // Thread.sleep(2000);
        // chrono.stop();
        // System.out.println(chrono.getDureeSec());
    }
}