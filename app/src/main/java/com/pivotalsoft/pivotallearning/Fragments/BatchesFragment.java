package com.pivotalsoft.pivotallearning.Fragments;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.pivotalsoft.pivotallearning.Adapters.StudentBatchAdapter;
import com.pivotalsoft.pivotallearning.Api.Apis;
import com.pivotalsoft.pivotallearning.BatchActivity;
import com.pivotalsoft.pivotallearning.Items.StudenBatchItem;
import com.pivotalsoft.pivotallearning.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BatchesFragment extends Fragment {
    private RecyclerView recyclerBatch;

    private StudentBatchAdapter batchesAdapter;
    private List<StudenBatchItem> batchesItemList= new ArrayList<>();

    // Progress dialog
    private ProgressDialog pDialog;

    private static String TAG = BatchActivity.class.getSimpleName();
    String regno;

    String URL;

    View rootView;
    public BatchesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_batches, container, false);

        // To retrieve value from shared preference in another activity
        SharedPreferences sp = getContext().getSharedPreferences("MyPref", 0); // 0 for private mode
        regno = sp.getString("regNo", "1");
        Log.e("Regno",""+regno);

        URL = Apis.STUDENT_BATCH_URL+"/"+regno;
        // Progress dialog
        pDialog = new ProgressDialog(getContext());
        pDialog.setCancelable(false);

        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        recyclerBatch = (RecyclerView)rootView. findViewById(R.id.recyclerBatches);



        GridLayoutManager mLayoutManager1 = new GridLayoutManager(getContext(),2);
        recyclerBatch.setLayoutManager(mLayoutManager1);
        recyclerBatch.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(20), true));
        recyclerBatch.setItemAnimator(new DefaultItemAnimator());





        prepareBatchesData();

        return rootView;

    }


    private void prepareBatchesData() {

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
                    batchesItemList.clear();

                    //Get the instance of JSONArray that contains JSONObjects
                    JSONArray jsonArray1 = response.optJSONArray("admissiondata");


                    //Iterate the jsonArray and print the info of JSONObjects
                    for (int i = 0; i < jsonArray1.length(); i++) {

                        JSONObject jsonObject = jsonArray1.getJSONObject(i);

                        String admid = jsonObject.optString("admid").toString();
                        String regno = jsonObject.optString("regno").toString();
                        String regdate = jsonObject.optString("regdate").toString();
                        String batchid = jsonObject.optString("batchid").toString();
                        String fee = jsonObject.optString("fee").toString();
                        String batchcode = jsonObject.optString("batchcode").toString();
                        String coursename = jsonObject.optString("coursename").toString();
                        String courseimage =Apis.IMAGE_COURSE_URL + jsonObject.optString("courseimage").toString();
                        Log.e("imageUrl",""+courseimage);
                        String colorcode = jsonObject.optString("colorcode").toString();

                        batchesItemList.add(new StudenBatchItem(admid,regno,regdate,batchid,fee,batchcode,coursename,courseimage,colorcode));

                        batchesAdapter = new StudentBatchAdapter(getContext(), batchesItemList);
                        recyclerBatch.setAdapter(batchesAdapter);
                        batchesAdapter.notifyDataSetChanged();


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
                hidepDialog();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
               // Toast.makeText(getContext(), "no data Found. check once data is enable or not..", Toast.LENGTH_LONG).show();
                // hide the progress dialog
                hidepDialog();
            }
        });

        //Creating request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

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

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

}
