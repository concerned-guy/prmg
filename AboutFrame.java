import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.effect.*;
import javafx.scene.layout.*;
import javafx.scene.image.*;
import javafx.scene.shape.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;
import javafx.geometry.*;
import javafx.collections.*;

public class AboutFrame {
    private Stage stage;
    private VBox panel;

    private Button closeButton;

    public AboutFrame() {
        this.stage = new Stage();
        this.stage.initModality(Modality.APPLICATION_MODAL);
        this.stage.setTitle("About");
        createComponents();

        this.closeButton.setOnAction(event -> close());
    }

    public void show() {
        this.stage.showAndWait();
    }

    public void close() {
        this.stage.close();
    }

    private void createComponents() {
        this.panel = new VBox();
        Scene scene = new Scene(this.panel);
        this.stage.setResizable(false);
        this.stage.setScene(scene);

        this.panel.setAlignment(Pos.CENTER);
        this.panel.setPadding(new Insets(35, 30, 25, 30));

        Image art = new Image("art.png");
        ImageView artView = new ImageView();
        artView.setPreserveRatio(true);
        artView.setFitWidth(300);
        artView.setCache(true);
        artView.setImage(art);
        DropShadow dropShadow = new DropShadow();
        artView.setEffect(dropShadow);

        Label nameLabel = new Label();
        nameLabel.setText("Project Management Pro Limited Edition Deluxe");
        nameLabel.setFont(new Font("Arial Bold", 18));
        nameLabel.setPadding(new Insets(25, 0, 0, 0));

        Label versionLabel = new Label();
        versionLabel.setText("v0.99");
        versionLabel.setFont(new Font("Arial", 12));

        Label subLabel = new Label();
        subLabel.setText("Manage projects like a sir.");
        subLabel.setPadding(new Insets(40));

        this.closeButton = new Button("Close");
        this.closeButton.setDefaultButton(true);

        panel.getChildren().addAll(artView, nameLabel, versionLabel, subLabel, this.closeButton);
    }
}
