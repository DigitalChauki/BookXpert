package com.example.bookxpert;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Formattable;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView table_rclView;
    BookExpertAdapter bookExpertAdapter;
    float totalAmount = 0.0f;
    List<BookXpertModelData> data;
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
  //  private String url = "https://fssservices.bookxpert.co/api/getownerslist/2021-01-16/payments/owner";
    private String data1 = "[{\"ActID\":845,\"ActName\":\"Current Liabilities\",\"amount\":0.0},{\"ActID\":387,\"ActName\":\"K.Ramalinga Raju - Capital\",\"amount\":0.0},{\"ActID\":728,\"ActName\":\"Krlr - Airport Land\",\"amount\":0.0},{\"ActID\":746,\"ActName\":\"Krlr - Airport Land Polls\",\"amount\":0.0},{\"ActID\":763,\"ActName\":\"Krlr - Giet Land Cement Polls\",\"amount\":0.0},{\"ActID\":770,\"ActName\":\"krlr - Matha Projects Llp\",\"amount\":0.0},{\"ActID\":490,\"ActName\":\"KRLR - Tadipudi Fish Ponds\",\"amount\":0.0},{\"ActID\":586,\"ActName\":\"Krlr Palm Oil Plantation\",\"amount\":0.0},{\"ActID\":493,\"ActName\":\"Krlr Travelling\",\"amount\":0.0},{\"ActID\":805,\"ActName\":\"KRLR-DRAWINGS\",\"amount\":0.0},{\"ActID\":821,\"ActName\":\"KRLR-Petty Expenses\",\"amount\":0.0},{\"ActID\":401,\"ActName\":\"Kvsn Raju\",\"amount\":0.0},{\"ActID\":7,\"ActName\":\"Profit & Loss - Current Year\",\"amount\":0.0},{\"ActID\":8,\"ActName\":\"Profit & Loss - Previous Year\",\"amount\":0.0}]";
    private JSONArray jsonarray;
    private TextView tv_totalAmount;
    Button btn_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        table_rclView = findViewById(R.id.table_rclView);
        data = new ArrayList<>();
        tv_totalAmount = findViewById(R.id.tv_totalAmount);
        btn_next = findViewById(R.id.btn_next);
        //sendAndRequestResponse();
        callStringUrl();
        callRecyclerView();
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent NextScreen = new Intent(MainActivity.this, NextScreen.class);
                startActivity(NextScreen);
            }
        });


    }

    private void callStringUrl() {
        try {
            JSONArray jsonArray = new JSONArray(data1);
            for(int i = 0;i<jsonArray.length();i++){
                JSONObject obj = jsonArray.getJSONObject(i);
                String ActID = obj.getString("ActID");
                String ActName = obj.getString("ActName");
                String ActAmount = obj.getString("amount");
                data.add(new BookXpertModelData(ActID,ActName,ActAmount));
                try {
                    totalAmount = Float.parseFloat(totalAmount + obj.getString("amount"));

                }catch (NumberFormatException e){
                    e.printStackTrace();
                }

               // callRecyclerView();

            }
            tv_totalAmount.setText((String.valueOf(totalAmount)));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    /*private void sendAndRequestResponse() {

        mRequestQueue = Volley.newRequestQueue(this);
        mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("ResponseResult","Success :" + response);
                try {
                    jsonarray = new JSONArray(response);
                    for(int i= 0 ; i<jsonarray.length();i++){
                        JSONObject obj = jsonarray.getJSONObject(i);
                        String ActID = obj.getString("ActID");
                        String ActName = obj.getString("ActName");
                        String ActAmount = obj.getString("amount");
                        data.add(new BookXpertModelData(ActID,ActName,ActAmount));
                        callRecyclerView();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


               // Toast.makeText(getApplicationContext(),"Response :" + response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i("TAG","Error :" + error.toString());
                Toast.makeText(getApplicationContext(),"Response :" + error, Toast.LENGTH_SHORT).show();

            }
        });

        mRequestQueue.add(mStringRequest);
    }*/

    private void callRecyclerView() {
        bookExpertAdapter = new BookExpertAdapter(this , data );
        table_rclView.setLayoutManager(new LinearLayoutManager(this));
        table_rclView.setHasFixedSize(true);
        table_rclView.setAdapter(bookExpertAdapter);


    }


}