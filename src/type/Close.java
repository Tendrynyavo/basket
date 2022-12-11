package type;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import graphical.Basket;
import statistique.Possession;

public class Close implements WindowListener {
    
    Basket basket;

    public void setBasket(Basket basket) {
        this.basket = basket;
    }

    public Basket getBasket() {
        return basket;
    }

    public Close(Basket basket) {
        setBasket(basket);
    }
    public void windowOpened(WindowEvent e) {}
    public void windowClosing(WindowEvent e) {
        try {
            getBasket().getMatch().getChrono().stop();
            int time = (int) getBasket().getMatch().getChrono().getDureeSec();
            Possession possession = new Possession(getBasket().getMatch().getType().getJoueur().getIdJoueur(), getBasket().getMatch().getIdMatch(), time);
            possession.insert(null);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(new JFrame(), ex.getMessage());
        }
    }
    public void windowClosed(WindowEvent e) {}
    public void windowIconified(WindowEvent e) {}
    public void windowDeiconified(WindowEvent e) {}
    public void windowActivated(WindowEvent e) {}
    public void windowDeactivated(WindowEvent e) {}
}