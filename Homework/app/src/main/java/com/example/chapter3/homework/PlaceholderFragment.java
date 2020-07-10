package com.example.chapter3.homework;


import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;
import java.util.List;

public class PlaceholderFragment extends Fragment {
    private ListView listView;
    private LottieAnimationView lottie;
    private AnimatorSet animatorSet;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO ex3-3: 修改 fragment_placeholder，添加 loading 控件和列表视图控件
        Log.i("PlaceholderFragment", "onCreateView");
        View view = inflater.inflate(R.layout.fragment_placeholder, container,false);
        listView = view.findViewById(R.id.lv);
        lottie = view.findViewById(R.id.animation_view);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,getData());
        listView.setAdapter(arrayAdapter);
        return view;
    }

    private List<String> getData() {
        List<String> data = new ArrayList<String>();
        data.add("赵一");
        data.add("钱二");
        data.add("孙三");
        data.add("李四");
        data.add("周五");
        data.add("吴六");
        data.add("郑七");
        data.add("王八");
        return data;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listView.setAlpha(0f);
        getView().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 这里会在 5s 后执行
                // TODO ex3-4：实现动画，将 lottie 控件淡出，列表数据淡入
                Log.i("PlaceholderFragment","animator");
                //listView.setVisibility(View.VISIBLE);
                listView.animate().alpha(1f).setDuration(2000).setListener(null);
                lottie.animate().alpha(0f).setDuration(2000).setListener(null);
//                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(lottie,"alpha",1,0);
//                objectAnimator.setDuration(2000);
//                ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(listView,"alpha",0,1);
//                objectAnimator1.setDuration(2000);
//
//                animatorSet = new AnimatorSet();
//                animatorSet.playSequentially(objectAnimator,objectAnimator1);
            }
        }, 5000);
    }
}
