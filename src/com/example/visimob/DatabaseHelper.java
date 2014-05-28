package com.example.visimob;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper
{
	static final String dbName = "banco";
	static final String tabelaImoveis = "Imoveis";
	static final String colID = "ImovelID";
	static final String colLocador = "Locador";
	static final String colLocatario = "Locatario";
	static final String colEndereco = "Endereco";
	static final String colBairro = "Bairro";
	static final String colTipo = "Tipo";
	static final String colData = "Data";
	static final String colImagem1 = "Imagem1";
	static final String colImagem2 = "Imagem2";

	static final String tabelaCampos = "Campos";
	static final String colCampoID = "CampoID";
	static final String colCampo = "Campo";
	static final String colImovelEnd = "ImovelEnd";
	static final String colComodoCampo = "ComodoCampo";
	static final String colEstado = "Estado";
	static final String colOBS = "Observacao";
	static final String colCampoImagem1 = "CampoImagem1";
	static final String colCampoImagem2 = "CampoImagem2";
	
	static final String tabelaComodos = "Comodos";
	static final String colComodoID = "ComodoID";
	static final String colComodoNome = "ComodoNome";
	static final String colComodoEndereco = "ComodoEndereco";
	
	public DatabaseHelper(Context context)
	{
		super(context,dbName,null,34);
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d("DATABASE", "BASE CRIADA");
		db.execSQL("CREATE TABLE " + tabelaImoveis + " (" + colID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + colEndereco + " TEXT, " +
				colLocador + " TEXT, " + colLocatario + " TEXT, " + colBairro + " TEXT, " + colTipo + " TEXT, " + colData
				+ " TEXT, " + colImagem1 + " TEXT, " + colImagem2 + " TEXT)");
		db.execSQL("CREATE TABLE " + tabelaCampos + " ( " + colCampoID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + colCampo + " TEXT, " +
				colImovelEnd + " TEXT, " + colComodoCampo + " TEXT, " + colEstado + " TEXT, " + colOBS + " TEXT, " + colCampoImagem1 + " TEXT, "
				+ colCampoImagem2 + " TEXT)");
		db.execSQL("CREATE TABLE " + tabelaComodos + " (" + colComodoID + " INTEGER PRIMARY KEY AUTOINCREMENT ," + colComodoNome + " TEXT, "
				+ colComodoEndereco + " TEXT)");
	}
	public void deletaTudo()
	{
		SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tabelaImoveis, null, null);
        db.delete(tabelaCampos, null, null);
        db.delete(tabelaComodos, null, null);
		db.execSQL("DELETE FROM sqlite_sequence WHERE name IS '" + tabelaImoveis + "'");
		db.close();
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2)
	{
		Log.d("DATABASE", "BASE RECRIADA");
		Log.d("BANCO", "Tabela antiga: " + arg1 + ", " + arg2 + ".");
		db.execSQL("DROP TABLE IF EXISTS "+tabelaImoveis);
		db.execSQL("DROP TABLE IF EXISTS "+tabelaCampos);
		db.execSQL("DROP TABLE IF EXISTS "+tabelaComodos);
		onCreate(db);
	}
	public void novoImovel(Imovel imv)
	{
		Log.d("DATABASE", "IMOVEL" + imv.endereco + " ADICIONADO");
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(colEndereco, imv.endereco);
		cv.put(colLocador, imv.locador);
		cv.put(colLocatario, imv.locatario);
		cv.put(colBairro, imv.bairro);
		cv.put(colTipo, imv.tipo);
		cv.put(colData, imv.data);
		cv.put(colImagem1, imv.imagem1);
		cv.put(colImagem2, imv.imagem2);
		db.insert(tabelaImoveis, null, cv);
		db.close();
	}
	private void populaBasico(String endereco, String comodo)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(colCampo, "Fechadura");
		cv.put(colImovelEnd, endereco);
		cv.put(colComodoCampo, comodo);
		db.insert(tabelaCampos, colCampoID, cv);
		cv.put(colCampo, "Interruptores");
		db.insert(tabelaCampos, colCampoID, cv);
		cv.put(colCampo, "Piso");
		db.insert(tabelaCampos, colCampoID, cv);
		cv.put(colCampo, "Tomada");
		db.close();
	}
	public void novoComodo(String endereco, Comodo com)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(colComodoNome, com.nomeComodo);
		cv.put(colComodoEndereco, endereco);
		db.insert(tabelaComodos, colCampoID, cv);
		this.populaBasico(endereco, com.nomeComodo);
		Log.d("DATABASE", "ja inseri na tabela");
		db.close();
	}
	public void novoCampo(String endereco, String comodo, Campo cam)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(colCampo, cam.nomeCampo);
		cv.put(colImovelEnd, endereco);
		cv.put(colComodoCampo, comodo);
		cv.put(colEstado, cam.estado);
		cv.put(colOBS, cam.estadoOBS);
		cv.put(colCampoImagem1, cam.imagem1);
		cv.put(colCampoImagem2, cam.imagem2);
		db.insert(tabelaCampos, colCampoID, cv);
		db.close();
	}
	public Boolean jaExiste(String endereco, String comodo)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		Log.d("DATABASE", "tentando achar " + comodo + " em " + endereco);
		try{
		Cursor cursor = db.query(tabelaComodos, new String[] { colComodoID },
		        colComodoEndereco + " IS '" + endereco + "' AND " + colComodoNome + " IS '" + comodo + "'", null, null, null, null);
		cursor.moveToFirst();
		if(cursor.isNull(0))
		{
			Log.d("DATABASE","Não encontrei!");
			return false;
		}
		else
		{
			Log.d("DATABASE", "este elemento existe");
			return true;
		}
		}catch(Exception e)
		{
			Log.d("DATABASE", e.getLocalizedMessage() + e.getMessage() + e.getCause());
			return false;
		}
	}
	public Boolean jaExiste(String endereco)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		Log.d("DATABASE", "tentando achar " + endereco);
		try{
		Cursor cursor = db.query(tabelaImoveis, new String[] { colID },
		        colEndereco + " IS '" + endereco + "'", null, null, null, null);
		cursor.moveToFirst();
		if(cursor.isNull(0))
		{
			Log.d("DATABASE","Não encontrei!");
			return false;
		}
		else
		{
			Log.d("DATABASE", "este elemento existe");
			return true;
		}
		}catch(Exception e)
		{
			Log.d("DATABASE", e.getLocalizedMessage() + e.getMessage() + e.getCause());
			return false;
		}
	}
	public Boolean jaExiste(String endereco, String comodo, String campo)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		Log.d("DATABASE", "tentando achar " + campo + " no " + comodo + " em " + endereco);
		try{
		Cursor cursor = db.query(tabelaCampos, new String[] { colCampoID },
		        colImovelEnd + " IS '" + endereco + "' AND " + colComodoCampo + " IS '" + comodo + "' AND " +
		        colCampo + " IS '" + campo + "'", null, null, null, null);
		cursor.moveToFirst();
		if(cursor.isNull(0))
		{
			Log.d("DATABASE","Não encontrei!");
			return false;
		}
		else
		{
			Log.d("DATABASE", "este elemento existe");
			return true;
		}
		}catch(Exception e)
		{
			Log.d("DATABASE", e.getLocalizedMessage() + e.getMessage() + e.getCause());
			return false;
		}
	}
	public Campo getCampo(String c, String endereco, String comodo)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		Campo cmp = new Campo();
		try{
		Cursor cursor = db.query(tabelaCampos, new String[] { colCampo, colImovelEnd, colComodoCampo, colEstado, colOBS, colCampoImagem1, colCampoImagem2},
		        colImovelEnd + " IS '" + endereco + "' AND " + colComodoCampo + " IS '" + comodo + "'" + " AND " + colCampo + " IS '" + c + "'", null, null, null, null);
		cursor.moveToFirst();
		cmp.nomeCampo = cursor.getString(0);
		cmp.endereco = cursor.getString(1);
		cmp.comodo = cursor.getString(2);
		cmp.estado = cursor.getString(3);
		cmp.estadoOBS = cursor.getString(4);
		cmp.imagem1 = cursor.getString(5);
		cmp.imagem2 = cursor.getString(6);
		}catch(Exception e)
		{
			Log.d("DATABASE", e.getLocalizedMessage() + e.getMessage() + e.getCause());
		}
		
		return cmp;
	}
	public int numeroDeImoveis()
	{
		return this.getAllImoveis().size();
	}
	public void deleteImovel(Imovel imv)
	  {
	   SQLiteDatabase db=this.getWritableDatabase();
	   db.delete(tabelaCampos, colImovelEnd+"=?", new String [] {String.valueOf(imv.endereco)});
	   db.delete(tabelaImoveis, colEndereco+"=?", new String [] {String.valueOf(imv.endereco)});
	   db.delete(tabelaComodos, colComodoEndereco+"=?", new String [] {String.valueOf(imv.endereco)});
	   db.close();
	  }
	public void deleteImovelSemCampos(int posicao)
	  {
	   SQLiteDatabase db=this.getWritableDatabase();
	   db.delete(tabelaImoveis, colID+"=?", new String [] {((Integer)posicao).toString()});
	   db.close();
	  }
	public void deleteImovel(String endereco)
	  {
	   SQLiteDatabase db=this.getWritableDatabase();
	   db.delete(tabelaCampos, colImovelEnd+"=?", new String [] {String.valueOf(endereco)});
	   db.delete(tabelaImoveis, colEndereco+"=?", new String [] {String.valueOf(endereco)});
	   db.close();
	  }
	public void deleteComodo(String endereco, String comodo)
	  {
	   SQLiteDatabase db=this.getWritableDatabase();
	   db.delete(tabelaCampos, colImovelEnd + " IS '" + endereco + "' AND " + colComodoCampo + " IS '" + comodo + "'",null);
	   db.delete(tabelaComodos, colComodoEndereco + " IS '" + endereco + "' AND " + colComodoNome + " IS '" + comodo + "'",null);
	   db.close();
	  }
	public void deleteCampo(String campo, String comodo, String endereco)
	  {
	   SQLiteDatabase db=this.getWritableDatabase();
	   int d = db.delete(tabelaCampos, colImovelEnd + " IS '" + endereco + "' AND " + colComodoCampo +
			   " IS '" + comodo + "'" + " AND " + colCampo + " IS '" + campo + "'", null);
	   Log.d("DATABASE", "apaguei " + d + "registros");
	   Log.d("DATABASE", "requested: " + colImovelEnd + " IS '" + endereco + "' AND " + colComodoCampo +
			   " IS '" + comodo + "'" + " AND " + colCampo + " IS '" + campo + "'");
	   db.close();
	  }
	public void exportaDB()
	{
		try {
	        File sd = Environment.getExternalStorageDirectory();
	        File data = Environment.getDataDirectory();

	        if (sd.canWrite()) {
	            String currentDBPath = "//data//com.example.visimob//databases//" + dbName;
	            String backupDBPath = "//visimob//" + dbName + ".db";
	            File currentDB = new File(data, currentDBPath);
	            File backupDB = new File(sd, backupDBPath);
//                Log.d("DATABASE", "NEXISTE" + backupDB.getAbsolutePath() + "; " + currentDB.getAbsolutePath());
	            if (currentDB.exists()) {
	                @SuppressWarnings("resource")
	                FileChannel src = new FileInputStream(currentDB).getChannel();
	                @SuppressWarnings("resource")
	                FileChannel dst = new FileOutputStream(backupDB).getChannel();
	                dst.transferFrom(src, 0, src.size());
	                src.close();
	                dst.close();
	                Log.d("DATABASE", "SALVO" + backupDB.getAbsolutePath());
	            }
	        }
	    } catch (Exception e) {
	    	Log.d("DATABASE", "ERRO AO SALVAR");
	    }
	}
	public void importaDB()
	{
		try {
	        File sd = Environment.getExternalStorageDirectory();
	        File data = Environment.getDataDirectory();

	        if (sd.canWrite()) {
	            String currentDBPath = "//data//com.example.visimob//databases//" + dbName;
	            String backupDBPath = "visimob//" + dbName + ".db";
	            File currentDB = new File(data, currentDBPath);
	            File backupDB = new File(sd, backupDBPath);
//                Log.d("DATABASE", "NEXISTE" + backupDB.getAbsolutePath() + "; " + currentDB.getAbsolutePath());
	            if (currentDB.exists()) {
	                @SuppressWarnings("resource")
					FileChannel dst = new FileInputStream(currentDB).getChannel();
	                @SuppressWarnings("resource")
	                FileOutputStream fileOutputStream = new FileOutputStream(backupDB);
					FileChannel src = fileOutputStream.getChannel();
	                dst.transferFrom(src, 0, src.size());
	                src.close();
	                dst.close();
	                Log.d("DATABASE", "IMPORTADO" + backupDB.getAbsolutePath());
	            }
	        }
	    } catch (Exception e) {
	    	Log.d("DATABASE", "IMPORT_ERRO");
	    }
	}
	public void limpaLista()
	{
		SQLiteDatabase db=this.getWritableDatabase();
	    Cursor cursor = db.query(tabelaImoveis, new String[] { colID },
	        colEndereco + " IS NULL OR " + colEndereco + " IS ''", null, null, null, null);
	      if (cursor.moveToFirst()) {
	         do {
	        	 deleteImovelSemCampos(cursor.getInt(0));
	         } while (cursor.moveToNext());
	      }
	      if (cursor != null && !cursor.isClosed()) {
	         cursor.close();
	      }
	}
	public Imovel getUltimoImovel()
	{
		SQLiteDatabase db=this.getWritableDatabase();
	    Cursor cursor = db.query(tabelaImoveis, new String[] { colID },
	        null, null, null, null, colID + " DESC");
	      if (cursor.moveToFirst()) {
	         do {
	        	 return this.getImovel(cursor.getInt(0));
	         } while (cursor.moveToNext());
	      }
	      if (cursor != null && !cursor.isClosed()) {
	         cursor.close();
	         Log.d("DATABASE", "Não consegui achar");
	      }
	    return null;
	}
	public int getUltimoImovelIndice()
	{
		SQLiteDatabase db=this.getWritableDatabase();
	    Cursor cursor = db.query(tabelaImoveis, new String[] { colID },
	    null, null, null, null, colID + " DESC");
	    cursor.moveToFirst();
	    return cursor.getInt(0);
	}
	public Imovel getImovel(int id)
	{
		Imovel imv = new Imovel();
		SQLiteDatabase db=this.getWritableDatabase();
		Cursor cursor = db.query(tabelaImoveis, new String[] { colEndereco, colLocador,
				colLocatario, colBairro	, colTipo, colData, colImagem1, colImagem2},
		        colID + " IS " + id, null, null, null, null);
		cursor.moveToFirst();
		imv.endereco = cursor.getString(0);
		imv.locador = cursor.getString(1);
		imv.locatario = cursor.getString(2);
		imv.bairro = cursor.getString(3);
		imv.tipo = cursor.getString(4);
		imv.data = cursor.getString(5);
		imv.imagem1 = cursor.getString(6);
		imv.imagem2 = cursor.getString(7);	
		cursor.close();	
		db.close();
		return imv;
	}
	public Imovel getImovel(String endereco)
	{
		Log.d("DATABASE", "pedido de imóvel efetuado");
		SQLiteDatabase db=this.getWritableDatabase();
		Cursor cursor = db.query(tabelaImoveis, new String[] { colID },
		        "colEnd" + " IS " + endereco, null, null, null, null);
		cursor.moveToFirst();
		if (cursor.getString(0)==null)
			{
				db.close();
				return null;
			}
		int val = Integer.parseInt(cursor.getString(0));
		db.close();
		return getImovel(val);
	}
	public int getImovelID(String endereco)
	{
		Log.d("DATABASE", "pedido de imóvel efetuado");
		SQLiteDatabase db=this.getWritableDatabase();
		Cursor cursor = db.query(tabelaImoveis, new String[] { colID },
		        "colEnd" + " IS " + endereco, null, null, null, null);
		cursor.moveToFirst();
		if (cursor.getString(0)==null)
			return -1;
		int val = Integer.parseInt(cursor.getString(0));
		db.close();
		return val;
	}
	public void atualizaImovel(Imovel imv, int pos)
	{
		SQLiteDatabase db=this.getWritableDatabase();
		Log.d("DATABASE", "indice: " + pos);
		ContentValues cv = new ContentValues();
		cv.put(colEndereco, imv.endereco);
		cv.put(colLocador, imv.locador);
		cv.put(colLocatario, imv.locatario);
		cv.put(colBairro, imv.bairro);
		cv.put(colTipo, imv.tipo);
		cv.put(colData, imv.data);
		cv.put(colImagem1, imv.imagem1);
		cv.put(colImagem2, imv.imagem2);
		Integer p1 = pos;
		Integer u = db.update(tabelaImoveis, cv, colID + " =?", new String[]{ p1.toString() });
		Log.d("DATABASE", u.toString());
		db.close();
	}
	public void atualizaCampo(String endereco, String comodo, Campo c)
	{
		Log.d("DATABASE", "tentando achar um campo com nome " + c.nomeCampo + " no comodo " + comodo + " e endereco " + endereco);
		Log.d("DATABASE", "substituindo....");
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(colCampo, c.nomeCampo);
		cv.put(colImovelEnd, endereco);
		cv.put(colComodoCampo, comodo);
		cv.put(colEstado, c.estado);
		cv.put(colOBS, c.estadoOBS);
		cv.put(colCampoImagem1, c.imagem1);
		cv.put(colCampoImagem2, c.imagem2);
		Log.d("DATABASE", "montando o update....");
		Integer u = db.update(tabelaCampos, cv, colCampo + " IS '" + c.nomeCampo + "' AND " + colImovelEnd + " IS '" + endereco + "'", null);
		Log.d("DATABASE", "achei: " + u.toString() + " para modificar");
		db.close();
	}
	public void atualizaImovel(Imovel imv)
	{
		SQLiteDatabase db=this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(colEndereco, imv.endereco);
		cv.put(colLocador, imv.locador);
		cv.put(colLocatario, imv.locatario);
		cv.put(colBairro, imv.bairro);
		cv.put(colTipo, imv.tipo);
		cv.put(colData, imv.data);
		cv.put(colImagem1, imv.imagem1);
		cv.put(colImagem2, imv.imagem2);
		db.update(tabelaImoveis, cv, colEndereco + " =?", new String[]{ imv.endereco});
		db.close();
	}
	public List<String>  getAllImoveis()
	{
	    List<String> result = new ArrayList<String>();
		SQLiteDatabase db=this.getWritableDatabase();
	    Cursor cursor = db.query(tabelaImoveis, new String[] { colEndereco },
	        colEndereco + " IS NOT NULL", null, null, null, colEndereco + " ASC");
	      if (cursor.moveToFirst()) {
	         do {
	        	 result.add(cursor.getString(0));
	         } while (cursor.moveToNext());
	      }
	      if (cursor != null && !cursor.isClosed()) {
	         cursor.close();
	      }
	      return result;
	}
	public List<String>  getAllCampos(String end, String com)
	{
	    List<String> result = new ArrayList<String>();
		SQLiteDatabase db=this.getWritableDatabase();
		Log.d("DATABASE", "tentarei achar um campo no endereco " + end + " e no comodo " + com);
	    Cursor cursor = db.query(tabelaCampos, new String[] { colCampo },
	        colImovelEnd + " IS '" + end + "' AND " + colComodoCampo + " IS '" + com + "'", null, null, null, colCampo + " ASC");
	      if (cursor.moveToFirst()) {
	         do {
	        	 result.add(cursor.getString(0));
	         } while (cursor.moveToNext());
	      }
	      if (cursor != null && !cursor.isClosed()) {
	         cursor.close();
	      }
	      return result;
	}
	public List<String>  getAllComodos(String endereco)
{
    List<String> result = new ArrayList<String>();
	SQLiteDatabase db=this.getWritableDatabase();
	Log.d("DATABASE","tentando achar");
	try{
    Cursor cursor = db.query(tabelaComodos, new String[] { colComodoNome },
        colComodoEndereco + " IS '" + endereco + "'", null, null, null, colComodoNome + " ASC");
    Log.d("DATABASE","tabela montada");
      if (cursor.moveToFirst()) {
         do {
        	 if(result.contains(cursor.getString(0)))
        	 {
        		 
        	 }
        	 else
        		 result.add(cursor.getString(0));
         } while (cursor.moveToNext());
      }
      if (cursor != null && !cursor.isClosed()) {
         cursor.close();
      }
	}catch(Exception e)
	{
		Log.d("DATABASE ERROR", e.getLocalizedMessage() + e.getMessage() + e.getCause());
	}
      return result;
}
}