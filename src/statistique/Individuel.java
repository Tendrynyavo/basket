package statistique;

import joueur.Joueur;

public class Individuel {

    Joueur joueur;
    Statistique[] statistiques;

    public Joueur getJoueur() {
        return joueur;
    }

    public void setJoueur(Joueur joueur) {
        this.joueur = joueur;
    }

    public Statistique[] getStatistiques() {
        return statistiques;
    }

    public void setStatistiques(Statistique[] statistiques) {
        this.statistiques = statistiques;
    }

    public Individuel(Joueur joueur, Statistique[] statistiques) {
        setJoueur(joueur);
        setStatistiques(statistiques);
    }
}
