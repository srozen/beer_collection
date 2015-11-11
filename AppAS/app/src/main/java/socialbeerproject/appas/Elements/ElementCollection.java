package socialbeerproject.appas.Elements;

/**
 * Utilisé pour une lister les bières (catalogue ou collection)
 */
public class ElementCollection {

    private String nom;
    private float ratingPer;
    private float ratingGlo;
    private int icon;
    private String id;

    public ElementCollection(String nom, int icon, String id) {
        this.nom = nom;
        this.icon = icon;
        this.id = id;
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
