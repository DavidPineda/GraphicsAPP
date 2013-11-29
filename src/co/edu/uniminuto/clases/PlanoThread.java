package co.edu.uniminuto.clases;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import co.edu.uniminuto.vista.Plano;

@SuppressLint("WrongCall")
public class PlanoThread extends Thread {

	private SurfaceHolder sh;
	private Plano view;
	private boolean run;
	private static final long FPS = 80;
	private boolean dibujando;
	public void setRunning(boolean run) {
		this.run = run;
	}

	public boolean isRun() {
		return run;
	}

	public boolean isDibujando() {
		return dibujando;
	}

	public void setDibujando(boolean dibujando) {
		this.dibujando = dibujando;
	}

	public PlanoThread(SurfaceHolder sh, Plano view) {
		this.sh = sh;
		this.view = view;
		run = false;
	}

	@Override
	public void run() {
		long framePS = 1000 / FPS;
		long tiempoInicial;
		long tiempoDormir;
		int contador = 0;
		while (run) {
			Canvas canvas = null;
			tiempoInicial = System.currentTimeMillis();
			try {
				canvas = sh.lockCanvas(null);
				synchronized (sh) {
					view.onDraw(canvas);
					view.paintSeno(canvas, contador);
					view.paintCoseno(canvas, contador);
					view.paintTangente(canvas, contador);
					view.paintSecante(canvas, contador);
					view.paintCosecante(canvas, contador);
					view.paintCotangente(canvas, contador);
				}
				if (dibujando) {
					contador++;
				}
				if (contador == ((view.getAnguloFin() - view.getAnguloIni()) - 1)) {
					contador = 0;
				}
			} finally {
				if (canvas != null)
					sh.unlockCanvasAndPost(canvas);
			}
			tiempoDormir = framePS
					- (System.currentTimeMillis() - tiempoInicial);
			try {
				if (tiempoDormir > 0) {
					sleep(tiempoDormir);
				} else {
					sleep(5);
				}
			} catch (Exception e) {
			}
		}

	}
}
