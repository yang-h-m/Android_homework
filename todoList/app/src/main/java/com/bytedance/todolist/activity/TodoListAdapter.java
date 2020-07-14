package com.bytedance.todolist.activity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bytedance.todolist.R;
import com.bytedance.todolist.database.TodoListEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangrui.sh
 * @since Jul 11, 2020
 */
public class TodoListAdapter extends RecyclerView.Adapter<TodoListItemHolder> {
    protected List<TodoListEntity> mDatas;
    private IOnItemClickListener mItemClickListener;
    String TAG = "TodoListActivity";
    public enum ViewName {CHECKBOX, CLEAR}

    public TodoListAdapter() {
        mDatas = new ArrayList<>();
    }

    public void setOnItemClickListener(IOnItemClickListener listener) {
        mItemClickListener = listener;
    }

    @NonNull
    @Override
    public TodoListItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TodoListItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TodoListItemHolder holder, int position) {
        holder.bind(mDatas.get(position));
        holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(v instanceof CheckBox || v instanceof ImageView)) {
                    Log.d(TAG,"return");
                    return;
                }
                int position = (int) v.getTag();
                if(mItemClickListener != null) {
                    switch (v.getId()) {
                        case R.id.ck_box:
                            Log.d(TAG,"checkbox");
                            mItemClickListener.onItemCLick(v,ViewName.CHECKBOX,position);
                            break;
                        case R.id.clear:
                            Log.d(TAG,"clear this item");
                            mItemClickListener.onItemCLick(v,ViewName.CLEAR,position);
                            break;
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @MainThread
    public void setData(List<TodoListEntity> list) {
        mDatas = list;
        notifyDataSetChanged();
    }

    public interface IOnItemClickListener {
        void onItemCLick(View view, ViewName viewName, int position);
    }
}
