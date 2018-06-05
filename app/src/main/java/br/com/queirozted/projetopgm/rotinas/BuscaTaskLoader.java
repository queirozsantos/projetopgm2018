package br.com.queirozted.projetopgm.rotinas;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import br.com.queirozted.projetopgm.basicas.Place;

import java.io.IOException;
import java.util.List;


public class BuscaTaskLoader extends AsyncTaskLoader<List<Place>>{

    private String mPesquisa;
    private List<Place> mPlaces;

    public BuscaTaskLoader(Context context,String pesquisa) {
        super(context);
        this.mPesquisa = pesquisa;
    }

    @Override
    protected void onStartLoading() {
        if (mPesquisa == null) return;

        if (mPlaces == null){
            forceLoad();
        }else{
            deliverResult(mPlaces);
        }
    }

    @Override
    public List<Place> loadInBackground() {
        try {
            mPlaces = PesquisarPlace.pesquisaPlaces(this.mPesquisa);
            return mPlaces;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
