package aplicacion;
/* Al registrar un nuevo vehiculo se recogen los siguientes datos: nombre del cliente, matricula y fecha de entrada. */

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Vehiculo {		// Creo la clase Vehiculo con sus 3 atributos mas el atributo enumerado TipoVehiculo, "tipo".
	
	private String nombreCliente;
	private int matricula;
	public static LocalDate fecha;
	private TipoVehiculo tipo;
	
	public Vehiculo(String nombreCliente, int matricula, LocalDate fecha, TipoVehiculo tipo)
	{										
		this.nombreCliente=nombreCliente;
		this.matricula=matricula;
		this.fecha=fecha;
		this.tipo=tipo;
	}
	//-----------------------------------------------------------------
	// getNombreCliente
	//-----------------------------------------------------------------
	public String getNombreCliente() {	// Creo los Getters y Setters de todos los atributos. 
		return nombreCliente;
	}
	//-----------------------------------------------------------------
	// setNombreCliente
	//-----------------------------------------------------------------
	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}
	//-----------------------------------------------------------------
	// getMatricula
	//-----------------------------------------------------------------
	public int getMatricula() {
		return matricula;
	}
	//-----------------------------------------------------------------
	// setMatricula
	//-----------------------------------------------------------------
	public void setMatricula(int matricula) {
		this.matricula = matricula;
	}
	//-----------------------------------------------------------------
	// getFecha
	//-----------------------------------------------------------------
	public LocalDate getFecha() {
		return fecha;
	}
	//-----------------------------------------------------------------
	// setFecha
	//-----------------------------------------------------------------
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}
	//-----------------------------------------------------------------
	// getTipo
	//-----------------------------------------------------------------
	public TipoVehiculo getTipo() {
		return tipo;
	}
	//-----------------------------------------------------------------
	// setTipo
	//-----------------------------------------------------------------
	public void setTipo(TipoVehiculo tipo) {
		this.tipo = tipo;
	}
	//-----------------------------------------------------------------
	// toString
	//-----------------------------------------------------------------
	public String toString() {	// Creo un toString donde devuelvo todos los atributos, y para "fecha" (tipo LocalDate), uso el metodo 
							// ofPattern de la clase DateTimeFormatter para obtener el formato deseado de fecha, lo guardo en 
						// "formato" y asigno al String "fechaFormateada" dicho atributo "fecha" convertido con el metodo format en cadena.
		
	    DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	    String fechaFormateada = fecha.format(formato);

		return "Cliente=" + nombreCliente + ", Matricula=" + matricula + ", Fecha=" + fechaFormateada
				+ ", Tipo de vehiculo=" + tipo ;
	}
}