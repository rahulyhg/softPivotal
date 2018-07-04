package com.pivotalsoft.pivotallearning;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
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
import com.pivotalsoft.pivotallearning.Adapters.CoursesDetailAdapter;
import com.pivotalsoft.pivotallearning.Api.Apis;
import com.pivotalsoft.pivotallearning.Items.CoursesDetailsItem;
import com.pivotalsoft.pivotallearning.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CourseDetailsActivity extends AppCompatActivity {
    private RecyclerView recyclerCourses;
    private CoursesDetailAdapter coursesDetailAdapter;
    private List<CoursesDetailsItem> coursesItemList = new ArrayList<>();
    // Progress dialog
    private ProgressDialog pDialog;

    private static String TAG = BatchActivity.class.getSimpleName();

    private String DETAILS_URL ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Course Details");

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        String courseImage = getIntent().getExtras().getString("courseImage");
        String colorCode = getIntent().getExtras().getString("colorCode");
        String courseName = getIntent().getExtras().getString("courseName");
        String courseId = getIntent().getExtras().getString("courseId");

        DETAILS_URL = Apis.COURSE_DETAILS_URL+"/"+courseId;


        TextView txtCourseName =(TextView)findViewById(R.id.txtCourseName);
        txtCourseName.setText(courseName);

        // layout background color
        LinearLayout colorLayout=(LinearLayout)findViewById(R.id.headerLayout);
        colorLayout.setBackgroundColor(Color.parseColor(colorCode));

        ImageView courseImageView = (ImageView)findViewById(R.id.courseImageview);
        // loading album cover using Glide library
        try {
            Glide.with(CourseDetailsActivity.this).load(courseImage).into(courseImageView);
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        recyclerCourses = (RecyclerView) findViewById(R.id.recyclerCourses);

        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerCourses.setLayoutManager(mLayoutManager1);
        recyclerCourses.setItemAnimator(new DefaultItemAnimator());

        prepareCourseDetailsData();
    }

    private void prepareCourseDetailsData() {

        showpDialog();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                DETAILS_URL, null, new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                Log.e("URL",""+DETAILS_URL);

                try {
                    // Parsing json object response
                    // response will be a json object
                    coursesItemList.clear();

                    //Get the instance of JSONArray that contains JSONObjects
                    JSONArray jsonArray1 = response.optJSONArray("contentdata");


                    //Iterate the jsonArray and print the info of JSONObjects
                    for (int i = 0; i < jsonArray1.length(); i++) {

                        JSONObject jsonObject = jsonArray1.getJSONObject(i);

                        String contentid = jsonObject.optString("contentid").toString();
                        String courseid = jsonObject.optString("courseid").toString();
                        String chapterno = jsonObject.optString("chapterno").toString();
                        String chaptername = jsonObject.optString("chaptername").toString();
                        String description = jsonObject.optString("description").toString();

                        coursesItemList.add(new CoursesDetailsItem(contentid, courseid,chapterno,chaptername,description));
                        coursesDetailAdapter = new CoursesDetailAdapter(CourseDetailsActivity.this, coursesItemList);
                        recyclerCourses.setAdapter(coursesDetailAdapter);
                        coursesDetailAdapter.notifyDataSetChanged();


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(CourseDetailsActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
                hidepDialog();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(CourseDetailsActivity.this, "no data Found.", Toast.LENGTH_LONG).show();
                // hide the progress dialog
                hidepDialog();
            }
        });

        //Creating request queue
        RequestQueue requestQueue = Volley.newRequestQueue(CourseDetailsActivity.this);

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
