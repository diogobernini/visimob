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

public class ListaCampos extends Activity {
	ListView listaCampos;
	DatabaseHelper db = new DatabaseHelper(this);
	String[] campos = null;
	String temp;
	ArrayList<String> arl = new ArrayList<String>();
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.campolista);
		listaCampos = (ListView) findViewById(R.id.listview_campos);
		Log.d("IMLISTA","criado");
		List<String> lista = db.getAllCampos(getIntent().getExtras().getString("imovel"), getIntent().getExtras().getString("comodo"));
		campos = lista.toArray(new String[lista.size()]);
		for(int i = 0; i<lista.size();i++)
		{
			arl.add(campos[i]);
		}
		Log.d("IMLISTA", arl.toString());
		
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,R.layout.itemsimples, lista);
		listaCampos.setAdapter(arrayAdapter);
		listaCampos.setOnItemClickListener(new OnItemClickListener()
        {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					String comodoselecionado=arl.get(arg2);
                    Log.d("IMLISTA","clicou em: " + comodoselecionado);
                    Intent i = new Intent(getApplicationContext(), EditaEApagaCampo.class);
                    i.putExtra("imovel", getIntent().getExtras().getString("imovel"));
                    i.putExtra("comodo", getIntent().getExtras().getString("comodo"));
                    i.putExtra("campo", comodoselecionado);
                    startActivity(i);
				}
        });
		Button b1 = (Button) findViewById(R.id.AdicionarCampo);
		b1.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v) {
                // Switching to Register screen
            	Log.d("LISTACOMODOS", "ADC_Campo");
                Intent i = new Intent(getApplicationContext(), CriaCampo.class);
                i.putExtra("imovel", getIntent().getExtras().getString("imovel"));
                i.putExtra("comodo", getIntent().getExtras().getString("comodo"));
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
			Intent i = new Intent(getApplicationContext(), ListaComodos.class);
            i.putExtra("imovel", getIntent().getExtras().getString("imovel"));
            startActivity(i);
		}
		return super.onKeyDown(keyCode, event);
	}
	protected void onDestroy(Bundle savedInstanceState)
	{
		db.close();
	}
}
