package co.edu.uniminuto.vista;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class PlanoActivity extends Activity {

	private boolean[] checks;
	private int anguloIni;
	private int anguloFin;
	
	public boolean[] getChecks() {
		return checks;
	}

	public void setChecks(boolean[] checks) {
		this.checks = checks;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		Bundle bundle = getIntent().getExtras();
		setChecks(bundle.getBooleanArray("seleccionados"));
		setAnguloIni(Integer.parseInt(bundle.getString("anguloIni")));
		setAnguloFin(Integer.parseInt(bundle.getString("anguloFin")));
		Plano p = new Plano(this);
		p.setValores(getChecks());
		p.setAnguloIni(getAnguloIni());
		p.setAnguloFin(getAnguloFin());
		setContentView(p);
	}
}
