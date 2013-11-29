package co.edu.uniminuto.vista;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import co.uniminuto.R;

public class MenuPrincipal extends Activity implements OnClickListener {

	private Button btnGraficar;

	public Button getBtnGraficar() {
		return btnGraficar;
	}

	public void setBtnGraficar(Button btnGraficar) {
		this.btnGraficar = btnGraficar;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_ejemplo_graficos);
		btnGraficar = (Button) findViewById(R.id.btn_graficar);
		btnGraficar.setOnClickListener(this);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ejemplo_graficos, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		EditText et1;
		EditText et2;
	
		boolean ejecutar = true;

		// Validando el dato de angulo inicial
		et1 = (EditText) findViewById(R.id.ang_ini);	
		if (!validarDatoNumerico(et1.getText().toString())) {
			ejecutar = false;
			Toast.makeText(this, "El angulo inicial debe ser un numero entero",
					Toast.LENGTH_SHORT).show();
		}

		// Validando el dato de angulo final
		et2 = (EditText) findViewById(R.id.ang_fin);
		if (!validarDatoNumerico(et2.getText().toString())) {
			ejecutar = false;
			Toast.makeText(this, "El angulo final debe ser un numero entero",
					Toast.LENGTH_SHORT).show();
		}

		if (ejecutar
				&& !validarDatoRango(
						Integer.parseInt(et1.getText().toString()),
						Integer.parseInt(et2.getText().toString()))) {
			ejecutar = false;
			Toast.makeText(this,
					"El angulo final debe ser mayor al angulo inicial",
					Toast.LENGTH_SHORT).show();
		}

		if (ejecutar
				&& !validarMinValor(Integer.parseInt(et1.getText().toString()))) {
			ejecutar = false;
			Toast.makeText(this, "El valor minimo del angulo inicial es -360",
					Toast.LENGTH_SHORT).show();
		}

		if (ejecutar
				&& !validarMaxValor(Integer.parseInt(et2.getText().toString()))) {
			ejecutar = false;
			Toast.makeText(this, "El valor maximo del angulo final es 360",
					Toast.LENGTH_SHORT).show();
		}

		if (ejecutar && !validarUnCheckSeleccionado()) {
			ejecutar = false;
			Toast.makeText(this, "Debe eligir una gráfica", Toast.LENGTH_SHORT)
					.show();
		}
		
	
		if (ejecutar) {
			Intent i = new Intent(this, PlanoActivity.class);
			i.putExtra("seleccionados", validarCheck());
			i.putExtra("anguloIni", et1.getText().toString());
			i.putExtra("anguloFin", et2.getText().toString());
			startActivity(i);
		}
	}

	public boolean[] validarCheck() {

		boolean[] valores = new boolean[6];
		CheckBox ch;

		ch = (CheckBox) findViewById(R.id.cb_sin);

		if (ch.isChecked()) {
			valores[0] = true;
		}

		ch = (CheckBox) findViewById(R.id.cb_cos);

		if (ch.isChecked()) {
			valores[1] = true;
		}

		ch = (CheckBox) findViewById(R.id.cb_tan);

		if (ch.isChecked()) {
			valores[2] = true;
		}

		ch = (CheckBox) findViewById(R.id.cb_sec);

		if (ch.isChecked()) {
			valores[3] = true;
		}

		ch = (CheckBox) findViewById(R.id.cb_csc);

		if (ch.isChecked()) {
			valores[4] = true;
		}

		ch = (CheckBox) findViewById(R.id.cb_atan);

		if (ch.isChecked()) {
			valores[5] = true;
		}

		return valores;
	}

	public boolean validarDatoNumerico(String dato) {

		Pattern pat = Pattern.compile("-?[0-9]+");
		Matcher mat = pat.matcher(dato);

		return mat.matches();

	}

	public boolean validarDatoRango(int dato1, int dato2) {

		return (dato2 > dato1) ? true : false;

	}

	public boolean validarMinValor(int dato) {

		return (dato >= -360) ? true : false;

	}

	public boolean validarMaxValor(int dato) {

		return (dato <= 360) ? true : false;

	}

	public boolean validarUnCheckSeleccionado() {

		CheckBox chs, chc, cht, chct, chse, chcs;

		chs = (CheckBox) findViewById(R.id.cb_sin);
		chc = (CheckBox) findViewById(R.id.cb_cos);
		cht = (CheckBox) findViewById(R.id.cb_tan);
		chct = (CheckBox) findViewById(R.id.cb_sec);
		chse = (CheckBox) findViewById(R.id.cb_csc);
		chcs = (CheckBox) findViewById(R.id.cb_atan);

		if (chs.isChecked() || chc.isChecked() || cht.isChecked()
				|| chct.isChecked() || chse.isChecked() || chcs.isChecked()) {
			return true;
		}

		return false;
	}
}
