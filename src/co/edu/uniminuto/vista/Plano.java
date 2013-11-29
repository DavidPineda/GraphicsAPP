package co.edu.uniminuto.vista;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import co.edu.uniminuto.clases.Botones;
import co.edu.uniminuto.clases.Grafico;
import co.edu.uniminuto.clases.PlanoThread;
import co.edu.uniminuto.mundo.Grafica;
import co.edu.uniminuto.mundo.Punto;
import co.uniminuto.R;

public class Plano extends SurfaceView implements SurfaceHolder.Callback {

	private PlanoThread paintThread;
	private int anchoPantalla, altoPantalla;
	private Grafica grafica;
	private Paint pincel;
	private boolean[] valores;
	private int anguloIni, anguloFin, hipotenusa;
	private double escala_y, escala_x;
	private Grafico detener, iniciar, grados, radianes;
	private Bitmap degradado;
	DisplayMetrics metrics;
	int screenHeight = 0, screenWidth = 0;

	public Grafico getGrados() {
		return grados;
	}

	public void setGrados(Grafico grados) {
		this.grados = grados;
	}

	public Grafico getRadianes() {
		return radianes;
	}

	public void setRadianes(Grafico radianes) {
		this.radianes = radianes;
	}

	public Grafico getDetener() {
		return detener;
	}

	public void setDetener(Grafico detener) {
		this.detener = detener;
	}

	public Grafico getIniciar() {
		return iniciar;
	}

	public void setIniciar(Grafico iniciar) {
		this.iniciar = iniciar;
	}

	public int getAnchoPantalla() {
		return anchoPantalla;
	}

	public void setAnchoPantalla(int anchoPantalla) {
		this.anchoPantalla = anchoPantalla;
	}

	public int getAltoPantalla() {
		return altoPantalla;
	}

	public void setAltoPantalla(int altoPantalla) {
		this.altoPantalla = altoPantalla;
	}

	public Grafica getGrafica() {
		return grafica;
	}

	public void setGrafica(Grafica grafica) {
		this.grafica = grafica;
	}

	public Paint getPincel() {
		return pincel;
	}

	public void setPincel(Paint pincel) {
		this.pincel = pincel;
	}

	public boolean[] getValores() {
		return valores;
	}

	public void setValores(boolean[] valores) {
		this.valores = valores;
	}

	public int getAnguloIni() {
		return anguloIni;
	}

	public void setAnguloIni(int anguloIni) {
		this.anguloIni = anguloIni;
	}

	public int getAnguloFin() {
		return anguloFin;
	}

	public void setAnguloFin(int anguloFin) {
		this.anguloFin = anguloFin;
	}

	public double getEscala_y() {
		return escala_y;
	}

	public void setEscala_y(double escala_y) {
		this.escala_y = escala_y;
	}

	public double getEscala_x() {
		return escala_x;
	}

	public void setEscala_x(double escala_x) {
		this.escala_x = escala_x;
	}

	public Plano(Context context) {
		super(context);
		getHolder().addCallback(this);
		pincel = new Paint();

	}

	public int getHipotenusa() {
		return hipotenusa;
	}

	public void setHipotenusa(int hipotenusa) {
		this.hipotenusa = hipotenusa;
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		this.anchoPantalla = w;
		this.altoPantalla = h;
		this.metrics = new DisplayMetrics();
		this.screenHeight = metrics.heightPixels;
		this.screenWidth = metrics.widthPixels;
		screenWidth = getWidth();
		screenHeight = getHeight();
		grafica = new Grafica(this.anchoPantalla, this.altoPantalla);

	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {

		Drawable play, stop, radian, grado;
		degradado = BitmapFactory.decodeResource(getResources(),
				R.drawable.degradado);
		degradado = Bitmap.createScaledBitmap(degradado, screenWidth,
				screenHeight, true);

		play = getContext().getResources().getDrawable(R.drawable.play);
		stop = getContext().getResources().getDrawable(R.drawable.stop);
		radian = getContext().getResources().getDrawable(R.drawable.radianes);
		grado = getContext().getResources().getDrawable(R.drawable.grados);
		grados = new Botones(grado, this);
		grados.setPosX((getAnchoPantalla() / 200) * 10);
		grados.setPosY((getAltoPantalla() / 150) * 15);
		radianes = new Botones(radian, this);
		radianes.setPosX((getAnchoPantalla() / 200) * 170);
		radianes.setPosY((getAltoPantalla() / 150) * 15);
		iniciar = new Botones(play, this);
		iniciar.setPosX((getAnchoPantalla() / 200) * 199);
		iniciar.setPosY((getAltoPantalla() / 200) * 180);
		detener = new Botones(stop, this);
		detener.setPosX((getAnchoPantalla() / 200) * 2);
		detener.setPosY((getAltoPantalla() / 200) * 180);
		paintThread = new PlanoThread(getHolder(), this);
		paintThread.setRunning(true);
		paintThread.setDibujando(true);
		paintThread.start();
		determinarEscala();

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		boolean retry = true;
		paintThread.setRunning(false);
		while (retry) {
			try {
				paintThread.join();
				retry = false;
			} catch (InterruptedException e) {
			}
		}
	}

	@SuppressLint("DrawAllocation")
	@Override
	public void onDraw(Canvas canvas) {

		// canvas.drawRGB(255, 255, 255);
		canvas.drawBitmap(degradado, 0, 0, pincel);
		radianes.dibujarGrafico(canvas);
		grados.dibujarGrafico(canvas);
		iniciar.dibujarGrafico(canvas);
		detener.dibujarGrafico(canvas);
		grados.escribirTexto(canvas);
		paintPlano(canvas, pincel);

	}
	
	public void dibujarTexto(int angulo, Canvas c){
		
		double grados = Math.toDegrees(angulo);
		double radianes = Math.toRadians(angulo);
		getGrados().setText(String.valueOf(grados));
		getRadianes().setText(String.valueOf(radianes));		
		
	}

	private void determinarEscala() {

		double x = 0, y = 0;

		if (getAnchoPantalla() < 950) {
			x = 0.5;
		} else if (getAnchoPantalla() > 950 && getAnchoPantalla() < 1300) {
			x = 1;
		} else if (getAnchoPantalla() > 1300 && getAnchoPantalla() < 2000) {
			x = 1.5;
		} else if (getAnchoPantalla() > 2000 && getAnchoPantalla() < 2700) {
			x = 2;
		} else {
			x = 2.5;
		}

		if (getAltoPantalla() < 600) {
			y = 45;
		} else if (getAltoPantalla() > 600 && getAltoPantalla() < 1000) {
			y = 90;
		} else if (getAltoPantalla() > 1000 && getAltoPantalla() < 1500) {
			y = 135;
		} else if (getAltoPantalla() > 1500 && getAltoPantalla() < 2000) {
			y = 180;
		} else {
			y = 225;
		}

		setEscala_x(x);
		setEscala_y(y);
		setHipotenusa(1);

	}

	public void paintPlano(Canvas canvas, Paint pincel) {

		pincel.setColor(Color.RED);
		pincel.setStyle(Style.STROKE);
		pincel.setStrokeWidth(5);

		// Pinta el Eje X
		canvas.drawLine(0, (getAltoPantalla() / 2), getAnchoPantalla(),
				(getAltoPantalla() / 2), pincel);

		// Pinta el Eje Y
		canvas.drawLine((getAnchoPantalla() / 2), 0, (getAnchoPantalla() / 2),
				getAltoPantalla(), pincel);

		paintEjes(
				canvas,
				pincel,
				getGrafica().paintEjes(getEscala_x(), 150,
						(getAltoPantalla() - 150),
						(getAnguloFin() - getAnguloIni())));

		// paintCirculo(canvas,pincel);
	}

	public void paintEjes(Canvas canvas, Paint pincel, ArrayList<Punto> puntos) {

		pincel.setColor(Color.RED);
		pincel.setStyle(Style.STROKE);
		pincel.setStrokeWidth(5);

		for (int i = 0; i < puntos.size(); i = i + 2) {

			pintar(pincel, puntos.get(i).getX(), puntos.get(i).getY(), puntos
					.get(i + 1).getX(), puntos.get(i + 1).getY(), canvas);
		}

	}

	public void paintCirculo(Canvas canvas, Paint pincel) {

		pincel.setColor(Color.BLACK);
		pincel.setStyle(Style.STROKE);
		pincel.setStrokeWidth(3);

		canvas.drawCircle(((altoPantalla / 300) * 124), (altoPantalla / 2),
				((altoPantalla / 300) * 100), pincel);

	}

	public void pintar(Paint p, int x1, int y1, int x2, int y2, Canvas c) {

		if (y1 >= 0 && y2 <= (getAltoPantalla())) {

			c.drawLine(x1, y1, x2, y2, p);

		}

	}

	public void paintSeno(Canvas c, int cont) {

		if (valores[0]) {

			Punto[] pun = getGrafica().paintSeno(getAnguloIni(),
					getAnguloFin(), getHipotenusa(), getEscala_y(),
					getEscala_x());

			Paint p = getPincel();
			p.setColor(Color.BLUE);
			p.setStyle(Style.STROKE);
			p.setStrokeWidth(5);

			for (int i = 0; i <= cont; i++) {

				pintar(p, pun[i].getX(), pun[i].getY(), pun[i + 1].getX(),
						pun[i + 1].getY(), c);

			}
		}

	}

	public void paintCoseno(Canvas c, int cont) {

		if (valores[1]) {

			Punto[] pun = getGrafica().paintCoseno(getAnguloIni(),
					getAnguloFin(), getHipotenusa(), getEscala_y(),
					getEscala_x());

			Paint p = getPincel();
			p.setColor(Color.RED);
			p.setStyle(Style.FILL);
			p.setStrokeWidth(5);

			for (int i = 0; i <= cont; i++) {

				pintar(p, pun[i].getX(), pun[i].getY(), pun[i + 1].getX(),
						pun[i + 1].getY(), c);

			}

		}

	}

	public void paintTangente(Canvas c, int cont) {

		if (valores[2]) {

			Punto[] pun = getGrafica().paintTangente(getAnguloIni(),
					getAnguloFin(), getHipotenusa(), getEscala_y(),
					getEscala_x());

			Paint p = getPincel();
			p.setColor(Color.GREEN);
			p.setStyle(Style.FILL);
			p.setStrokeWidth(5);

			for (int i = 0; i <= cont; i++) {

				pintar(p, pun[i].getX(), pun[i].getY(), pun[i + 1].getX(),
						pun[i + 1].getY(), c);

			}
		}

	}

	public void paintCosecante(Canvas c, int cont) {

		if (valores[3]) {

			Punto[] pun = getGrafica().paintCosecante(getAnguloIni(),
					getAnguloFin(), getHipotenusa(), getEscala_y(),
					getEscala_x());

			Paint p = getPincel();
			p.setColor(Color.YELLOW);
			p.setStyle(Style.FILL);
			p.setStrokeWidth(5);

			for (int i = 0; i <= cont; i++) {

				pintar(p, pun[i].getX(), pun[i].getY(), pun[i + 1].getX(),
						pun[i + 1].getY(), c);

			}
		}

	}

	public void paintSecante(Canvas c, int cont) {

		if (valores[4]) {

			Punto[] pun = getGrafica().paintSecante(getAnguloIni(),
					getAnguloFin(), getHipotenusa(), getEscala_y(),
					getEscala_x());

			Paint p = getPincel();
			p.setColor(Color.MAGENTA);
			p.setStyle(Style.FILL);
			p.setStrokeWidth(5);

			for (int i = 0; i <= cont; i++) {

				pintar(p, pun[i].getX(), pun[i].getY(), pun[i + 1].getX(),
						pun[i + 1].getY(), c);

			}

		}

	}

	public void paintCotangente(Canvas c, int cont) {

		if (valores[5]) {

			Punto[] pun = getGrafica().paintCotangente(getAnguloIni(),
					getAnguloFin(), getHipotenusa(), getEscala_y(),
					getEscala_x());

			Paint p = getPincel();
			p.setColor(Color.BLACK);
			p.setStyle(Style.FILL);
			p.setStrokeWidth(5);

			for (int i = 0; i <= cont; i++) {

				pintar(p, pun[i].getX(), pun[i].getY(), pun[i + 1].getX(),
						pun[i + 1].getY(), c);

			}
		}

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		int x = (int) event.getX();
		int y = (int) event.getY();

		switch (event.getAction()) {

		case MotionEvent.ACTION_DOWN:
			if (detener.getRectElemento().contains(x, y)) {
				paintThread.setDibujando(false);
				// setRun(false);
			}
			if (iniciar.getRectElemento().contains(x, y)) {
				paintThread.setDibujando(true);
				// setRun(true);
			}
			break;
		}

		return true;
	}

}
