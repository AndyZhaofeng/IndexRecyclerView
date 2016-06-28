package com.zfeng.tools.indexrecyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.zfeng.tools.indexrecyclerview.widget.IndexAdapter;
import com.zfeng.tools.indexrecyclerview.widget.IndexRecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private IndexRecyclerView indexRecyclerView;
    private IndexAdapter indexAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        indexRecyclerView=(IndexRecyclerView)findViewById(R.id.indexrecyclerview);
        ArrayList<String> lists=new ArrayList<String>();
        lists.add("An");
        lists.add("interesting");
        lists.add("alternative");
        lists.add("to");
        lists.add("the");
        lists.add("main");
        lists.add("npm");
        lists.add("repository");
        lists.add("search");
        lists.add("built");
        lists.add("by");
        lists.add("Bower");
        lists.add("Cruz");
        lists.add("certainly");
        lists.add("fast");
        lists.add("Numerous");
        lists.add("Node");
        lists.add("users");
        lists.add("are");
        lists.add("reporting");
        lists.add("there");
        lists.add("major");
        lists.add("issue");
        lists.add("that");
        lists.add("breaks");
        lists.add("package");
        lists.add("publishing");
        lists.add("from");
        lists.add("Node");
        lists.add("For");
        lists.add("now");
        lists.add("switch");
        lists.add("to");
        lists.add("Node");
        lists.add("before");
        lists.add("publishing");
        lists.add("MainActivity");
        lists.add("赵峰");
        lists.add("判断");
        lists.add("中文");
        lists.add("利用");
        indexAdapter=new IndexAdapter(this,lists);
        indexRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        indexRecyclerView.setAdapter(indexAdapter);
        indexRecyclerView.setLists(lists);
    }
}
