package socialbeerproject.appas.Elements;

/**
 * Classe ElementListeBiere, utilisé pour représenter une bière dans une liste (catalogue ou collection)
 * @author Voet Rémy, Faignaert Florian, Pierret Cyril
 */

public class ElementListeBiere {

    private String nom;
    private float ratingPer;
    private float ratingGlo;
    private int icon; //Utile? TODO
    private String id;

    public ElementListeBiere(String nom, int icon, String id) {
        this.nom = nom;
        this.id = id;

        this.icon = icon;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getRatingPer() {
        return ratingPer;
    }

    public void setRatingPer(float ratingPer) {
        this.ratingPer = ratingPer;
    }

    public float getRatingGlo() {
        return ratingGlo;
    }

    public void setRatingGlo(float ratingGlo) {
        this.ratingGlo = ratingGlo;
    }
}
