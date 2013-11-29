package co.edu.uniminuto.mundo;

import java.util.ArrayList;

public class Grafica {

	private Punto[] puntos;
	private int anchoPant;
	private int altoPant;

	public int getAnchoPant() {
		return anchoPant;
	}

	public void setAnchoPant(int anchoPant) {
		this.anchoPant = anchoPant;
	}

	public int getAltoPant() {
		return altoPant;
	}

	public void setAltoPant(int altoPant) {
		this.altoPant = altoPant;
	}

	public Punto[] getPuntos() {
		return puntos;
	}

	public void setPuntos(Punto[] puntos) {
		this.puntos = puntos;
	}

	public Grafica(int anchoPant, int altoPant) {
		this.anchoPant = anchoPant;
		this.altoPant = altoPant;
	}

	/***
	 * Permite encontrarlos puntos para pintar todos los ejes donde cortan las graficas en x
	 * @param escala_x Escala de proporcion en X
	 * @param escala_yPos Posicion del punto Y en la parte superior de la pantalla
	 * @param escala_yNeg Posicion del punto Y en la parte inferior de la pantalla
	 * @param difPuntos la cantidad de puntos a cada extremo del eje Y
	 * @return Los puntos para la colocacion de las lineas
	 */
	public ArrayList<Punto> paintEjes(double escala_x, int escala_yPos, int escala_yNeg, int difPuntos) {

		ArrayList<Punto> puntos = new ArrayList<Punto>();

		double valInicial = -360;
		double inc = 0;
		
		for(int i = 0; i < 9; i++){
			inc = valInicial * escala_x;
			puntos.add(new Punto((int) coord_x(inc), escala_yPos));
			puntos.add(new Punto((int) coord_x(inc), escala_yNeg));
			valInicial += 90;
		}
		
		return puntos;

	}

	/***
	 * Permite Encontrar los puntos para realizar la grafica de la funcion seno
	 * @param xIni Angulo desde el cual se inicia a graficar
	 * @param xFin Angulo en el cual se finaliza el proceso de graficacion
	 * @param hipotenusa la magnitud de la hipotenusa
	 * @param escala_y La escala de tamano para el eje Y
	 * @param escala_x La escala de tamano para el eje X
	 * @return Puntos de la grafica de la funcion seno
	 */
	public Punto[] paintSeno(int xIni, int xFin, int hipotenusa,
			double escala_y, double escala_x) {

		int difPuntos = xFin - xIni;
		puntos = new Punto[difPuntos];
		double y1 = 0;
		int contador = 0;
		double seno = xIni;
		double escala = ((difPuntos / 2) * escala_x);
		double incremento = ((difPuntos / 2) - escala);

		for (int i = xIni; i < xFin; i++) {

			double co = catetoOpuesto(hipotenusa, i);
			double x1 = seno;
			y1 = co * escala_y;
			puntos[contador++] = new Punto((int) coord_x(x1, incremento),
					(int) coord_y(y1));
			seno += escala_x;
		}

		return puntos;
	}

	/***
	 * Permite Encontrar los puntos para realizar la grafica de la funcion coseno
	 * @param xIni Angulo desde el cual se inicia a graficar
	 * @param xFin Angulo en el cual se finaliza el proceso de graficacion
	 * @param hipotenusa la magnitud de la hipotenusa
	 * @param escala_y La escala de tamano para el eje Y
	 * @param escala_x La escala de tamano para el eje X
	 * @return Puntos de la grafica de la funcion coseno
	 */
	public Punto[] paintCoseno(int xIni, int xFin, int hipotenusa,
			double escala_y, double escala_x) {

		int difPuntos = xFin - xIni;
		puntos = new Punto[difPuntos];
		double y1 = 0;
		int contador = 0;
		double coseno = xIni;
		double escala = ((difPuntos / 2) * escala_x);
		double incremento = ((difPuntos / 2) - escala);

		for (int i = xIni; i < xFin; i++) {

			double ca = catetoAdyacente(hipotenusa, i);
			double x1 = coseno;
			y1 = ca * escala_y;
			puntos[contador++] = new Punto((int) coord_x(x1, incremento),
					(int) coord_y(y1));
			coseno += escala_x;
		}

		return puntos;
	}

	/***
	 * Permite Encontrar los puntos para realizar la grafica de la funcion tangente
	 * @param xIni Angulo desde el cual se inicia a graficar
	 * @param xFin Angulo en el cual se finaliza el proceso de graficacion
	 * @param hipotenusa la magnitud de la hipotenusa
	 * @param escala_y La escala de tamano para el eje Y
	 * @param escala_x La escala de tamano para el eje X
	 * @return Puntos de la grafica de la funcion tangente
	 */
	public Punto[] paintTangente(int xIni, int xFin, int hipotenusa,
			double escala_y, double escala_x) {

		int difPuntos = xFin - xIni;
		puntos = new Punto[difPuntos];
		double y1 = 0;
		int contador = 0;
		double tangente = xIni;
		double escala = ((difPuntos / 2) * escala_x);
		double incremento = ((difPuntos / 2) - escala);

		for (int i = xIni; i < xFin; i++) {

			double ca = catetoAdyacente(hipotenusa, i);
			double co = catetoOpuesto(hipotenusa, i);
			double x1 = tangente;
			y1 = (co / ca) * escala_y;
			puntos[contador++] = new Punto((int) coord_x(x1, incremento),
					(int) coord_y(y1));
			tangente += escala_x;
		}

		return puntos;
	}

	/***
	 * Permite Encontrar los puntos para realizar la grafica de la funcion cotangente
	 * @param xIni Angulo desde el cual se inicia a graficar
	 * @param xFin Angulo en el cual se finaliza el proceso de graficacion
	 * @param hipotenusa la magnitud de la hipotenusa
	 * @param escala_y La escala de tamano para el eje Y
	 * @param escala_x La escala de tamano para el eje X
	 * @return Puntos de la grafica de la funcion cotangente
	 */
	public Punto[] paintCotangente(int xIni, int xFin, int hipotenusa,
			double escala_y, double escala_x) {

		int difPuntos = xFin - xIni;
		puntos = new Punto[difPuntos];
		double y1 = 0;
		int contador = 0;
		double cotangente = xIni;
		double escala = ((difPuntos / 2) * escala_x);
		double incremento = ((difPuntos / 2) - escala);

		for (int i = xIni; i < xFin; i++) {

			double ca = catetoAdyacente(hipotenusa, i);
			double co = catetoOpuesto(hipotenusa, i);
			double x1 = cotangente;
			y1 = (ca / co) * escala_y;
			puntos[contador++] = new Punto((int) coord_x(x1, incremento),
					(int) coord_y(y1));
			cotangente += escala_x;
		}

		return puntos;
	}

	/***
	 * Permite Encontrar los puntos para realizar la grafica de la funcion secante
	 * @param xIni Angulo desde el cual se inicia a graficar
	 * @param xFin Angulo en el cual se finaliza el proceso de graficacion
	 * @param hipotenusa la magnitud de la hipotenusa
	 * @param escala_y La escala de tamano para el eje Y
	 * @param escala_x La escala de tamano para el eje X
	 * @return Puntos de la grafica de la funcion secante
	 */
	public Punto[] paintSecante(int xIni, int xFin, int hipotenusa,
			double escala_y, double escala_x) {

		int difPuntos = xFin - xIni;
		puntos = new Punto[difPuntos];
		double y1 = 0;
		int contador = 0;
		double secante = xIni;
		double escala = ((difPuntos / 2) * escala_x);
		double incremento = ((difPuntos / 2) - escala);

		for (int i = xIni; i < xFin; i++) {

			double ca = catetoAdyacente(hipotenusa, i);
			double x1 = secante;
			y1 = (1 / ca) * escala_y;
			puntos[contador++] = new Punto((int) coord_x(x1, incremento),
					(int) coord_y(y1));
			secante += escala_x;
		}

		return puntos;
	}

	/***
	 * Permite Encontrar los puntos para realizar la grafica de la funcion cosecante
	 * @param xIni Angulo desde el cual se inicia a graficar
	 * @param xFin Angulo en el cual se finaliza el proceso de graficacion
	 * @param hipotenusa la magnitud de la hipotenusa
	 * @param escala_y La escala de tamano para el eje Y
	 * @param escala_x La escala de tamano para el eje X
	 * @return Puntos de la grafica de la funcion cosecante
	 */
	public Punto[] paintCosecante(int xIni, int xFin, int hipotenusa,
			double escala_y, double escala_x) {

		int difPuntos = xFin - xIni;
		puntos = new Punto[difPuntos];
		double y1 = 0;
		int contador = 0;
		double cosecante = xIni;
		double escala = ((difPuntos / 2) * escala_x);
		double incremento = ((difPuntos / 2) - escala);

		for (int i = xIni; i < xFin; i++) {

			double co = catetoOpuesto(hipotenusa, i);
			double x1 = cosecante;
			y1 = (1 / co) * escala_y;
			puntos[contador++] = new Punto((int) coord_x(x1, incremento),
					(int) coord_y(y1));
			cosecante += escala_x;
		}

		return puntos;
	}

	/***
	 * 
	 * @param xIni Angulo desde el cual se inicia a graficar
	 * @param xFin Angulo en el cual se finaliza el proceso de graficacion
	 * @param hipotenusa la magnitud de la hipotenusa
	 * @param escala_y La escala de tamano para el eje Y
	 * @param escala_x La escala de tamano para el eje X
	 * @return
	 */
	public Punto[] paintCirculo(int xIni, int xFin, int xFin2, int radio) {

		int difPoint = xFin - xIni;
		puntos = new Punto[difPoint];
		double y1 = 0;
		int contador = 0;

		for (int i = xIni; i < xFin; i++) {

			double x1 = xFin2 - 110;
			double co = catetoOpuesto(radio, i);
			double ca = catetoAdyacente(radio, i);

			y1 = co;
			x1 = ca;

			puntos[contador++] = new Punto((int) coord_x_circulo(x1),
					(int) coord_y(y1));
			xFin2--;

		}

		return puntos;
	}

	public double catetoOpuesto(int hipotenusa, double angulo) {
		return (double) (hipotenusa * Math.sin(Math.toRadians(angulo)));
	}

	public double catetoAdyacente(int hipotenusa, double angulo) {
		return (double) (hipotenusa * Math.cos(Math.toRadians(angulo)));
	}

	private double coord_x(double x, double incremento) {
		return (((this.getAnchoPant() / 2) + x) + incremento);
	}

	private double coord_x(double x) {
		return (((this.getAnchoPant() / 2) + x));
	}
	
	private double coord_y(double y) {
		return (this.getAltoPant() / 2) - y;
	}

	private double coord_x_circulo(double x) {
		double real_x = x + 110;
		return real_x;
	}

}
