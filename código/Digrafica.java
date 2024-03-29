import java.util.*;

/**
	* Clase que modela una gráfica dirigida.
	* Una gráfica dirigida consiste en un conjunto de vértices (VerticeD) y un conjunto de flechas (Flecha) que conectan los vértices.
*/
public class Digrafica{

	//Atributos de la clase grafica
	ArrayList<VerticeD> vertices;
	ArrayList<Flecha> flechas;

	/**
	    * Constructor de una gráfica dirigida.
	    * Crea una gráfica con los vértices y flechas especificados.
	    *
	    * @param vertices Los vértices que forman parte de la gráfica.
	    * @param flechas Las flechas que conectan los vértices de la gráfica.
    */
	public Digrafica(ArrayList<VerticeD> vertices, ArrayList<Flecha> flechas){
		this.vertices = vertices;
		this.flechas = flechas;

		// Agrega las conexiones entre los vértices y las flechas a los vecinos correspondientes
		for(VerticeD v : this.vertices){
			for(Flecha a : this.flechas){
				if(v.equals(a.getEX1())){
					v.exVecinos.add(a.getEX2());
				}else if(a.getEX2().equals(v)){
					v.inVecinos.add(a.getEX1());
				}
			}
		}
	}

	/**
	    * Método que clona la gráfica actual.
	    * Crea una copia exacta de la gráfica, incluyendo los vértices y flechas.
	    *
	    * @return Una nueva instancia de Digrafica que es una copia de la gráfica actual.
    */
    public Digrafica clonar(){
		ArrayList<VerticeD> vertices2 = new ArrayList<VerticeD>();
      	ArrayList<Flecha> flechas2 = new ArrayList<Flecha>();

	    // Clonar los vértices y agregarlos a vertices2
	    for (VerticeD v : this.vertices) {
	    	VerticeD v2 = new VerticeD(v.identificador);
			vertices2.add(v2);
		}

	    // Clonar las flechas y agregarlas a flechas2
	    for (Flecha a : this.flechas) {
	        // Encontrar los vértices correspondientes en vertices2
	        VerticeD v1 = null;
	        VerticeD v2 = null;
	        for (VerticeD v : vertices2) {
	            if (v.identificador.equals(a.extremo1.identificador)) {
	                v1 = v;
	            }
	            if (v.identificador.equals(a.extremo2.identificador)) {
	                v2 = v;
	            }
	        }

	    	Flecha a2 = new Flecha(v1, v2);
	    	flechas2.add(a2);
	    }
      return new Digrafica(vertices2, flechas2);
    }

	/**
	    * Método que elimina un vértice de la gráfica.
	    * Elimina el vértice especificado y todas las flechas que lo involucran de la gráfica.
	    *
	    * @param v El vértice que se va a eliminar de la gráfica.
    */
	public void eliminaV(VerticeD v){
		this.vertices.remove(v);
		for(VerticeD aux : this.vertices){
			aux.inVecinos.remove(v);
            aux.exVecinos.remove(v);
		}
		ArrayList<Flecha> eliminadas = new ArrayList<Flecha>();
		for(Flecha a : this.flechas){
			if(a.getEX1().equals(v) || a.getEX2().equals(v)){
				eliminadas.add(a);
			}
		}
		for(Flecha a2 : eliminadas){
			this.flechas.remove(a2);
		}
	}

	/**
	    * Método que elimina una flecha de la gráfica.
	    * Elimina la flecha especificada y actualiza los vecinos de los vértices involucrados.
	    *
	    * @param a La flecha que se va a eliminar de la gráfica.
    */
	public void eliminaA(Flecha a){
		this.flechas.remove(a);
		for(VerticeD v : this.vertices){
			if(v.equals(a.getEX1())){
				v.exVecinos.remove(a.getEX2());
			}else if(v.equals(a.getEX2())){
				v.inVecinos.remove(a.getEX1());
			}
		}
	}

	/**
		* Método auxiliar utilizado para obtener el núcleo de una digráfica aciclica.
		* Este método se encarga de encontrar y eliminar los vértices que forman parte del núcleo de la gráfica.
		*
		* @param nucleo La lista que almacenará los vértices que forman el núcleo de la gráfica.
		* @return La lista de vértices que forman el núcleo de la gráfica.
	*/
    public ArrayList<VerticeD> getNucleoAciAux(ArrayList<VerticeD> nucleo){
        if(this.vertices.size() == 0){
			return nucleo;
        }else{
            ArrayList<VerticeD> ex0 = new ArrayList<VerticeD>();
            ArrayList<VerticeD> eliminados = new ArrayList<VerticeD>();
            for(VerticeD aux : this.vertices){
                if(aux.getExGrado() == 0){
                    ex0.add(aux);
                    nucleo.add(aux);
                }
            }
            if(ex0.size() == 0){
                return nucleo;
            }
            for(VerticeD aux2 : ex0){
                eliminados.add(aux2);
                for(VerticeD aux3 : aux2.inVecinos){
                    eliminados.add(aux3);
                }
            }
            for(VerticeD aux4 : eliminados){
                this.eliminaV(aux4);
            }
            return this.getNucleoAciAux(nucleo);
        }
    }

	/**
	    * Método que encuentra el núcleo de una digráfica aciclica.
	    * El núcleo de una gráfica es un conjunto de vértices absorbente y dominante
	    *
	    * @return Una lista de vértices que forman el núcleo de la gráfica.
    */
    public ArrayList<VerticeD> getNucleoAci(){
        ArrayList<VerticeD> nucleo = new ArrayList<VerticeD>();
		Digrafica d2 = this.clonar();
        return d2.getNucleoAciAux(nucleo);
    }

	/**
		* Método auxiliar utilizado para obtener el núcleo de una digráfica simetrica.
		* Este método se encarga de encontrar y eliminar los vértices que forman parte del núcleo de la gráfica.
		*
		* @param nucleo La lista que almacenará los vértices que forman el núcleo de la gráfica.
		* @return La lista de vértices que forman el núcleo de la gráfica.
	*/
	public ArrayList<VerticeD> getNucleoSimeAux(ArrayList<VerticeD> nucleo){
		if (this.vertices.size() == 0) {
        	return nucleo;
    	} else {
        	VerticeD vi = this.vertices.get(0);
        	if (!this.veificaVecinoLista(nucleo, vi)) {
            	nucleo.add(vi); // Agregar vi nuevamente si no es vecino
        	}
			this.vertices.remove(vi);
        	return this.getNucleoSimeAux(nucleo);
    	}
	}

	/**
		* Obtiene el núcleo de una digráfica simétrica.
		* El núcleo de una digráfica simétrica es un conjunto de vértices que no tienen vecinos en común.
		*
		* @return Una lista de vértices que forman el núcleo de la digráfica simétrica.
 	*/
	public ArrayList<VerticeD> getNucleoSime(){
		ArrayList<VerticeD> nucleo = new ArrayList<VerticeD>();
		Digrafica d2 = this.clonar();
		VerticeD v1 = d2.vertices.get(0);
		d2.vertices.remove(v1);
		nucleo.add(v1);
        return d2.getNucleoSimeAux(nucleo);
	}

	/**
		* Obtiene el núcleo de una digráfica bipartita fuertemente conexa
		*
		* @param nucleo El núcleo actual obtenido hasta el momento.
		* @return El núcleo de la digráfica.
	*/
	public ArrayList<VerticeD> getNucleoBiFCAux(ArrayList<VerticeD> nucleo){
		if (this.vertices.size() == 0) {
        	return nucleo;
    	} else {
        	VerticeD vi = this.vertices.get(0);
			nucleo.add(vi);
			for(VerticeD vAux : vi.inVecinos){
				this.eliminaV(vAux);
			}
			for(VerticeD vAux2 : vi.exVecinos){
				this.eliminaV(vAux2);
			}
			for(VerticeD vAux3 : nucleo){
				this.eliminaV(vAux3);
			}
			return this.getNucleoBiFCAux(nucleo);
    	}
	}

	/**
		* Obtiene el núcleo de una digráfica bipartita fuertemente conexa.
		*
		* @return El núcleo de la digráfica.
	*/
	public ArrayList<VerticeD> getNucleoBiFC(){
		ArrayList<VerticeD> nucleo = new ArrayList<VerticeD>();
		Digrafica d2 = this.clonar();
        return d2.getNucleoSimeAux(nucleo);
	}

	/**
		* Verifica si un vértice es vecino de algún vértice en una lista de vértices.
		*
		* @param verticesAux La lista de vértices en la que se buscarán los vecinos.
		* @param auxiliar El vértice cuya vecindad se verificará.
		* @return true si el vértice es vecino de algún vértice en la lista, false en caso contrario.
	*/
	public boolean veificaVecinoLista(ArrayList<VerticeD> verticesAux, VerticeD auxiliar){
		boolean vecino = false;

		// Verifica si el vértice es vecino de algún vértice en la lista a través de sus vecinos de entrada
		for(VerticeD x : verticesAux){
			if(x.exVecinos.contains(auxiliar)){
				vecino = true;
			}
		}

		// Verifica si el vértice es vecino de algún vértice en la lista a través de sus vecinos de salida
		for(VerticeD y : verticesAux){
			if(y.inVecinos.contains(auxiliar)){
				vecino = true;
			}
		}

		return vecino;
	}

	/**
	    * Método que verifica si un conjunto de vértices es absorbente en la gráfica.
	    * Un conjunto de vértices es absorbente si cada vértice en el conjunto y sus flechas pueden alcanzar todos los vértices de la gráfica.
	    *
	    * @param vertex El conjunto de vértices que se va a verificar.
	    * @return true si el conjunto de vértices es absorbente, false en caso contrario.
    */
    public boolean esAbsorbente(ArrayList<VerticeD> vertex){
        int cont = 0;
        for(VerticeD v1 : vertex){
            for(VerticeD v2 : this.vertices){
                if(v1.equals(v2)){
                    cont++;
                }
            }
            for(Flecha f : this.flechas){
                if(f.extremo2.equals(v1)){
                    cont++;
                }
            }
        }

        if(cont >= this.vertices.size()){
            return true;
        }else{
            return false;
        }
    }

	/**
	    * Método que devuelve una representación en cadena de la gráfica.
	    * La representación en cadena incluye los conjuntos de vértices y flechas.
	    *
	    * @return Una cadena que representa la gráfica.
    */
	public String toString(){
		//Crea una representacion en cadena del conjunto de vertices
		String vert = "{";
		int cont = 0;
		for(VerticeD v : this.vertices){
			if(cont == 0){
				vert = vert + v.toString();
			}else{
				vert = vert + ", " + v.toString();
			}
			cont++;
		}
		vert = vert + "}";
		cont = 0;

		//Crea una representacion en cadena del conjunto de las aristas
		String flech = "{";
		for(Flecha f : this.flechas){
			if(cont == 0){
				flech = flech + f.toString();
			}else{
				flech = flech + ", " + f.toString();
			}
			cont++;
		}
		flech = flech + "}";
		return "El conjunto de vertices es: " +  vert + "\n" + "El conjunto de flechas es: " + flech;
	}

	/**
		* Realiza un recorrido en profundidad (DFS) en la digrafica comenzando desde el vértice especificado.
		* Devuelve una lista de vértices en orden post-orden, es decir, en el orden en que son visitados por el algoritmo DFS.
		*
		* @param v El vértice desde el cual iniciar el recorrido DFS.
		* @return Una lista de vértices en orden post-orden.
	*/
	public ArrayList<VerticeD> dfs(VerticeD v) {
	    ArrayList<VerticeD> postOrden = new ArrayList<VerticeD>();
	    if (!v.visitado) {
	        dfsVisitados(v, postOrden);
	    }
		for (VerticeD aux : postOrden) {
 		   aux.visitado = false;
 	   	}
	    return postOrden;
	}

	/**
		* Realiza un recorrido en profundidad (DFS) en la digrafica comenzando desde el vértice especificado.
		* Marca cada vértice visitado durante el recorrido y agrega los vértices al orden post-orden.
		*
		* @param v El vértice actual en el recorrido DFS.
		* @param postOrden Una lista que almacena los vértices en orden post-orden.
	*/
	public void dfsVisitados(VerticeD v, ArrayList<VerticeD> postOrden) {
	    v.visitado = true;
	    for (VerticeD vecino : v.exVecinos) {
	        if (!vecino.visitado) {
	            dfsVisitados(vecino, postOrden);
	        }
	    }
	    postOrden.add(v); // Agregar el vértice al stack después de visitar todos sus vecinos
	}

	/**
		* Realiza un algoritmo de Kosaraju para encontrar las componentes fuertemente conexas de la grafica dirigida.
		* Este método es auxiliar y se utiliza para encontrar las componentes fuertemente conexas recursivamente.
		*
		* @param composFC La lista de componentes fuertemente conexas.
		* @return Una lista de componentes fuertemente conexas de la digrafica.
	*/
	public ArrayList<ArrayList<VerticeD>> kosarajuAux(ArrayList<ArrayList<VerticeD>> composFC) {
	   	//Encontrar el primer vértice no visitado
		VerticeD raiz = this.obtenerNovisitado();

		//caso base del método
		if(raiz == null){
			for (VerticeD aux2 : this.vertices) {
	 		   aux2.visitado = false;
	 	   	}
			return composFC;
		}

		// Realizar un recorrido DFS en la digrafica original para calcular el ordenamiento post-orden
		ArrayList<VerticeD> postOrden = this.dfs(raiz);

		//creamos arraylist auxiliar para guardar los vertices de la componente
		ArrayList<VerticeD> componente = new ArrayList<VerticeD>();

		//verificamos que vertices pueden llegar a la raiz
		for (VerticeD vi : postOrden) {
			ArrayList<VerticeD> llego = this.dfs(vi);
			if(llego.contains(raiz)){
				componente.add(vi);
			}
	   }

	   //marcamos los vertices de la componente como true, para que no se metan a otra componente
	   for (VerticeD aux : componente) {
		  aux.visitado = true;
	   }
	   composFC.add(componente);

	   return this.kosarajuAux(composFC);
	}

	/**
		* Implementa el algoritmo de Kosaraju para encontrar las componentes fuertemente conexas de la digrafica.
		* Este método inicia el proceso de búsqueda de componentes fuertemente conexas llamando al método auxiliar kosarajuAux.
		*
		* @return Una lista de listas que contiene las componentes fuertemente conexas de la digrafica.
	*/
	public ArrayList<ArrayList<VerticeD>> kosaraju(){
		ArrayList<ArrayList<VerticeD>> compos = new ArrayList<ArrayList<VerticeD>>();
		return this.kosarajuAux(compos);
	}

	/**
		* Obtiene un vértice no visitado en en la digrafica.
		*
		* @return El primer vértice no visitado encontrado en la digrafica, o null si todos los vértices han sido visitados.
	*/
	public VerticeD obtenerNovisitado(){
		VerticeD x = null;
		for(VerticeD aux : this.vertices){
			if(!aux.visitado){
				x = aux;
			}
		}
		return x;
	}

	/**
		* Obtiene una componente terminal de la digráfica utilizando el algoritmo de Kosaraju.
		* Una componente terminal es aquella que no tiene flechas salientes.
		*
		* @return Una lista de vértices que representa una componente terminal de la digráfica.
	*/
	public ArrayList<VerticeD> obtenerCompoTerminal(){
		ArrayList<ArrayList<VerticeD>> compos = this.kosaraju();
		ArrayList<VerticeD> terminal = new ArrayList<VerticeD>();
		for(ArrayList<VerticeD> compoAux : compos){
			if(this.verificarCompoTerminal(compoAux)){
				terminal = compoAux;
				break;
			}
		}
		return terminal;
	}

	/**
		* Verifica si una componente de la digráfica es terminal, es decir, si no tiene flechas salientes fuera de la componente.
		*
		* @param compo La lista de vértices que representa una componente de la digráfica.
		* @return true si la componente es terminal, false en caso contrario.
	*/
	public boolean verificarCompoTerminal(ArrayList<VerticeD> compo){
		boolean terminal = true;
		for(VerticeD x : compo){
			for(VerticeD ex : x.exVecinos){
				if(!compo.contains(ex) && x.exVecinos.size() != 0){
					terminal = false;
					break;
				}
			}
		}
		return terminal;
	}

}
