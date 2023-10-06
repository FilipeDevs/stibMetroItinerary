package view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.dto.StationDto;
import model.exceptions.RepositoryException;
import org.controlsfx.control.SearchableComboBox;

import java.util.List;

public class FxmlMainController {
    @FXML
    private TableView<StationDto> table;
    @FXML
    private TableColumn<StationDto, String> stationsCol;
    @FXML
    private TableColumn<StationDto, List<Integer>> linesCol;
    @FXML
    private SearchableComboBox<String> stationSource;
    @FXML
    private SearchableComboBox<String> stationDest;
    @FXML
    private Button searchButton;
    @FXML
    private Label labelNbrStations;
    @FXML
    private MenuItem consultRoutes;

    public FxmlMainController() {}

    public void initialize(List<StationDto> stations) {
        stationsCol.setCellValueFactory(new PropertyValueFactory<>("nameStation"));
        linesCol.setCellValueFactory(new PropertyValueFactory<>("lines"));
        for(StationDto station : stations) {
            this.stationSource.getItems().add(station.getNameStation());
            this.stationDest.getItems().add(station.getNameStation());
        }
        stationSource.getSelectionModel().selectFirst();
        stationDest.getSelectionModel().selectFirst();

    }

    public void setHandler(Presenter presenter) {
        searchButton.setOnAction(event -> {
            try {
                presenter.findShortestPath();
            } catch (RepositoryException e) {
                throw new RuntimeException(e);
            }
        });

        consultRoutes.setOnAction(event -> {
            try {
                presenter.initializeFavoriteRoutesView();
            } catch (RepositoryException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void addPath(List<StationDto> stationDtos) {
        table.getItems().clear();
        for(StationDto station : stationDtos) {
            table.getItems().add(station);
        }
        labelNbrStations.setText("Nombre de stations : " + stationDtos.size());
    }

    public SearchableComboBox<String> getStationSource() {
        return stationSource;
    }

    public SearchableComboBox<String> getStationDest() {
        return stationDest;
    }


}