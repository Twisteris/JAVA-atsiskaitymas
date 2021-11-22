package com.example.aplikacija;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class CreateTodo extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText titleView, idView, userIdView;
    private CheckBox completedView;


    private String URL;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_todo);
        titleView = findViewById(R.id.title);
        idView = findViewById(R.id.id);
        userIdView = findViewById(R.id.userId);
        completedView = findViewById(R.id.completed);

        queue = Volley.newRequestQueue(this);

        URL = "http://" + getResources().getString(R.string.HOST) + "/post/create";
    }

    public void create(View view){
        JSONObject body = new JSONObject();


        try {
            body.put("id",Integer.parseInt(idView.getText().toString()));
            body.put("userId", Integer.parseInt(idView.getText().toString()));
            body.put("title", titleView.getText().toString().trim());
            body.put("completed", completedView.isChecked());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, body, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();
            }
        });

// Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}