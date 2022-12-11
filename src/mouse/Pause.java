package mouse;

import match.Match;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import formulaire.Button;

public class Pause implements MouseListener {

    Match match;
    Button button;

    public void setButton(Button button) {
        this.button = button;
    }

    public Button getButton() {
        return button;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public Pause(Match match) {
        setMatch(match);
    }

    public void mouseClicked(MouseEvent e) {
        if (getMatch().getChrono().isPause()) {
            getMatch().getChrono().resume();
            getMatch().getChrono().setPause(false);
            getButton().setText("Pause");
        } else {
            getMatch().getChrono().pause();
            getMatch().getChrono().setPause(true);
            getButton().setText("Resume");
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }
    
}