package socialbeerproject.appas.Elements;

/**
 * Created by Pierret on 22-11-15.
 */
public class ElementPlan {

    // TODO : IMPLEMENTER LES DERNIERS ATTRIBUTS EN FONCTION DE LA DB

    /*
        t.string   "name"
        t.string   "nameBeer"
        t.text     "description"
        t.string   "categorie"
        t.datetime "datedebut"
        t.datetime "datefin"
        t.float    "prix"
        t.float    "reference"
        t.float    "reduction"
     */

    private String title;
    private String description;
    private String dateDebut;
    private String dateFin;
    private String reference;

    public ElementPlan(String title, String descr, String dD, String dE, String ref) {
        this.title = title;
        this.description = descr;
        this.dateDebut = dD;
        this.dateFin = dE;
        this.reference = ref;
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
}
