package view;

import model.Observable;
import model.StibModel;
import model.dto.RouteDto;
import model.dto.StationDto;
import model.exceptions.RepositoryException;

import java.util.List;


public class Presenter implements Observer {

    private StibModel model;
    private MainView mainView;
    private RoutesView viewFavRoutes;

    public Presenter(StibModel model, MainView mainView, RoutesView viewFavRoutes) {
        this.model = model;
        this.mainView = mainView;
        this.viewFavRoutes = viewFavRoutes;
    }

    public void initialize() throws RepositoryException {
        model.initialize();
        List<StationDto> data = model.getAllStations();
        mainView.initialize(data);
    }

    public void initializeFavoriteRoutesView() throws RepositoryException {
        model.initFavoriteRoutes();
        List<RouteDto> data = model.getFavoriteRoutes();
        List<StationDto> data_2 = model.getAllStations();
        viewFavRoutes.initialize(data, data_2);
    }

    public void findShortestPath() throws RepositoryException {
        if(this.mainView.getStationSource() == null || this.mainView.getStationDest() == null ) {
            return;
        }
        this.model.calculateShortestRoute(this.mainView.getStationSource(), this.mainView.getStationDest());
    }

    public void saveRoute() {
        Integer key = this.viewFavRoutes.getTotalRows() + 1;
        if(this.viewFavRoutes.isSelectRoute()) {
            key = this.viewFavRoutes.getSelectedRow().getKey();
        }
        RouteDto routeDto = new RouteDto(key, this.viewFavRoutes.getNameField(),
                this.viewFavRoutes.getStationSource(), this.viewFavRoutes.getStationDest());
        try {
            this.model.addNewFavoriteRoute(routeDto);
        } catch (RepositoryException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteRoute() {
        try {
            this.model.deleteFavoriteRoute(this.viewFavRoutes.getSelectedRow());
        } catch (RepositoryException e) {
            throw new RuntimeException(e);
        }
    }

    public void addObserver() {
        model.addObserver(this);
    }


    @Override
    public void update(Observable observable, Object arg) {
        StibModel savedModel = (StibModel) observable;
        if(arg.equals("path")) {
            this.mainView.addPath(savedModel.getCurrentPath());
        } else if(arg.equals("modifyRoute")) {
            this.viewFavRoutes.refreshTable(savedModel.getFavoriteRoutes());
        }
    }
}
