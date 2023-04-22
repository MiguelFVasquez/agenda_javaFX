package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import application.Aplicacion;
import co.uniquindio.p2.agenda.exceptions.ReunionException;
import co.uniquindio.p2.agenda.model.Agenda;
import co.uniquindio.p2.agenda.model.Reunion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ReunionController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Reunion> tableViewReuniones;

    @FXML
    private TableColumn<Reunion, String> columFecha;

    @FXML
    private TableColumn<Reunion, String> columHora;

    @FXML
    private TableColumn<Reunion, String> columDescripcion;
    @FXML
    private Button btnVolver;

    @FXML
    private Button btnLimpiar;

    @FXML
    private Button btnEliminar;

    @FXML
    private TextField txtHora;

    @FXML
    private Button btnCrear;

    @FXML
    private TextField txtDescripcion;

    @FXML
    private TextField txtFecha;

	private InicioController inicioController;

	private Agenda agenda;

	private Aplicacion aplicacion;

	private Stage stage;


	//Atributos y metodos necesarios para inicializar la tabla de reuniones
    ObservableList<Reunion> listaReuniones= FXCollections.observableArrayList();
   	private Reunion reunionSeleccion;
    private ObservableList<Reunion> getDocumentosReunion() {
    	listaReuniones.addAll(agenda.getListaReuniones());
		return listaReuniones;
	}



	private boolean validarDatos(String descripcion, String fecha, String hora) {
		String notificacion = "";

		if (descripcion == null || descripcion.equals(""))
			notificacion += "Nombre invalido\n";


		/*Se valida que el saldo ingresado no sea null ni sea cadena vacía,
		además se valida que sea numérico para su correcta conversión */
		if (fecha == null || fecha.equals(""))
			notificacion += "Alias inválido\n";


		if(hora == null || hora.equals(""))
			notificacion+= "Email invalido\n";


		if (!notificacion.equals("")) {
			mostrarMensaje("Notificación", "Contacto no creado", notificacion, AlertType.WARNING);
			return false;
		}

		return true;
	}

	public void mostrarMensaje(String titulo, String header, String contenido, AlertType alertype) {
		Alert alert = new Alert(alertype);
		alert.setTitle(titulo);
		alert.setHeaderText(header);
		alert.setContentText(contenido);
		alert.showAndWait();
	}


    @FXML
    void crearReunion(ActionEvent event) {
    	String descripcion = txtDescripcion.getText();
    	String fecha = txtFecha.getText();
    	String hora= txtHora.getText();
    	if (validarDatos(descripcion, fecha, hora)) {
    		nuevaReunion(descripcion, fecha, hora);
    		txtDescripcion.setText("");
    		txtFecha.setText("");
    		txtHora.setText("");
		}
    }


    private void nuevaReunion(String descripcion, String fecha,String hora){
    	if (aplicacion.crearReunion(descripcion, fecha, hora)) {
    		mostrarMensaje("Notificacion reunion", "Reunion creada", "La reunion fue creada existosamente", AlertType.INFORMATION);
    		tableViewReuniones.getItems().clear();
    		tableViewReuniones.setItems(getDocumentosReunion());
		}else {
			mostrarMensaje("Notificacion reunion", "Reunion no creada", "La reunion no se ha podido crear", AlertType.INFORMATION);
		}
    }

    @FXML
    void limpiar(ActionEvent event) {

    }

    @FXML
    void eliminarReunion(ActionEvent event) throws ReunionException {
    	if (reunionSeleccion!=null) {
    		int confirmacion=  JOptionPane.showConfirmDialog(null, "Seguro que desea eliminar este contacto");
    		if(confirmacion==0){
    			if (aplicacion.eliminarReunion(reunionSeleccion)) {
    				listaReuniones.remove(reunionSeleccion);
    				mostrarMensaje("Reunion eliminada", "Eliminacion de reunion", "Se ha eliminado la reunion correctamente", AlertType.INFORMATION);
				}else {
					mostrarMensaje("Reunion eliminada", "Eliminacion de reunion", "No se ha podido eliminar la reunion", AlertType.WARNING);
				}
    		}
		}else {
			mostrarMensaje("Reunion seleccion", "Reunion seleccion", "No se ha seleccionado ninguna reunion", AlertType.WARNING);
		}
    }

    @FXML
    void vovler(ActionEvent event) {
    	inicioController.show();
		stage.close();
    }

    @FXML
    void initialize() {


    }
	public void init(Stage stage, InicioController inicioController) {
		this.inicioController = inicioController;
		this.agenda = aplicacion.getAgenda();
		this.stage = stage;

	}

	public void setAplicacion(Aplicacion aplicacion){
		this.aplicacion = aplicacion;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.columDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
		this.columFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
		this.columHora.setCellValueFactory(new PropertyValueFactory<>("hora"));

		tableViewReuniones.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if(newSelection != null){
				reunionSeleccion= newSelection;
				//mostrarInfoGrupo();
			}
		});

	}

}
