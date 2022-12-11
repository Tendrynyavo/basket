package time;

public class Chrono {

    long tempsDepart = 0;
    long tempsFin = 0;
    long pauseDepart = 0;
    long pauseFin = 0;
    long duree = 0;
    boolean pause = false;

    public boolean isPause() {
        return pause;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    public void start() {
        tempsDepart = System.currentTimeMillis();
        tempsFin = 0;
        pauseDepart = 0;
        pauseFin = 0;
        duree = 0;
    }

    public void pause() {
        if(tempsDepart == 0) {return;}
        pauseDepart = System.currentTimeMillis();
        duree = Math.abs(tempsDepart - pauseDepart);
    }

    public void resume() {
        if(tempsDepart == 0) {return;}
        if(pauseDepart == 0) {return;}
        pauseFin = System.currentTimeMillis();
        tempsDepart = tempsDepart + pauseFin - pauseDepart;
        tempsFin = 0;
        pauseDepart = 0;
        pauseFin = 0;
        duree = 0;
    }
        
    public void stop() {
        if(tempsDepart == 0) {return;}
        tempsFin = System.currentTimeMillis();
        duree = (tempsFin-tempsDepart) - (pauseFin - pauseDepart);
        tempsDepart = 0;
        tempsFin = 0;
        pauseDepart = 0;
        pauseFin = 0;
    }        

    public long getDureeSec() {
        return duree/1000;
    }
        
    public long getDureeMs() {
        return duree;
    }        

    public String getDureeTxt() {
        return timeToHMS(getDureeSec());
    }

    public static String timeToHMS(long tempsS) {
        int h = (int) (tempsS / 3600);
        int m = (int) ((tempsS % 3600) / 60);
        int s = (int) (tempsS % 60);
        String r="";
        if(h>0) {r+=h+" h ";}
        if(m>0) {r+=m+" min ";}
        if(s>0) {r+=s+" s";}
        if(h<=0 && m<=0 && s<=0) {r="0 s";}
        return r;
    }

}