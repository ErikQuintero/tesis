import java.util.*;

//Esta clase modela una arista
public class Arista{

	//Atributos
	Vertice extremo1;
	Vertice extremo2;

	//
	public Arista(){}

	//Metodo constructor de la clase arista
	public Arista(Vertice v1, Vertice v2){
		this.extremo1 = v1;
		this.extremo2 = v2;
	}

	//Metodo que devuelve el primer extremo de una arista
	public Vertice getEX1(){
		return this.extremo1;
	}

	//Metodo que devuelve el segundo extremo de una arista
	public Vertice getEX2(){
		return this.extremo2;
	}

	//Metodo que verifica si dos aristas son iguales
	public boolean equals(Arista a){
		if(this.extremo1.equals(a.getEX1())&&this.extremo2.equals(a.getEX2())){
			return true;
		}else if(this.extremo1.equals(a.getEX2())&&this.extremo2.equals(a.getEX1())){
			return true;
		}else{
			return false;
		}
	}

	//Metodo que devuelve una representacion en cadena de nuestras aristas
	public String toString(){
		String arista = this.extremo1.getID() + this.extremo2.getID();
		return arista;
	}

	//Metodo que orienta una arista
	public void orientarA(){
		if(extremo1.id > extremo2.id){
			//System.out.println("hola");
			Vertice temp = extremo1;
			extremo1 = extremo2;
			extremo2 = temp;
		}
	}
}
