package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import application.Aplicacion;
import co.uniquindio.p2.agenda.exceptions.AgendaException;
import co.uniquindio.p2.agenda.exceptions.ContactoException;
import co.uniquindio.p2.agenda.model.Agenda;
import co.uniquindio.p2.agenda.model.Contacto;
import co.uniquindio.p2.agenda.model.Grupo;
import co.uniquindio.p2.agenda.model.Reunion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class ContactoController implements Initializable {

	    @FXML
	    private ResourceBundle resources;

	    @FXML
	    private URL location;

	    @FXML
	    private TableColumn<Contacto, String> columNombre;

	    @FXML
	    private TableColumn<Contacto, String> columDireccion;

	    @FXML
	    private TableColumn<Contacto, String> columTelefono;

	    @FXML
	    private TableColumn<Contacto, String> columAlias;

	    @FXML
	    private TableColumn<Contacto, String> columEmail;

	    @FXML
	    private TableView<Contacto> tableViewContactos;


	    @FXML
	    private TableView<Reunion> tableViewReuniones;
	    @FXML
	    private TableColumn<Reunion, String> columDescripcionReuniones;

	    @FXML
	    private TableView<Grupo> tableViewGrupos;
	    @FXML
	    private TableColumn<Grupo, String> columNombreGrupos;


	    @FXML
	    private TextField txtAliasContacto;

	    @FXML
	    private TextField txtDireccion;


	    @FXML
	    private TextField txtEmail;

	    @FXML
	    private TextField txtTelefono;

	    @FXML
	    private TextField txtNombreContacto;


	    @FXML
	    private Button btnEliminarContacto;


	    @FXML
	    private Button btnLimpiar;


	    @FXML
	    private Button aniadirReunion;

	    @FXML
	    private Button aniadirGrupo;

	    @FXML
	    private Button btnAgregarContacto;


	    @FXML
	    private Button btnVolver;

		private InicioController inicioController;

		private Agenda agenda;

		private Aplicacion aplicacion;

		private Stage stage;

		private Contacto contactoSeleccion;

		private Grupo grupoSeleccion;

		private Reunion reunionSeleccion;

		ObservableList<Contacto> listaContactos= FXCollections.observableArrayList();
	    ObservableList<Grupo> listaGrupos= FXCollections.observableArrayList();
	    ObservableList<Reunion> listaReuniones= FXCollections.observableArrayList();

	    private ObservableList<Contacto> getListaContactos() {
	    	listaContactos.addAll(agenda.getListaContactos());
			return listaContactos;
		}

   		private ObservableList<Grupo> getListaGrupos() {
   			listaGrupos.addAll(agenda.getListaGrupos());
   			return listaGrupos;

		}

 		private ObservableList<Reunion> getListaReuniones() {
 			listaReuniones.addAll(agenda.getListaReuniones());
   			return listaReuniones;

		}


		private boolean validarDatos(String nombre, String alias, String telefono, String email, String direccion) {
			String notificacion = "";

			if (nombre == null || nombre.equals(""))
				notificacion += "Nombre invalido\n";


			/*Se valida que el saldo ingresado no sea null ni sea cadena vacía,
			además se valida que sea numérico para su correcta conversión */
			if (alias == null || alias.equals(""))
				notificacion += "Alias inválido\n";


			/*Se valida que la tasa anual ingresada no sea null ni sea cadena vacía,
			además se valida que sea numérico para su correcta conversión */
			if (telefono == null || telefono.equals("")) {
				notificacion += "Número de telefono invalido\n";
			} else if (!validarTelefono(telefono)) {
				notificacion += "El telefono debe de ser un valor numrico\n";
			}

			if(email == null || email.equals(""))
				notificacion+= "Email invalido\n";


			if(direccion== null || direccion.equals(""))
				notificacion+= "Direccion inavlida\n";


			if (!notificacion.equals("")) {
				mostrarMensaje("Notificación", "Contacto no creado", notificacion, AlertType.WARNING);
				return false;
			}

			return true;
		}
		/**
		 * Metodo que sive para validar que el telefono contenga un valor numerico, y evitar registar un contacto con numero de telefono "hola"
		 * @param telefono
		 * @return true si el telefono esta ingresado correctamente
		 */
		private boolean validarTelefono(String telefono){
		    boolean valido = true;
		    for (int i = 0; i < telefono.length(); i++) {
		        if (!Character.isDigit(telefono.charAt(i))) {
		            valido = false;
		            break;
		        }
		    }
		    return valido;
		}



		public void mostrarMensaje(String titulo, String header, String contenido, AlertType alertype) {
			Alert alert = new Alert(alertype);
			alert.setTitle(titulo);
			alert.setHeaderText(header);
			alert.setContentText(contenido);
			alert.showAndWait();
		}




	    @FXML
	    void aniadirContacto(ActionEvent event) throws ContactoException, AgendaException {
	    	String nombre= txtNombreContacto.getText();
	    	String alias= txtAliasContacto.getText();
	    	String telefono= txtTelefono.getText();
	    	String direccion= txtDireccion.getText();
	    	String email= txtEmail.getText();

	    	if(validarDatos(nombre, alias, telefono, email, direccion)){

	    		crearContacto(nombre, alias, telefono, email, direccion);

	    		txtNombreContacto.setText("");
	    		txtAliasContacto.setText("");
	    		txtTelefono.setText("");
	    		txtEmail.setText("");
	    		txtDireccion.setText("");

	    	}

	    }



	    private void crearContacto(String nombre, String alias, String telefono, String email, String direccion) throws ContactoException, AgendaException{
	    	if(aplicacion.crearContacto(nombre,alias,telefono,email,direccion)){
	    		tableViewContactos.getItems().clear();
	    		tableViewContactos.setItems(getListaContactos());

	    		mostrarMensaje("Notificación Contacto", "Contacto creado", "Contacto agregado con exito", AlertType.INFORMATION);
			}else{
				mostrarMensaje("Notificación Contacto", "Contacto no creado", "El contacto no pudo ser creado", AlertType.WARNING);
			}

	    }


	    @FXML
	    void showVentanaPrincipal(ActionEvent event) {
	    	inicioController.show();
			stage.close();
	    }



	    @FXML
	    void eliminarContacto(ActionEvent event) throws ContactoException {

	    	if(contactoSeleccion!=null){
	    		int confirmacion= JOptionPane.showConfirmDialog(null, "Seguro que desea eliminar este contacto?");
	    		if(confirmacion==0){
		    		if(aplicacion.eliminarContacto(contactoSeleccion)){
		    			listaContactos.remove(contactoSeleccion);
		    			mostrarMensaje("Contacto eliminado", "Eliminacion de contacto", "Se ha eliminado el contacto con exito", AlertType.INFORMATION);
		    		}else{
		    			mostrarMensaje("Contacto eliminado", "Eliminacion de contacto", "No se ha podido eliminar el contacto", AlertType.WARNING);
		    		}
	    		}

	    	}else
	    		mostrarMensaje("Contacto seleccion", "Contacto Seleccion", "No se ha seleccionado ningun contacto", AlertType.WARNING);
	    }


	    @FXML
	    void limpiarCamposTxt(ActionEvent event) {
	    	txtNombreContacto.setText("");
			txtAliasContacto.setText("");
			txtTelefono.setText("");
			txtEmail.setText("");
			txtDireccion.setText("");

	    	txtNombreContacto.setDisable(false);
			txtTelefono.setDisable(false);
	    }

	    @FXML
	    void aniadirAgrupo(ActionEvent event) {

	    }

	    @FXML
	    void aniadirAReunion(ActionEvent event) {

	    }

		private void mostrarInformacionContacto() {
			if(contactoSeleccion != null) {
				txtNombreContacto.setText(contactoSeleccion.getNombre());
				txtAliasContacto.setText(contactoSeleccion.getAlias());
				txtDireccion.setText(contactoSeleccion.getDireccion());
				txtTelefono.setText(contactoSeleccion.getTelefono());
				txtEmail.setText(contactoSeleccion.getEmail());
				//Deshabilito los textFields necesarios
				txtNombreContacto.setDisable(true);
				txtTelefono.setDisable(true);
			}
		}


		public void init(Stage stage, InicioController inicioController) {
			this.inicioController = inicioController;
			this.agenda = aplicacion.getAgenda();
			this.stage = stage;

		}

		public void setAplicacion(Aplicacion aplicacion){
			this.aplicacion = aplicacion;
		}

	    @FXML
	    void initialize() {

	    }


		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {


			this.columNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
			this.columAlias.setCellValueFactory(new PropertyValueFactory<>("alias"));
			this.columTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
			this.columDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
			this.columEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

//
//			this.columNombreGrupos.setCellValueFactory(new PropertyValueFactory<>("nombre"));
//			this.columDescripcionReuniones.setCellValueFactory(new PropertyValueFactory<>("descripcion"));


			tableViewContactos.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
				if(newSelection != null){
					contactoSeleccion= newSelection;
					mostrarInformacionContacto();
				}
			});

		}



}
