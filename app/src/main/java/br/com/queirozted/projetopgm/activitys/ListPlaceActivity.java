package br.com.queirozted.projetopgm.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import br.com.queirozted.projetopgm.R;
import br.com.queirozted.projetopgm.basicas.OnPlaceClick;
import br.com.queirozted.projetopgm.basicas.Place;
import br.com.queirozted.projetopgm.fragments.DetalhePlaceFragment;



public class ListPlaceActivity extends AppCompatActivity implements OnPlaceClick {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);
    }

    @Override
    public void OnPlaceClick(Place place) {
        if (getResources().getBoolean(R.bool.isphone)) {
            Intent it = new Intent(this, DetalhePlaceActivity.class);
            it.putExtra("lugar_escolhido", place);
            startActivity(it);
        }else{
            DetalhePlaceFragment dtFra = DetalhePlaceFragment.novaInstancia(place);

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.contente_place,dtFra,"detalhes")
                    .commit();

        }
    }
}
