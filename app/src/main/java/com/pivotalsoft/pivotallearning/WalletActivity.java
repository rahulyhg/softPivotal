package com.pivotalsoft.pivotallearning;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import com.pivotalsoft.pivotallearning.Adapters.WalletHistoryAdapter;
import com.pivotalsoft.pivotallearning.Api.Apis;
import com.pivotalsoft.pivotallearning.Items.WalletItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WalletActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = WalletActivity.class.getSimpleName();
    // Progress dialog
    private ProgressDialog pDialog;
    private List<WalletItem> walletItemList =new ArrayList<>();
    TextView txtTotalBalance, txtReferral;
    Button btnInvite;
    LinearLayout linearLayoutOne, linearLayouttWO, historyLayout;
    CardView earnLayout;
    String myreferralcode;

    WalletHistoryAdapter adapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Wallet");

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        // To retrieve value from shared preference in another activity
        SharedPreferences sp = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 for private mode
        String emailId = sp.getString("email", null);
        myreferralcode = sp.getString("myreferralcode", null);

        txtTotalBalance = (TextView) findViewById(R.id.txtTotalBalance);

        txtReferral = (TextView) findViewById(R.id.txtReferral);
        txtReferral.setTextIsSelectable(true);
        txtReferral.setText(myreferralcode);

        btnInvite = (Button) findViewById(R.id.btnInvite);
        linearLayoutOne = (LinearLayout) findViewById(R.id.linearLayoutEarn);
        linearLayouttWO = (LinearLayout) findViewById(R.id.linearLayoutHistory);
        earnLayout = (CardView) findViewById(R.id.earnLayout);
        historyLayout = (LinearLayout) findViewById(R.id.historyLayout);

        btnInvite.setOnClickListener(this);
        linearLayoutOne.setOnClickListener(this);
        linearLayouttWO.setOnClickListener(this);

        recyclerView =(RecyclerView)findViewById(R.id.recyclerHistory);
        RecyclerView.LayoutManager mLayoutManager1 = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(mLayoutManager1);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        // prepare wallet data
        prepareWalletData(emailId);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btnInvite:

                String msg = "Use My Referral Code : " + myreferralcode + ". You can get 1000 rupees and your friend can get 2000 rupees as Referral Bonus. " + "https://play.google.com/store/apps/details?id=com.pivotalsoft.pivotallearning&hl=en";

                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT, msg);
                startActivity(Intent.createChooser(share, "Share PivotalLearning App"));

                break;

            case R.id.linearLayoutEarn:

                earnLayout.setVisibility(View.VISIBLE);
                historyLayout.setVisibility(View.GONE);

                break;

            case R.id.linearLayoutHistory:

                earnLayout.setVisibility(View.GONE);
                historyLayout.setVisibility(View.VISIBLE);

                break;
        }

    }

    // check refer code
    private void prepareWalletData(final String email) {
        showpDialog();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                Apis.REFERRAL_MONEY_URL + email, null, new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                Log.e("URL", "" + Apis.REFERRAL_MONEY_URL + email);

                try {
                    // Parsing json object response
                    // response will be a json object
                     walletItemList.clear();
                    //Get the instance of JSONArray that contains JSONObjects
                    JSONArray jsonArray1 = response.optJSONArray("walletdata");

                    //Iterate the jsonArray and print the info of JSONObjects
                    for (int i = 0; i < jsonArray1.length(); i++) {

                        JSONObject jsonObject = jsonArray1.getJSONObject(i);

                        String entryid = jsonObject.optString("entryid").toString();
                        String useremail = jsonObject.optString("useremail").toString();
                        String bonustype = jsonObject.optString("bonustype").toString();
                        String bonusamount = jsonObject.optString("bonusamount").toString();
                        String status = jsonObject.optString("status").toString();
                        String description = jsonObject.optString("description").toString();
                        String bonusdate = jsonObject.optString("bonusdate").toString();


                        walletItemList.add(new WalletItem(entryid,useremail,bonustype,bonusamount,status,description,bonusdate));


                    }

                    adapter =new WalletHistoryAdapter(WalletActivity.this,walletItemList);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                   grandTotal(walletItemList);



                } catch (JSONException e) {
                    e.printStackTrace();

                    Toast.makeText(WalletActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

                hidepDialog();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());

                //Toast.makeText(WalletActivity.this, "no data Found. check once data is enable or not..", Toast.LENGTH_SHORT).show();
                // hide the progress dialog
                hidepDialog();


            }
        });

        //Creating request queue
        RequestQueue requestQueue = Volley.newRequestQueue(WalletActivity.this);

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

    private int grandTotal(List<WalletItem> items){

        int totalPrice = 0;
        for(int i = 0 ; i < items.size(); i++) {
            String approval =items.get(i).getStatus();
            if (approval.equalsIgnoreCase("Approved")) {

                int bonus = Integer.parseInt(items.get(i).getBonusamount());
                Log.e("bonus", "" + bonus);

                totalPrice += Integer.parseInt(items.get(i).getBonusamount());
            }

        }

        Log.e("TOTAL",""+totalPrice);
        txtTotalBalance.setText(String.valueOf(totalPrice));

        return totalPrice;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_wallet, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //finish();
                onBackPressed();
                break;

            case R.id.action_settings:

                // custom dialog
                final Dialog dialog = new Dialog(WalletActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.setContentView(R.layout.about_pivotal_layout);

                // set the custom dialog components - text, image and button
            ImageView image = (ImageView) dialog.findViewById(R.id.imageView);
            image.setImageResource(R.drawable.terms);

                dialog.show();

                break;
        }
        return true;
    }
}
