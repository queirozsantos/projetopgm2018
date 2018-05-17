package br.com.queirozted.projetopgm.activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import br.com.queirozted.projetopgm.R;
import br.com.queirozted.projetopgm.basicas.Place;
import br.com.queirozted.projetopgm.fragments.DetalhePlaceFragment;

public class DetalhePlaceActivity extends AppCompatActivity {

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
}
