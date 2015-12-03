package socialbeerproject.appas.Elements;

/**
 * Classe ElementPlan, utilisé représenter les bons plans dans une liste
 * @author Voet Rémy, Faignaert Florian, Pierret Cyril
 */

public class ElementPlan {

    private String title;
    private String nameBeer;
    private String nomBiere;
    private String description;
    private String categorie;
    private String prix;
    private String dateDebut;
    private String dateFin;
    private String reference;
    private String id;

    public ElementPlan(String title, String nameBeer, String descr, String categorie, String prix, String dD, String dE, String ref, String id) {
        this.title = title;
        this.nameBeer = nameBeer;
        this.description = descr;
        this.categorie = categorie;
        this.prix = prix;
        this.dateDebut = dD;
        this.dateFin = dE;
        this.reference = ref;
        this.id=id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String descr){
        this.description = descr;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public String getDateFin() {
        return dateFin;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
