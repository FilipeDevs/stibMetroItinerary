import config.ConfigManager;
import javafx.application.Application;
import javafx.stage.Stage;
import model.StibModel;
import model.exceptions.RepositoryException;
import view.Presenter;
import view.MainView;
import view.RoutesView;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException, RepositoryException {

        ConfigManager.getInstance().load();

        StibModel model = new StibModel();
        MainView mainView = new MainView(stage);
        RoutesView routesView = new RoutesView();
        Presenter presenter = new Presenter(model, mainView, routesView);

        presenter.addObserver();
        mainView.addHandler(presenter);
        routesView.addHandler(presenter);

        presenter.initialize();

    }

    public static void main(String[] args) {
        launch();

    }
}
