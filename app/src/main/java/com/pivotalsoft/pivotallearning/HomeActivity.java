package com.pivotalsoft.pivotallearning;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.pivotalsoft.pivotallearning.Api.Apis;
import com.pivotalsoft.pivotallearning.R;
import com.pivotalsoft.pivotallearning.helper.Session;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import ss.com.bannerslider.banners.Banner;
import ss.com.bannerslider.banners.RemoteBanner;
import ss.com.bannerslider.views.BannerSlider;



public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private Session session;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    LinearLayout coursesLayout, batchesLayout, eventsLayout, askLayout, studyLayout, offerLayout, qrCodeLayout;

    //qr code scanner object
    private IntentIntegrator qrScan;

    // Progress dialog
    private ProgressDialog pDialog;

    private static String TAG = HomeActivity.class.getSimpleName();

    String offerId;
    String emailId,fullName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.splash_footer_icon);

        session = new Session(this);
        if(!session.loggedin()){
            logout();
        }



        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        // To retrieve value from shared preference in another activity
        preferences = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 for private mode
        emailId = preferences.getString("email", "1");
        fullName = preferences.getString("fullname", "1");
        String mobile = preferences.getString("mobile", "1");
        String regNo = preferences.getString("regNo", "");
        String profilepicurl = preferences.getString("profilepicurl", "");


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        //drawer.setScrimColor(getResources().getColor(android.R.color.transparent));

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerLayout = navigationView.getHeaderView(0);

        // userName on Nav Header
        TextView txtname = (TextView)headerLayout.findViewById(R.id.txtUserName);
        txtname.setText(fullName);

        // regno on Nav Header
        TextView txtRegNo = (TextView)headerLayout.findViewById(R.id.txtRegNo);
        txtRegNo.setText(regNo);

        // email on Nav Header
        TextView txtemail = (TextView)headerLayout.findViewById(R.id.txtEmail);
        txtemail.setText(emailId);

        CircleImageView imageView =(CircleImageView)headerLayout.findViewById(R.id.imageView);

        // loading album cover using Glide library
        try {
            if (profilepicurl.equals("http://vizagnext.com/pivotallearn/uploads/profilepics/")){
                Glide.with(this).load(R.drawable.user_icon_img).into(imageView);
            }else {
                Glide.with(this).load(profilepicurl).into(imageView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Menu menuNav=navigationView.getMenu();
        MenuItem nav_item1 = menuNav.findItem(R.id.nav_dashboard);


        if (regNo != null && !regNo.isEmpty()){

            nav_item1.setVisible(true);
            txtRegNo.setVisibility(View.VISIBLE);
        }
        else {

            nav_item1.setVisible(false);
            txtRegNo.setVisibility(View.GONE);

        }






        BannerSlider bannerSlider = (BannerSlider) findViewById(R.id.banner_slider1);
        List<Banner> banners=new ArrayList<>();
        //add banner using image url
        banners.add(new RemoteBanner("http://www.chromamarketing.com/wp-content/uploads/2015/08/custom-website-banners.jpg"));
       // banners.add(new RemoteBanner("http://vizagnext.com/pivotallearn/uploads/banners/banner-1.jpg"));

        /*//add banner using resource drawable
        banners.add(new DrawableBanner(R.drawable.splash_footer_icon));
        bannerSlider.setBanners(banners);*/

        bannerSlider.setBanners(banners);

        coursesLayout=(LinearLayout)findViewById(R.id.coursesLayout);
        coursesLayout.setOnClickListener(this);

        batchesLayout=(LinearLayout)findViewById(R.id.batchsLayout);
        batchesLayout.setOnClickListener(this);

        eventsLayout=(LinearLayout)findViewById(R.id.eventsLayout);
        eventsLayout.setOnClickListener(this);

        askLayout=(LinearLayout)findViewById(R.id.askLayout);
        askLayout.setOnClickListener(this);

        studyLayout=(LinearLayout)findViewById(R.id.studyLayout);
        studyLayout.setOnClickListener(this);

        offerLayout =(LinearLayout)findViewById(R.id.offerLayout);
        offerLayout.setOnClickListener(this);

        qrCodeLayout =(LinearLayout)findViewById(R.id.qrCodeLayout);
        qrCodeLayout.setOnClickListener(this);

        //intializing scan object
        qrScan = new IntentIntegrator(this);



    }

    private void logout(){
        session.setLoggedin(false);
        finish();
        startActivity(new Intent(HomeActivity.this,LoginActivity.class));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
           // super.onBackPressed();

            /**/

            // custom dialog
            final Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.setContentView(R.layout.exit_layout);

            // set the custom dialog components - text, image and button
            TextView text = (TextView) dialog.findViewById(R.id.text);
            ImageView image = (ImageView) dialog.findViewById(R.id.image);
            image.setImageResource(R.drawable.logo_pivotal);

            Button dismissButton =(Button)dialog.findViewById(R.id.buttonCancel);

            Button dialogButton = (Button) dialog.findViewById(R.id.buttonOk);
            // if button is clicked, close the custom dialog

            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    dialog.dismiss();
                }
            });

            dismissButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.show();


        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_dashboard) {

            Intent myBatchs =new Intent(this,StudentDetailsActivity.class);
            myBatchs.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(myBatchs);

        }
        else if (id == R.id.nav_wallet) {

            Intent wallet =new Intent(this,WalletActivity.class);
            wallet.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(wallet);

        }
        else if (id == R.id.nav_share) {

            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("text/plain");
            share.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.pivotalsoft.pivotalsoft&hl=en");

            startActivity(Intent.createChooser(share, "Share pivotallearning app"));

        } else if (id == R.id.nav_contact) {

            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:+91939172255"));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(intent);

        } else if (id == R.id.nav_about) {

            // custom dialog
            final Dialog dialog = new Dialog(HomeActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.setContentView(R.layout.about_pivotal_layout);

            // set the custom dialog components - text, image and button
            ImageView image = (ImageView) dialog.findViewById(R.id.imageView);
            image.setImageResource(R.drawable.about);

            dialog.show();

        }else if (id==R.id.nav_logout){

            Intent logout =new Intent(this,LoginActivity.class);
            logout.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(logout);

            editor = preferences.edit();
            editor.clear();
            editor.apply();

            logout();

        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.coursesLayout:

                Intent pivotal =new Intent(this,CoursesActivity.class);
                pivotal.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(pivotal);

                break;

            case R.id.batchsLayout:
                Intent batch =new Intent(this,BatchActivity.class);
                batch.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(batch);

                break;

            case R.id.eventsLayout:
                Intent events =new Intent(this,EventsActivity.class);
                events.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(events);

                break;

            case R.id.askLayout:

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:+91939172255"));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(intent);

                break;

            case R.id.studyLayout:

                Intent study =new Intent(this,StudyLiveActivity.class);
                study.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(study);

                break;

            case R.id.offerLayout:

                Intent offer =new Intent(this,OffersActivity.class);
                offer.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(offer);

                break;

            case R.id.qrCodeLayout:

                //initiating the qr code scan
                qrScan.initiateScan();

                break;
    }}


    private void prepareOffersData() {

        final String QRCODE_URL = Apis.OFFERS_DETAILS+"/"+1;

        showpDialog();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                QRCODE_URL, null, new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                Log.e("URL",""+QRCODE_URL);

                try {
                    // Parsing json object response
                    // response will be a json object

                    //Get the instance of JSONArray that contains JSONObjects
                    JSONArray jsonArray1 = response.optJSONArray("offersdata");


                    //Iterate the jsonArray and print the info of JSONObjects
                    for (int i = 0; i < jsonArray1.length(); i++) {

                        JSONObject jsonObject = jsonArray1.getJSONObject(i);

                        String offerid = jsonObject.optString("offerid").toString();
                        String offercode = jsonObject.optString("offercode").toString();
                        String offername = jsonObject.optString("offername").toString();
                        String description = jsonObject.optString("description").toString();
                        String startdate = jsonObject.optString("startdate").toString();
                        String enddate = jsonObject.optString("enddate").toString();
                        String isreferal = jsonObject.optString("isreferal").toString();


                        // custom dialog
                        final Dialog dialog = new Dialog(HomeActivity.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                /*dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));*/
                        dialog.setContentView(R.layout.offer_details_layout);

                        // set the custom dialog components - text, image and button
                        TextView offerName = (TextView) dialog.findViewById(R.id.offerName);
                        offerName.setText(offername);

                        TextView code = (TextView) dialog.findViewById(R.id.code);
                        code.setText(offercode);

                        TextView expDate = (TextView) dialog.findViewById(R.id.expDate);
                        expDate.setText("Exp Date : "+startdate +" - " +enddate);

                        TextView txtdescription = (TextView) dialog.findViewById(R.id.description);

                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                            txtdescription.setText(Html.fromHtml(description,Html.FROM_HTML_MODE_LEGACY));
                        } else {
                            txtdescription.setText(Html.fromHtml(description));
                        }

                        ImageView image = (ImageView) dialog.findViewById(R.id.image);
                        image.setImageResource(R.drawable.offersgrid_img);

                        dialog.show();


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(HomeActivity.this,
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
                hidepDialog();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(HomeActivity.this,
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog
                hidepDialog();
            }
        });

        //Creating request queue
        RequestQueue requestQueue = Volley.newRequestQueue(HomeActivity.this);

        //Adding request to the queue
        requestQueue.add(jsonObjReq);

    }
    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data
                try {
                    //converting the data to json
                    JSONObject obj = new JSONObject(result.getContents());
                    //setting values to textviews
                    offerId =obj.getString("offerid");
                    Log.e("Data",""+offerId);


                    prepareOffersData();



                } catch (JSONException e) {
                    e.printStackTrace();
                    //if control comes here
                    //that means the encoded format not matches
                    //in this case you can display whatever data is available on the qrcode
                    //to a toast
                    Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }




}



