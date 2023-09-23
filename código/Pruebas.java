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

public class Pruebas{

	public static void main (String args []){

		///////////////////////////////////////////
		String nombreArchivo = "Archivo.txt";

	   	List<String> vertex = getVertices(nombreArchivo);
	   	List<String> arrow = getFlechas(nombreArchivo);
	   	ArrayList<VerticeD> vertices = new ArrayList<VerticeD>();
	   	ArrayList<Flecha> flechas = new ArrayList<Flecha>();
	   	for (String s : vertex) {
		   vertices.add(new VerticeD(s));

	   	}
	   	for (String ss : arrow) {
		   String[] a = ss.split(",");
		   VerticeD ver1 = new VerticeD("Aux1");
		   VerticeD ver2 = new VerticeD("Aux2");
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
		   Flecha f = new Flecha(ver1,ver2);
		   flechas.add(f);
	   	}
	   	Digrafica d = new Digrafica(vertices,flechas);

		//
		Graph<String, String> grafo = new DirectedSparseGraph<>();

        for(VerticeD v1 : d.vertices){
			grafo.addVertex(v1.identificador);
		}

		System.out.println("___________________________");
		System.out.println(d.flechas);
		System.out.println("___________________________");
		System.out.println(d.vertices);
		int cont = 1;
		for(Flecha fle1 : d.flechas){
			String faux = fle1.extremo1 + "," + fle1.extremo2;
			String[] a = faux.split(",");
			String saux = "Flecha";
			saux = saux + cont;
			grafo.addEdge(saux, a[0], a[1]);
			cont++;
		}

        // Crear un layout dirigido
		CircleLayout<String, String> layout = new CircleLayout<>(grafo);

        // Crear un visualizador dirigido
        VisualizationViewer<String, String> visualizador = new VisualizationViewer<>(layout);

        // Configurar un decorador para etiquetas
        visualizador.getRenderContext().setVertexLabelTransformer(v -> v.toString());

        // Crear un frame para la visualización
        JFrame frame = new JFrame("Visualizacion de Grafo Dirigido");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(visualizador);
        frame.pack();
        frame.setVisible(true);
    }

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
        } catch (IOException e) {
            e.printStackTrace();
        }
        return elementos;
 	}

	public static List<String> getFlechas(String nombreArchivo) {
       List<String> elementos = new ArrayList<>();
       try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
           String linea;
           int cont = 0;
           while ((linea = br.readLine()) != null) {
               if (cont > 1) {
                   elementos.add(linea.trim());
               } else{
                   cont++;
               }
			}
       } catch (IOException e) {
           e.printStackTrace();
       }
       return elementos;
   }

}
