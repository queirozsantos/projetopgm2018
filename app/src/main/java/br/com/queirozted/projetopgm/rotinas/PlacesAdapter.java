package br.com.queirozted.projetopgm.rotinas;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import br.com.queirozted.projetopgm.R;
import br.com.queirozted.projetopgm.basicas.Constantes;
import br.com.queirozted.projetopgm.basicas.Place;
import com.squareup.picasso.Picasso;

import java.util.List;



public class PlacesAdapter extends ArrayAdapter<Place> {

    public PlacesAdapter(Context context, List<Place> places) {
        super(context,0,places);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Place place = getItem(position);

        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_item_list, parent, false);

            vh = new ViewHolder();
            vh.mTextNome = (TextView) convertView.findViewById(R.id.name_place);
            vh.mRating = (RatingBar) convertView.findViewById(R.id.rating_placer);
            vh.mImagem = (ImageView) convertView.findViewById(R.id.img_place);
            vh.mIcone = (ImageView) convertView.findViewById(R.id.img_icone);

            convertView.setTag(vh);
            //Log.i("NGVL", "Novo Contexto - " + position);
        } else {
            vh = (ViewHolder) convertView.getTag();
            //Log.i("NGVL", "Reutilizado Contexto - " + position);
        }
        vh.mTextNome.setText(place.getName());
        vh.mRating.setRating(place.getRating());

        try {
            Picasso.with(getContext())
                    .load(place.getIcon().toString())
                    .into(vh.mIcone);
        }catch (Exception e){
            e.printStackTrace();
        }

        if (place.getPhoto() != null){
            try {
                Picasso.with(getContext())
                        .load(String.format(Constantes.URL_PHOTO, place.getPhoto().getPhoto_reference().toString(), "200", "200", Constantes.KEY_PESQUISA))
                        .into(vh.mImagem);
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            vh.mImagem.setImageResource(R.mipmap.ic_imgfalta);
        }

        return convertView;
    }

    static class ViewHolder{
        TextView mTextNome;
        RatingBar mRating;
        ImageView mImagem;
        ImageView mIcone;
    }
}
