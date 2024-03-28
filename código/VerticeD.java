import java.util.*;

/**
	* Clase que representa un vértice en una gráfica dirigida.
	* Cada vértice tiene un identificador único, un conjunto de vecinos entrantes (inVecinos) y un conjunto de vecinos salientes (exVecinos).
	* También puede tener un estado de "absorbido" que indica si el vértice ha sido absorbido por otro vértice en el proceso de análisis de la gráfica.
*/
public class VerticeD{

	//Atributos de los vertices de la grafica
	int id = 0;
	String identificador;
	ArrayList<VerticeD> inVecinos = new ArrayList<VerticeD>(); //Una herramienta que nos servira mas tarde
    ArrayList<VerticeD> exVecinos = new ArrayList<VerticeD>();
	boolean absorbido = false;

	/**
	    * Constructor de la clase VerticeD.
	    * Crea un nuevo vértice con el identificador dado.
	    *
	    * @param id El identificador único del vértice.
    */
	public VerticeD(String id){
		this.identificador = id;
	}

	/**
	    * Método que devuelve el grado de entrada de un vértice.
	    * El grado de entrada de un vértice es la cantidad de vecinos entrantes que tiene.
	    *
	    * @return El grado de entrada del vértice.
    */
	public int getInGrado(){
		return inVecinos.size();
	}

	/**
	    * Método que devuelve el grado de salida de un vértice.
	    * El grado de salida de un vértice es la cantidad de vecinos salientes que tiene.
	    *
	    * @return El grado de salida del vértice.
    */
	public int getExGrado(){
		return exVecinos.size();
	}

	/**
	    * Método que devuelve los vecinos entrantes de un vértice.
	    * Los vecinos entrantes de un vértice son aquellos vértices que tienen una flecha que apunta hacia el vértice actual.
	    *
	    * @return Una lista de los vecinos entrantes del vértice.
    */
	public ArrayList<VerticeD> getInVecinos() {
		return inVecinos;
	}

	/**
		* Método que devuelve los vecinos salientes de un vértice.
		* Los vecinos salientes de un vértice son aquellos vértices hacia los cuales hay una flecha desde el vértice actual.
		*
		* @return Una lista de los vecinos salientes del vértice.
	*/
	public ArrayList<VerticeD> getExVecinos() {
		return exVecinos;
	}

	/**
	    * Método que devuelve el identificador de un vértice.
	    *
	    * @return El identificador del vértice.
    */
	public String getID(){
		return identificador;
	}

	/**
	    * Método equals que verifica si dos vértices son iguales.
	    * Dos vértices son iguales si tienen el mismo identificador.
	    *
	    * @param v1 El vértice con el que se compara el vértice actual.
	    * @return true si los vértices son iguales, false en caso contrario.
    */
	public boolean equals(VerticeD v1){
		if(this.identificador.equals(v1.getID())){
			return true;
		}else{
			return false;
		}
	}

	/**
	    * Método toString que devuelve una representación en cadena de un vértice.
	    * La representación en cadena consiste en el identificador del vértice.
	    *
	    * @return El identificador del vértice.
    */
	public String toString(){
		return identificador;
	}

}
