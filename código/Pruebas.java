import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JFrame;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import java.awt.Color;
import java.util.function.Function;
import java.awt.Paint;
import java.awt.Dimension;
import java.awt.geom.Point2D;

/**
	* Clase de prueba que demuestra la construcción y visualización de un grafo dirigido utilizando la biblioteca JUNG.
	* Esta clase incluye métodos para leer datos de un archivo, construir un grafo dirigido y visualizarlo en una ventana.
*/
public class Pruebas{

	/**
		* Método principal que inicia la aplicación.
		* Este método crea un grafo dirigido a partir de los datos obtenidos de un objeto Digrafica.
		* Los vértices y las flechas del grafo se obtienen mediante el método getDigrafica().
		* Se visualiza el grafo utilizando la biblioteca JUNG y Swing.
		*
		* @param args Argumentos de línea de comandos (no se utilizan en este método).
	*/
	public static void main (String[] args){

		// Obtener un objeto Digrafica con datos de vértices y flechas
		Digrafica d = getDigrafica();

		// Crear una grafica dirigida
		Graph<String, String> grafo = new DirectedSparseGraph<>();

		// Agregar vértices al grafo
		for(VerticeD v1 : d.vertices){
			grafo.addVertex(v1.identificador);
		}

		// Imprimir las flechas y vértices obtenidos de Digrafica (para depuración)
		System.out.println("___________________________");
		System.out.println(d.flechas);
		System.out.println("___________________________");
		System.out.println(d.vertices);

		// Agregar flechas al grafo y asignar identificadores a las flechas
		int cont = 1;
		for(Flecha fle1 : d.flechas){
			String faux = fle1.extremo1 + "," + fle1.extremo2;
			String[] a = faux.split(",");
			String saux = "Flecha";
			saux = saux + cont;
			grafo.addEdge(saux, a[0], a[1]);
			cont++;
		}

		// Obtener el núcleo de acuerdo a alguna lógica (en este caso se usa getNucleoAci())
		ArrayList<VerticeD> nuc = d.getNucleoAci();
		System.out.println(nuc);

		// Crear un layout circular para la grafica
		CircleLayout<String, String> layout = new CircleLayout<>(grafo);

		// Crear un visualizador de la grafica
		VisualizationViewer<String, String> visualizador = new VisualizationViewer<>(layout);

		// Configurar un decorador para las etiquetas de los vértices
        visualizador.getRenderContext().setVertexLabelTransformer(v -> v.toString());

		// Configurar la función para pintar los vértices, azules si pertenecen al nucleo, rojo en otro caso
		Function<String, Paint> vertexPaintFunction = v -> {
			for (VerticeD v1 : nuc) {
	   			if (v.equals(v1.identificador)) {
		   			return Color.BLUE;
	   			}
   			}
			return Color.RED;
		};
		visualizador.getRenderContext().setVertexFillPaintTransformer(vertexPaintFunction::apply);

		/// Crear un frame para la visualización de la grafica
		JFrame frame = new JFrame("Visualizacion de Grafo Dirigido");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(visualizador);
		frame.pack();
		frame.setVisible(true);
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
		*        Cada string representa una flecha en el formato "origen,destino".
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
		String nombreArchivo = "Archivo.txt";

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
