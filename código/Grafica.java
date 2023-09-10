import java.util.*;

//Clase que modela una grafica
public class Grafica{

	//Atributos de la clase grafica
	ArrayList<Vertice> vertices;
	ArrayList<Arista> aristas;

	//Metodo constructor de una grafica
	public Grafica(ArrayList<Vertice> vertices, ArrayList<Arista> aristas){
		this.vertices = vertices;
		this.aristas = aristas;
		for(Vertice v : this.vertices){
			for(Arista a : this.aristas){
				if(a.getEX1().equals(v)){
					v.vecinos.add(a.getEX2());
				}else if(a.getEX2().equals(v)){
					v.vecinos.add(a.getEX1());
				}
			}
		}
	}

	//Metodo que nos devuelva una representacion en cadena de la grafica
	public String toString(){
		//Crea una representacion en cadena del conjunto de vertices
		String vert = "{";
		for(Vertice v : this.vertices){
			vert = vert + ", " + v.toString();
		}
		vert = vert + "}";

		//Crea una representacion en cadena del conjunto de las aristas
		String arist = "{";
		for(Arista a : this.aristas){
			arist = arist + ", " + a.toString();
		}
		arist = arist + "}";

		return "El conjunto de vertices es: " +  vert + "\n" + "El conjunto de aristas es: " + arist;
	}

	//Metodo que elimina un vertice de la grafica
	public void eliminaV(Vertice v){
		this.vertices.remove(v);
		for(Vertice aux : this.vertices){
			aux.vecinos.remove(v);
		}
		ArrayList<Arista> eliminadas = new ArrayList<>();
		for(Arista a : this.aristas){
			if(a.getEX1().equals(v) || a.getEX2().equals(v)){
				eliminadas.add(a);
			}
		}
		for(Arista a2 : eliminadas){
			this.aristas.remove(a2);
		}
	}

	//Metodo que elimina una arista
	public void eliminaA(Arista a){
		this.aristas.remove(a);
		for(Vertice v : this.vertices){
			if(v.equals(a.getEX1())){
				v.vecinos.remove(a.getEX2());
			}else if(v.equals(a.getEX2())){
				v.vecinos.remove(a.getEX1());
			}
		}
	}

	//Metodo que ordena la lista en descendente
	public static ArrayList<Integer> ordena (ArrayList<Integer> sucesion){
		Collections.sort(sucesion);
		ArrayList<Integer> auxS = new ArrayList<Integer>();
		for(int y : sucesion){
			auxS.add(0,y);
		}
		return auxS; // auxS es la reversa de sucesion ya ordenado
	}

	//Metodo que hace el primer paso de havel hakimi
	public static ArrayList<Integer> havelHakimiAux(ArrayList<Integer> sucesion){
		sucesion = ordena(sucesion);
		Integer aux = sucesion.get(0);
		sucesion.remove(aux);
		int cont = 0;
		ArrayList<Integer> aux2 = new ArrayList<Integer>();
		for(int x : sucesion){
			if(cont < aux){
				aux2.add(x-1);
			}else{
				aux2.add(x);
			}
			cont++;
		}
		return aux2;
	}

	//Metodo havel hakimi
	public static ArrayList<ArrayList<Integer>> havelHakimi(ArrayList<Integer> sucesion, ArrayList<ArrayList<Integer>> sucesiones){
		boolean continua = false;
		for(int x : sucesion){
			if(x != 0 && x > 0){
				continua = true;
			}
		}
		if(continua){
			ArrayList<Integer> sucesionAux = havelHakimiAux(sucesion);
			sucesiones.add(sucesionAux);
			havelHakimi(sucesionAux, sucesiones);
		}else{
			return sucesiones;
		}
		return sucesiones;
	}

	//metodo auxiliar para el metodo es conexa
	public LinkedList<Vertice> esConexaAux(Vertice aux, LinkedList<Vertice> pila){
		for(Vertice v1 : aux.vecinos){
			if(!v1.esVisitado){
				v1.modificaVisitado();
				pila.add(v1);
			}
		}
		return pila;
	}

	//Metodo que verifica si una grafica es Conexa
	public void esConexa(){
		Vertice aux = vertices.get(0);
		LinkedList<Vertice> pila = new LinkedList<Vertice>();
		aux.modificaVisitado();
		pila = esConexaAux(aux, pila);
		if(pila.size() != 0){
			Vertice aux2 = pila.pop();
			pila = esConexaAux(aux2, pila);
		}
	}

	//Metodo que regresa si una grafica es Conexa
	public boolean esConexaF(){
		this.esConexa();
		for(Vertice v1 : this.vertices){
			if(!v1.esVisitado){
				return false;
			}
		}
		return true;
	}

	//Metodo que imprime una grafica como lista de adyacencias
	public String imprime(){
		String vert = "";
		for(Vertice v : this.vertices){
			vert = vert + v.toString() + " " + v.vecinos.toString() + " " + v.esVisitado + "\n";
		}
		return vert;
	}

}
