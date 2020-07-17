package com.example.minidouyin.adapter.recycler;

import com.example.minidouyin.R;

import java.util.ArrayList;
import java.util.List;


public class TestDataSet {

    public static List<TestData> getData() {
        List<TestData> result = new ArrayList();

        result.add(new TestData( R.drawable.a,"A","000","20:00"));
        result.add(new TestData( R.drawable.b, "B","111","19:00"));
        result.add(new TestData( R.drawable.c, "C","222","18:00"));
        result.add(new TestData( R.drawable.d, "D","333","17:00"));
        result.add(new TestData( R.drawable.e, "E","444","16:00"));

        result.add(new TestData( R.drawable.a, "F","555","15:00"));
        result.add(new TestData( R.drawable.b, "G","666","14:00"));
        result.add(new TestData( R.drawable.c, "H","777","13:00"));
        result.add(new TestData( R.drawable.d, "I","888","12:00"));
        result.add(new TestData( R.drawable.e, "J","999","11:00"));

        result.add(new TestData( R.drawable.a, "K","000","10:00"));
        result.add(new TestData( R.drawable.b, "L","111","09:00"));
        result.add(new TestData( R.drawable.c, "M","222","08:00"));
        result.add(new TestData( R.drawable.d, "N","333","07:00"));
        result.add(new TestData( R.drawable.e,  "O","444","06:00"));

        result.add(new TestData( R.drawable.a,"P","555","05:00"));
        result.add(new TestData( R.drawable.b, "Q","666","04:00"));
        result.add(new TestData( R.drawable.c, "R","777","03:00"));
        result.add(new TestData( R.drawable.d,"S","888","02:00"));
        result.add(new TestData( R.drawable.e, "T","999","01:00"));

        return result;
    }

}
