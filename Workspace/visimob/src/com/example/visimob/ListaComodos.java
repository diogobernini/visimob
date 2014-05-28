package com.example.visimob;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;


public class ListaComodos extends Activity
{
	ListView listaComodos;
	DatabaseHelper db = new DatabaseHelper(this);
	String[] imoveis = null;
	String temp;
	ArrayList<String> arl = new ArrayList<String>();
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.comodolista);
		
		listaComodos = (ListView) findViewById(R.id.listview_comodos);
		Log.d("IMLISTA","criado");
		List<String> lista = db.getAllComodos(getIntent().getExtras().getString("imovel"));
		imoveis = lista.toArray(new String[lista.size()]);
		for(int i = 0; i<lista.size();i++)
		{
			arl.add(imoveis[i]);
		}
		Log.d("IMLISTA", arl.toString());
		
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,R.layout.itemsimples, lista);
		listaComodos.setAdapter(arrayAdapter);
		listaComodos.setOnItemClickListener(new OnItemClickListener()
        {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					String comodoselecionado=arl.get(arg2);
                    Log.d("IMLISTA","clicou em: " + comodoselecionado);
                    Intent i = new Intent(getApplicationContext(), EditaEApagaComodo.class);
                    i.putExtra("imovel", getIntent().getExtras().getString("imovel"));
                    i.putExtra("comodo", comodoselecionado);
                    startActivity(i);
				}
        });
		Button b1 = (Button) findViewById(R.id.AdicionarCampo);
		b1.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v) {
                // Switching to Register screen
            	Log.d("LISTACOMODOS", "ADC_Campo");
                Intent i = new Intent(getApplicationContext(), CriaComodo.class);
                i.putExtra("imovel", getIntent().getExtras().getString("imovel"));
                startActivity(i);
            }
		}
				);
	}
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if((keyCode == KeyEvent.KEYCODE_BACK))
		{
			Log.d(this.getClass().getName(), "apertou botão voltar");
			db.close();
			Intent i = new Intent(getApplicationContext(), ImovelLista.class);
            startActivity(i);
		}
		return super.onKeyDown(keyCode, event);
	}
	protected void onDestroy(Bundle savedInstanceState)
	{
		db.close();
	}
}
