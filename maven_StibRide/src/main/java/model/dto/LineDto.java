package model.dto;

public class LineDto extends Dto<Integer>{
    private int numLine;
    /**
     * Creates a new instance of <code>Dto</code> with the key of the data.
     *
     * @param key key of the data.
     */
    public LineDto(Integer key) {
        super(key);
    }

    public LineDto(Integer key, int numLine) {
        super(key);
        this.numLine = numLine;
    }

    public int getNumLine() {
        return numLine;
    }
}
