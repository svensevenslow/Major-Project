package com.example.carbonfootprintmajorproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class LoginActivity extends AppCompatActivity {

    private Button login;
    private EditText email;
    private EditText password;
    String responseCode = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.button2);

        email =findViewById(R.id.editText);

        password =findViewById(R.id.editText2);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    String emailVal = email.getText().toString().trim();
                    String passwordVal = password.getText().toString().trim();
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    String URL = getString(R.string.localhost)+"/auth/loginWithCredentials";
                    JSONObject body = new JSONObject();
                    body.put("email", emailVal);
                    body.put("password", passwordVal);

                    final String requestBody = body.toString();

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.i("VOLLEY", response);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("VOLLEY", error.toString());
                        }
                    }) {
                        @Override
                        public String getBodyContentType() {
                            return "application/json; charset=utf-8";
                        }

                        @Override
                        public byte[] getBody() throws AuthFailureError {
                            try {
                                return requestBody == null ? null : requestBody.getBytes("utf-8");
                            } catch (UnsupportedEncodingException uee) {
                                VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                                return null;
                            }
                        }

                        @Override
                        protected Response<String> parseNetworkResponse(NetworkResponse response) {
                            if (response != null) {
                                responseCode = String.valueOf(response.statusCode);
                                // can get more details such as response.headers
                            }
                            return Response.success(responseCode, HttpHeaderParser.parseCacheHeaders(response));
                        }
                    };
                    stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                            10000,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    requestQueue.add(stringRequest);

                }catch(JSONException e){
                    e.printStackTrace();
                }
                if(responseCode.endsWith("200")){
                    Intent i = new Intent(getApplicationContext(), HomePageActivity.class);
                    startActivity(i);
                    Toast toast = Toast.makeText(getApplicationContext(), "Successful Login", Toast.LENGTH_LONG);
                    toast.show();
                }else{
                    Toast toast = Toast.makeText(getApplicationContext(), "An error occurred, please try again. Status code returned" + responseCode, Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
    }
}
