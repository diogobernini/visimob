package com.example.visimob;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ImovelCria extends Activity
{
	DatabaseHelper db = new DatabaseHelper(this);
	Imovel imv;
	Boolean sobreescreve = false;
	TextView locador, locatario, endereco, bairro, tipo, data;
	ImageView iv, iv2;
	int clicafoto;
	int indice;
	File sd = Environment.getExternalStorageDirectory();

	protected void leCampos()
	{
		locador.setText(imv.locador);
		locatario.setText(imv.locatario);
		endereco.setText(imv.endereco);
		bairro.setText(imv.bairro);
		tipo.setText(imv.tipo);
		data.setText(imv.data);
		String caminho = "//visimob//fotos//" + indice + "_1.jpg";
		String caminho2 = "//visimob//fotos//" + indice + "_2.jpg";
		File ft1 = new File(sd,caminho);
		File ft2 = new File(sd,caminho2);
		Log.d("IMCRIA", "vou procurar a imagem 1 em " + ft1.getAbsolutePath());
		if(ft1.exists()) 
		{
			Bitmap bp = BitmapFactory.decodeFile(sd.getAbsolutePath() + caminho);
			iv.setImageBitmap(bp);
			Log.d("DBG", "f1 set");
		}
		Log.d("IMCRIA", "vou procurar a imagem 2 em " + ft2.getAbsolutePath());
		if(ft2.exists())
		{
			Bitmap bp = BitmapFactory.decodeFile(sd.getAbsolutePath() + caminho2);
			iv2.setImageBitmap(bp);
			Log.d("DBG", "f1 set");
		}
	}
	protected void enviaTextoImovel()
	{
		imv.locador = locador.getText().toString();
		imv.locatario = locatario.getText().toString();
		imv.endereco = endereco.getText().toString();
		imv.bairro = bairro.getText().toString();
		imv.tipo = tipo.getText().toString();
		imv.data = data.getText().toString();
		Log.d("IMOVELCRIA", locador.getText().toString());
	}

	protected void onResume() {
		super.onResume();
		imv = db.getUltimoImovel();
		Log.d("IMCRIA", "lendo imovel " + imv.endereco);
		indice = db.getUltimoImovelIndice();
		leCampos();
		Log.d("IMCRIA","resumi");
	}

	protected void onDestroy() {
		super.onDestroy();
		db.atualizaImovel(imv, db.getUltimoImovelIndice());
		db.close();
		Log.d("IMCRIA","destruido");
	}

	protected void onPause()
	{
		super.onPause();
		enviaTextoImovel();
		db.atualizaImovel(imv, db.getUltimoImovelIndice());
		db.close();
		Log.d("IMCRIA","pausou");
	}

	protected void onCreate(Bundle savedInstanceState) {
		Log.d("IMCRIA","criado");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.criaimovel);
		Button b1 = (Button) findViewById(R.id.avanca);
		locador = (TextView) findViewById(R.id.locador);
		locatario = (TextView) findViewById(R.id.locatario);
		endereco = (TextView) findViewById(R.id.endereco);
		bairro = (TextView) findViewById(R.id.bairro);
		tipo = (TextView) findViewById(R.id.tipo);
		data = (TextView) findViewById(R.id.data);
		b1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				enviaTextoImovel();
//				if(db.jaExiste(endereco.getText().toString())||sobreescreve==true)
//				{
//					Toast.makeText(getApplicationContext(), "IMOVEL JÁ EXISTE, PARA SOBREESCREVER PRESSIONE MAIS UMA VEZ.", Toast.LENGTH_SHORT).show();
//					sobreescreve=true;
//				}
//				else
//				{
					db.atualizaImovel(imv, db.getUltimoImovelIndice());
					db.exportaDB();
					Log.d("ImovelCRIA", "dados atualizados");
					Intent i = new Intent(getApplicationContext(), ImovelLista.class);
					startActivity(i);
//				}
			}
		});
		iv = (ImageView) findViewById(R.id.imageView1);
		iv.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				String caminho = "//visimob//fotos//" + indice + "_1.jpg";		    
				File arquivo = new File(sd, caminho);
				if(!arquivo.exists())
					{try {
						arquivo.createNewFile();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					}
				if(arquivo.canWrite())
					Log.d("IMOVELCRIA", "aquivo null");
				Log.d("IMOVELCRIA", Uri.fromFile(arquivo).toString());
				cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(arquivo));  // set the image file name
				clicafoto = 1;
				Log.d("IMOVELCRIA", "vou tirar foto 1");
				startActivityForResult(cameraIntent, 1);
			}
		});
		iv2 = (ImageView) findViewById(R.id.imageView2);
		iv2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				String caminho = "//visimob//fotos//" + indice + "_2.jpg";
				File arquivo = new File(sd, caminho);
				if(!arquivo.exists())
				{try {
					arquivo.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
				if(arquivo.canWrite())
				Log.d("IMOVELCRIA", "aquivo null");
				Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(arquivo));  // set the image file name
				clicafoto = 2;
				Log.d("IMOVELCRIA", "vou tirar foto 2");
				startActivityForResult(cameraIntent, 1);
			}
		});
	}
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		Log.d("DBG", "entrou no activity result da camera");
		super.onActivityResult(requestCode, resultCode, data);
//		String caminho = "//visimob//fotos//" + indice + "_1.jpg";
//		String caminho2 = "//visimob//fotos//" + indice + "_2.jpg";
//		if(new File(caminho).exists())
//		{
//			Log.d("IMOVELCRIA", "imagem1 existe");
//			Bitmap bp = BitmapFactory.decodeFile(caminho);
//			iv.setImageBitmap(bp);
//			Log.d("DBG", "f1 set");
//		}
//		if(new File(caminho2).exists())
//		{
//			Log.d("IMOVELCRIA", "imagem2 existe");
//			Bitmap bp = BitmapFactory.decodeFile(caminho2);
//			iv2.setImageBitmap(bp);
//			Log.d("DBG", "f1 set");
//		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		Log.d("DBG","menu");
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
