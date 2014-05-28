package com.example.visimob;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

public class EditaEApagaCampo extends Activity {
	DatabaseHelper db = new DatabaseHelper(this);
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editaeapagacampo);
		Button edita = (Button) findViewById(R.id.botaoeditarcampo);
		Button apaga = (Button) findViewById(R.id.botaoapagarcampo);
		edita.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Switching to Register screen
            	Log.d("EDITAEAPAGA", "editar");
                Intent i = new Intent(getApplicationContext(), EditaCampo.class);
                i.putExtra("imovel", getIntent().getExtras().getString("imovel"));
                i.putExtra("comodo", getIntent().getExtras().getString("comodo"));
                i.putExtra("campo", getIntent().getExtras().getString("campo"));
                startActivity(i);
            }
        });
		apaga.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Switching to Register screen
            	Log.d("EDITAEAPAGA", "apagar");
            	db.deleteCampo(getIntent().getExtras().getString("campo"),getIntent().getExtras().getString("comodo"),getIntent().getExtras().getString("imovel"));
                Intent i = new Intent(getApplicationContext(), ListaCampos.class);
                i.putExtra("imovel", getIntent().getExtras().getString("imovel"));
                i.putExtra("comodo", getIntent().getExtras().getString("comodo"));
                startActivity(i);
            }
        });
	}
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if((keyCode == KeyEvent.KEYCODE_BACK))
		{
			Log.d(this.getClass().getName(), "apertou botão voltar");
			Intent i = new Intent(getApplicationContext(), ListaCampos.class);
            i.putExtra("imovel", getIntent().getExtras().getString("imovel"));
            i.putExtra("comodo", getIntent().getExtras().getString("comodo"));
            startActivity(i);
            db.close();
		}
		return super.onKeyDown(keyCode, event);
	}
}
