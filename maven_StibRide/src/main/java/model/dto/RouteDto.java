package model.dto;

public class RouteDto extends Dto<Integer> {

    private String name;

    private String sourceStation;

    private String destStation;

    /**
     * Creates a new instance of <code>Dto</code> with the key of the data.
     *
     * @param key key of the data.
     */
    protected RouteDto(Integer key) {
        super(key);
    }

    public RouteDto(Integer key, String name,String sourceStation, String destStation) {
        super(key);
        this.sourceStation = sourceStation;
        this.destStation = destStation;
        this.name = name;
    }

    public String getSourceStation() {
        return sourceStation;
    }

    public String getDestStation() {
        return destStation;
    }

    public String getName() {
        return name;
    }
}
