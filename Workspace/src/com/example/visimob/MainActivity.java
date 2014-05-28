package com.example.visimob;

import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.widget.Button;
import android.view.View;
import android.content.Intent;

public class MainActivity extends Activity {
	DatabaseHelper db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button b1 = (Button) findViewById(R.id.botaoapagar);
		b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Switching to Register screen
            	Log.d("MAIN ACT", "imovel cria started");
                Intent i = new Intent(getApplicationContext(), ImovelCria.class);
                startActivity(i);
            }
        });
		Button b2 = (Button) findViewById(R.id.botaoeditar);
		b2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Switching to Register screen
            	Log.d("MAIN ACT", "imovel cria started");
                Intent i = new Intent(getApplicationContext(), ImovelLista.class);
                startActivity(i);
            }
        });
	}

	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if((keyCode == KeyEvent.KEYCODE_BACK))
		{
			Log.d(this.getClass().getName(), "apertou botão voltar");
            db.close();
			this.moveTaskToBack(true);
		}
		return super.onKeyDown(keyCode, event);
	}
	protected void onPause() {
		super.onPause();
		Log.d("main","pausou main");
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	public void onResume()
	{
		super.onResume();
		db = new DatabaseHelper(getApplicationContext());
		try{
		Imovel imv = db.getUltimoImovel();
		List<String> lista = db.getAllComodos(imv.endereco);
		Log.d("main", lista.size() + ", " + lista);
		if(lista.size()==0)
		{
			db.deleteImovel(imv);
			Log.d("main", "eu apaguei o ultimo imovel");
		}}
		catch(Exception e)
		{
		}
		db.limpaLista();
		Imovel imovel = new Imovel();
		imovel.endereco = "";
		db.novoImovel(imovel);
		Log.d("MAIN","criei o imovel, agora vou exportar e encerrar");
		db.exportaDB();
		db.close();
	}
}
