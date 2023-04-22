package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import application.Aplicacion;
import co.uniquindio.p2.agenda.exceptions.GrupoException;
import co.uniquindio.p2.agenda.model.Agenda;
import co.uniquindio.p2.agenda.model.Categoria;
import co.uniquindio.p2.agenda.model.Grupo;
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
import controllers.ContactoController;
public class GrupoController implements Initializable{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Categoria> comboBoxCategoria;

    @FXML
    private TextField txtNombreGrupo;

    @FXML
    private Button btnVolver;

    @FXML
    private Button btnBuscarContacto;
    @FXML
    private Button btnLimpiar;

    @FXML
    private Button btnEliminar;

    @FXML
    private TableView<Grupo> tableViewGrupos;
    @FXML
    private TableColumn<Grupo, Categoria> columCategoria;
    @FXML
    private TableColumn<Grupo, String> columNombreGrupo;

    @FXML
    private Button btnNuevoGrupo;

	private Aplicacion aplicacion;

	private InicioController inicioController;


	private Agenda agenda;

	private Stage stage;

//Atributos y metodos necesarios para inicializar la tabla de grupos
    ObservableList<Grupo> listaGrupos= FXCollections.observableArrayList();
    ObservableList<Grupo> listaGrupos2= FXCollections.observableArrayList();

   	private Grupo grupoSeleccion;
    private ObservableList<Grupo> getDocumentosGrupos() {
    	listaGrupos.addAll(agenda.getListaGrupos());
		return listaGrupos;
	}

    @FXML
    void crearGrupo(ActionEvent event) {
    	String nombreGrupo = txtNombreGrupo.getText();
    	Categoria categoriaGrupo= comboBoxCategoria.getValue();
    	if(validarDatos(nombreGrupo,categoriaGrupo)){
    		nuevoGrupo(nombreGrupo, categoriaGrupo);
    		txtNombreGrupo.setText("");
    		comboBoxCategoria.setValue(null);
    	}
    }

	private void nuevoGrupo(String nombreGrupo, Categoria categoria){
		if(aplicacion.crearGrupo(nombreGrupo, categoria)){
			mostrarMensaje("Notificacion grupo", "Grupo creado", "El grupo "+ nombreGrupo+ " Ha sido creado con exito", AlertType.INFORMATION);
			tableViewGrupos.getItems().clear();
			tableViewGrupos.setItems(getDocumentosGrupos());

		}else {
			mostrarMensaje("Notificacion grupo", "Grupo no creado","El grupo no se ha podido crear" , AlertType.WARNING);
		}
	}


    @FXML
    void showVentanaPrincipal(ActionEvent event) {
    	inicioController.show();
		stage.close();
    }

    @FXML
    void buscarGrupo(ActionEvent event) {

    }


    @FXML
    void limpiarCampos(ActionEvent event) {

    }

/**
 * Se hace una confirmacion con el showConfirmDialog, este retorna 0 si seleccionan "Si", 1 si seleccionan "No o cancelar" y si no seleccionan retorna .1
 * @param event
 * @throws GrupoException
 */
    @FXML
    void eliminarGrupo(ActionEvent event) throws GrupoException {
    	if(grupoSeleccion !=null){
    		int confirmacion= JOptionPane.showConfirmDialog(null, "Seguro que desea eliminar este grupo?");
    		if(confirmacion==0){
    			if (aplicacion.eliminarGrupo(grupoSeleccion)) {
    				listaGrupos.remove(grupoSeleccion);
    				mostrarMensaje("Grupo eliminado", "Eliminacion de grupo", "Se ha elimindao el grupo correctamente", AlertType.INFORMATION);
				}else {
					mostrarMensaje("Grupo eliminado", "Eliminacion de grupo", "No se ha podido eliminar el grupo", AlertType.WARNING);
				}
    		}
    	}else{
    		mostrarMensaje("Grupo seleccion", "Grupo seleccion", "No se ha seleccionado ningun grupo", AlertType.WARNING);
    	}


    }

    @FXML
    void initialize() {
    	txtNombreGrupo.setText("");
		comboBoxCategoria.setValue(null);
    }

	public void init(Stage stage, InicioController inicioController) {
		this.inicioController = inicioController;
		this.agenda = aplicacion.getAgenda();
		this.stage = stage;

	}

	public void setAplicacion(Aplicacion aplicacion){
		this.aplicacion = aplicacion;
	}


	private void mostrarInfoGrupo() {
		if(grupoSeleccion != null) {
			txtNombreGrupo.setText(grupoSeleccion.getNombre());
			comboBoxCategoria.setValue(null);
		}
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {

		comboBoxCategoria.getItems().addAll(Categoria.AMIGOS, Categoria.FAMILIA, Categoria.FIESTA, Categoria.OFICINA);
		this.columNombreGrupo.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		this.columCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));


		tableViewGrupos.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if(newSelection != null){
				grupoSeleccion= newSelection;
				mostrarInfoGrupo();
			}
		});

	}


	private boolean validarDatos(String nombreGrupo, Categoria categoriaGrupo) {
		String notificacion = "";

		if (nombreGrupo == null || nombreGrupo.equals(""))
			notificacion += "Nombre invalido\n";


		/*Se valida que el saldo ingresado no sea null ni sea cadena vacía,
		además se valida que sea numérico para su correcta conversión */
		if(categoriaGrupo==null){
			notificacion+= "Ninguna categoria ha sido seleccionada";
		}

		if (!notificacion.equals("")) {
			mostrarMensaje("Notificación", "Grupo no creado", notificacion, AlertType.WARNING);
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



}
