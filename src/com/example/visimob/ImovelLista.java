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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ImovelLista extends Activity {
	ListView listaImoveis;
	DatabaseHelper db = new DatabaseHelper(this);
	String[] imoveis = null;
	String temp;
	ArrayList<String> arl = new ArrayList<String>();
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.imovelrow);
		
		listaImoveis = (ListView) findViewById(R.id.listview_imoveis);
		Log.d("IMLISTA","criado");
//		try{
//		Imovel im = db.getUltimoImovel();
//		if(im.endereco==null||im.endereco=="")
//			db.deleteImovel(im);}
//		catch(Exception e)
//		{}
		db.limpaLista();
		List<String> lista = db.getAllImoveis();
		imoveis = lista.toArray(new String[lista.size()]);
		for(int i = 0; i<lista.size();i++)
		{
			arl.add(imoveis[i]);
		}
		Log.d("IMLISTA", arl.toString());
		
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,R.layout.itemsimples, lista);
		listaImoveis.setAdapter(arrayAdapter);
		listaImoveis.setOnItemClickListener(new OnItemClickListener()
        {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					String imovelselecionado=arl.get(arg2);
                    Log.d("IMLISTA","clicou em: " + imovelselecionado);
                    Intent i = new Intent(getApplicationContext(), EditarEApagar.class);
                    i.putExtra("imovel", imovelselecionado);
                    startActivity(i);
				}
        });
	}
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if((keyCode == KeyEvent.KEYCODE_BACK))
		{
			Log.d(this.getClass().getName(), "apertou botão voltar");
			Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
		}
		return super.onKeyDown(keyCode, event);
	}
	protected void onDestroy(Bundle savedInstanceState)
	{
		db.close();
	}
}
