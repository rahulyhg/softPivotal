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
import com.pivotalsoft.pivotallearning.Adapters.BatchesAdapter;
import com.pivotalsoft.pivotallearning.Adapters.UpcomingBatchesAdapter;
import com.pivotalsoft.pivotallearning.Api.Apis;
import com.pivotalsoft.pivotallearning.Items.BatchesItem;
import com.pivotalsoft.pivotallearning.Items.UpcomingBatchItem;
import com.pivotalsoft.pivotallearning.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BatchActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recyclerCurrentBatch,recyclerUpcomingBatches;

    private BatchesAdapter batchesAdapter;
    private List<BatchesItem> batchesItemList= new ArrayList<>();

    private UpcomingBatchesAdapter upcomingBatchesAdapter;
    private List<UpcomingBatchItem> upcomingBatchItemList= new ArrayList<>();

    // Progress dialog
    private ProgressDialog pDialog;

    private static String TAG = BatchActivity.class.getSimpleName();

    TextView txtPopular,txtNew;
    LinearLayout currentLayout,upcomingLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batch);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Batches");


        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        recyclerCurrentBatch = (RecyclerView) findViewById(R.id.recyclerCourses);
        recyclerUpcomingBatches =(RecyclerView)findViewById(R.id.recyclerNewCourses);


        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerCurrentBatch.setLayoutManager(mLayoutManager1);
        recyclerCurrentBatch.setItemAnimator(new DefaultItemAnimator());
        recyclerCurrentBatch.setAdapter(batchesAdapter);

        RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerUpcomingBatches.setLayoutManager(mLayoutManager2);
        recyclerUpcomingBatches.setItemAnimator(new DefaultItemAnimator());


       /* txtPopular =(TextView)findViewById(R.id.txtPopularCourses);
        txtPopular.setOnClickListener(this);
        txtNew =(TextView)findViewById(R.id.txtNewCourses);
        txtNew.setOnClickListener(this);*/

       currentLayout=(LinearLayout)findViewById(R.id.currentLayout);
        upcomingLayout=(LinearLayout)findViewById(R.id.upcomingLayout);

        prepareBatchesData();


    }



    private void prepareBatchesData() {

        showpDialog();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                Apis.BATCHES_URL, null, new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                Log.e("URL",""+Apis.BATCHES_URL);

                try {
                    // Parsing json object response
                    // response will be a json object
                    batchesItemList.clear();

                    //Get the instance of JSONArray that contains JSONObjects
                    JSONArray jsonArray1 = response.optJSONArray("batchesdata");


                    //Iterate the jsonArray and print the info of JSONObjects
                    for (int i = 0; i < jsonArray1.length(); i++) {

                        JSONObject jsonObject = jsonArray1.getJSONObject(i);

                        String batchcode = jsonObject.optString("batchcode").toString();
                        String startdate = jsonObject.optString("startdate").toString();
                        String batchtype = jsonObject.optString("batchtype").toString();
                        String description = jsonObject.optString("description").toString();
                        String coursename = jsonObject.optString("coursename").toString();
                        String courseid = jsonObject.optString("courseid").toString();
                        String courseimage =Apis.IMAGE_BASE_URL+ jsonObject.optString("courseimage").toString();
                        Log.e("imageUrl",""+courseimage);
                        String fee = jsonObject.optString("fee").toString();
                        String disfee = jsonObject.optString("disfee").toString();
                        String colorcode = jsonObject.optString("colorcode").toString();

                        Random r = new Random();
                        int randomNum = r.nextInt(20 - 1) + 1;
                        String learners = String.valueOf(randomNum);
                        Log.e("learners",""+learners);


                       /* if (upcomingBatchItemList.size()==0){

                            upcomingLayout.setVisibility(View.GONE);
                            currentLayout.setVisibility(View.VISIBLE);

                        }else if (batchesItemList.size()==0){

                            upcomingLayout.setVisibility(View.VISIBLE);
                            currentLayout.setVisibility(View.GONE);

                        }*/


                        if (startdate.trim().equals("0000-00-00")){

                            upcomingBatchItemList.add(new UpcomingBatchItem(batchcode, startdate, batchtype, description, coursename,courseid, courseimage, fee, disfee,colorcode,learners));
                            upcomingBatchesAdapter = new UpcomingBatchesAdapter(BatchActivity.this, upcomingBatchItemList);
                            recyclerUpcomingBatches.setAdapter(upcomingBatchesAdapter);
                            upcomingBatchesAdapter.notifyDataSetChanged();

                        }
                        else {

                                batchesItemList.add(new BatchesItem(batchcode, startdate, batchtype, description, coursename, courseid, courseimage, fee, disfee, colorcode, learners));
                                batchesAdapter = new BatchesAdapter(BatchActivity.this, batchesItemList);
                                recyclerCurrentBatch.setAdapter(batchesAdapter);
                                batchesAdapter.notifyDataSetChanged();


                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(BatchActivity.this,
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
                hidepDialog();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(BatchActivity.this, "no data Found. check once data is enable or not..", Toast.LENGTH_LONG).show();
                // hide the progress dialog
                hidepDialog();
            }
        });

        //Creating request queue
        RequestQueue requestQueue = Volley.newRequestQueue(BatchActivity.this);

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
                Intent pivotal = new Intent(this, AllCurrentBatchsActivity.class);
                pivotal.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(pivotal);

                break;

            case R.id.txtNewCourses:
                Intent newCourse = new Intent(this, AllUpcomingBatchesActivity.class);
                newCourse.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(newCourse);

                break;*/

        }
    }
}

