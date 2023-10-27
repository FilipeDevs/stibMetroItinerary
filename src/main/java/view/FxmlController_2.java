package view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.dto.RouteDto;
import model.dto.StationDto;
import org.controlsfx.control.SearchableComboBox;

import java.util.List;

public class FxmlController_2 {

    @FXML
    private TableView<RouteDto> favoritesTable;
    @FXML
    private TableColumn<RouteDto, Integer> colId;
    @FXML
    private TableColumn<RouteDto, String> colName;
    @FXML
    private TableColumn<RouteDto, String> colOrigin;
    @FXML
    private TableColumn<RouteDto, String> colDest;
    @FXML
    private Button saveRoute;
    @FXML
    private Button deleteRoute;
    @FXML
    private TextField nameField;
    @FXML
    private TextField idField;
    @FXML
    private SearchableComboBox<String> origin;
    @FXML
    private SearchableComboBox<String> destination;

    public FxmlController_2() {}

    public void initialize(List<RouteDto> favoriteRoutes, List<StationDto> stations) {

        colId.setCellValueFactory(new PropertyValueFactory<>("key"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colOrigin.setCellValueFactory(new PropertyValueFactory<>("sourceStation"));
        colDest.setCellValueFactory(new PropertyValueFactory<>("destStation"));

        this.refreshTable(favoriteRoutes);

        for(StationDto station : stations) {
            this.origin.getItems().add(station.getNameStation());
            this.destination.getItems().add(station.getNameStation());
        }
        origin.getSelectionModel().selectFirst();
        destination.getSelectionModel().selectFirst();
    }

    public void refreshTable(List<RouteDto> favoriteRoutes) {
        favoritesTable.getItems().clear();
        idField.clear();

        for(RouteDto routeDto : favoriteRoutes) {
            favoritesTable.getItems().add(routeDto);
        }

        favoritesTable.refresh();
    }

    @FXML
    private void rowClicked() {
        try {
            RouteDto routeDto = favoritesTable.getSelectionModel().getSelectedItem();

            idField.setText(Integer.toString(routeDto.getKey()));
            nameField.setText(routeDto.getName());
            origin.getSelectionModel().select(routeDto.getSourceStation());
            destination.getSelectionModel().select(routeDto.getDestStation());
        } catch (RuntimeException e) {
            System.out.println("No row selected");
        }

    }

    public void setHandler(Presenter presenter) {
        saveRoute.setOnAction(event -> {
            presenter.saveRoute();
        });
        deleteRoute.setOnAction(event -> {
            presenter.deleteRoute();
        });
    }

    public TextField getNameField() {
        return nameField;
    }

    public SearchableComboBox<String> getOrigin() {
        return origin;
    }

    public SearchableComboBox<String> getDestination() {
        return destination;
    }

    public RouteDto getSelectedRow() {
        if(favoritesTable.getSelectionModel().getSelectedItem() == null) {
            return null;
        }
        return favoritesTable.getSelectionModel().getSelectedItem();
    }

    public int getTotalRows() {
        return favoritesTable.getItems().size();
    }

    public boolean getSelectRoute() {
        return favoritesTable.getSelectionModel().getSelectedItem() != null;
    }
}
