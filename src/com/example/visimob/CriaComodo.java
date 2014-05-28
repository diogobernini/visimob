package com.example.visimob;

import com.example.visimob.R.id;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CriaComodo extends Activity
{
	DatabaseHelper db = new DatabaseHelper(this);
	Comodo cmd = new Comodo();
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.criacomodo);
		Button salvar = (Button) findViewById(R.id.botaosalvarcomodo);
		salvar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Switching to Register screen
            	Log.d("CRIACOMODO", "criar");
        		EditText tv = (EditText) findViewById(id.criacomodonome);
            	cmd.nomeComodo = tv.getText().toString();
            	Log.d("CRIACOMODO", "vou criar um comodo");
            	if(db.jaExiste(getIntent().getExtras().getString("imovel"), cmd.nomeComodo))
            	{
            		Toast.makeText(getApplicationContext(), "COMODO JÁ EXISTE", Toast.LENGTH_SHORT).show();
            	}
            	else
            	{
            	db.novoComodo(getIntent().getExtras().getString("imovel"), cmd);
            	Log.d("CRIACOMODO", "criei um comodo");
            	db.close();
            	Log.d("CRIACOMODO", cmd.nomeComodo);
                Intent i = new Intent(getApplicationContext(), ListaComodos.class);
                i.putExtra("imovel", getIntent().getExtras().getString("imovel"));
                i.putExtra("comodo", cmd.nomeComodo);
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
			Intent i = new Intent(getApplicationContext(), ListaComodos.class);
            i.putExtra("imovel", getIntent().getExtras().getString("imovel"));
            i.putExtra("comodo", cmd.nomeComodo);
            startActivity(i);
            db.close();
		}
		return super.onKeyDown(keyCode, event);
	}
}
