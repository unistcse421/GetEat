package com.unist.am.lineup;

import android.app.Activity;
import android.app.AlertDialog;
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
import android.widget.FrameLayout;
import android.widget.Toast;

import com.unist.am.lineup.R;

import net.daum.mf.map.api.CameraUpdateFactory;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

/**
 * Created by 정현짱월드 on 2015-10-29.
 */
public class MapActivity extends Activity implements LocationListener, MapView.POIItemEventListener {

    /* private final Context mContext; */

    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;
    boolean isGetLocation = false;
 //   FrameLayout information;


    Location location;
    double lat;
    double lon;
    Intent get_flag_intent;
    Context mContext;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;
    protected LocationManager locationManager;
/*
    public MapActivity(Context context){
        this.mContext = context;
    }
*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        get_flag_intent = getIntent();


     //   information = (FrameLayout) findViewById(R.id.information);

        try {


            if (get_flag_intent.getExtras().getBoolean("flag")) {
                lat = get_flag_intent.getExtras().getDouble("lat");
                lon = get_flag_intent.getExtras().getDouble("lon");
                Log.e("Location1",String.valueOf(lat) + "\t" + String.valueOf(lon));
            } else {
                getLocation();
            }
        }
        catch (Exception e){
            Log.e("Map Error",e.toString());
        }

        final MapView mapView = new MapView(this);
        mapView.setDaumMapApiKey("c139894fdfab4e242e1789b34b7fd34c");
        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);
        MapView.setMapTilePersistentCacheEnabled(true);

        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);
        mapView.setHDMapTileEnabled(true);
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(lat, lon), true);
        mapView.setZoomLevel(2, true);
        mapView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
     //           information.setVisibility(View.GONE);
                return false;
            }
        });
        mapView.setShowCurrentLocationMarker(true);
        mapView.setPOIItemEventListener(this);
  //      Toast.makeText(getApplicationContext(), "위도: " + lat + "경도: " + lon, Toast.LENGTH_LONG).show();
        addMarker(mapView);
 //       information.bringToFront();


    }

    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem poiItem) {


        Log.d("MAP", "CLICKED");
        MapPoint location;
        location = poiItem.getMapPoint();
        Log.d("LOC", " " + location);
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
        MapPOIItem marker = new MapPOIItem();
        marker.setShowCalloutBalloonOnTouch(true);
        marker.setShowDisclosureButtonOnCalloutBalloon(false);
        marker.setItemName("레스토랑");
        marker.setTag(0);
        marker.setMapPoint(MapPoint.mapPointWithGeoCoord(lat, lon));
        marker.setMarkerType(MapPOIItem.MarkerType.RedPin);
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.BluePin);


        mapView.addPOIItem(marker);
    }


    public Location getLocation() {
        try {
            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if (!isGPSEnabled && !isNetworkEnabled) {

            } else {
                this.isGetLocation = true;
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            lat = location.getLatitude();
                            lon = location.getLongitude();
                        }
                    }
                }
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                lat = location.getLatitude();
                                lon = location.getLongitude();
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return location;
    }

    public void stopUsingGPS() {
        if (locationManager != null) {
  //          locationManager.removeUpdates(MapActivity.this);
        }
    }

    public double getLatitude() {
        if (location != null) {
            lat = location.getLatitude();
        }
        return lat;
    }

    public double getLongitude() {
        if (location != null) {
            lon = location.getLongitude();
        }
        return lon;
    }

    public boolean isGetLocation() {
        return this.isGetLocation;
    }


    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getApplicationContext());

        alertDialog.setTitle("GPS 사용유무셋팅");
        alertDialog.setMessage("GPS 셋팅이 되지 않았을 수도 있습니다. \n 설정창으로 가시겠습니까?");

        alertDialog.setPositiveButton("Settings",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        getApplicationContext().startActivity(intent);
                    }
                });

        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialog.show();
    }


    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}


