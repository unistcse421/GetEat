package com.unist.am.lineup;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import net.daum.mf.map.api.CameraUpdateFactory;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.util.ArrayList;

/**
 * Created by 정현짱월드 on 2015-10-29.
 */
public class MapActivity_all_reslist extends Activity implements MapView.POIItemEventListener {

    /* private final Context mContext; */

 //   FrameLayout information;


    Location location;
    double lat;
    double lon;
    Intent get_flag_intent;
    Context mcontext;

    protected LocationManager locationManager;
    ArrayList<ResListItem> Items;
    String nickname;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_rest_list);
        Intent mintent = getIntent();
        try {
            Items = new ArrayList<ResListItem>((ArrayList<ResListItem>) mintent.getExtras().get("items"));
            nickname = mintent.getExtras().getString("username");
        }
        catch(Exception e){
            Log.e("Main_Map","Intent : " + e.toString());
        }

        Log.e("Main_Map",String.valueOf(Items.size()));


        try {

            final MapView mapView = new MapView(this);
            mapView.setDaumMapApiKey("c139894fdfab4e242e1789b34b7fd34c");
            ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view_restlist);
            mapViewContainer.addView(mapView);
            MapView.setMapTilePersistentCacheEnabled(true);

            mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);
            mapView.setHDMapTileEnabled(true);
            mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(35.5734738, 129.1896375), true);
            //mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(Items.get(0).res_x_coordinate, Items.get(0).res_y_coordinate), true);
            mapView.setZoomLevel(1, true);
            mapView.setShowCurrentLocationMarker(true);
            mapView.setPOIItemEventListener(this);

            addMarker(mapView);
        }
        catch (Exception e){
            Log.e("Main_Map","mapView settings :  " + e.toString());
        }


    }

    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem poiItem) {


        Log.d("MAP", "CLICKED");
        MapPoint location = null;
        try{

            location = poiItem.getMapPoint();
            Log.d("LOC", " " + location);

            for (int i = 0; i < Items.size(); i++) {
                if (poiItem.getItemName() == Items.get(i).res_name) {
                    Intent intent = new Intent(mcontext, RestaurantInfo.class);
                    intent.putExtra("name", Items.get(i).res_name);
                    intent.putExtra("cuisine", Items.get(i).res_cuisine);
                    intent.putExtra("timing", Items.get(i).res_timing);
                    intent.putExtra("img_large", Items.get(i).res_imgurl);
                    intent.putExtra("location", Items.get(i).res_location);
                    intent.putExtra("phone_num", Items.get(i).res_phone_num);
                    intent.putExtra("x_coordinate", Items.get(i).res_x_coordinate);
                    intent.putExtra("y_coordinate", Items.get(i).res_y_coordinate);
                    intent.putExtra("username", nickname);
                    intent.putExtra("dummy_name", Items.get(i).res_dummyname);

                    startActivity(intent);
                    break;
                }

            }
        }
        catch(Exception e){
            Log.e("Main_Map", "PollItemSelectedListen : " + e.toString());
        }
        mapView.moveCamera(CameraUpdateFactory.newMapPoint(location));
  //      information.setVisibility(View.VISIBLE);
    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {

    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {

    }

    public void addMarker(MapView mapView) {

        try {

            for (int i = 0; i < 1; i++) {
                MapPOIItem marker = new MapPOIItem();
                marker.setShowCalloutBalloonOnTouch(true);
                marker.setShowDisclosureButtonOnCalloutBalloon(false);
                marker.setItemName(getString(R.string.Halmae));
                marker.setTag(0);
                marker.setMapPoint(MapPoint.mapPointWithGeoCoord(35.5766918,129.1907242));
                marker.setMarkerType(MapPOIItem.MarkerType.RedPin);
                marker.setSelectedMarkerType(MapPOIItem.MarkerType.BluePin);


                mapView.addPOIItem(marker);
            }
        }
        catch(Exception e){
            Log.e("Main_Map", "AddMarker : " + e.toString());
        }
    }

}


