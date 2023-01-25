package aplicacion;
/* Aplicacion que gestiona un parking de estacionamiento de larga estancia de vehiculos. 
   Los tipos de vehiculos que pueden ser estacionados en el parking son: coche, moto y furgoneta.
    
   El parking tiene una capacidad de 25 plazas, que se distribuyen, de manera cuadrada, en una unica planta de 5 x 5 plazas.
    
   La aplicacion permite: registrar un nuevo vehiculo, liberar una plaza y listar las plazas de parking ocupadas y 
   disponibles, tanto en formato de lista como en una matriz de 5 x 5 plazas.
   
   A la hora de liberar una plaza de parking se calcula la diferencia de fechas entre la fecha de entrada y la de salida, 
   y cobra al cliente en funcion del tipo de vehiculo:	importe = dias x 5 + recargo
   
   El recargo es de 1,50 para las motos, 2,75 para los coches y 5 para las furgonetas.
   
   Ademas, se tienen 5 plazas adicionales, disponibles unicamente para caravanas y autocaravanas, cobrando al cliente 
   un recargo de 3,75, que se cobra diariamente segun la formula: importe = dias x 6,25 + dias x recargo.
   
   Se controlan que los datos introducidos son correctos, si el parking esta lleno y tiene plazas libres. */

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Aplicacion {

	static int numFilas = 5;
	static int numColumnas = 5;		
					// Creo el ArrayList bidimensional "plazas",cuyo maximo seran 5("numFilas").
	public static ArrayList<ArrayList<Plaza>> plazas = new ArrayList<>(numFilas); 
					// Creo el ArrayList "plazasExtra" para las plazas de caravana y autocaravana.
	public static ArrayList<Plaza> plazasExtra = new ArrayList<>(); 

	public static int plazasOcupadas = 0;		 // Creo los atributos estaticos "plazasOcupadas" y "plazasExtrasOcupadas" 
	public static int plazasExtrasOcupadas = 0;	// y los inicializo a 0.

	public static void main(String[] args) {
		
		for(int i=0; i < numFilas; i++) // Recorro con un doble bucle for el ArrayList "plazas" y en cada iteracion del primero,  
		{								// agrego un nuevo ArrayList, hasta 5("numFilas"), que almacenan objetos tipo Plaza.
			plazas.add(new ArrayList<Plaza>(numColumnas));
			for(int j=0; j< numColumnas; j++) {	// Y en el segundo for, en cada iteracion creo un objeto tipo Plaza, hasta 5 ("numColumnas").
				plazas.get(i).add(new Plaza());
			}
		} 		
		for(int i=0; i < 5; i++) { // Recorro con un for el ArrayList "plazasExtra" y en cada iteracion le agrego un objeto tipo Plaza.
		 plazasExtra.add(new Plaza());
		}	
		int iteracion=1;
		Scanner scan = new Scanner (System.in);
		
		while (iteracion==1)	// Creo un bucle while para mostrar siempre el menu (mientras no elija la opcion "Salir").
		{			
			System.out.println("\n Menu de aparcamiento JJ");	// Imprimo todo el menu.
			System.out.println("1) Registra un coche, moto o furgoneta");
			System.out.println("2) Registra una caravana o autocaravana");
			System.out.println("3) listar las plazas de parking ocupadas y disponibles");
			System.out.println("4) liberar una plaza");
			System.out.println("5) Salir \n");
			System.out.print("Introduce una opcion: ");
			
			int opcion =scan.nextInt();	// Elijo una opcion.
			
			switch(opcion)
			{
				case 1:
				{
					if (plazasOcupadas>=25) 		// Si hay 25 o mas plazas ocupadas, imprime que no quedan mas plazas.
					{System.out.println("No quedan plazas.");}
					else
					{ registrarVehiculo(); }	// Si no, ejecuta el metodo estatico "registrarVehiculo".
					break;	
				}
				case 2:
				{
					if (plazasExtrasOcupadas>=5) 	// Si hay 5 o mas plazas extra ocupadas, imprime que no quedan mas plazas.
					{ System.out.println("No quedan plazas."); }
					else
					{ registrarVehiculoEspecial(); }	// Si no, ejecuta el metodo estatico "registrarVehiculoEspecial".
					break;		
				}
				case 3:
				{ 
					listarPlazas();	// Ejecuta el metodo estatico "listarPlazas".
					break;
				}
			case 4:
				{ 
					liberarPlaza(); // Ejecuta el metodo estatico "liberarPlaza".
					break;
				}
			case 5:
				{
					iteracion=0; // Cambio el valor de "iteracion" y salgo del while y del menu.
					break;
				}
			}
		}
	}
	//-----------------------------------------------------------------
	// buscarPlaza
	//-----------------------------------------------------------------
	public static int[] buscarPlaza()
	{
		int [] ret  = new int[2]; // Creo el Array "ret" de tamaño 2, para guardar las coordenadas de la primera plaza libre disponible.
		
		for(int i=0; i < numFilas; i++) {			// Recorro con un doble bucle for el ArrayList bidimensional "plazas"
			for(int j=0; j < numColumnas; j++) {
				
				Plaza p = plazas.get(i).get(j); // Doy dentro de una variable tipo Plaza cada posicion de dicho ArrayList bidimensional 
				
				if (p.estaOcupado()==false)	//  "plazas", y si no esta ocupada (comprobandolo con el metodo "estaOcupado"):
				{
					ret[0]=i;	// El primer valor del Array "ret" va a ser esa posicion i.
					ret[1]=j;	// El segundo valor del Array "ret" va a ser esa posicion j.	
					return ret;	// Y devuelvo ret (las coordenadas de la plaza libre).
				}
			} 
		} 
		return ret; // Uso return para evitar el caso de que no devuelva ningun valor en la funcion.
	}
	//-----------------------------------------------------------------
	// registrarVehiculo
	//-----------------------------------------------------------------	
	public static void registrarVehiculo()
	{
		int[] plazaLibre = buscarPlaza();	// Asigno al Array "plazaLibre", los valores resultantes del metodo "buscarPlaza".
		Scanner scan = new Scanner(System.in);
		System.out.print("");				// Meto los atributos de Vehiculo, "nombreCliente", "matricula" y "fecha".
		System.out.print("Introduce el nombre del cliente: ");
		String nombreCliente = scan.nextLine();
		System.out.print("Introduce la matricula: ");
		int matricula = scan.nextInt();
		LocalDate fecha = LocalDate.now(); // Con el metodo now() de la clase LocalDate (el tipo de dato de "fecha") asigno la fecha actual.
		System.out.print("La fecha de entrada es "+ fecha );
		System.out.print("\n");
		System.out.print("Introduce: 1. Coche 2. Moto 3. Furgoneta: "); // Pido elegir entre coche, moto y furgoneta.
		int tipoVehiculo = scan.nextInt();	// Asigno la opcion elegida a "tipoVehiculo"
		
		switch (tipoVehiculo)
		{				
			case 1:
			{
				TipoVehiculo tipo= TipoVehiculo.COCHE; // Y en cada opcion, asigno a "tipo" su valor del enumerado TipoVehiculo.
				System.out.print("\n Has elegido coche \n");	// Imprimo la eleccion.
				Vehiculo v= new Vehiculo(nombreCliente, matricula, fecha, tipo); // Instancio el objeto registrado ("v").
				plazas.get(plazaLibre[0]).get(plazaLibre[1]).aparcarVehiculo(v); // Y en el ArrayList "plazas", en las dos posiciones que nos da el 	
				plazasOcupadas++; // Array "plazaLibre" le aplico a "v" el metodo "aparcarVehiculo" de la clase Plaza. E incremento "plazasOcupadas".
				break;
			}
			case 2:
			{
				TipoVehiculo tipo= TipoVehiculo.MOTO;
				System.out.print("\n Has elegido moto \n");
				Vehiculo v= new Vehiculo(nombreCliente, matricula, fecha, tipo);
				plazas.get(plazaLibre[0]).get(plazaLibre[1]).aparcarVehiculo(v);
				plazasOcupadas++;
				break;
			}
			case 3:
			{
				TipoVehiculo tipo= TipoVehiculo.FURGONETA ;
				System.out.print("\n Has elegido furgoneta \n");
				Vehiculo v= new Vehiculo(nombreCliente, matricula, fecha, tipo);
				plazas.get(plazaLibre[0]).get(plazaLibre[1]).aparcarVehiculo(v);
				plazasOcupadas++;
				break;
			}
			default:
			{ System.out.println("Opcion incorrecta"); break; } // Si no elijo una de las 3 opciones imprimo "Opcion incorrecta".
		}
	}
	//-----------------------------------------------------------------
	// buscarPlazaExtra
	//-----------------------------------------------------------------	
	public static int buscarPlazaExtra()
	{
		int ret = -1;				// Creo el int "ret", para guardar la posicion de la primera plaza extra libre disponible.
		for(int i=0; i < 5; i++) {	// Recorro con un for el ArrayList "plazasExtra".
			
			Plaza p = plazasExtra.get(i); 	// Asigno a una variable tipo Plaza cada posicion de dicho ArrayList "plazasExtra".
			if (p.estaOcupado()==false)		// Si dicha plaza no esta ocupada:
			{ ret=i;		// El valor de "ret" va a ser esa posicion i.			
			  return ret;	// Y devuelvo ret (la posicion de la plaza libre).
			}
		} 
		return ret; // Uso return para evitar el caso de que no devuelva ningun valor en la funcion.
	}
	//-----------------------------------------------------------------
	// registrarVehiculoEspecial
	//-----------------------------------------------------------------	
	public static void 	registrarVehiculoEspecial()
	{
		int plazaLibre = buscarPlazaExtra();	// Asigno al int "plazaLibre", el valor resultante del metodo "buscarPlazaExtra".
		Scanner scan = new Scanner(System.in);
		System.out.print("");		// Meto los atributos de Vehiculo, "nombreCliente", "matricula" y "fecha".
		System.out.print("Introduce el nombre del cliente: ");
		String nombreCliente = scan.nextLine();
		System.out.print("Introduce la matricula: ");
		int matricula = scan.nextInt();
		LocalDate fecha = LocalDate.now(); // Con el metodo now() de la clase LocalDate (el tipo de dato de "fecha") asigno la fecha actual.
		System.out.print("La fecha de entrada es "+ fecha );
		System.out.print("\n");
		System.out.print("Introduce: 1. Caravana 2. Autocaravana: ");  // Pido elegir entre caravana y autocaravana.
		int tipoVehiculo = scan.nextInt();		// Asigno la opcion elegida a "tipoVehiculo".
		
		switch (tipoVehiculo)
		{			
			case 1:
			{
				TipoVehiculo tipo= TipoVehiculo.CARAVANA ; // Y en cada opcion, asigno a "tipo" su valor del enumerado TipoVehiculo.
				System.out.print("\n Has elegido caravana \n"); // Imprimo la eleccion.
				Vehiculo v= new Vehiculo(nombreCliente, matricula, fecha, tipo); // Instancio el objeto registrado ("v").
				plazasExtra.get(plazaLibre).aparcarVehiculo(v); // Y en el ArrayList "plazasExtra", en la posicion que nos da "plazaLibre"	
				plazasExtrasOcupadas++; // le aplico a "v" el metodo "aparcarVehiculo" de la clase Plaza. E incremento "plazasExtrasOcupadas".
				break; 
			}
			 case 2:
			{
				TipoVehiculo tipo= TipoVehiculo.AUTOCARAVANA ;
				System.out.print("\n Has elegido autocaravana \n");
				Vehiculo v= new Vehiculo(nombreCliente, matricula, fecha, tipo);
				plazasExtra.get(plazaLibre).aparcarVehiculo(v);
				plazasExtrasOcupadas++;
				break;
			} 
			default:
			{ System.out.println("Opcion incorrecta"); break; } // Si no elijo una de las 2 opciones imprimo "Opcion incorrecta".
		}
	}
	//-----------------------------------------------------------------
	// listarPlazas
	//-----------------------------------------------------------------	
	public static void listarPlazas()
	{
		Scanner scan = new Scanner (System.in); // Imprimo las opciones a listar.
		System.out.println("Elige: 1) listar plazas 2) Mostrar matriz plazas 3) Listar plazas extra (caravanas, autocaravanas)");
		int tipoLista = scan.nextInt();	// Elijo una opcion.
		switch(tipoLista)
		{
			case 1:
			{
				String listado = "";	// Declaro e inicializo el String "listado".
				for(int i=0; i < numFilas; i++) {		// Recorro con un doble bucle for el ArrayList bidimensional "plazas".
					for(int j=0; j < numColumnas; j++) {
						
						Plaza p = plazas.get(i).get(j); // Asigno a una variable tipo Plaza cada posicion del ArrayList bidimensional.
						listado += "["+i+", "+j+"] "+p.toString()+"\n"; // Y concateno cada posicion del parking con el toString de Plaza (que, 
					} 										// a su vez, contiene el toString de Vehiculo si esta ocupado) y un salto de linea.
				} 
				System.out.println(listado);	// E imprimo el listado.
				break;
			}
			case 2:
			{
				String listado = "";
				for(int i=0; i < numFilas; i++) {
					for(int j=0; j < numColumnas; j++) {
							
						Plaza p = plazas.get(i).get(j); // Asigno a una variable tipo Plaza cada posicion del ArrayList bidimensional "plazas".
						String estado = (p.estaOcupado()) ? "1" : "0"; // Y con un operador ternario, le doy a cada una 1 si esta ocupado y 0 si no
						listado += estado+" ";		// con el metodo "estaOcupado" de la clase Plaza. Y concateno todos los valores de "estado"
					} 								
					listado +="\n";	// con un salto de linea al final de cada fila.
				} 
				System.out.println(listado); break; // E imprimo la matriz.
			}
			case 3:
			{
				String listadoExtra = "";	// Declaro e inicializo el String "listadoExtra".
				for(int i=0; i < 5; i++) {	// Recorro con un bucle for el ArrayList "plazasExtra".
					
						Plaza p = plazasExtra.get(i); // Asigno a una variable tipo Plaza cada posicion del ArrayList "plazasExtra".
						listadoExtra += p.toString()+"\n"; // Y concateno todos sus toString con un salto de linea.
					} 
				System.out.println(listadoExtra); // E imprimo el listado extra (caravanas y autocaravanas).
				break;
			}
			default:
			{ System.out.println("Opcion incorrecta"); break; } // Si no elijo una de las 3 opciones imprimo "Opcion incorrecta".
		}
	}
	//-----------------------------------------------------------------
	// liberarPlaza
	//-----------------------------------------------------------------	
	public static void liberarPlaza() 
	{
		Scanner scan = new Scanner(System.in);
		System.out.println();
		System.out.println("1) Liberar un coche, moto o furgoneta");	// Pido una opcion para liberar un tipo de vehiculo y lo meto.
		System.out.println("2) Liberar una caravana o autocaravana");
		System.out.print("Introduce una opcion: ");
		
		int opcion =scan.nextInt();	// Elijo una opcion.
		
		switch(opcion)
		{
			case 1:
			{
				liberarPlazaNormal(); break;	// Llamo al metodo "liberarPlazaNormal".
			}
			case 2:
			{
				liberarPlazaExtra();break;		//  Llamo al metodo "liberarPlazaExtra".
			}
		    default:
			{ System.out.println("Opcion incorrecta"); break; } // Si no elijo una de las 3 opciones imprimo "Opcion incorrecta".
		}
	}
	//-----------------------------------------------------------------
	// liberarPlazaNormal
	//-----------------------------------------------------------------		
	public static void liberarPlazaNormal()
	{
		Scanner scan = new Scanner(System.in);
		System.out.println();
		int terminar = 0;
		
		while (terminar == 0) // Creo un bucle while para mostrar siempre el menu mientras no elija una plaza ocupada.
		{
			System.out.print("");	// Pido y meto las coordenadas "x" e "y"
			System.out.print("Introduce la coordenada x: ");
			int x = scan.nextInt();
			while (x>=5 || x<0 )	// Mientras la posicion x sea menor de 0 o mayor o igual de 5 va a dar opcion incorrecta.
			{
				System.out.println("Opcion incorrecta, introduce de nuevo la posicion: ");
				x = scan.nextInt();
			}
			System.out.print("Introduce la coordenada y: ");
			int y = scan.nextInt();
			while (y>=5 || y<0 )	// Mientras la posicion y sea menor de 0 o mayor o igual de 5 va a dar opcion incorrecta.
			{
				System.out.println("Opcion incorrecta, introduce de nuevo la posicion: ");
				y = scan.nextInt();
			}			
			Plaza p = plazas.get(x).get(y); // Doy dentro de una variable tipo Plaza dichas coordenadas del ArrayList bidimensional "plazas".
			
			if (p.estaOcupado()==true)	// Si esta ocupado: 
			{
				System.out.print("Introduce la fecha de salida: ");	// Pedimos y metemos la fecha: año, mes y dia.
				System.out.print("\n");
				System.out.print("Introduce el año: ");
				String anio = scan.next();
				System.out.print("Introduce el mes: ");
				String mes = scan.next();
				System.out.print("Introduce el dia: ");
				String dia = scan.next();
				
				String fechaSalidaString = anio+"-"+mes+"-"+dia; // Los concatenamos en "fechaSalidaString"
				
				// Con el metodo ofPattern de la de la clase DateTimeFormatter obtengo el formato deseado de fecha, y lo guardo en "formato".
				DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd"); 
				
				// Convierto a LocalDate el String "fechaSalidaString" con dicho formato, y lo guardo en "fechaSalida".
				LocalDate fechaSalida = LocalDate.parse(fechaSalidaString, formato);
				
				// Uso la clase Period para obtener la diferencia entre la fecha en que se habia aparcado y la introducida de salida.
				Period duracion = Period.between(p.getVehiculo().getFecha(), fechaSalida); 
				
			    int dias = (int) duracion.getDays(); // Y guardo la cantidad de dias de dicho periodo con "getDays()" en el int "dias".
			    
			    double recargo = 0;	// Declaro e inicializo las variables "recargo" e "importe".
			    double importe = 0;

			    switch(p.getVehiculo().getTipo())	// Y asigno mediante un switch, el recargo y el importe para cada tipo de vehiculo,
			    {									// restando cada plaza ocupada.
				    case COCHE:
				    {
				    	recargo= 2.75;
				    	importe = (dias*5)+recargo;
						plazasOcupadas--;
				    	break;
				    }
				    case MOTO:
				    {
				    	recargo= 1.50;
				    	importe = (dias*5)+recargo;
						plazasOcupadas--;
				    	break;
				    }
				    case FURGONETA:
				    {
				    	recargo= 5;
				    	importe = (dias*5)+recargo;
						plazasOcupadas--;
				    	break;
				    }
				    default:
					{ System.out.println("Opcion incorrecta"); break; } // Si no elijo una de las 3 opciones imprimo "Opcion incorrecta".
			    }
			   System.out.println("El importe es: "+importe); // Finalmente, imprimo el importe de cada operacion,
			    p.sacarVehiculo();		// llamo a la funcion "sacarVehiculo" de la clase Plaza,
				terminar= 1;	// y salgo del bucle.
			}
			else
			{ System.out.println("Esta plaza esta libre"); } // Si esa plaza esta libre, nos lo avisa y se repite el bucle.
		}	
	}	
	//-----------------------------------------------------------------
	// liberarPlazaExtra
	//-----------------------------------------------------------------	
	public static void liberarPlazaExtra()
	{
		Scanner scan = new Scanner(System.in);
		System.out.println();
		int terminar = 0;
		
		while (terminar == 0) // Creo un bucle while para mostrar siempre el menu mientras no elija una plaza ocupada.
		{
			System.out.print("");	// Pido y meto la posicion "x".
			
			System.out.print("Introduce la posicion: ");
			int x = scan.nextInt();
			while (x>=5 || x<0)	// Mientras la posicion sea menor de 0 o mayor o igual a 5 va a dar opcion incorrecta.
			{
				System.out.println("Opcion incorrecta, introduce de nuevo la posicion: ");
				x = scan.nextInt();
			}
			Plaza p = plazasExtra.get(x); // Doy dentro de una variable tipo Plaza dicha posicion del ArrayList "plazasExtra".
			
			if (p.estaOcupado()==true)	// Si esta ocupado: 
			{
				System.out.print("Introduce la fecha de salida: ");	// Pedimos y metemos la fecha: año, mes y dia.
				System.out.print("\n");
				System.out.print("Introduce el año: ");
				String anio = scan.next();
				System.out.print("Introduce el mes: ");
				String mes = scan.next();
				System.out.print("Introduce el dia: ");
				String dia = scan.next();
				
				String fechaSalidaString = anio+"-"+mes+"-"+dia; // Los concatenamos en "fechaSalidaString"
				
				// Con el metodo ofPattern de la de la clase DateTimeFormatter obtengo el formato deseado de fecha, y lo guardo en "formato".
				DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd"); 
				
				// Convierto a LocalDate el String "fechaSalidaString" con dicho formato, y lo guardo en "fechaSalida".
				LocalDate fechaSalida = LocalDate.parse(fechaSalidaString, formato);
				
				// Uso la clase Period para obtener la diferencia entre la fecha en que se habia aparcado y la introducida de salida.
				Period duracion = Period.between(p.getVehiculo().getFecha(), fechaSalida); 
				
			    int dias = (int) duracion.getDays(); // Y guardo la cantidad de dias de dicho periodo con "getDays" en el int "dias".
			    
			    double recargo = 0;	// Declaro e inicializo las variables "recargo" e "importe".
			    double importe = 0;
			    
			    switch(p.getVehiculo().getTipo())	// Y asigno mediante un switch, el recargo y el importe para cada tipo de vehiculo,
			    {									// restando cada plaza extra ocupada.
				    case CARAVANA:
				    {
				    	recargo= 3.75;
				    	importe = (dias*6.25)+(recargo*dias);
				    	plazasExtrasOcupadas--;
				    	break;
				    }
				    case AUTOCARAVANA:
				    {
				    	recargo= 3.75;
				    	importe = (dias*6.25)+(recargo*dias);
				    	plazasExtrasOcupadas--;
				    	break;
				    }
				    default:
				    { System.out.println("Opcion incorrecta"); break; } // Si no elijo una de las 2 opciones imprimo "Opcion incorrecta".
			    }
			   System.out.println("El importe es: "+importe); // Finalmente, imprimo el importe de cada operacion,
			    p.sacarVehiculo();		// llamo a la funcion "sacarVehiculo" de la clase Plaza,
				terminar= 1;	// y salgo del bucle.
			}
			else
			{ System.out.println("Esta plaza esta libre"); } // Si esa plaza esta libre, nos lo avisa y se repite el bucle.
		}	
	}	
}