package br.com.queirozted.projetopgm.database;

public interface DbContrato {

    String _ID="_ID";
    String PLACE_ID="place_id";
    String NAME="name";
    String FORMATTED_ADDRESS ="formatted_address";
    String GEOMETRY_LAT ="location_lat";
    String GEOMETRY_LNG ="location_lng";
    String RATING="rating";
    String ICON="icon";
    String REFERENCE="reference";
    String PRICE_LEVEL="price_level";
    String PHOTO_REFERENCE ="photo_reference";
    String PHOTO_HEIGHT ="height";
    String PHOTO_WIDTH ="width";

    String TABELA_PLACE="tab_place";
    Integer TABELA_VERSAO=1;

    StringBuilder Str_Crear_Db
            = new StringBuilder("Create table " + TABELA_PLACE +"( "
            + (_ID +" integer primary key autoincrement,")
            + ( PLACE_ID +" text not null,")
            + ( NAME +" text not null,")
            + ( FORMATTED_ADDRESS +" text null,")
            + ( GEOMETRY_LAT +" REAL,")
            + ( GEOMETRY_LNG +" REAL,")
            + ( RATING +" REAL,")
            + ( ICON +" text null,")
            + ( REFERENCE +" text null,")
            + ( PRICE_LEVEL +" REAL,")
            + ( PHOTO_REFERENCE +" text null,")
            + ( PHOTO_HEIGHT +" integer,")
            + ( PHOTO_WIDTH +" integer")
            + (");"));

}
