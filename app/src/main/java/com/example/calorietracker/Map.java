package com.example.calorietracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class Map extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    SharedPreferences myhealth;
    String getddr;
    View vmap;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vmap = inflater.inflate(R.layout.activity_map, container, false);
        myhealth = this.getActivity().getSharedPreferences("fname", Context.MODE_PRIVATE);
        getddr = myhealth.getString("Addr",null);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return vmap;
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;





        // Add a marker in Sydney and move the camera
        mMap.addMarker(new MarkerOptions().position(getAddress(getddr)).title("your location"));
        mMap.addMarker(new MarkerOptions().position(getPlace(getddr)).title("park"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(getAddress(getddr)));


    }
    public LatLng getAddress(String straddress){
        Geocoder geocoder = new Geocoder(getActivity());
        List<Address> address;
        LatLng ll = null;
        try{
            address = geocoder.getFromLocationName(straddress,5);
            if (address == null){
                return null;
            }
            Address locatin = address.get(0);
            ll = new LatLng(locatin.getLatitude(),locatin.getLongitude());
        }catch (IOException e){
            e.printStackTrace();
        }
        return ll;
    }

    public LatLng getPlace(String straddress){
        Geocoder geocoder = new Geocoder(getActivity());
        List<Address> address;
        LatLng ll = null;
        try{
            address = geocoder.getFromLocationName("park" + straddress,5);
            if (address == null){
                return null;
            }
            for (int i=0; i <address.size();i++){
            Address locatin = address.get(i);
            ll = new LatLng(locatin.getLatitude(),locatin.getLongitude());
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return ll;
    }



}



