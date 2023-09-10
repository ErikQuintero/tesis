import java.util.*;

public class Vertice{

	//Atributos de los vertices de la grafica
	int id = 0;
	String identificador;
	ArrayList<Vertice> vecinos = new ArrayList<>(); //Una herramienta que nos servira mas tarde
	boolean esVisitado = false;

	//Constructor de la clase Vertice
	public Vertice(String id){
		this.identificador = id;
	}

	//Metodo que devuelve el gardo de un vertice
	public int getGrado(){
		return vecinos.size();
	}

	//Metodo que devuelve los vecinos de un vertice
	public ArrayList<Vertice> getVecinos() {
		return vecinos;
	}

	//Metodo que devuelve el identificador de un vertice
	public String getID(){
		return identificador;
	}

	//Metodo equals que verifica si dos vertices son iguales
	public boolean equals(Vertice v1){
		if(this.identificador.equals(v1.getID())){
			return true;
		}else{
			return false;
		}
	}

	//Metodo toString que devuelve una representación en cadena de un vertice
	public String toString(){
		return identificador;
	}

	//Metodo que modifica el estado de visitado de un vertices
	public void modificaVisitado(){
		if(esVisitado){
			this.esVisitado = false;
		}else{
			this.esVisitado = true;
		}
	}
}
