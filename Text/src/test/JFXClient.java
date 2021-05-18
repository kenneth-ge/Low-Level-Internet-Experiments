package test;

import client.TextClientDNS;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import util.SimpleErrorHandler;

public class JFXClient extends Application {

	public static void main(String[] args) {
		JFXClient.launch(args);
	}
	
	Stage stage;
	StackPane main;
	
	TextArea ta;
	TextField loc, req;
	
	@Override
	public void start(Stage s) throws Exception {
		this.stage = s;
		
		stage.setTitle("P2P Chatting App!");
		main = new StackPane();
		StackPane.setMargin(main, new Insets(50));
		
		ta = new TextArea();
		
		ta.setEditable(false);
		
		Button load = new Button("Load");
		load.setOnAction(this::load);
		
		ta.prefHeightProperty().bind(main.heightProperty().subtract(load.getHeight()).subtract(5));
		
		loc = new TextField();
		req = new TextField();
		
		loc.prefWidthProperty().bind(main.widthProperty().subtract(load.widthProperty()).divide(2));
		req.prefWidthProperty().bind(main.widthProperty().subtract(load.widthProperty()).divide(2));
		
		VBox vbox = new VBox();
		vbox.getChildren().addAll(ta, new HBox(loc, req, load));
		
		main.getChildren().add(vbox);
		
		stage.setScene(new Scene(main, 1000, 500));
		stage.show();
	}
	
	public void load(ActionEvent e) {
		TextClientDNS client = new TextClientDNS(new SimpleErrorHandler());
		
		ta.setText(client.request(req.getText(), loc.getText()));
	}

}
