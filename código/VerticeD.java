import java.util.*;

public class VerticeD{

	//Atributos de los vertices de la grafica
	int id = 0;
	String identificador;
	ArrayList<VerticeD> inVecinos = new ArrayList<VerticeD>(); //Una herramienta que nos servira mas tarde
    ArrayList<VerticeD> exVecinos = new ArrayList<VerticeD>();
	boolean absorbido = false;

	//Constructor de la clase Vertice
	public VerticeD(String id){
		this.identificador = id;
	}

	//Metodo que devuelve el gardo de un vertice
	public int getInGrado(){
		return inVecinos.size();
	}

    //Metodo que devuelve el gardo de un vertice
	public int getExGrado(){
		return exVecinos.size();
	}

	//Metodo que devuelve los vecinos de un vertice
	public ArrayList<VerticeD> getInVecinos() {
		return inVecinos;
	}

    //Metodo que devuelve los vecinos de un vertice
	public ArrayList<VerticeD> getExVecinos() {
		return exVecinos;
	}

	//Metodo que devuelve el identificador de un vertice
	public String getID(){
		return identificador;
	}

	//Metodo equals que verifica si dos vertices son iguales
	public boolean equals(VerticeD v1){
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

}
