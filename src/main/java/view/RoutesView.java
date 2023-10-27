package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.dto.RouteDto;
import model.dto.StationDto;

import java.io.IOException;
import java.util.List;

public class RoutesView {

    private FxmlController_2 mainPane;

    private Stage stage;

    public RoutesView() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/routeFavorites.fxml"));
        Parent root = loader.load();
        mainPane = loader.getController();
        stage = new Stage();
        Scene scene = new Scene(root);
        stage.setTitle("Trajets Favoris");
        stage.setScene(scene);
    }

    public void initialize(List<RouteDto> routeDtos, List<StationDto> stationDtos) {
        stage.show();
        mainPane.initialize(routeDtos, stationDtos);
    }

    public void refreshTable(List<RouteDto> favoriteRoutes) {
        this.mainPane.refreshTable(favoriteRoutes);
    }

    public void addHandler(Presenter presenter) {
        mainPane.setHandler(presenter);
    }

    public String getNameField() {
        return mainPane.getNameField().getText();
    }

    public String getStationSource() {
        return this.mainPane.getOrigin().getSelectionModel().getSelectedItem();
    }

    public String getStationDest() {
        return this.mainPane.getDestination().getSelectionModel().getSelectedItem();
    }

    public int getTotalRows() {
        return this.mainPane.getTotalRows();
    }

    public RouteDto getSelectedRow() {
        return this.mainPane.getSelectedRow();
    }

    public boolean isSelectRoute() {
        return this.mainPane.getSelectRoute();
    }

}
