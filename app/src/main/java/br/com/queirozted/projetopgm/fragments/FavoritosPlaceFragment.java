package br.com.queirozted.projetopgm.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import br.com.queirozted.projetopgm.R;
import br.com.queirozted.projetopgm.basicas.MsgEventAtualizar;
import br.com.queirozted.projetopgm.basicas.OnPlaceClick;
import br.com.queirozted.projetopgm.basicas.Place;
import br.com.queirozted.projetopgm.database.DaoPlace;
import br.com.queirozted.projetopgm.rotinas.PlacesAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.util.List;

public class FavoritosPlaceFragment extends Fragment {

    DaoPlace mDao;
    ListView mList;
    List<Place> mListPlace;
    PlacesAdapter mAdapter;

    public FavoritosPlaceFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favoritos_place, container, false);

        mList = (ListView) view.findViewById(R.id.list);

        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {

                if (getActivity() instanceof OnPlaceClick){
                    Place place = (Place) mList.getItemAtPosition(position);
                    executarClick(place);
                }
            }
        });
        return view;
    }

    private void executarClick(Place place) {
        ((OnPlaceClick)getActivity()).OnPlaceClick(place);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
        mDao = new DaoPlace(getActivity());
        mListPlace = mDao.Listar();
        mAdapter = new PlacesAdapter(getActivity(),mListPlace);

        if (mAdapter !=null) {
            mList.setAdapter(mAdapter);
            if (!getResources().getBoolean(R.bool.isphone)) {
                if(mListPlace.size() >0) {
                    if (savedInstanceState == null){
                        executarClick(mListPlace.get(0));
                    }
                }
            }
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MsgEventAtualizar event) {
        mListPlace.clear();
        mListPlace.addAll(mDao.Listar());
        mAdapter.notifyDataSetChanged();
    }
}
