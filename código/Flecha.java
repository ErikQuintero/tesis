import java.util.*;

//Esta clase modela una arista
public class Flecha{

	//Atributos
	VerticeD extremo1;
	VerticeD extremo2;

	//
	public Flecha(){}

	//Metodo constructor de la clase arista
	public Flecha(VerticeD v1, VerticeD v2){
		this.extremo1 = v1;
		this.extremo2 = v2;
	}

	//Metodo que devuelve el primer extremo de una arista
	public VerticeD getEX1(){
		return this.extremo1;
	}

	//Metodo que devuelve el segundo extremo de una arista
	public VerticeD getEX2(){
		return this.extremo2;
	}

	//Metodo que verifica si dos aristas son iguales
	public boolean equals(Arista a){
		if(this.extremo1.equals(a.getEX1())&&this.extremo2.equals(a.getEX2())){
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
			VerticeD temp = extremo1;
			extremo1 = extremo2;
			extremo2 = temp;
		}
	}
}
