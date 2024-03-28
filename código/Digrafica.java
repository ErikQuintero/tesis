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
      for(VerticeD v : this.vertices){
          String nombre = v.identificador;
          VerticeD v2 = new VerticeD(nombre);
          vertices2.add(v2);
      }
      ArrayList<Flecha> flechas2 = new ArrayList<Flecha>();
      for(Flecha a : this.flechas){
          VerticeD v1 = a.extremo1;
          VerticeD v2 = a.extremo2;
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
		* Método auxiliar utilizado para obtener el núcleo de la gráfica.
		* Este método se encarga de encontrar y eliminar los vértices que forman parte del núcleo de la gráfica.
		* Un vértice se considera parte del núcleo si no tiene flechas entrantes.
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
            return this.getNucleoAux(nucleo);
        }
    }

	/**
	    * Método que encuentra el núcleo de la gráfica.
	    * El núcleo de una gráfica es un conjunto de vértices que no tienen flechas entrantes y pueden ser alcanzados por otros vértices de la gráfica.
	    *
	    * @return Una lista de vértices que forman el núcleo de la gráfica.
    */
    public ArrayList<VerticeD> getNucleoAci(){
        ArrayList<VerticeD> nucleo = new ArrayList<VerticeD>();
		Digrafica d2 = this.clonar();
        return d2.getNucleoAciAux(nucleo);
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
