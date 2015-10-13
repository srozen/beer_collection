package socialbeerproject.appas.Elements;

public class ElementCollection {

    private String nom;
    private int icon;

    public ElementCollection(String nom, int icon) {
        this.nom = nom;
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

}
