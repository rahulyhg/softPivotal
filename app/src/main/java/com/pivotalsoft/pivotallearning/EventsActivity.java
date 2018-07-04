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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.pivotalsoft.pivotallearning.Adapters.EventsAdapter;
import com.pivotalsoft.pivotallearning.Adapters.UpcomingEventsAdapter;
import com.pivotalsoft.pivotallearning.Api.Apis;
import com.pivotalsoft.pivotallearning.Items.EventsItem;
import com.pivotalsoft.pivotallearning.Items.UpcomingEventsItem;
import com.pivotalsoft.pivotallearning.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EventsActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recyclerCurrentEvents,recyclerUpcomingEvents;
    private EventsAdapter eventsAdapter;
    private List<EventsItem> eventsItemList= new ArrayList<>();;

    private UpcomingEventsAdapter upcomingEventsAdapter;
    private List<UpcomingEventsItem> upcomingEventsItemList= new ArrayList<>();;
    // Progress dialog
    private ProgressDialog pDialog;

    private static String TAG = BatchActivity.class.getSimpleName();

    LinearLayout currentLayout,upcomingLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Events");

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        recyclerCurrentEvents = (RecyclerView) findViewById(R.id.recyclerCourses);
        recyclerUpcomingEvents =(RecyclerView)findViewById(R.id.recyclerNewCourses);


        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerCurrentEvents.setLayoutManager(mLayoutManager1);
        recyclerCurrentEvents.setItemAnimator(new DefaultItemAnimator());


        RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerUpcomingEvents.setLayoutManager(mLayoutManager2);
        recyclerUpcomingEvents.setItemAnimator(new DefaultItemAnimator());

        currentLayout=(LinearLayout)findViewById(R.id.currentLayout);
        upcomingLayout=(LinearLayout)findViewById(R.id.upcomingLayout);
       /* txtPopular =(TextView)findViewById(R.id.txtPopularCourses);
        txtPopular.setOnClickListener(this);
        txtNew =(TextView)findViewById(R.id.txtNewCourses);
        txtNew.setOnClickListener(this);*/

        prepareEventsData();
    }

    private void prepareEventsData() {

        showpDialog();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                Apis.EVENTS_URL, null, new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                Log.e("URL",""+Apis.EVENTS_URL);

                try {
                    // Parsing json object response
                    // response will be a json object
                    eventsItemList.clear();

                    //Get the instance of JSONArray that contains JSONObjects
                    JSONArray jsonArray1 = response.optJSONArray("eventsdata");


                    //Iterate the jsonArray and print the info of JSONObjects
                    for (int i = 0; i < jsonArray1.length(); i++) {

                        JSONObject jsonObject = jsonArray1.getJSONObject(i);

                        String eventid = jsonObject.optString("eventid").toString();
                        String eventname = jsonObject.optString("eventname").toString();
                        String venue = jsonObject.optString("venue").toString();
                        String eventdate = jsonObject.optString("eventdate").toString();
                        String description = jsonObject.optString("description").toString();
                        String tentativedate = jsonObject.optString("tentativedate").toString();


                       /* if (upcomingEventsItemList.size()==0){

                            upcomingLayout.setVisibility(View.GONE);
                            currentLayout.setVisibility(View.VISIBLE);

                        }else if (eventsItemList.size()==0){

                            upcomingLayout.setVisibility(View.VISIBLE);
                            currentLayout.setVisibility(View.GONE);

                        }*/

                        if (eventdate.trim().equals("0000-00-00")){
                            upcomingEventsItemList.add(new UpcomingEventsItem(eventid, eventname, venue, eventdate, description, tentativedate));
                            upcomingEventsAdapter = new UpcomingEventsAdapter(EventsActivity.this, upcomingEventsItemList);
                            recyclerUpcomingEvents.setAdapter(upcomingEventsAdapter);
                            upcomingEventsAdapter.notifyDataSetChanged();
                        }
                        else {
                            eventsItemList.add(new EventsItem(eventid, eventname, venue, eventdate, description, tentativedate));
                            eventsAdapter = new EventsAdapter(EventsActivity.this, eventsItemList);
                            recyclerCurrentEvents.setAdapter(eventsAdapter);
                            eventsAdapter.notifyDataSetChanged();
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(EventsActivity.this,
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
                hidepDialog();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(EventsActivity.this, "no data Found. check once data is enable or not..", Toast.LENGTH_LONG).show();
                // hide the progress dialog
                hidepDialog();
            }
        });

        //Creating request queue
        RequestQueue requestQueue = Volley.newRequestQueue(EventsActivity.this);

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
                Intent pivotal = new Intent(this, AllCurrentEventsActivity.class);
                pivotal.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(pivotal);

                break;

            case R.id.txtNewCourses:
                Intent newCourse = new Intent(this, AllUpcomingEventsActivity.class);
                newCourse.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(newCourse);

                break;*/

        }
    }
}


