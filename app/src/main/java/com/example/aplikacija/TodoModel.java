package com.example.aplikacija;

public class TodoModel {
    private int userId;
    private int id;
    private String title;
    private Boolean completed;
    private String _id;

    TodoModel(int userId, int id, String title, boolean completed, String _id){
        this._id = _id;
        this.userId = userId;
        this.id = id;
        this.title = title;
        this.completed = completed;
    }

    public int getUserId(){
        return userId ;
    }

    public int getId(){
        return id ;
    }

    public String getTitle(){
        return title ;
    }

    public Boolean getCompleted(){
        return completed ;
    }

    public String get_Id(){
        return _id ;
    }


}
