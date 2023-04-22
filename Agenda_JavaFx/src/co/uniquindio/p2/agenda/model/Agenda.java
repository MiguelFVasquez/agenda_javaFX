package co.uniquindio.p2.agenda.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import co.uniquindio.p2.agenda.exceptions.AgendaException;
import co.uniquindio.p2.agenda.exceptions.ContactoException;
import co.uniquindio.p2.agenda.exceptions.GrupoException;
import co.uniquindio.p2.agenda.exceptions.ReunionException;

public class Agenda implements Serializable {

	/**
	 *
	 */
	//private static final long serialVersionUID = 1L;

	private String nombre;
	private Contacto[] listaContactos;
	private Grupo[] listaGrupos;
	private Reunion[] listaReuniones;


	public Agenda(String nombre, int numeroContactos,int numeroGrupos,int numeroReuniones) {
		this.nombre = nombre;
		this.listaContactos = new Contacto[numeroContactos];
		this.listaGrupos = new Grupo[numeroGrupos];
		this.listaReuniones = new Reunion[numeroReuniones];

		//Inicializo valores para probarlos en la tabla

/*
		Contacto contacto1 = new Contacto("Miguel", "Floweers", "Cr 24", "314121", "miguelF@algo.com", 0, 0);
		Contacto contacto2 = new Contacto("Juan", "Bandido", "Cr 24A", "2131212", "juanV@algo.com", 0, 0);
		listaContactos[0] = contacto1;
		listaContactos[1] = contacto2;
*/
	}


	public Agenda() {
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public Contacto[] getListaContactos() {
		return listaContactos;
	}


	public void setListaContactos(Contacto[] listaContactos) {
		this.listaContactos = listaContactos;
	}


	public Grupo[] getListaGrupos() {
		return listaGrupos;
	}


	public void setListaGrupos(Grupo[] listaGrupos) {
		this.listaGrupos = listaGrupos;
	}


	public Reunion[] getListaReuniones() {
		return listaReuniones;
	}


	public void setListaReuniones(Reunion[] listaReuniones) {
		this.listaReuniones = listaReuniones;
	}


	@Override
	public String toString() {
		return "Agenda \nnombre" + nombre ;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Agenda other = (Agenda) obj;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}

	/**
	 *Recibe un contacto como parametro
	 *Se verifica si este contacto ya existe si la varible contacto toma el valor de null, indica que no existe este contacto, por lo tanto se puede crear
	 *Luego se ve que posicion del arreglo está disponible, si se encuentra una posicion disponible se agrega el contacto al arreglo
	 *Si retorna -1 indica que no hay espacio disponibe, por lo tanto se lanza la excepcion
	 *Si este metodo retorna false es que el contacto no se ha podido agregar, si retorna true es que fue agregado con exito
	 * @param newContacto
	 * @throws ContactoException
	 */
	public boolean aniadirContacto(Contacto newContacto) {
		Contacto contacto = obtenerContacto(newContacto);
		int posDisponible = obtenerPosicionLibre();
		if (!existeContacto(newContacto)) {
			if(contacto == null && posDisponible != -1) {
				listaContactos[posDisponible] = newContacto;
				return true;
			}
		}else{
			return false;
		}
		return false;

	}

	public Contacto obtenerContacto(Contacto newContacto) {
		Contacto contactoEncontrado = null;
		for(Contacto contacto : listaContactos) {
			//Tengo que ponerle el que sea diferente a null porque o sino causo un nullPointerException
			if(contacto != null && contacto.getNombre().equals(newContacto.getNombre()) && contacto.getTelefono().equals(newContacto.getTelefono())) {
				contactoEncontrado = contacto;
			}
		}
		return contactoEncontrado;
	}

	private boolean existeContacto(Contacto newContacto) {
		boolean encontrado= false;
		List<Contacto> asList = Arrays.asList(this.listaContactos);
		for (Contacto contacto : asList) {
			if(contacto!= null && contacto.getNombre().equals(newContacto.getNombre()) && contacto.getTelefono().equals(newContacto.getTelefono()) ){
				encontrado=true;
				return encontrado;
			}
		}
		return encontrado;

	}
	/**
	 * Recibe el arreglo y se analiza cada posicion y si en la posicion i=null la variable pos toma este valor
	 * si no hay ningun sitio disponible retorna -1
	 * @param arreglo
	 * @return
	 */
	private int obtenerPosicionLibre() {
	    for (int i = 0; i < listaContactos.length; i++) {
	        if(listaContactos[i] == null) {
	            return i;
	        }
	    }
	    return -1;
	}

	/** METODO 2
	 *
	 * @param contactoEliminar
	 * Se evalua que el contacto si exista, si eciste se inicia el recorrido del arreglo hasta encontrarlo en el arreglo, al momento de encontrarlo se elimina
	 * Si el contacto no eciste lanza la excepcion
	 *
	 * @return false, si el contacto no se pudo eliminar
	 * @return true, si el contacto fue eliminado con exito
	 * @throws ContactoException
	 */
	public boolean eliminarContacto2(Contacto contactoEliminar) throws ContactoException{

		boolean eliminado= false;
		boolean existeContacto= existeContacto(contactoEliminar);

		if(!existeContacto){
			eliminado= false;
			throw new ContactoException("El contacto no existe, por lo tanto no se puede eliminar");
		}

		for (int i = 0; i < listaContactos.length; i++) {
			if(listaContactos[i] == contactoEliminar){
				listaContactos[i]= null;
				eliminado= true;
				break;
			}
		}

		return eliminado;
	}
	/**
	 *	Si en una posicion del arreglo hay un "null" significa que no tiene ningun contacto, por lo tanto la agenda no estaria llena
	 * @return
	 */
	public boolean agendaLlena(){
		boolean agendaLlena= true;;
		for (int i = 0; i < listaContactos.length; i++) {
			if(listaContactos[i] ==null){
				agendaLlena=false;
			}
		}
		return agendaLlena;
	}
	/**
	 * Si hay una posicion con valor null indica que hay un hueco libre, el cual se suma al contador
	 * @return la cantidad de espacios dispibles del arreglo
	 */
	public int huecosLibres(){
		int cantHuecos=0;
		for (int i = 0; i < listaContactos.length; i++) {
			if(listaContactos[i] ==null){
				cantHuecos++;
			}
		}

		return cantHuecos;
	}
//------------------------- la logica de los grupos-----------------------------

/**
 *
 * @param newGrupo
 * @return
 */
	public boolean crearGrupo(Grupo newGrupo){
		boolean creado= false;
		Grupo grupoNuevoGrupo= obtenerGrupo(newGrupo);
		int posDisponible = obtenerPosicionLibreGrupo();


		if(!existeGrupo(newGrupo)){
			if(grupoNuevoGrupo == null && posDisponible != -1) {
				listaGrupos[posDisponible] = newGrupo;
				return true;
			}
		}
		return creado;
	}


/**
 *
 * @param newGrupo
 * @return
 */
	public Grupo obtenerGrupo(Grupo newGrupo) {
		Grupo grupoEncontrado = null;
		for(Grupo grupoAux : listaGrupos) {
			//Tengo que ponerle el que sea diferente a null porque o sino causo un nullPointerException
			if(grupoAux != null && grupoAux.getNombre().equals(newGrupo.getNombre())) {
				grupoEncontrado = grupoAux;
			}
		}
		return grupoEncontrado;
	}

/**
 *
 * @param newGrupo
 * @return
 */
	private boolean existeGrupo(Grupo newGrupo) {
		boolean encontrado= false;
		List<Grupo> asList = Arrays.asList(this.listaGrupos);
		for (Grupo grupoAux : asList) {
			if(grupoAux != null && grupoAux.getNombre().equals(newGrupo.getNombre())){
				encontrado=true;
				return encontrado;
			}
		}
		return encontrado;

	}

	public boolean eliminarGrupo(Grupo grupoEliminar) throws GrupoException{

		boolean eliminado= false;
		boolean existeGrupo= existeGrupo(grupoEliminar);

		if(!existeGrupo){
			eliminado= false;
			throw new GrupoException("El grupo no existe, por lo tanto no se puede eliminar");
		}

		for (int i = 0; i < listaGrupos.length; i++) {
			if(listaGrupos[i] == grupoEliminar){
				listaGrupos[i]= null;
				eliminado= true;
				break;
			}
		}

		return eliminado;
	}


/**
 *
 * @return
 */
	private int obtenerPosicionLibreGrupo() {
	    for (int i = 0; i < listaGrupos.length; i++) {
	        if(listaGrupos[i] == null)
	            return i;
	    }
	    return -1;
	}
//Metodos reunion------------------------------------------------------------------------------
	/**
	 *
	 * @param newReunion
	 * @return
	 */
	public boolean crearReunion(Reunion newReunion){
		boolean creado= false;
		Reunion reunionNueva= obtenerReunion(newReunion);
		int posDisponible = obtenerPosicionLibreReunion();


		if(!existeReunion(newReunion)){
			if(reunionNueva == null && posDisponible != -1) {
				listaReuniones[posDisponible] = newReunion;
				return true;
			}
		}
		return creado;
	}

	/**
	 *
	 * @param newReunion
	 * @return
	 */
	public Reunion obtenerReunion(Reunion newReunion) {
		Reunion reunionEncontrada = null;
		for(Reunion reunionAux : listaReuniones) {
			//Tengo que ponerle el que sea diferente a null porque o sino causo un nullPointerException
			if(reunionAux != null && reunionAux.getDescripcion().equals(newReunion.getDescripcion())) {
				reunionEncontrada = reunionAux;
			}
		}
		return reunionEncontrada;
	}
	/**
	 *
	 * @param newReunion
	 * @return
	 */
	private boolean existeReunion(Reunion newReunion) {
		boolean encontrado= false;
		List<Reunion> asList = Arrays.asList(this.listaReuniones);
		for (Reunion reunionAux : asList) {
			if(reunionAux != null && reunionAux.getDescripcion().equals(newReunion.getDescripcion())){
				encontrado=true;
				return encontrado;
			}
		}
		return encontrado;

	}
	private int obtenerPosicionLibreReunion() {
	    for (int i = 0; i < listaGrupos.length; i++) {
	        if(listaGrupos[i] == null)
	            return i;
	    }
	    return -1;
	}

	public boolean eliminarReunion(Reunion reunionEliminar) throws ReunionException{

		boolean eliminado= false;
		boolean existeReunion= existeReunion(reunionEliminar);

		if(!existeReunion){
			eliminado= false;
			throw new ReunionException("La reunion no existe, por lo tanto no se puede eliminar");
		}

		for (int i = 0; i < listaReuniones.length; i++) {
			if(listaReuniones[i] == reunionEliminar){
				listaReuniones[i]= null;
				eliminado= true;
				break;
			}
		}

		return eliminado;
	}


}
