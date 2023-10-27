package model.dto;

import java.util.ArrayList;
import java.util.List;

public class StationDto extends Dto<Integer>{

    private String nameStation;

    private List<Integer> lines;

    /**
     * Creates a new instance of <code>Dto</code> with the key of the data.
     *
     * @param key key of the data.
     */
    protected StationDto(Integer key) {
        super(key);
    }

    public StationDto(Integer key, String nameStation) {
        super(key);
        this.nameStation = nameStation;
        this.lines = new ArrayList<>();
    }

    public String getNameStation() {
        return nameStation;
    }

    public void addLine(int line) {
        this.lines.add(line);
    }

    public List<Integer> getLines() {
        return lines;
    }
}
