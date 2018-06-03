package br.com.queirozted.projetopgm.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class DbOpenHelper extends SQLiteOpenHelper{

    public DbOpenHelper(Context context) {
        super(context, DbContrato.TABELA_PLACE, null, DbContrato.TABELA_VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DbContrato.Str_Crear_Db.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase,  int i,  int i1) {

    }
}
