package socialbeerproject.appas.Elements;

public class ElementMenuP {

    private String title;
    private String description;
    private int icon;

    public ElementMenuP(String title, String descr, int icon) {
        this.title = title;
        this.icon = icon;
        this.description = descr;
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

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

}
