package com.bytedance.todolist.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;

import com.bytedance.todolist.database.TodoListDao;
import com.bytedance.todolist.database.TodoListDatabase;
import com.bytedance.todolist.database.TodoListEntity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.bytedance.todolist.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.Date;
import java.util.List;

public class TodoListActivity extends AppCompatActivity {

    private TodoListAdapter mAdapter;
    private FloatingActionButton mFab;
    String TAG = "TodoListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo_list_activity_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Log.d(TAG,"onCreate");

        RecyclerView recyclerView = findViewById(R.id.rv_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new TodoListAdapter();
        mAdapter.setOnItemClickListener(TodoListClickListener);
        recyclerView.setAdapter(mAdapter);

        mFab = findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO
                Intent intent = new Intent();
                intent.setClass(TodoListActivity.this,TakeNote.class);
                startActivityForResult(intent,1);
//                confirm = findViewById(R.id.confirm_button);
//                editText = findViewById(R.id.input_content);
//                confirm.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        note = editText.getText().toString();
//                        Log.d(TAG,note);
//                    }
//                });
            }
        });

        mFab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new Thread() {
                    @Override
                    public void run() {
                        TodoListDao dao = TodoListDatabase.inst(TodoListActivity.this).todoListDao();
                        dao.deleteAll();
                        for (int i = 0; i < 20; ++i) {
                            dao.addTodo(new TodoListEntity(0,"This is " + i + " item", new Date(System.currentTimeMillis())));
                        }
                        Snackbar.make(mFab, R.string.hint_insert_complete, Snackbar.LENGTH_SHORT).show();
                    }
                }.start();
                return true;
            }
        });

        loadFromDatabase();
        Log.d(TAG,"log");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //此处可以根据两个Code进行判断，本页面和结果页面跳过来的值
        if (requestCode == 1 && resultCode == 3) {
            final String note = data.getStringExtra("result");
            Log.d(TAG,note);
            new Thread() {
                @Override
                public void run() {
                    TodoListDao dao = TodoListDatabase.inst(TodoListActivity.this).todoListDao();
                    dao.addTodo(new TodoListEntity(0,note, new Date(System.currentTimeMillis())));
                    Log.d(TAG,"add note");
                    Snackbar.make(mFab, R.string.insert_note, Snackbar.LENGTH_SHORT).show();

                    loadFromDatabase();
                    Log.d(TAG,"log "+note);
                }
            }.start();
        }
    }

    private TodoListAdapter.IOnItemClickListener TodoListClickListener = new TodoListAdapter.IOnItemClickListener() {
        @Override
        public void onItemCLick(final View view, TodoListAdapter.ViewName viewName, final int position) {
            if(view == null) return;
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    TodoListDao dao = TodoListDatabase.inst(TodoListActivity.this).todoListDao();
                    switch (view.getId()) {
                        case R.id.ck_box:
                            if(((CheckBox)view).isChecked()) {
                                Log.d(TAG,"update status to checked");
                                dao.update(1,mAdapter.mDatas.get(position).getId());
                            }
                            else {
                                dao.update(0,mAdapter.mDatas.get(position).getId());
                            }
                            break;
                        case R.id.clear:
                            dao.deleteItem(mAdapter.mDatas.get(position));
                    }
                    loadFromDatabase();
                }
            }.start();
        }
    };

    private void loadFromDatabase() {
        new Thread() {
            @Override
            public void run() {
                TodoListDao dao = TodoListDatabase.inst(TodoListActivity.this).todoListDao();
                final List<TodoListEntity> entityList = dao.loadAll();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.setData(entityList);
                    }
                });
            }
        }.start();
    }
}
