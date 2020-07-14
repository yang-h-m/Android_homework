package com.bytedance.todolist.activity;

import android.graphics.Color;
import android.graphics.Paint;
import android.media.Image;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bytedance.todolist.R;
import com.bytedance.todolist.database.TodoListDao;
import com.bytedance.todolist.database.TodoListDatabase;
import com.bytedance.todolist.database.TodoListEntity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wangrui.sh
 * @since Jul 11, 2020
 */
public class TodoListItemHolder extends RecyclerView.ViewHolder {
    private TextView mContent;
    private TextView mTimestamp;
    private CheckBox checkBox;
    private View contentView;
    String TAG = "TodoListActivity";

    public TodoListItemHolder(@NonNull View itemView) {
        super(itemView);
        contentView = itemView;
        mContent = itemView.findViewById(R.id.tv_content);
        mTimestamp = itemView.findViewById(R.id.tv_timestamp);
        checkBox = itemView.findViewById(R.id.ck_box);
    }

    public void bind(TodoListEntity entity) {
        mContent.setText(entity.getContent());
        if(entity.getState() == 1) {
            Log.d(TAG,"checked");
            mContent.setTextColor(Color.GRAY);
            mContent.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            mContent.getPaint().setAntiAlias(true);
        }
        else {
            Log.d(TAG,"unchecked");
            mContent.setTextColor(Color.BLACK);
            mContent.setPaintFlags(0);
        }
        mTimestamp.setText(formatDate(entity.getTime()));
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    mContent.setTextColor(Color.GRAY);
                    mContent.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                    mContent.getPaint().setAntiAlias(true);
                }
                else {
                    mContent.setTextColor(Color.BLACK);
                    mContent.setPaintFlags(0);
                }
            }
        });
    }

    public void setOnClickListener(View.OnClickListener listener) {
        if (listener != null) {
            contentView.setOnClickListener(listener);
        }
    }

    private String formatDate(Date date) {
        DateFormat format = SimpleDateFormat.getDateInstance();
        return format.format(date);
    }
}
