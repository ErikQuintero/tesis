import java.util.*;

/**
	* Clase que modela una gráfica dirigida.
	* Una gráfica dirigida consiste en un conjunto de vértices (VerticeD) y un conjunto de flechas (Flecha) que conectan los vértices.
*/
public class Digrafica implements Cloneable{

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

}
