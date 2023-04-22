package application;

import java.io.IOException;

import co.uniquindio.p2.agenda.exceptions.AgendaException;
import co.uniquindio.p2.agenda.exceptions.ContactoException;
import co.uniquindio.p2.agenda.exceptions.GrupoException;
import co.uniquindio.p2.agenda.exceptions.ReunionException;
import co.uniquindio.p2.agenda.model.Agenda;
import co.uniquindio.p2.agenda.model.Categoria;
import co.uniquindio.p2.agenda.model.Contacto;
import co.uniquindio.p2.agenda.model.Grupo;
import co.uniquindio.p2.agenda.model.Reunion;
import controllers.InicioController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class Aplicacion extends Application{

	//Solo puede existir una instancia de la clase principal, en este caso tenemos una sola intancia
	//de la clase biblioteca
	private Agenda agenda;
	private Stage primaryStage;


	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage= primaryStage;
		this.agenda= new Agenda("Mi agenda", 3, 20, 22);
		mostrarVentanaPrincipal();
	}

	private void mostrarVentanaPrincipal() throws IOException {
		//Se establece la ruta de la ventana que desea ejecutar

		FXMLLoader loader= new FXMLLoader();
		loader.setLocation(Aplicacion.class.getResource("../Views/InicioView.fxml"));
		AnchorPane anchorPane= (AnchorPane)loader.load();
		InicioController inicionController = loader.getController();
		inicionController.setAplicacion(this);

		Scene scene= new Scene(anchorPane);
		primaryStage.setScene(scene);
		primaryStage.show();
		InicioController controller = loader.getController();
		controller.setStage(primaryStage);

	}


	public Agenda getAgenda() {
		return agenda;
	}

	public void setAgenda(Agenda agenda) {
		this.agenda = agenda;
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public static void main(String[] args) {
		launch(args);
	}
//Metodos de contacto ------------------------------------------------------------------------------------------------
	public boolean crearContacto(String nombre, String alias, String direccion, String telefono, String email) {
		Contacto contactoAux= new Contacto(nombre, alias, direccion, telefono, email);
		return agenda.aniadirContacto(contactoAux);
	}

	public boolean eliminarContacto(Contacto contactoSeleccion) throws ContactoException {
		return agenda.eliminarContacto2(contactoSeleccion);
	}
//---------------------------------------------------------------------------------------------------------------------

//Metodos de grupo ----------------------------------------------------------------------------------------------------
	public boolean crearGrupo(String nombreGrupo, Categoria categoria){
		Grupo grupoAux= new Grupo(nombreGrupo, categoria);
		return agenda.crearGrupo(grupoAux);
	}

	public boolean eliminarGrupo(Grupo grupoSeleccion) throws GrupoException{
		return agenda.eliminarGrupo(grupoSeleccion);
	}
//---------------------------------------------------------------------------------------------------------------------

//Metodos de reunion---------------------------------------------------------------------------------------------------

	public boolean crearReunion(String descripcion, String fecha, String hora){
		Reunion reunionAux= new Reunion(descripcion, fecha, hora);
		return agenda.crearReunion(reunionAux);
	}

	public boolean eliminarReunion(Reunion reunionEliminar) throws ReunionException{
		return agenda.eliminarReunion(reunionEliminar);
	}

}
