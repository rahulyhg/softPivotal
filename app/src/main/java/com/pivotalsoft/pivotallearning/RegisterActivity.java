package com.pivotalsoft.pivotallearning;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.google.android.gms.common.api.GoogleApiClient;
import com.pivotalsoft.pivotallearning.Adapters.OffersAdapter;
import com.pivotalsoft.pivotallearning.Api.Apis;
import com.pivotalsoft.pivotallearning.Items.OffersItem;
import com.pivotalsoft.pivotallearning.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RegisterActivity extends Activity implements View.OnClickListener {
    private static final String TAG = RegisterActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    EditText etFullName, etEmail, etMobile, etPassword, etArea, etCity, etReferalcode;
    String fullname, email, mobile, password, area, city, currentDateandTime,currentDate, referralCode;
    int myReferralcode;
    Button btnSignUp;
    TextView txtLogin;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        /*getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Register");*/

        /*String emailId = getIntent().getExtras().getString("emailId");
        Log.e("email",""+emailId);
        String fullName = getIntent().getExtras().getString("fullName");*/

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss aa");
        currentDateandTime = sdf.format(new Date());
        Log.e("timestamp:", "" + currentDateandTime);

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        currentDate = sdf1.format(new Date());
        Log.e("timestamp:", "" + currentDate);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        etFullName = (EditText) findViewById(R.id.etFullName);
        //  etFullName.setText(fullName);

        etEmail = (EditText) findViewById(R.id.etEmail);
        // etEmail.setText(emailId);

        etMobile = (EditText) findViewById(R.id.etMobile);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etArea = (EditText) findViewById(R.id.etArea);
        etCity = (EditText) findViewById(R.id.etCity);
        etReferalcode = (EditText) findViewById(R.id.etRefferalCode);

        txtLogin = (TextView) findViewById(R.id.txtLogin);
        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent welcome = new Intent(RegisterActivity.this, LoginActivity.class);
                welcome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(welcome);
            }
        });

        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        fullname = etFullName.getText().toString().trim();
        email = etEmail.getText().toString().trim();
        mobile = etMobile.getText().toString().trim();
        password = etPassword.getText().toString().trim();
        area = etArea.getText().toString().trim();
        city = etCity.getText().toString().trim();
        referralCode = etReferalcode.getText().toString().trim();

        // random no generater
        Random r = new Random();
        myReferralcode = r.nextInt(800000 - 650000) + 65;
        Log.e("REFEREAL", "" + String.valueOf(myReferralcode) + "   " + referralCode);


        if (!fullname.isEmpty() && !email.isEmpty() && !mobile.isEmpty() && !password.isEmpty() && !area.isEmpty() && !city.isEmpty()) {

            if (referralCode.isEmpty()) {
                registerUser();
            } else {
                checkRefercode(referralCode);
            }

        } else {
            Toast.makeText(getApplicationContext(), "Please enter your details!", Toast.LENGTH_LONG).show();
        }


    }

    // user register
    private void registerUser() {

        pDialog.setMessage("Registering ...");
        showDialog();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Apis.REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("RESPONSE : ", "" + response);
                        hideDialog();

                        addMoneytoWallet(email);

                        alertDilogue();

                        // Toast.makeText(RegisterActivity.this,"Registration Successfully",Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("RESPONSE_ERROR: ", "" + error);
                        hideDialog();
                        Toast.makeText(RegisterActivity.this, "Email Already Exist", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("fullname", fullname);
                params.put("email", email);
                params.put("mobile", mobile);
                params.put("password", password);
                params.put("area", area);
                params.put("city", city);
                params.put("lastlogintime", currentDateandTime);
                params.put("referralcode", referralCode);
                params.put("myreferralcode", String.valueOf(myReferralcode));

                Log.e("DataRegisterdfsd :", "" + params);

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

   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //finish();

                onBackPressed();
                break;
        }
        return true;
    }*/

    // check refer code
    private void checkRefercode(final String referralCode) {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                Apis.CHECK_REFER_CODE_URL + referralCode, null, new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                Log.e("URL", "" + Apis.CHECK_REFER_CODE_URL + referralCode);

                try {
                    // Parsing json object response
                    // response will be a json object
                    /*offersItemList.clear();*/

                    //Get the instance of JSONArray that contains JSONObjects
                    JSONArray jsonArray1 = response.optJSONArray("referraldata");

                    //Iterate the jsonArray and print the info of JSONObjects
                   /* for (int i = 0; i < jsonArray1.length(); i++) {*/

                    JSONObject jsonObject = jsonArray1.getJSONObject(0);

                    String regno = jsonObject.optString("regno").toString();
                    String fullname = jsonObject.optString("fullname").toString();
                    String area = jsonObject.optString("area").toString();
                    String city = jsonObject.optString("city").toString();
                    String email = jsonObject.optString("email").toString();
                    String mobile = jsonObject.optString("mobile").toString();
                    String referralcode = jsonObject.optString("referralcode").toString();
                    String myreferralcode = jsonObject.optString("myreferralcode").toString();
                    String password = jsonObject.optString("password").toString();
                    String lastlogintime = jsonObject.optString("lastlogintime").toString();
                    String role = jsonObject.optString("role").toString();
                    String profilepicurl = jsonObject.optString("profilepicurl").toString();
                    String staffid = jsonObject.optString("staffid").toString();

                    JSONObject messageObject = new JSONObject(response.toString());
                    String message = messageObject.getString("message");
                    Log.e("msg", "" + message);

                    if (message.equals("success")) {

                        Log.e("MSG", "" + "success");

                        registerUser();
                        addMoneytoReferral(email);


                    } else {
                        etReferalcode.setError("Invalid Referral code");
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(RegisterActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                etReferalcode.setError("Invalid Referral code");
                // Toast.makeText(RegisterActivity.this, "no data Found. check once data is enable or not..", Toast.LENGTH_SHORT).show();
                // hide the progress dialog

            }
        });

        //Creating request queue
        RequestQueue requestQueue = Volley.newRequestQueue(RegisterActivity.this);

        //Adding request to the queue
        requestQueue.add(jsonObjReq);

    }

    // alert dilogue
    private void alertDilogue() {

        // custom dialog
        final Dialog dialog = new Dialog(RegisterActivity.this, android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.register_dilogue);
        dialog.setCancelable(false);

        Button dialogButton = (Button) dialog.findViewById(R.id.btnDone);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pivotal = new Intent(RegisterActivity.this, LoginActivity.class);
                pivotal.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(pivotal);
            }
        });

        dialog.show();
    }

    // referral from friends
    private void addMoneytoReferral(final String useremail) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Apis.REFERRAL_MONEY_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("RESPONSE : ", "" + response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("RESPONSE_ERROR: ", "" + error);
                        //Toast.makeText(RegisterActivity.this,"Email Already Exist",Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("useremail", useremail);
                params.put("bonustype", "Referral");
                params.put("bonusamount", "1000");
                params.put("status", "Pending");
                params.put("description", "Referral Bonus from " + fullname);
                params.put("bonusdate", currentDate);

                Log.e("referralBonus :", "" + params);

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    // referral from friends
    private void addMoneytoWallet(final String useremail) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Apis.REFERRAL_MONEY_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("RESPONSE : ", "" + response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("RESPONSE_ERROR: ", "" + error);
                        //Toast.makeText(RegisterActivity.this,"Email Already Exist",Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("useremail", useremail);
                params.put("bonustype", "Registration");
                params.put("bonusamount", "2000");
                params.put("status", "Approved");
                params.put("description", "Self Registration Bonus" );
                params.put("bonusdate", currentDate);

                Log.e("referralBonus :", "" + params);

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}

