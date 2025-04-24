import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PruebasAux{

	/**
	* Método principal que inicia la aplicación.
	* Este método crea un grafo dirigido a partir de los datos obtenidos de un objeto Digrafica.
	* Los vértices y las flechas del grafo se obtienen mediante el método getDigrafica().
	* Se visualiza el grafo utilizando la biblioteca JUNG y Swing.
	*
	* @param args Argumentos de línea de comandos (no se utilizan en este método).
	*/
	public static void main (String[] args){

        	Digrafica d = getDigrafica();
		System.out.println(d);
		//Stack<VerticeD> alcansables = d.dfs();
		//
		ArrayList<ArrayList<VerticeD>> compos = d.kosaraju();

		System.out.println(compos);

		System.out.println("______________________");
		ArrayList<VerticeD> terminal = d.obtenerCompoTerminal();
		System.out.println(terminal);
	}

	/**
	* Lee y devuelve una lista de vértices desde un archivo dado.
	* Los vértices se leen desde el archivo especificado por el parámetro "nombreArchivo".
	* Se espera que los vértices estén en la segunda línea del archivo, separados por comas.
	*
	* @param nombreArchivo El nombre del archivo desde el cual se leerán los vértices.
	* @return Una lista de strings que representan los vértices leídos desde el archivo.
	* @throws IOException Si ocurre un error al intentar leer el archivo.
 	*/
	public static List<String> getVertices(String nombreArchivo) {
		List<String> elementos = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
			br.readLine();
			String segundaLinea = br.readLine();
        		if (segundaLinea != null) {
            			String[] elementosArray = segundaLinea.split(",");
            			for (String elemento : elementosArray) {
                			elementos.add(elemento);
            			}
        		}
    		}catch (IOException e) {
        		e.printStackTrace();
    		}
    		return elementos;
	}

	/**
	* Lee y devuelve una lista de flechas desde un archivo dado.
	* Las flechas se leen desde el archivo especificado por el parámetro "nombreArchivo".
	* Cada línea del archivo se considera una flecha.
	*
	* @param nombreArchivo El nombre del archivo desde el cual se leerán las flechas.
	* @return Una lista de strings que representan las flechas leídas desde el archivo.
	*Cada string representa una flecha en el formato "origen,destino".
	* @throws IOException Si ocurre un error al intentar leer el archivo.
	*/
	public static List<String> getFlechas(String nombreArchivo) {
		List<String> elementos = new ArrayList<>();

		// Abrir el archivo y leer las flechas
       		try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
			String linea;
			int cont = 0;
			while ((linea = br.readLine()) != null) {
				// Si cont es mayor que 1, se considera que la línea contiene una flecha
				if (cont > 1) {
					// Agregar la línea (flecha) a la lista de elementos después de eliminar los espacios en blanco alrededor
                   			elementos.add(linea.trim());
               			}else{
					// Incrementar cont si la línea no contiene flechas
                   			cont++;
               			}
			}
		} catch (IOException e) {
			// Si ocurre un error al leer el archivo, imprimir la traza de la excepción
			e.printStackTrace();
		}
		return elementos;
   	}

	/**
	* Construye y devuelve un objeto Digrafica a partir de los datos leídos desde un archivo.
	* Los datos de los vértices y las flechas se leen desde el archivo "Archivo.txt".
	*
	* @return Un objeto Digrafica construido a partir de los datos del archivo.
	*/
   	public static Digrafica getDigrafica(){
		// Nombre del archivo del cual se leerán los datos
		String nombreArchivo = "Archivo3.txt";

		// Obtener la lista de vértices y flechas desde el archivo
	   	List<String> vertex = getVertices(nombreArchivo);
	   	List<String> arrow = getFlechas(nombreArchivo);

		// Inicializar listas para almacenar los vértices y las flechas
	   	ArrayList<VerticeD> vertices = new ArrayList<VerticeD>();
	   	ArrayList<Flecha> flechas = new ArrayList<Flecha>();

		// Construir los vértices a partir de los datos leídos
	   	for (String s : vertex) {
			vertices.add(new VerticeD(s));
	   	}

		// Construir las flechas a partir de los datos leídos
	   	for (String ss : arrow) {
			String[] a = ss.split(",");
		  	VerticeD ver1 = new VerticeD("Aux1");
		  	VerticeD ver2 = new VerticeD("Aux2");

			// Buscar los vértices correspondientes en la lista de vértices
		  	for (VerticeD v1 : vertices) {
				if(a[0].equals(v1.identificador)){
					ver1 = v1;
			  	}
		  	}
		  	for (VerticeD v2 : vertices) {
				if(a[1].equals(v2.identificador)){
					ver2 = v2;
			  	}
		  	}

			 // Crear la flecha con los vértices encontrados y agregarla a la lista de flechas
		  	Flecha f = new Flecha(ver1,ver2);
		  	flechas.add(f);
	   	}

		// Construir y retornar un objeto Digrafica con los vértices y las flechas construidas
	   	return new Digrafica(vertices,flechas);
   	}
}
