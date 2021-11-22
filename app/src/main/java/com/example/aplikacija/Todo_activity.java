package com.example.aplikacija;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class Todo_activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private String title, mognoId;
    private boolean completed;
    private int position, id, userId;

    private EditText titleView, idView, userIdView;
    public Spinner spinner;
    private CheckBox completedView;

    private final String URL = "http://" + getResources().getString(R.string.HOST) +"/post/update";
    RequestQueue queue;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        //spinner
        spinner = findViewById(R.id.items);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.items, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        Intent intent = getIntent();
        queue = Volley.newRequestQueue(this);

        mognoId = intent.getStringExtra("_id");

        title = intent.getStringExtra("title");
        titleView = findViewById(R.id.title);
        titleView.setText(title.toString().trim());

        id = intent.getIntExtra("id", 0);
        idView = findViewById(R.id.id);
        idView.setText(Integer.toString(id));

        userId = intent.getIntExtra("userId", 0);
        userIdView = findViewById(R.id.userId);
        userIdView.setText(Integer.toString(userId));

        completed = intent.getBooleanExtra("completed", false);
        completedView = findViewById(R.id.completed);
        completedView.setChecked(completed);
        completedView.setEnabled(true);

        position = intent.getIntExtra("position", -1);


    }

    public void update(View view){
       Intent intent = new Intent(this, Datas.class);

        JSONObject body = new JSONObject();
        String _title =  titleView.getText().toString().trim();
        String a11 =  idView.getText().toString();
        int _id = Integer.parseInt(a11);
        String a22 = userIdView.getText().toString();
        int _userId = Integer.parseInt(a22);

        boolean _completed =completedView.isChecked();

        try {
            body.put("_id", mognoId);
            body.put("id", _id);
            body.put("userId", _userId);
            body.put("title", _title);
            body.put("completed", _completed);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PATCH, URL, body, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                startActivity(intent);
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
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}