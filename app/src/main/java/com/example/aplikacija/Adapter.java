package com.example.aplikacija;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private List<TodoModel> todoList;
    private OnNoteListener mOnNoteListener;
    public Adapter (List<TodoModel>todoList, OnNoteListener onNoteListener){this.todoList=todoList; this.mOnNoteListener = onNoteListener;}




    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_design, parent, false);
        return new ViewHolder(view, mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
        int _userId = todoList.get(position).getUserId();
        int _id = todoList.get(position).getId();
        String _title = todoList.get(position).getTitle();
        Boolean _completed = todoList.get(position).getCompleted();

        holder.setData(_userId, _id, _title, _completed);

    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView userIdView;
        private TextView idView;
        private TextView titleView;
        private CheckBox completedView;

        OnNoteListener onNoteListener;

        public ViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);

            userIdView = itemView.findViewById(R.id.userId);
            idView = itemView.findViewById(R.id.id);
            titleView = itemView.findViewById(R.id.title);
            completedView = itemView.findViewById(R.id.completed);

            this.onNoteListener = onNoteListener;
            itemView.setOnClickListener(this);

        }

        public void setData(int userId, int id, String title, Boolean completed) {
            userIdView.setText(Integer.toString(userId));
            idView.setText(Integer.toString(id));
            titleView.setText(title);
            completedView.setChecked(completed);

            completedView.setEnabled(false);
        }

        @Override
        public void onClick(View view) {
            onNoteListener.onNoteClick(getBindingAdapterPosition());
        }
    }

    public interface OnNoteListener{
        void onNoteClick(int position);
    }
}
