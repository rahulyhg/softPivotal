package com.pivotalsoft.pivotallearning;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.pivotalsoft.pivotallearning.Api.Apis;
import com.pivotalsoft.pivotallearning.R;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class EnrollActivity extends AppCompatActivity implements View.OnClickListener  {

    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    LocationRequest mLocationRequest;
    Double latitude,longitude;
    String subLocality,locality;

    private static final String TAG = EnrollActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    EditText etFullName,etEmail,etMobile,etCourse;
    Button btnEnroll;
    String currentDate,stFullName,stEmail,stMobile,area,city,courseName;
    String remarks="enquiry from app";
    String source ="App";
    String status ="open";
    String track ="normal";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("New Enrollment");

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        courseName = getIntent().getExtras().getString("courseName");

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        stEmail =  pref.getString("email", null);
        stFullName = pref.getString("fullname", null);
        stMobile = pref.getString("mobile", null);
        area=pref.getString("area",null);
        city=pref.getString("city",null);


        currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());


        etFullName =(EditText)findViewById(R.id.etFullName);
        etFullName.setText(stFullName);

        etEmail =(EditText)findViewById(R.id.etEmail);
        etEmail.setText(stEmail);

        etMobile =(EditText)findViewById(R.id.etMobile);
        etMobile.setText(stMobile);

        etCourse =(EditText)findViewById(R.id.etCourse);
        etCourse.setText(courseName);

        btnEnroll=(Button)findViewById(R.id.btnEnroll);
        btnEnroll.setOnClickListener(this);

/*

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        } else
            Toast.makeText(this, "Not Connected!", Toast.LENGTH_SHORT).show();
*/





    }

  /*  *//*Ending the updates for the location service*//*
    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        settingRequest();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(this, "Connection Suspended!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Connection Failed!", Toast.LENGTH_SHORT).show();
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, 90000);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
            Log.i("Current Location", "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    *//*Method to get the enable location settings dialog*//*
    public void settingRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);    // 10 seconds, in milliseconds
        mLocationRequest.setFastestInterval(1000);   // 1 second, in milliseconds
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient,
                        builder.build());

        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {

            @Override
            public void onResult(@NonNull LocationSettingsResult result) {
                final Status status = result.getStatus();
                final LocationSettingsStates state = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can
                        // initialize location requests here.
                        getLocation();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied, but this can be fixed
                        // by showing the user a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(EnrollActivity.this, 1000);
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way
                        // to fix the settings so we won't show the dialog.
                        break;
                }
            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        final LocationSettingsStates states = LocationSettingsStates.fromIntent(data);
        switch (requestCode) {
            case 1000:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        // All required changes were successfully made
                        getLocation();
                        break;
                    case Activity.RESULT_CANCELED:
                        // The user was asked to change settings, but chose not to
                        Toast.makeText(this, "Location Service not Enabled", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
                break;
        }
    }

    public void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        } else {
            *//*Getting the location after aquiring location service*//*
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);

            if (mLastLocation != null) {

                latitude = mLastLocation.getLatitude();
                longitude =mLastLocation.getLongitude();

                Log.e("Latitude:",""+ latitude);
                Log.e("Longitude:",""+ longitude);

                Geocoder gcd=new Geocoder(EnrollActivity.this, Locale.getDefault());
                List<Address> addresses;

                try {
                    addresses=gcd.getFromLocation(latitude,longitude,1);
                    if(addresses.size()>0)

                    {
                        String address = addresses.get(0).getAddressLine(0);
                         locality = addresses.get(0).getLocality().toString();
                         subLocality = addresses.get(0).getSubLocality().toString();
                        String AddressLine = addresses.get(0).getAddressLine(0).toString();
                        //locTextView.setText(cityname);
                        // }
                        Log.e("locality",""+locality);
                        Log.e("SubLocality",""+subLocality);
                        Log.e("AddressLine",""+AddressLine);
                    }

                } catch (IOException e) {
                    e.printStackTrace();

                }
                *//*_progressBar.setVisibility(View.INVISIBLE);
                _latitude.setText("Latitude: " + String.valueOf(mLastLocation.getLatitude()));
                _longitude.setText("Longitude: " + String.valueOf(mLastLocation.getLongitude()));*//*
            } else {
                *//*if there is no last known location. Which means the device has no data for the loction currently.
                * So we will get the current location.
                * For this we'll implement Location Listener and override onLocationChanged*//*
                Log.i("Current Location", "No data for location found");

                if (!mGoogleApiClient.isConnected())
                    mGoogleApiClient.connect();

                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, EnrollActivity.this);
            }
        }
    }

    *//*When Location changes, this method get called. *//*
    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;

        latitude = mLastLocation.getLatitude();
        longitude =mLastLocation.getLongitude();

        Log.e("Latitude1:",""+ latitude);
        Log.e("Longitude1:",""+ longitude);

        Geocoder gcd=new Geocoder(EnrollActivity.this, Locale.getDefault());
        List<Address> addresses;

        try {
            addresses=gcd.getFromLocation(latitude,longitude,1);
            if(addresses.size()>0)

            {
                String address = addresses.get(0).getAddressLine(0);
                 locality = addresses.get(0).getLocality().toString();
                 subLocality = addresses.get(0).getSubLocality().toString();
                String AddressLine = addresses.get(0).getAddressLine(0).toString();
                //locTextView.setText(cityname);
                // }
                Log.e("locality1",""+locality);
                Log.e("SubLocality1",""+subLocality);
                Log.e("AddressLine1",""+AddressLine);
            }

        } catch (IOException e) {
            e.printStackTrace();

        }
    }*/



    /*public void getAddress() {

        mLastLocation =null;
        Geocoder geocoder;
        List<Address> addresses = new ArrayList<>();
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(mLastLocation.getLatitude(), mLastLocation.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (IOException e) {
            e.printStackTrace();
        }

        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        String city = addresses.get(0).getLocality();
        String state = addresses.get(0).getAdminArea();
        String country = addresses.get(0).getCountryName();
        String postalCode = addresses.get(0).getPostalCode();
        String knownName = addresses.get(0).getFeatureName();

        Log.e("currentLocation :",""+city);



    }*/



    @Override
    public void onClick(View v) {

         stFullName = etFullName.getText().toString().trim();
         stEmail = etEmail.getText().toString().trim();
         stMobile = etMobile.getText().toString().trim();
         courseName = etCourse.getText().toString().trim();

        /*final String Latitude = String.valueOf(latitude);
        final String Longitude = String.valueOf(longitude);
        final String Locality = locality;
        final String SubLocality = subLocality;*/

        if (!stFullName.isEmpty() && !stEmail.isEmpty() && !stMobile.isEmpty() && !courseName.isEmpty()) {

            enrollUser();

        } else {
            Toast.makeText(getApplicationContext(),
                    "Please enter your details!", Toast.LENGTH_LONG)
                    .show();
        }
    }

    private void enrollUser(){

        pDialog.setMessage("Loading ...");
        showDialog();
       /* final String paymentMode ="Online";
        final String paymentStatus ="success";
        final String transactionId ="65465464";*/

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Apis.ENROLLMENT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("RESPONSE : ",""+response);
                        hideDialog();

                        Intent pivotal = new Intent(EnrollActivity.this, HomeActivity.class);
                        pivotal.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(pivotal);

                        Toast.makeText(EnrollActivity.this,"Data Sent Successfully",Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("RESPONSE_ERROR: ",""+error);
                        hideDialog();
                       // Toast.makeText(EnrollActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("fullname",stFullName);
                params.put("course",courseName);
                params.put("track",track);
                params.put("enquirydate",currentDate);
                params.put("source",source);
                params.put("area",area);
                params.put("city",city);
                params.put("email",stEmail);
                params.put("mobile",stMobile);
                params.put("remarks",remarks);
                params.put("status",status);
                params.put("staffid","1");
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //finish();
                onBackPressed();
                break;
        }
        return true;
    }


}
