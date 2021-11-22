package com.example.aplikacija;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Datas extends AppCompatActivity implements Adapter.OnNoteListener {

    Intent intent = getIntent();

    private SearchView searchView;

    //recycler
    RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private List<TodoModel> todoList;
    private RecyclerView.Adapter adapter;

    private String title;
    private int id;
    private boolean completed;
    private int position;
    private String _id;

    private  String URL;
    private  String URLSEARCH;
//    private final String URL = "http://87.247.83.215:3001/post/posts";
//    private final String URLSEARCH = "http://87.247.83.215:3001/post/getSearch/";
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datas);

        URL = "http://" + getResources().getString(R.string.HOST) +"/post/posts";
        URLSEARCH = "http://" + getResources().getString(R.string.HOST) +"/post/getSearch";

        queue = Volley.newRequestQueue(this);

        intent = getIntent();
        initData();

        //search bar
        searchView = (SearchView) findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String text) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                initDataSearch(newText);
                return false;
            }
        });

    }


    private void initData() {
        todoList = new ArrayList<>();


        queue.add( new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            JSONArray data = response.getJSONArray("data");
                            for(int i=0;i<data.length();i++){
                                JSONObject todo = data.getJSONObject(i);

                                String _id = (String) todo.get("_id");
                                int userId = todo.getInt("userId");
                                int id = todo.getInt("id");
                                String title = todo.getString("title");
                                boolean completed = todo.getBoolean("completed");

                                todoList.add(new TodoModel(userId, id, title,completed,_id));
                            }
                            initRecyclerView();
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        System.out.println(error);
                    }
                }
        ));
    }

    private void initDataSearch(String text){
        if(text.trim().toString().isEmpty()){
            initData();
            return;
        }
        todoList = new ArrayList<>();
        queue.add( new JsonObjectRequest(
                Request.Method.GET,
                URLSEARCH + text,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            JSONArray data = response.getJSONArray("data");
                            for(int i=0;i<data.length();i++){
                                JSONObject todo = data.getJSONObject(i);

                                String _id = (String) todo.get("_id");
                                int userId = todo.getInt("userId");
                                int id = todo.getInt("id");
                                String title = todo.getString("title");
                                boolean completed = todo.getBoolean("completed");

                                todoList.add(new TodoModel(userId, id, title,completed,_id));
                            }
                            initRecyclerView();
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        System.out.println("-----------------------" + error);
                    }
                }
        ));

    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new Adapter(todoList, this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }


    @Override
    public void onNoteClick(int position) {
        Intent intent = new Intent(this, Todo_activity.class);


        intent.putExtra("_id", todoList.get(position).get_Id());
        intent.putExtra("title", todoList.get(position).getTitle());
        intent.putExtra("userId", todoList.get(position).getUserId());
        intent.putExtra("id", todoList.get(position).getId());
        intent.putExtra("completed", todoList.get(position).getCompleted());
        intent.putExtra("position",position);


        startActivity(intent);
    }
}