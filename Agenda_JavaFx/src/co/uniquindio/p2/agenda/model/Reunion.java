package co.uniquindio.p2.agenda.model;

import java.util.Arrays;

public class Reunion implements IReunion{

	private String descripcion;
	private String fecha;
	private String hora;
	Contacto[] listaContactos;


	public Reunion() {
		super();
	}


	public Reunion(String descripcion, String fecha, String hora, Contacto[] listaContactos) {
		super();
		this.descripcion = descripcion;
		this.fecha = fecha;
		this.hora = hora;
		this.listaContactos = listaContactos;
	}


	/**
	 * @param descripcion
	 * @param fecha
	 * @param hora
	 */
	public Reunion(String descripcion, String fecha, String hora) {
		super();
		this.descripcion = descripcion;
		this.fecha = fecha;
		this.hora = hora;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public String getFecha() {
		return fecha;
	}


	public void setFecha(String fecha) {
		this.fecha = fecha;
	}


	public String getHora() {
		return hora;
	}


	public void setHora(String hora) {
		this.hora = hora;
	}


	public Contacto[] getListaContactos() {
		return listaContactos;
	}


	public void setListaContactos(Contacto[] listaContactos) {
		this.listaContactos = listaContactos;
	}


	@Override
	public String toString() {
		return "Reunion [descripcion=" + descripcion + ", fecha=" + fecha + ", hora=" + hora + ", listaContactos="
				+ Arrays.toString(listaContactos) + "]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
		result = prime * result + ((hora == null) ? 0 : hora.hashCode());
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
		Reunion other = (Reunion) obj;
		if (fecha == null) {
			if (other.fecha != null)
				return false;
		} else if (!fecha.equals(other.fecha))
			return false;
		if (hora == null) {
			if (other.hora != null)
				return false;
		} else if (!hora.equals(other.hora))
			return false;
		return true;
	}


	@Override
	public void agendar() {

		System.out.println("agendado");
	}



}
