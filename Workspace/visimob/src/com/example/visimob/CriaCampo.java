package com.example.visimob;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CriaCampo extends Activity {
	DatabaseHelper db = new DatabaseHelper(this);
	Campo cmp = new Campo();
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.criacampo);
		Button salvar = (Button) findViewById(R.id.botaosalvarcomodo);
		salvar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Switching to Register screen
            	Log.d("CRIACAMPO", "criar");
        		EditText tv = (EditText) findViewById(R.id.criacomodonome);
        		cmp.nomeCampo = tv.getText().toString();
            	Log.d("CRIACAMPO", "vou criar um campo com nome de " + cmp.nomeCampo);
            	if(db.jaExiste(getIntent().getExtras().getString("imovel"),  getIntent().getExtras().getString("comodo"), cmp.nomeCampo))
            	{
            		Toast.makeText(getApplicationContext(), "CAMPO JÁ EXISTE", Toast.LENGTH_SHORT).show();
            	}
            	else
            	{
            	db.novoCampo(getIntent().getExtras().getString("imovel"), getIntent().getExtras().getString("comodo"), cmp);
            	Log.d("CRIACAMPO", "criei um campo");
            	db.close();
            	Log.d("CRIACAMPO", cmp.nomeCampo);
                Intent i = new Intent(getApplicationContext(), ListaCampos.class);
                i.putExtra("comodo", getIntent().getExtras().getString("comodo"));
                i.putExtra("imovel", getIntent().getExtras().getString("imovel"));
                startActivity(i);
            	}
            }
        });
	}
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if((keyCode == KeyEvent.KEYCODE_BACK))
		{
			Log.d(this.getClass().getName(), "apertou botão voltar");
			Intent i = new Intent(getApplicationContext(), ListaCampos.class);
            i.putExtra("comodo", getIntent().getExtras().getString("comodo"));
            i.putExtra("imovel", getIntent().getExtras().getString("imovel"));
            startActivity(i);
            db.close();
		}
		return super.onKeyDown(keyCode, event);
	}
}
