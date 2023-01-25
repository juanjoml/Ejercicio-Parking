package aplicacion;

public class Plaza {		// Creo la clase Plaza con un atributo booleano, "ocupado" y otro tipo Vehiculo, "vehiculo"
	
	private boolean ocupado;
	private Vehiculo vehiculo;
	
	public Plaza()		// Creo un constructor vacio de un objeto Plaza con sus valores por defecto.
	{
		this.ocupado=false;
		this.vehiculo=null;
	}
	//-----------------------------------------------------------------
	// getVehiculo
	//-----------------------------------------------------------------
	public Vehiculo getVehiculo()	// Devuelvo el atributo "vehiculo" con todos los valores de la instancia de este.
	{
		return vehiculo;
	}
	//-----------------------------------------------------------------
	// aparcarVehiculo
	//-----------------------------------------------------------------
	public void aparcarVehiculo(Vehiculo vehiculo)	// Para el parametro tipo Vehiculo que le pasamos, cambiamos el objeto Plaza,
	{												// (ahora, estando ocupada y teniendo su propiedad vehiculo los atributos de dicho parametro).
		this.ocupado=true;							
		this.vehiculo=vehiculo;
	}
	//-----------------------------------------------------------------
	// sacarVehiculo
	//-----------------------------------------------------------------
	public void sacarVehiculo()		// Cambia el objeto Plaza (no esta ocupada y no hay vehiculo).
	{								 
		this.ocupado=false;
		this.vehiculo=null;
	}
	//-----------------------------------------------------------------
	// estaOcupado
	//-----------------------------------------------------------------
	public boolean estaOcupado()	// Devuelve si la plaza en la que llamamos a este metodo esta ocupada o no.
	{
		return this.ocupado;
	}	
	//-----------------------------------------------------------------
	// toString
	//-----------------------------------------------------------------
	@Override
	public String toString() {	// Creo un toString donde uso dos operadores ternarios: Uno, que asigna a "estado" el valor resultante de
								//	la expresion "this.ocupado" (si es true selecciona "ocupado" y si es false, "libre"),
								//	 y otro que asigna a "v" el valor resultante de la expresion "this.vehiculo==null" (si es true, 
								//	selecciona "" (no hay vehiculo) y si es false, el toString del atributo "vehiculo".
		String estado = (this.ocupado) ? "ocupado" : "libre";
		String v = (this.vehiculo==null) ? "" : vehiculo.toString();

		return "[" + estado + "] " + v;	// Devuelve los valores de los Strings "estado" y "v".
	}
}