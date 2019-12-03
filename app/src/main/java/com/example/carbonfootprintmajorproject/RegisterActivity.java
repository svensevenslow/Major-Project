package com.example.carbonfootprintmajorproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private Button register;
    private EditText name;
    private EditText email;
    private EditText contact;
    private EditText password;
    private EditText vid;
    String responseCode = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register = findViewById(R.id.button);
        name = findViewById(R.id.editText6);
        email =findViewById(R.id.editText4);
        contact =findViewById(R.id.editText3);
        password =findViewById(R.id.editText5);
        vid = findViewById(R.id.editText7);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String nameVal = name.getText().toString().trim();
                    String emailVal = email.getText().toString().trim();
                    String contactVal = contact.getText().toString().trim();
                    String passwordVal = password.getText().toString().trim();
                    String vidVal = vid.getText().toString().trim();
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    String URL = getString(R.string.localhost)+"/auth/register";
                    JSONObject body = new JSONObject();
                    body.put("name", nameVal);
                    body.put("email", emailVal);
                    body.put("contact", contactVal);
                    body.put("password", passwordVal);
                    body.put("vid", vidVal);
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
                                try {
                                    String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                                    JSONObject jsonObject = new JSONObject(json);
                                    String token = jsonObject.getString("token");
                                    SharedPreferences sharedPref = RegisterActivity.this.getPreferences(Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPref.edit();
                                    editor.putString(getString(R.string.access_token), token);
                                    editor.commit();
                                }catch(Exception e){

                                }
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
                    //Redirect to next activity
                    Toast toast = Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_LONG);
                    toast.show();
                }else{
                    Toast toast = Toast.makeText(getApplicationContext(), "An error occurred, please try again. Status code returned" + responseCode, Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
    }
}


