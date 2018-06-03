package br.com.queirozted.projetopgm.fragments;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;

import android.view.MenuInflater;
import android.view.MenuItem;

import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import android.view.Menu;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;

import br.com.queirozted.projetopgm.R;
import br.com.queirozted.projetopgm.basicas.OnPlaceClick;
import br.com.queirozted.projetopgm.rotinas.BuscaTaskLoader;
import br.com.queirozted.projetopgm.basicas.Place;
import br.com.queirozted.projetopgm.rotinas.PlacesAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListPlaceFragment extends ListFragment implements SearchView.OnQueryTextListener, LoaderManager.LoaderCallbacks<List<Place>> {

    private final int LOADER_ID=1;
    private LoaderManager mLoaderManager;
    private PlacesAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        List<Place> data = new ArrayList<Place>();
        mAdapter = new PlacesAdapter(getActivity(),data);
        setListAdapter(mAdapter);

        setListShown(true);

        mLoaderManager = getLoaderManager();
        mLoaderManager.initLoader(LOADER_ID,null,this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_pesquisa, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);
        searchView.setQuery("",false);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Bundle bundle = new Bundle();
        bundle.putString("textoPesquisa",query);
        setListShown(false);

        mLoaderManager.restartLoader(LOADER_ID,bundle,this);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public Loader<List<Place>> onCreateLoader(int id, Bundle args) {
        String pesquisa = null;

        if (args != null) {
            pesquisa = args.getString("textoPesquisa");
        }
        return new BuscaTaskLoader(getActivity(),pesquisa);
    }

    @Override
    public void onLoadFinished(Loader<List<Place>> loader, List<Place> data) {
        int registros = 0;
        if (data != null){
            //Log.i("RAST","places validas");
            setListAdapter(new PlacesAdapter(getActivity(), data));
            registros = data.size();
        }
        setListShown(true);
        if (registros==0){
            Toast.makeText(getActivity(), getResources().getString(R.string.msg_noloc), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Place>> loader) {

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        if (getActivity() instanceof OnPlaceClick){
            Place place = (Place) l.getItemAtPosition(position);
            ((OnPlaceClick)getActivity()).OnPlaceClick(place);
        }
    }
}
