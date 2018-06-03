package br.com.queirozted.projetopgm.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import br.com.queirozted.projetopgm.basicas.Geometry;
import br.com.queirozted.projetopgm.basicas.Photo;
import br.com.queirozted.projetopgm.basicas.Place;

import java.util.ArrayList;
import java.util.List;

public class DaoPlace {

    private static Context mContext;

    public DaoPlace(Context contx){
        this.mContext = contx;
    }

    public long Inserir(Place place){
        long retorno = -1;

        DbOpenHelper dbHelper= new DbOpenHelper(mContext);
        SQLiteDatabase dbOpen = dbHelper.getWritableDatabase();

        retorno=dbOpen.insert(DbContrato.TABELA_PLACE,null,RetornoCampos(place));
        dbOpen.close();

        return retorno;
    }

    public long Atualizar(Place place){
        long retorno = -1;

        DbOpenHelper dbHelper= new DbOpenHelper(mContext);
        SQLiteDatabase dbOpen = dbHelper.getWritableDatabase();

        retorno=dbOpen.update(DbContrato.TABELA_PLACE,RetornoCampos(place),DbContrato.PLACE_ID +" = ?",new String[]{place.getPlace_id()});
        dbOpen.close();

        return retorno;
    }

    public long Deletar(String place_id){
        long retorno = -1;

        DbOpenHelper dbHelper= new DbOpenHelper(mContext);
        SQLiteDatabase dbOpen = dbHelper.getWritableDatabase();

        retorno=dbOpen.delete(DbContrato.TABELA_PLACE,DbContrato.PLACE_ID +" = ?",new String[]{place_id});
        dbOpen.close();

        return retorno;
    }

    public  List<Place> Listar(){

        List<Place> lista = new ArrayList<Place>();

        DbOpenHelper dbHelper= new DbOpenHelper(mContext);
        SQLiteDatabase dbOpen = dbHelper.getWritableDatabase();

        Cursor cursor = dbOpen.rawQuery("Select * from " +DbContrato.TABELA_PLACE,null);

        while(cursor.moveToNext()){
            Place place = new Place();
            Photo photo = new Photo();
            Geometry geometry = new Geometry();

            place.setPlace_id(cursor.getString(cursor.getColumnIndex(DbContrato.PLACE_ID)));
            place.setName(cursor.getString(cursor.getColumnIndex(DbContrato.NAME)));
            place.setFormatted_address(cursor.getString(cursor.getColumnIndex(DbContrato.FORMATTED_ADDRESS)));
            place.setIcon(cursor.getString(cursor.getColumnIndex(DbContrato.ICON)));
            place.setReference(cursor.getString(cursor.getColumnIndex(DbContrato.REFERENCE)));
            place.setRating(cursor.getFloat(cursor.getColumnIndex(DbContrato.RATING)));
            place.setPrice_level(cursor.getInt(cursor.getColumnIndex(DbContrato.PRICE_LEVEL)));

            // carregar dados da foto
            photo.setPhoto_reference(cursor.getString(cursor.getColumnIndex(DbContrato.PHOTO_REFERENCE)));
            photo.setWidth(cursor.getLong(cursor.getColumnIndex(DbContrato.PHOTO_WIDTH)));
            photo.setHeight(cursor.getLong(cursor.getColumnIndex(DbContrato.PHOTO_HEIGHT)));
            place.setPhoto(photo);

            // carregar dados localização
            geometry.setLocation_lat(cursor.getFloat(cursor.getColumnIndex(DbContrato.GEOMETRY_LAT)));
            geometry.setLocation_lng(cursor.getFloat(cursor.getColumnIndex(DbContrato.GEOMETRY_LNG)));
            place.setGeometry(geometry);
            lista.add(place);
        }
        cursor.close();
        dbOpen.close();

        return lista;
    }

    public  Boolean Localizar(String place_id){
        Boolean novoRegistro;

        DbOpenHelper dbHelper= new DbOpenHelper(mContext);
        SQLiteDatabase dbOpen = dbHelper.getWritableDatabase();

        Cursor cursor = dbOpen.rawQuery("Select * from " +DbContrato.TABELA_PLACE+" where " +DbContrato.PLACE_ID +" = ? ",new String[]{place_id});

        novoRegistro = false;
        while (cursor.moveToNext()){
            novoRegistro = true;
        }
        dbOpen.close();

        return novoRegistro;
    }

    private  ContentValues RetornoCampos(Place place){
        ContentValues values = new ContentValues();
        values.put(DbContrato.PLACE_ID,place.getPlace_id());
        values.put(DbContrato.NAME,place.getName());
        values.put(DbContrato.FORMATTED_ADDRESS,place.getFormatted_address());
        values.put(DbContrato.GEOMETRY_LAT,place.getGeometry().getLocation_lat());
        values.put(DbContrato.GEOMETRY_LNG,place.getGeometry().getLocation_lng());
        values.put(DbContrato.RATING,place.getRating());
        values.put(DbContrato.ICON,place.getIcon());
        values.put(DbContrato.REFERENCE,place.getReference());
        values.put(DbContrato.PRICE_LEVEL,place.getPrice_level());
        values.put(DbContrato.PHOTO_REFERENCE,place.getPhoto().getPhoto_reference());
        values.put(DbContrato.PHOTO_HEIGHT,place.getPhoto().getHeight());
        values.put(DbContrato.PHOTO_WIDTH,place.getPhoto().getWidth());

        return  values;
    }


}
