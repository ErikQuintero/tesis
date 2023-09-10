import java.util.*;

public class GraficaAl{

    //Metodo que genera una lista de vertices para una grafica
    public static ArrayList<Vertice> generaVertices(){
        ArrayList<Vertice> vertices = new ArrayList<Vertice>();
        int maxNum = (int)(Math.floor(((Math.random()*10)+1)));
        for(int i = 0; i < maxNum;i++){
            String aux =  "v" + (i+1);
            Vertice v = new Vertice(aux);
            v.id = i+1;
            vertices.add(v);
        }
        return vertices;
    }

    //Metodo que nos regresa un valor booleano aleatorio, nos servira en el metodo agregaAd
    public static boolean randomBool(){
        int random = (int)(Math.floor(Math.random()*10+1));
        if (random <= 3){
            return true;
        }else{
            return false;
       }
   }

    //Metodo que genera una lista de aristas dado una lista de vertices
    public static ArrayList<Arista> generaAristas(ArrayList<Vertice> vertices){
        ArrayList<Arista> aristas = new ArrayList<Arista>();
        for (Vertice x: vertices){
            for (Vertice y: vertices){
                boolean aux = randomBool();
                if(aux && (!x.getID().equals(y.getID()))){
                    Arista a1 = new Arista(x,y);
                    aristas.add(a1);
                }
            }
        }
        ArrayList<Arista> aristas2 = new ArrayList<Arista>();
		for(Arista aux : aristas){
			aristas2.add(0,aux);
		}
		GraficaAl.eliminaArista(aristas2);
		ArrayList<Arista> aristas3 = new ArrayList<Arista>();
		for(Arista aux : aristas2){
			aux.orientarA();
			aristas3.add(0,aux);
		}
        return aristas3;
    }

    //Metodo auxiliar que elimina una arista de una lista
    public static boolean verificaArista(ArrayList<Arista> aristas, Arista a1){
        int cont = 0;
        Arista aux = new Arista();
        for(Arista a2 : aristas){
            if(a2.equals(a1)){
                cont++;
            }
        }
        if(cont > 1){
            return true;
        }else{
            return false;
        }
    }

    //Metodo auxiliar para eliminar repetidos
    public static void eliminaArista(ArrayList<Arista> aristas){
        ArrayList<Arista> eliminadas = new ArrayList<Arista>();
        for(Arista a1 : aristas){
            if(verificaArista(aristas, a1)){
                eliminadas.add(a1);
            }
        }
        for(Arista a2 : eliminadas){
            if(verificaArista(aristas, a2)){
                aristas.remove(a2);
            }
        }
    }

    //Metodo que regresa una grafica aleatoria
    public static Grafica graficaAle(){
        ArrayList<Vertice> vertices = generaVertices();
        ArrayList<Arista> aristas = generaAristas(vertices);
        Grafica g = new Grafica(vertices, aristas);
        return g;
    }

    //Metodo que regresa una digrafica aleatoria
    public static Digrafica digraficaAle(){
        ArrayList<Vertice> vertices = generaVertices();
        ArrayList<Arista> aristas = generaAristas(vertices);
        Grafica g = new Grafica(vertices, aristas);
        ArrayList<VerticeD> verticesD = new ArrayList<VerticeD>();
        for(Vertice v1 : vertices){
            VerticeD v2 = new VerticeD(v1.identificador);
            verticesD.add(v2);
        }
        ArrayList<Flecha> flechas = new ArrayList<Flecha>();
        for(Arista a : aristas){
            VerticeD ex1 = new VerticeD(a.extremo1.identificador);
            VerticeD ex2 = new VerticeD(a.extremo2.identificador);
            Flecha f = new Flecha(ex1, ex2);
            flechas.add(f);
        }
        Digrafica d = new Digrafica(verticesD, flechas);
        return d;
    }
}
