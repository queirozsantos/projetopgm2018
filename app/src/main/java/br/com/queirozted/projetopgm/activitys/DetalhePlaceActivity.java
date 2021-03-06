package br.com.queirozted.projetopgm.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import br.com.queirozted.projetopgm.R;
import br.com.queirozted.projetopgm.basicas.OnPlaceClick;
import br.com.queirozted.projetopgm.basicas.Place;
import br.com.queirozted.projetopgm.fragments.DetalhePlaceFragment;
import br.com.queirozted.projetopgm.fragments.MapaPlaceFragment;

public class DetalhePlaceActivity extends AppCompatActivity implements OnPlaceClick {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detalhe_place);

        Place place = (Place) getIntent().getSerializableExtra("lugar_escolhido");

        DetalhePlaceFragment dtFra = DetalhePlaceFragment.novaInstancia(place);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.contente_place,dtFra,"detalhes")
                .commit();
    }
    @Override
    public void OnPlaceClick(Place place) {
        Intent it = new Intent(this,MapaPlaceActivity.class);
        it.putExtra("lugar_escolhido", place);
        startActivity(it);
    }
}
