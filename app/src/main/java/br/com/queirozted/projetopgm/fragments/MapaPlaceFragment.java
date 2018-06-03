package br.com.queirozted.projetopgm.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.queirozted.projetopgm.R;
import br.com.queirozted.projetopgm.basicas.OnPlaceClick;
import br.com.queirozted.projetopgm.basicas.Place;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapaPlaceFragment extends Fragment  implements OnMapReadyCallback {

    private Place mPlace;
    private LatLng mLatlgn;
    private GoogleMap mMap;
    FloatingActionButton mFab;

    // metodo estatico implementa um contrutor com parametro obj place
    public static MapaPlaceFragment novaInstancia(Place place){
        Bundle  bundle = new Bundle();
        bundle.putSerializable("lugar_escolhido",place);

        MapaPlaceFragment frag = new MapaPlaceFragment();
        frag.setArguments(bundle);
        return frag;
    }

    public MapaPlaceFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mapa_place, container, false);

        mPlace = (Place) getArguments().getSerializable("lugar_escolhido");

        SupportMapFragment frag = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.fram_mapa_place);

        frag.getMapAsync(this);

        mFab = (FloatingActionButton) view.findViewById(R.id.bt_ret_mapa);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() instanceof OnPlaceClick){
                    ((OnPlaceClick)getActivity()).OnPlaceClick(mPlace);
                }
            }
        });

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (googleMap != null) {
            atualizar(googleMap);
        }
    }

    private void atualizar(GoogleMap googleMap){
        mMap = googleMap;

        BitmapDescriptor  icon = BitmapDescriptorFactory.fromResource(R.mipmap.ic_location);
        mLatlgn = new LatLng(mPlace.getGeometry().getLocation_lat(),mPlace.getGeometry().getLocation_lng());

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.addMarker(new MarkerOptions()
                .position(mLatlgn)
                .title(mPlace.getName())
                .icon(icon)
                .snippet(mPlace.getFormatted_address()));

        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.getUiSettings().setZoomControlsEnabled(true);

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(mLatlgn)
                .zoom(17)
                .bearing(90)
                .tilt(45)
                .build();

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
}
