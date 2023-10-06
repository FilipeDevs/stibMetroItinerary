package model;

import model.dto.StationDto;
import model.exceptions.RepositoryException;
import model.repository.StationRepository;

import java.util.*;

public class MetroGraph {

    private final HashMap<String, StationNode> graph;

    public MetroGraph() throws RepositoryException {
        this.graph = new HashMap<>();
        this.createAllNodes();
        this.linkNodes();
    }

    public void createAllNodes() throws RepositoryException {
        StationRepository stationRepository = new StationRepository();
        List<StationDto> stations = stationRepository.getAll();

        for(StationDto stationDto : stations) {
            this.graph.put(stationDto.getNameStation(), new StationNode(stationDto.getKey()));
        }
    }

    public void linkNodes() throws RepositoryException {
        StationRepository stationRepository = new StationRepository();

        for(String station : graph.keySet()) {
            Map<StationNode, Integer> adjacentNodes = new HashMap<>(); // initialize adjacentNodes of current station
            // iterate through all neighbours stations and populate adjacentNodes of current station
            for(StationDto neighbourStation : stationRepository.getNeighboursStations(graph.get(station).getId_station())) {
                adjacentNodes.put(this.graph.get(neighbourStation.getNameStation()), 1);
            }
            this.graph.get(station).setAdjacentNodes(adjacentNodes); // current station has now all adjacentNodes
        }
    }

    public void resetGraph() {
        for(String station : graph.keySet()) {
            this.graph.get(station).getShortestPath().clear();
        }
    }

    public HashMap<String, StationNode> getGraph() {
        return graph;
    }
}
