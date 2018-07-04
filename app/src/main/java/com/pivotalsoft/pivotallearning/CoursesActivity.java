package com.pivotalsoft.pivotallearning;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
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
import com.pivotalsoft.pivotallearning.Adapters.CoursesAdapter;
import com.pivotalsoft.pivotallearning.Adapters.NewCoursesAdapter;
import com.pivotalsoft.pivotallearning.Api.Apis;
import com.pivotalsoft.pivotallearning.Items.CoursesItem;
import com.pivotalsoft.pivotallearning.Items.NewCourseItem;
import com.pivotalsoft.pivotallearning.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CoursesActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recyclerCourses,recyclerNewCourses;

    private CoursesAdapter coursesAdapter;
    private List<CoursesItem> coursesItemList = new ArrayList<>();;

    private NewCoursesAdapter newCoursesAdapter;
    private List<NewCourseItem> newCourseItemList = new ArrayList<>();;

    TextView txtPopular,txtNew;

    // Progress dialog
    private ProgressDialog pDialog;

    private static String TAG = CoursesActivity.class.getSimpleName();

    LinearLayout currentLayout,upcomingLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Courses");

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        recyclerCourses = (RecyclerView) findViewById(R.id.recyclerCourses);
        recyclerNewCourses =(RecyclerView)findViewById(R.id.recyclerNewCourses);



        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerCourses.setLayoutManager(mLayoutManager1);
        recyclerCourses.setItemAnimator(new DefaultItemAnimator());


        RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerNewCourses.setLayoutManager(mLayoutManager2);
        recyclerNewCourses.setItemAnimator(new DefaultItemAnimator());
        recyclerNewCourses.setAdapter(newCoursesAdapter);

        currentLayout=(LinearLayout)findViewById(R.id.currentLayout);
        upcomingLayout=(LinearLayout)findViewById(R.id.upcomingLayout);
       /* txtPopular =(TextView)findViewById(R.id.txtPopularCourses);
        txtPopular.setOnClickListener(this);
        txtNew =(TextView)findViewById(R.id.txtNewCourses);
        txtNew.setOnClickListener(this);*/

        prepareCoursesData();
    }


    private void prepareCoursesData() {

         showpDialog();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                Apis.COURSES_URL, null, new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                Log.e("URL",""+Apis.COURSES_URL);

                try {
                    // Parsing json object response
                    // response will be a json object
                    coursesItemList.clear();

                    //Get the instance of JSONArray that contains JSONObjects
                    JSONArray jsonArray1 = response.optJSONArray("coursesdata");


                    //Iterate the jsonArray and print the info of JSONObjects
                    for (int i = 0; i < jsonArray1.length(); i++) {

                        JSONObject jsonObject = jsonArray1.getJSONObject(i);

                        String courseid = jsonObject.optString("courseid").toString();
                        String coursename = jsonObject.optString("coursename").toString();
                        String duration = jsonObject.optString("duration").toString();
                        String fastduration = jsonObject.optString("fastduration").toString();
                        String description = jsonObject.optString("description").toString();
                        String courseimage =Apis.IMAGE_BASE_URL+ jsonObject.optString("courseimage").toString();
                        Log.e("imageUrl",""+courseimage);
                        String fee = jsonObject.optString("fee").toString();
                        String disfee = jsonObject.optString("disfee").toString();
//                        String learners = jsonObject1.optString("learners").toString();
                        String type = jsonObject.optString("type").toString();
                        String colorcode = jsonObject.optString("colorcode").toString();

                        Random r = new Random();
                        int randomNum = r.nextInt(20 - 1) + 1;
                        String learners = String.valueOf(randomNum);
                        Log.e("learners",""+learners);


                       /* if (newCourseItemList.size()==0){

                            upcomingLayout.setVisibility(View.GONE);
                            currentLayout.setVisibility(View.VISIBLE);

                        }else if (coursesItemList.size()==0){

                            upcomingLayout.setVisibility(View.VISIBLE);
                            currentLayout.setVisibility(View.GONE);

                        }*/


                        if (type.trim().equals("1")){

                            coursesItemList.add(new CoursesItem(courseid, coursename, duration, fastduration, description, courseimage, fee, disfee, type,colorcode,learners));

                            coursesAdapter = new CoursesAdapter(CoursesActivity.this, coursesItemList);
                            recyclerCourses.setAdapter(coursesAdapter);
                            coursesAdapter.notifyDataSetChanged();
                        }
                         if (type.trim().equals("2")){

                             newCourseItemList.add(new NewCourseItem(courseid, coursename, duration, fastduration, description, courseimage, fee, disfee, type,colorcode,learners));

                             newCoursesAdapter = new NewCoursesAdapter(CoursesActivity.this, newCourseItemList);
                             recyclerNewCourses.setAdapter(newCoursesAdapter);
                             newCoursesAdapter.notifyDataSetChanged();
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(CoursesActivity.this,
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
                 hidepDialog();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(CoursesActivity.this, "no data Found. check once data is enable or not..", Toast.LENGTH_LONG).show();
                // hide the progress dialog
                 hidepDialog();
            }
        });


        //Creating request queue
        RequestQueue requestQueue = Volley.newRequestQueue(CoursesActivity.this);

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

           /* case R.id.txtPopularCourses:
                Intent pivotal = new Intent(this, AllCoursesActivity.class);
                pivotal.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(pivotal);

                break;

            case R.id.txtNewCourses:
                Intent newCourse = new Intent(this, AllNewCoursesActivity.class);
                newCourse.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(newCourse);

                break;*/

        }
    }
}
