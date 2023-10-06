package model;

import java.util.*;

public class StationNode {

    private final int id_station;

    private List<StationNode> shortestPath = new LinkedList<>();

    private Integer distance = Integer.MAX_VALUE;

    Map<StationNode, Integer> adjacentNodes = new HashMap<>();

    public StationNode(int id_station) {
        this.id_station = id_station;
    }

    public void setAdjacentNodes(Map<StationNode, Integer> adjacentNodes) {
        this.adjacentNodes = adjacentNodes;
    }

    public int getId_station() {
        return id_station;
    }

    public List<StationNode> getShortestPath() {
        return shortestPath;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setShortestPath(List<StationNode> shortestPath) {
        this.shortestPath = shortestPath;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Map<StationNode, Integer> getAdjacentNodes() {
        return adjacentNodes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StationNode stopNode = (StationNode) o;
        return id_station == stopNode.id_station;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_station);
    }
}
