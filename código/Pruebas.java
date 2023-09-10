import java.util.*;

public class Pruebas{

	public static void main (String args []){
		/*ArrayList<Vertice> vertices = GraficaAl.generaVertices();
		ArrayList<Arista> aristas = GraficaAl.generaAristas(vertices);
		System.out.println(vertices);
		System.out.println("__________________________");
		System.out.println(aristas);
		System.out.println("___________________________");
		ArrayList<Arista> aristas2 = new ArrayList<Arista>();
		for(Arista aux : aristas){
			aristas2.add(0,aux);
		}
		GraficaAl.eliminaArista(aristas2);
		System.out.println(aristas2);
		System.out.println("___________________________");
		ArrayList<Arista> aristas3 = new ArrayList<Arista>();
		for(Arista aux : aristas2){
			aux.orientarA();
			aristas3.add(0,aux);
		}
		System.out.println(aristas3);*/
		/*Digrafica d = GraficaAl.digraficaAle();
		System.out.println(d);
		Digrafica d2 = d.clonar();
		ArrayList<VerticeD> nucleo = d.getNucleo();
		System.out.println(nucleo);
		boolean si = d2.esAbsorbente(nucleo);
		System.out.println(si);*/
		VerticeD a = new VerticeD("a");
		VerticeD b = new VerticeD("b");
		VerticeD c = new VerticeD("c");
		VerticeD d = new VerticeD("d");
		VerticeD e = new VerticeD("e");
		VerticeD f = new VerticeD("f");
		VerticeD g = new VerticeD("g");
		VerticeD h = new VerticeD("h");
		VerticeD i = new VerticeD("i");
		VerticeD j = new VerticeD("j");
		Flecha f1 = new Flecha(b,a);
		Flecha f2 = new Flecha(b,c);
		Flecha f3 = new Flecha(d,c);
		Flecha f4 = new Flecha(d,e);
		Flecha f5 = new Flecha(c,j);
		Flecha f6 = new Flecha(e,h);
		Flecha f7 = new Flecha(j,i);
		Flecha f8 = new Flecha(h,g);
		Flecha f9 = new Flecha(g,f);
		Flecha f10 = new Flecha(i,h);
		ArrayList<VerticeD> vertex = new ArrayList<VerticeD>();
		vertex.add(a);
		vertex.add(b);
		vertex.add(c);
		vertex.add(d);
		vertex.add(e);
		vertex.add(f);
		vertex.add(g);
		vertex.add(h);
		vertex.add(i);
		vertex.add(j);
		ArrayList<Flecha> fle = new ArrayList<Flecha>();
		fle.add(f1);
		fle.add(f2);
		fle.add(f3);
		fle.add(f4);
		fle.add(f5);
		fle.add(f6);
		fle.add(f7);
		fle.add(f8);
		fle.add(f9);
		fle.add(f10);

		Digrafica di = new Digrafica(vertex,fle);
		ArrayList<VerticeD> nucleo = di.getNucleo();
		System.out.println(di);
		System.out.println(nucleo);
		boolean si = di.esAbsorbente(nucleo);
		System.out.println(si);

	}

}
