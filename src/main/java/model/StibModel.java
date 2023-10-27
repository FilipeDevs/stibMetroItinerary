package model;

import model.dto.RouteDto;
import model.dto.StationDto;
import model.exceptions.RepositoryException;
import model.repository.RouteRepository;
import model.repository.StationRepository;

import java.util.*;

public class StibModel extends Observable {

    private List<StationDto> currentPath;

    private List<StationDto> allStations;

    private List<RouteDto> favoriteRoutes;

    private StationRepository stationRepository;

    private RouteRepository routeRepository;

    public StibModel() throws RepositoryException {
        this.stationRepository = new StationRepository();
        this.routeRepository = new RouteRepository();
    }

    public void initialize() throws RepositoryException {
        this.allStations = this.stationRepository.getAll();
    }

    public void initFavoriteRoutes() throws RepositoryException {
        this.favoriteRoutes = this.routeRepository.getAll();
    }

    public void addNewFavoriteRoute(RouteDto routeDto) throws RepositoryException {
        this.routeRepository.add(routeDto);
        this.initFavoriteRoutes();
        this.notifyObservers("modifyRoute");
    }

    public void deleteFavoriteRoute(RouteDto routeDto) throws RepositoryException {
        this.routeRepository.remove(routeDto.getKey());
        this.initFavoriteRoutes();
        this.notifyObservers("modifyRoute");
    }

    public void calculateShortestRoute(String sourceStation, String destStation) throws RepositoryException {
        MetroGraph metroGraph = new MetroGraph();

        List<StationDto> route = new ArrayList<>();

        StationNode source = metroGraph.getGraph().get(sourceStation);
        Dijkstra.calculateShortestPathFromSource(metroGraph, source);
        StationNode dest = metroGraph.getGraph().get(destStation);

        for(var elem : dest.getShortestPath()) {
            route.add(this.stationRepository.getFullStation(elem.getId_station()));
        }

        route.add(this.stationRepository.getFullStation(dest.getId_station())); // dest station
        this.currentPath = route;

        this.notifyObservers("path");
    }

    public List<StationDto> getAllStations() {
        return allStations;
    }

    public List<StationDto> getCurrentPath() {
        return currentPath;
    }

    public List<RouteDto> getFavoriteRoutes() {
        return favoriteRoutes;
    }
}

