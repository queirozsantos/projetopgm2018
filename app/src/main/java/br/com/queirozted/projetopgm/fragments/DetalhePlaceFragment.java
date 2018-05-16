package br.com.queirozted.projetopgm.fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.queirozted.projetopgm.R;
import br.com.queirozted.projetopgm.basicas.Constantes;
import br.com.queirozted.projetopgm.basicas.Place;
import com.squareup.picasso.Picasso;

public class DetalhePlaceFragment extends Fragment {

    Place mplace;

    // metodo estatico implementa um contrutor com parametro
    // de place
    public static DetalhePlaceFragment novaInstancia(Place place){
        Bundle  bundle = new Bundle();
        bundle.putSerializable("lugar_escolhido",place);

        DetalhePlaceFragment dtFrag = new DetalhePlaceFragment();
        dtFrag.setArguments(bundle);
        return dtFrag;
    }

    public DetalhePlaceFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detalhe_place, container, false);

        mplace = (Place) getArguments().getSerializable("lugar_escolhido");

        TextView text = (TextView) view.findViewById(R.id.texto);
        text.setText(mplace.getFormatted_address());

        if (getResources().getBoolean(R.bool.isphone)){
            Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
            ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
            ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(mplace.getName());
        }

        ImageView imageView = (ImageView) view.findViewById(R.id.img_lugar);

        if (mplace.getPhoto() != null){
            try {
                Picasso.with(getActivity())
                        .load(String.format(Constantes.URL_PHOTO, mplace.getPhoto().getPhoto_reference().toString(), "200", "200", Constantes.KEY_PESQUISA))
                        .into(imageView);
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            imageView.setImageResource(R.mipmap.ic_imgfalta);
        }

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO adicionar aos favoritos
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        return view;
    }

}
