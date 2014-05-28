package com.example.visimob;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class EditaCampo extends Activity {
	DatabaseHelper db = new DatabaseHelper(this);
	CheckBox cb1, cb2, cb3, cb4, cb5, cb6, cb7;
	Campo cam = new Campo();
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editacomodo);
		if(db.jaExiste(getIntent().getExtras().getString("imovel"), getIntent().getExtras().getString("comodo"), getIntent().getExtras().getString("campo")))
				{	
					Log.d("EDITACAMPO", "vou tentar pegar o campo no banco de dados");
					cam = db.getCampo(getIntent().getExtras().getString("campo"), getIntent().getExtras().getString("imovel"), getIntent().getExtras().getString("comodo"));
					Log.d("EDITACAMPO", "busquei o campo " + getIntent().getExtras().getString("campo") + " já, ele está "+ cam.estado + " e " + cam.estadoOBS);
					String[] valores = null;
					if(cam.estado != null || cam.estado != "")
					{
						try{
						valores = cam.estado.split("ENDOFBOXES");
						for(int i = 0; i<valores.length; i++)
							Log.d("EDITACAMPO", valores[i]);
						if(valores[0].contains("Funcionando"))
						{
							cb1 = (CheckBox) findViewById(R.id.checkBox1);
							cb1.setChecked(true);
						}
						if(valores[0].contains("Conservado"))
						{
							cb2 = (CheckBox) findViewById(R.id.checkBox2);
							cb2.setChecked(true);
						}
						if(valores[0].contains("Em perfeito estado"))
						{
							cb2 = (CheckBox) findViewById(R.id.checkBox3);
							cb2.setChecked(true);
						}
						if(valores[0].contains("Com pequenos estragos"))
						{
							cb2 = (CheckBox) findViewById(R.id.checkBox4);
							cb2.setChecked(true);
						}
						if(valores[0].contains("Com defeito"))
						{
							cb2 = (CheckBox) findViewById(R.id.checkBox5);
							cb2.setChecked(true);
						}
						if(valores[0].contains("Sujo"))
						{
							cb2 = (CheckBox) findViewById(R.id.checkBox6);
							cb2.setChecked(true);
						}
						if(valores[0].contains("Limpo"))
						{
							cb2 = (CheckBox) findViewById(R.id.checkBox7);
							cb2.setChecked(true);
						}}
						catch(Exception e)
						{
							Log.d("EDITACAMPO", "campo vazio");
						}
		        		try{EditText et = (EditText) findViewById(R.id.editText1);
		        		et.setText(cam.estadoOBS);}
		        		catch(Exception e)
		        		{
		        			Log.d("EDITACAMPO", "campo obs vazio");
		        		}
					}
					else
					{
						Log.d("EDITACAMPO", "td vazio nesse campo");
					}
					Log.d("EDITACAMPO", "inseri os valores correspondentes nos formularios");
				}		
		Button salvar = (Button) findViewById(R.id.botaosalvarcampo);
		salvar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
        		String estado = "";
            	cb1 = (CheckBox) findViewById(R.id.checkBox1);
        		if(cb1.isChecked())
        		{
        			estado = estado + "Funcionando ";
        		}
        		cb2 = (CheckBox) findViewById(R.id.checkBox2);
        		if(cb2.isChecked())
        		{
        			estado = estado + "Conservado ";
        		}
        		cb3 = (CheckBox) findViewById(R.id.checkBox3);
        		if(cb3.isChecked())
        		{
        			estado = estado + "Em perfeito estado ";
        		}
        		cb4 = (CheckBox) findViewById(R.id.checkBox4);
        		if(cb4.isChecked())
        		{
        			estado = estado + "Com pequenos estragos ";
        		}
        		cb5 = (CheckBox) findViewById(R.id.checkBox5);
        		if(cb5.isChecked())
        		{
        			estado = estado + "Com defeito ";
        		}
        		cb6 = (CheckBox) findViewById(R.id.checkBox6);
        		if(cb6.isChecked())
        		{
        			estado = estado + "Sujo ";
        		}
        		cb7 = (CheckBox) findViewById(R.id.checkBox7);
        		if(cb7.isChecked())
        		{
        			estado = estado + "Limpo ";
        		}
        		estado = estado + "ENDOFBOXES";
        		cam.nomeCampo = getIntent().getExtras().getString("campo");
            	cam.estado = estado;
        		EditText et = (EditText) findViewById(R.id.editText1);
            	cam.estadoOBS = et.getText().toString();
            	db.atualizaCampo(getIntent().getExtras().getString("imovel"), getIntent().getExtras().getString("comodo"), cam);
            	db.close();
            	Log.d("EDITACAMPO", cam.nomeCampo + cam.estado + cam.estadoOBS);
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
