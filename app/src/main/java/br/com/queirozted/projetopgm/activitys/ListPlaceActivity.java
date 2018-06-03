package br.com.queirozted.projetopgm.activitys;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import br.com.queirozted.projetopgm.R;
import br.com.queirozted.projetopgm.basicas.OnDetalheClick;
import br.com.queirozted.projetopgm.basicas.OnPlaceClick;
import br.com.queirozted.projetopgm.basicas.Place;
import br.com.queirozted.projetopgm.fragments.DetalhePlaceFragment;
import br.com.queirozted.projetopgm.fragments.FavoritosPlaceFragment;
import br.com.queirozted.projetopgm.fragments.ListPlaceFragment;
import br.com.queirozted.projetopgm.fragments.MapaPlaceFragment;

import java.nio.channels.Selector;

public class ListPlaceActivity extends AppCompatActivity implements OnPlaceClick ,OnDetalheClick{

    Toolbar mToolbar;
    ListPlaceFragment mListPlaceFragment;
    FavoritosPlaceFragment mFavoritosPlaceFragment;
    ViewPager mViewPager;
    SelectorPageAdapter mSelectorPageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        BuildViewPager();
    }

    private void BuildViewPager(){

        mViewPager = (ViewPager) findViewById(R.id.container);
        mSelectorPageAdapter = new SelectorPageAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSelectorPageAdapter);

        TabLayout tab = (TabLayout)findViewById(R.id.tabs);
        tab.setupWithViewPager(mViewPager);
    }

    @Override
    public void OnPlaceClick(Place place) {
        if (getResources().getBoolean(R.bool.isphone)) {
            Intent it = new Intent(this, DetalhePlaceActivity.class);
            it.putExtra("lugar_escolhido", place);
            startActivity(it);
        }else{
            // tablet
            DetalhePlaceFragment dtFra = DetalhePlaceFragment.novaInstancia(place);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.contente_place,dtFra,"detalhes")
                    .commit();
        }
    }

    @Override
    public void OnDetalheClick(Place place) {
        if (!getResources().getBoolean(R.bool.isphone)) {
            // tablet
            MapaPlaceFragment mapaFrag = MapaPlaceFragment.novaInstancia(place);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.contente_place,mapaFrag,"mapa_place")
                    .commit();
        }
    }

    private class SelectorPageAdapter extends FragmentPagerAdapter{

        public SelectorPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    if(mFavoritosPlaceFragment == null){
                        mFavoritosPlaceFragment = new FavoritosPlaceFragment();
                    }
                    return mFavoritosPlaceFragment;
                case 1:
                default:
                    if (mListPlaceFragment ==null){
                        mListPlaceFragment = new ListPlaceFragment();
                    }
                    return mListPlaceFragment;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:
                    return getResources().getString(R.string.aba_1);
                case 1:
                default:
                    return getResources().getString(R.string.aba_2);
            }
        }
    }
}
