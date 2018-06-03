package br.com.queirozted.projetopgm.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.com.queirozted.projetopgm.R;
import br.com.queirozted.projetopgm.basicas.OnPlaceClick;
import br.com.queirozted.projetopgm.basicas.Place;
import br.com.queirozted.projetopgm.fragments.MapaPlaceFragment;

public class MapaPlaceActivity extends AppCompatActivity implements OnPlaceClick {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_place);

        Place place = (Place) getIntent().getSerializableExtra("lugar_escolhido");

        MapaPlaceFragment dtFra = MapaPlaceFragment.novaInstancia(place);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.contente_mapa_place,dtFra, "mapa_place")
                .commit();
    }

    @Override
    public void OnPlaceClick(Place place) {
        finish();
    }
}
