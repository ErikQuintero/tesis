import java.util.*;

/**
* Clase que modela una flecha en una gráfica dirigida.
* Cada flecha tiene dos extremos, representados por vértices (VerticeD).
*/
public class Flecha{

	//Atributos de la clase flecha
	VerticeD extremo1;
	VerticeD extremo2;

	/**
	* Constructor por defecto de la clase Flecha.
	* Crea una flecha sin asignar sus extremos.
 	*/
	public Flecha(){}

	/**
	* Constructor de la clase Flecha.
	* Crea una flecha con los extremos especificados.
	*
	* @param v1 El primer extremo de la flecha.
	* @param v2 El segundo extremo de la flecha.
	*/
	public Flecha(VerticeD v1, VerticeD v2){
		this.extremo1 = v1;
		this.extremo2 = v2;
	}

	/**
	* Método que devuelve el primer extremo de la flecha.
	*
	* @return El primer extremo de la flecha.
    	*/
	public VerticeD getEX1(){
		return this.extremo1;
	}

	/**
	* Método que devuelve el segundo extremo de la flecha.
	*
	* @return El segundo extremo de la flecha.
 	*/
	public VerticeD getEX2(){
		return this.extremo2;
	}

	/**
	* Método que verifica si dos flechas son iguales.
	* Dos flechas son iguales si tienen los mismos extremos.
	*
	* @param a La flecha con la que se compara la flecha actual.
	* @return true si las flechas son iguales, false en caso contrario.
    	*/
	public boolean equals(Flecha a){
		if(this.extremo1.equals(a.getEX1())&&this.extremo2.equals(a.getEX2())){
			return true;
		}else{
			return false;
		}
	}

	/**
	* Método que devuelve una representación en cadena de la flecha.
	* La representación en cadena consiste en los identificadores de los extremos de la flecha.
	*
	* @return Una cadena que representa la flecha.
    	*/
	public String toString(){
		String arista = "(" + this.extremo1.getID() + "," + this.extremo2.getID() + ")";
		return arista;
	}

	/**
	* Método que orienta la flecha.
	* Cambia el orden de los extremos de la flecha para que el extremo1 tenga un id menor que el extremo2.
    	*/
	public void orientarA(){
		if(extremo1.id > extremo2.id){
			VerticeD temp = extremo1;
			extremo1 = extremo2;
			extremo2 = temp;
		}
	}
}
