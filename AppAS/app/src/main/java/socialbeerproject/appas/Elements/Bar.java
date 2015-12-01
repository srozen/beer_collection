package socialbeerproject.appas.Elements;

/**
 * Created by Pierret on 01-12-15.
 */
public class Bar {
    private String id;
    private String name;
    private String description;
    private Double lattitude;
    private Double longitude;

    public Bar(String id, String name, String description, Double lattitude, Double longitude) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.lattitude = lattitude;
        this.longitude = longitude;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getLattitude() {
        return lattitude;
    }

    public void setLattitude(Double lattitude) {
        this.lattitude = lattitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
