package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Aplicacion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class InicioController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnCerrrar;

    @FXML
    private Button btnGrupo;

    @FXML
    private Button btnContacto;

    @FXML
    private Button btnReunion;

	private Aplicacion aplicacion;

	private Stage stage;

    @FXML
    void cerrarInicio(ActionEvent event) {
    	this.stage.close();
    }

    @FXML
    void abrirContactos(ActionEvent event) throws IOException {
  		FXMLLoader loader= new FXMLLoader();
		loader.setLocation(Aplicacion.class.getResource("../Views/VistaContacto.fxml"));
		AnchorPane anchorPane= (AnchorPane)loader.load();
		ContactoController contactoController = loader.getController();
		contactoController.setAplicacion(aplicacion);
		Scene scene= new Scene(anchorPane);
		Stage stage = new Stage();
		stage.setScene(scene);
		contactoController.init(stage, this);
		stage.show();
		this.stage.close();
    }

    public void show() {
 		stage.show();

 	}

    @FXML
    void abrirGrupos(ActionEvent event) throws IOException {
 		FXMLLoader loader= new FXMLLoader();
		loader.setLocation(Aplicacion.class.getResource("../Views/ViewGrupo.fxml"));
		AnchorPane anchorPane= (AnchorPane)loader.load();
		GrupoController grupoController = loader.getController();
		grupoController.setAplicacion(aplicacion);
		Scene scene= new Scene(anchorPane);
		Stage stage = new Stage();
		stage.setScene(scene);
		grupoController.init(stage, this);
		stage.show();
		this.stage.close();
    }

    @FXML
    void abrirReuniones(ActionEvent event) throws IOException {
    	FXMLLoader loader= new FXMLLoader();
		loader.setLocation(Aplicacion.class.getResource("../Views/ReunionView.fxml"));
		AnchorPane anchorPane= (AnchorPane)loader.load();
		ReunionController reunionController = loader.getController();
		reunionController.setAplicacion(aplicacion);
		Scene scene= new Scene(anchorPane);
		Stage stage = new Stage();
		stage.setScene(scene);
		reunionController.init(stage, this);
		stage.show();
		this.stage.close();

    }

    public void setAplicacion(Aplicacion aplicacion) {
		this.aplicacion= aplicacion;

	}

    @FXML
    void initialize() {

    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	public void setStage(Stage primaryStage) {
		stage = primaryStage;

	}


}
