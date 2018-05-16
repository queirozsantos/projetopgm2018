package br.com.queirozted.projetopgm.rotinas;

import android.util.JsonReader;

import br.com.queirozted.projetopgm.basicas.Constantes;
import br.com.queirozted.projetopgm.basicas.Geometry;
import br.com.queirozted.projetopgm.basicas.Photo;
import br.com.queirozted.projetopgm.basicas.Place;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;



public class PesquisarPlace {

    public static List<Place> pesquisaPlaces(String textoPesq) throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(String.format(Constantes.URL_PESQUISA, textoPesq, Constantes.KEY_PESQUISA))
                .build();
        Response response = client.newCall(request).execute();
        if (response.networkResponse().code() == HttpURLConnection.HTTP_OK) {
            String jsonData = response.body().string();

            JSONObject userObject;
            try {
                userObject = new JSONObject(jsonData);
                return covertListComunicado(userObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static List<Place> covertListComunicado(JSONObject json) {
        List <Place> palces = new ArrayList<Place>();

        if (json !=null) {
            try {
                JSONArray it = json.optJSONArray("results");
                for (int i = 0; i < it.length(); i++) {
                    Place mPlace = new Place();
                    Geometry geo = new Geometry();
                    Photo photo = new Photo();

                    JSONObject item = it.getJSONObject(i);

                    if (item != null) {
                        mPlace.setName(item.getString("name"));
                        mPlace.setFormatted_address(item.getString("formatted_address"));
                        mPlace.setIcon(item.getString("icon"));

                        try{
                            mPlace.setRating(Float.parseFloat(item.getString("rating")));
                        }catch (JSONException e){
                            mPlace.setRating(0f);
                        }

                        mPlace.setId(item.getString("id"));
                        mPlace.setPlace_id(item.getString("place_id"));

                        try{
                            mPlace.setPrice_level(item.getInt("price_level"));
                        }catch (JSONException e){
                            mPlace.setPrice_level(0);
                        }

                        mPlace.setReference(item.getString("reference"));

                        //recuperando localizacao
                        JSONObject itemGeo = item.getJSONObject("geometry");
                        if (itemGeo != null) {
                            JSONObject itemloc = itemGeo.getJSONObject("location");
                            if (itemloc != null) {
                                geo.setLocation_lat(itemloc.getLong("lat"));
                                geo.setLocation_lng(itemloc.getLong("lng"));
                                mPlace.setGeometry(geo);
                            }
                        }
                        //recuperando informacoes da foto
                        JSONArray itemphotos = item.optJSONArray("photos");
                        if (itemphotos != null) {
                            if (itemphotos.length() > 0) {
                                JSONObject itempho = itemphotos.getJSONObject(0);
                                if (itempho != null) {
                                    photo.setHeight(itempho.getLong("height"));
                                    photo.setWidth(itempho.getLong("width"));
                                    photo.setPhoto_reference(itempho.getString("photo_reference"));
                                    mPlace.setPhoto(photo);
                                }
                            }
                        }
                        palces.add(mPlace);
                    }
                }
                return palces;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }
}
