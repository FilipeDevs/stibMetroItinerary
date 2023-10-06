package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.dto.StationDto;

import java.io.IOException;
import java.util.List;

public class MainView {

    private FxmlMainController mainPane;


    public MainView(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/stibGui.fxml"));
        Parent root = loader.load();
        mainPane = loader.getController();
        Scene scene = new Scene(root);
        stage.setTitle("STIB_APP");
        stage.setScene(scene);
        stage.show();
    }

    public void initialize(List<StationDto> stationDtos) {
        mainPane.initialize(stationDtos);
    }

    public void addHandler(Presenter presenter) {
        mainPane.setHandler(presenter);
    }

    public void addPath(List<StationDto> stationDtos) {
        this.mainPane.addPath(stationDtos);
    }

    public String getStationSource() {
        return this.mainPane.getStationSource().getSelectionModel().getSelectedItem();
    }

    public String getStationDest() {
        return this.mainPane.getStationDest().getSelectionModel().getSelectedItem();
    }


}
