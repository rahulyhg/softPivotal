package com.pivotalsoft.pivotallearning;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.pivotalsoft.pivotallearning.Api.Apis;
import com.pivotalsoft.pivotallearning.R;
import com.pivotalsoft.pivotallearning.helper.Session;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends Activity implements View.OnClickListener {
    private Session session;
    EditText etEmail,etPassword;
    Button btnSignIn;
    // Progress dialog
    private ProgressDialog pDialog;

    private static String TAG = LoginActivity.class.getSimpleName();

    private String URL_LOGIN;
    TextView txtRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        session = new Session(this);

        if(session.loggedin()){
            startActivity(new Intent(LoginActivity.this,HomeActivity.class));
            finish();
        }

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);


        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);

        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        btnSignIn.setOnClickListener(this);

        txtRegister = (TextView) findViewById(R.id.txtRegister);
        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent register = new Intent(LoginActivity.this, RegisterActivity.class);
                register.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(register);

            }
        });

    }

    @Override
    public void onClick(View v) {

        final String email = etEmail.getText().toString().trim();
        final String password = etPassword.getText().toString().trim();

        URL_LOGIN =Apis.LOGIN_URL+"/"+email+"/"+password;

        Log.e("loginurl",""+URL_LOGIN);


        if (!email.isEmpty()  && !password.isEmpty()) {

            loginUser();

        } else {
            Toast.makeText(getApplicationContext(), "Please enter your details!", Toast.LENGTH_LONG).show();
        }
    }

    private void loginUser() {

        showpDialog();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                URL_LOGIN, null, new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                Log.e("URL",""+URL_LOGIN);

                try {
                    // Parsing json object response
                    // response will be a json object

                    //Get the instance of JSONArray that contains JSONObjects
                    JSONArray jsonArray1 = response.optJSONArray("userdata");

                    //Iterate the jsonArray and print the info of JSONObjects
                   // for (int i = 0; i < jsonArray1.length(); i++) {

                        JSONObject jsonObject = jsonArray1.getJSONObject(0);

                        String email = jsonObject.optString("email").toString();
                        String fullname = jsonObject.optString("fullname").toString();
                        String mobile = jsonObject.optString("mobile").toString();
                        String area = jsonObject.optString("area").toString();
                        String city = jsonObject.optString("city").toString();
                        String password = jsonObject.optString("password").toString();
                        String lastlogintime = jsonObject.optString("lastlogintime").toString();
                        String regNo = jsonObject.optString("regno").toString();
                        String referralcode = jsonObject.optString("referralcode").toString();
                        String myreferralcode = jsonObject.optString("myreferralcode").toString();
                        String role = jsonObject.optString("role").toString();
                        String staffid = jsonObject.optString("staffid").toString();
                        String profilepicurl = Apis.IMAGE_PROFILE_URL + jsonObject.optString("profilepicurl").toString();



                       JSONObject messageObject =new JSONObject(response.toString());
                       String message =messageObject.getString("message");
                       Log.e("msg",""+message);

                    if (message.equals("login successfully..")){

                        session.setLoggedin(true);

                        Intent pivotal = new Intent(LoginActivity.this, HomeActivity.class);
                        pivotal.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(pivotal);
                        Toast.makeText(LoginActivity.this,"Login Successfully",Toast.LENGTH_LONG).show();


                        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();

                        editor.putString("email", email);  // Saving string
                        editor.putString("fullname", fullname);  // Saving string
                        editor.putString("mobile", mobile);  // Saving string
                        editor.putString("area", area);  // Saving string
                        editor.putString("city", city);  // Saving string
                        editor.putString("regNo", regNo);  // Saving string
                        editor.putString("profilepicurl", profilepicurl);  // Saving string
                        editor.putString("myreferralcode", myreferralcode);  // Saving string
                        // Save the changes in SharedPreferences
                        editor.commit(); // commit changes
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(LoginActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
                hidepDialog();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(LoginActivity.this, "Invalid Username/Password", Toast.LENGTH_SHORT).show();
                // hide the progress dialog
                hidepDialog();
            }
        });

        //Creating request queue
        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);

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


}
