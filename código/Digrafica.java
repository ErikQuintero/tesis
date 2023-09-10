import java.util.*;

//Clase que modela una grafica
public class Digrafica implements Cloneable{

	//Atributos de la clase grafica
	ArrayList<VerticeD> vertices;
	ArrayList<Flecha> flechas;

	//Metodo constructor de una grafica
	public Digrafica(ArrayList<VerticeD> vertices, ArrayList<Flecha> flechas){
		this.vertices = vertices;
		this.flechas = flechas;
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

    //Metodo que nos ayuda a clonar una grafica
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

    //Metodo que elimina un vertice de la grafica
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

	//Metodo que elimina una flecha
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

    //
    public ArrayList<VerticeD> getNucleoAux(ArrayList<VerticeD> nucleo){
        if(this.vertices.size() == 0){
			return nucleo;
        }else{
			System.out.println("aa");
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

    //
    public ArrayList<VerticeD> getNucleo(){
        ArrayList<VerticeD> nucleo = new ArrayList<VerticeD>();
		Digrafica d2 = this.clonar();
        return d2.getNucleoAux(nucleo);
    }

    //
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

        /*for(VerticeD v5 : this.vertices){
            if(!v5.absorbid){
                System.out.println(v5);
                return false;
            }
        }*/
        if(cont >= this.vertices.size()){
            return true;
        }else{
            return false;
        }
    }

	//Metodo que nos devuelva una representacion en cadena de la grafica
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
