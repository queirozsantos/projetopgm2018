package br.com.queirozted.projetopgm.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import br.com.queirozted.projetopgm.R;
import br.com.queirozted.projetopgm.basicas.Constantes;
import br.com.queirozted.projetopgm.basicas.MsgEventAtualizar;
import br.com.queirozted.projetopgm.basicas.OnDetalheClick;
import br.com.queirozted.projetopgm.basicas.OnPlaceClick;
import br.com.queirozted.projetopgm.basicas.Place;
import br.com.queirozted.projetopgm.database.DaoPlace;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

public class DetalhePlaceFragment extends Fragment {

    Place mPlace;
    RatingBar mRating;
    ImageView mIcone;
    TextView mText;
    FloatingActionButton mFloat;
    FloatingActionButton mFloatMapa;
    DaoPlace mDao;

    // metodo estatico implementa um contrutor com parametro obj place
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


        mFloat = (FloatingActionButton) view.findViewById(R.id.bt_favoritos);
        mFloatMapa = (FloatingActionButton) view.findViewById(R.id.bt_mapa);
        mText = (TextView) view.findViewById(R.id.txt_endereco);
        mRating = (RatingBar) view.findViewById(R.id.rating_placer);
        mIcone = (ImageView) view.findViewById(R.id.img_icone);

        mPlace = (Place) getArguments().getSerializable("lugar_escolhido");

        mText.setText(mPlace.getFormatted_address());
        mRating.setRating(mPlace.getRating());
        try {
            Picasso.with(getContext())
                    .load(mPlace.getIcon().toString())
                    .into(mIcone);
        }catch (Exception e){
            e.printStackTrace();
        }

        if (getResources().getBoolean(R.bool.isphone)){
            Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
            ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
            ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(mPlace.getName());
        }else{
            TextView mTextNome = (TextView)view.findViewById(R.id.name_place);
            mTextNome.setText(mPlace.getName());
        }

        ImageView imageView = (ImageView) view.findViewById(R.id.img_lugar);

        if (mPlace.getPhoto() != null){
            try {
                Picasso.with(getActivity())
                        .load(String.format(Constantes.URL_PHOTO, mPlace.getPhoto().getPhoto_reference().toString(), "200", "200", Constantes.KEY_PESQUISA))
                        .into(imageView);
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            imageView.setImageResource(R.mipmap.ic_imgfalta);
        }

        mDao = new DaoPlace(getContext());
        if (mDao.Localizar(mPlace.getPlace_id())){
            imagemFloat (true);
        }else{
            imagemFloat (false);
        }

        mFloat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mDao.Localizar(mPlace.getPlace_id())){
                    mDao.Deletar(mPlace.getPlace_id());
                    Toast.makeText(getActivity(),getResources().getString(R.string.loc_remove),Toast.LENGTH_SHORT).show();
                    imagemFloat (false);
                }else{
                    mDao.Inserir(mPlace);
                    Toast.makeText(getActivity(),getResources().getString(R.string.loc_add),Toast.LENGTH_SHORT).show();
                    imagemFloat (true);
                }

                EventBus.getDefault().post(new MsgEventAtualizar(" "));
            }
        });


        mFloatMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getResources().getBoolean(R.bool.isphone)) {
                    if (getActivity() instanceof OnPlaceClick) {
                        ((OnPlaceClick) getActivity()).OnPlaceClick(mPlace);
                    }
                }else {
                    if (getActivity() instanceof OnDetalheClick) {
                        ((OnDetalheClick) getActivity()).OnDetalheClick(mPlace);
                    }
                }
            }
        });

        return view;
    }

    private void imagemFloat(boolean status){
        if (status) {
            mFloat.setImageResource(R.mipmap.ic_favgrav);
        }else{
            mFloat.setImageResource(R.mipmap.ic_favnograv);
        }
    }

}


