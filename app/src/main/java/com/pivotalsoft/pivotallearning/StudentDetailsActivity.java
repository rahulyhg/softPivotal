package com.pivotalsoft.pivotallearning;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
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
import com.pivotalsoft.pivotallearning.Api.Apis;
import com.pivotalsoft.pivotallearning.Fragments.AttendanceFragment;
import com.pivotalsoft.pivotallearning.Fragments.BatchesFragment;
import com.pivotalsoft.pivotallearning.Fragments.PaymentFragment;
import com.pivotalsoft.pivotallearning.Fragments.WorksFragment;
import com.pivotalsoft.pivotallearning.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class StudentDetailsActivity extends AppCompatActivity {
    // Progress dialog
    private ProgressDialog pDialog;
    private static String TAG = StudentDetailsActivity.class.getSimpleName();

    private TabLayout tabLayout;
    private ViewPager viewPager;

    String URL;
    String Regno,fullname,email,mobile,area,city,profilepicurl;

    CircleImageView profilImg;
    TextView txtName,txtRegno,txtMobile,txtEmail,txtAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("DashBoard");

        // To retrieve value from shared preference in another activity
        SharedPreferences sp = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 for private mode
        Regno = sp.getString("regNo", "1");

        URL  = Apis.ONE_STUDENT_URL+"/"+Regno;

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        profilImg =(CircleImageView)findViewById(R.id.profileImage);
        txtAddress=(TextView)findViewById(R.id.txtAddress) ;
        txtName=(TextView)findViewById(R.id.txtName) ;
        txtRegno=(TextView)findViewById(R.id.txtRegNo) ;
        txtMobile=(TextView)findViewById(R.id.txtMobile) ;
        txtEmail=(TextView)findViewById(R.id.txtEmail) ;

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        prepareRegData();
    }

    private void prepareRegData() {


        showpDialog();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                URL, null, new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                Log.e("URL",""+URL);

                try {
                    // Parsing json object response
                    // response will be a json object
                    //mList.clear();

                    //Get the instance of JSONArray that contains JSONObjects
                    JSONArray jsonArray1 = response.optJSONArray("onedata");

                    JSONObject messageObject =new JSONObject(response.toString());
                    String message =messageObject.getString("message");
                    Log.e("msg",""+message);



                    //Iterate the jsonArray and print the info of JSONObjects
                        //  for (int i = 0; i < jsonArray1.length(); i++) {

                        JSONObject jsonObject = jsonArray1.getJSONObject(0);

                        Regno = jsonObject.optString("regno").toString();
                        fullname = jsonObject.optString("fullname").toString();
                        area = jsonObject.optString("area").toString();
                        city = jsonObject.optString("city").toString();
                        email = jsonObject.optString("email").toString();
                        mobile = jsonObject.optString("mobile").toString();
                        String password = jsonObject.optString("password").toString();
                        String lastlogintime = jsonObject.optString("lastlogintime").toString();
                        String role = jsonObject.optString("role").toString();
                        profilepicurl = Apis.IMAGE_PROFILE_URL + jsonObject.optString("profilepicurl").toString();

                        txtName.setText(fullname);
                        txtRegno.setText(Regno);
                        txtMobile.setText(mobile);
                        txtEmail.setText(email);
                        txtAddress.setText(area + "," + city);

                        // loading album cover using Glide library
                        try {
                            Glide.with(StudentDetailsActivity.this).load(profilepicurl).into(profilImg);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }






                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(StudentDetailsActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
                hidepDialog();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(StudentDetailsActivity.this, "no data Found. check once Registration no", Toast.LENGTH_LONG).show();
                // hide the progress dialog
                hidepDialog();

               // startActivity(new Intent(StudentDetailsActivity.this,StudentsActivity.class));
            }
        });

        //Creating request queue
        RequestQueue requestQueue = Volley.newRequestQueue(StudentDetailsActivity.this);

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


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new BatchesFragment(), "Batches");
        adapter.addFragment(new PaymentFragment(), "Payment");
        adapter.addFragment(new AttendanceFragment(), "Attendance");
        adapter.addFragment(new WorksFragment(), "Works");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
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

