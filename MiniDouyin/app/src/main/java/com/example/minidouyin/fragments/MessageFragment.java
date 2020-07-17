package com.example.minidouyin.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.minidouyin.R;
import com.example.minidouyin.activities.GetResultActivity;
import com.example.minidouyin.adapter.MessageAdapter;
import com.example.minidouyin.adapter.recycler.LinearItemDecoration;
import com.example.minidouyin.adapter.recycler.TestData;
import com.example.minidouyin.adapter.recycler.TestDataSet;

public class MessageFragment extends Fragment implements MessageAdapter.IOnItemClickListener {
    private static final String TAG = "TAG";

    private RecyclerView recyclerView;
    private MessageAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private GridLayoutManager gridLayoutManager;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);

        recyclerView = view.findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new MessageAdapter(TestDataSet.getData());
        mAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(mAdapter);
        LinearItemDecoration itemDecoration = new LinearItemDecoration(Color.BLUE);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

        return view;
    }

    @Override
    public void onItemCLick(int position, TestData data) {
        Toast.makeText(getActivity(), "点击了第" + (position+1) + "个", Toast.LENGTH_SHORT).show();
        //mAdapter.addData(position + 1, new TestData("新增头条", "0w"));

//        Intent intent = new Intent(getActivity(), GetResultActivity.class);
//        //startActivityForResult(intent, REQUEST_CODE_1);
//        intent.putExtra("number", position+1);
//        startActivity(intent);
    }

    @Override
    public void onItemLongCLick(int position, TestData data) {

    }
}
