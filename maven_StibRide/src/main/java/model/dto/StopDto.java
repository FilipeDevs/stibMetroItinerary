package model.dto;

public class StopDto extends Dto<StopKey> {

    private int order;

    /**
     * Creates a new instance of <code>Dto</code> with the key of the data.
     *
     * @param key key of the data.
     */
    public StopDto(StopKey key) {
        super(key);
    }

    public StopDto(StopKey key, int order) {
        super(key);
        this.order = order;
    }

    public int getOrder() {
        return order;
    }
}
