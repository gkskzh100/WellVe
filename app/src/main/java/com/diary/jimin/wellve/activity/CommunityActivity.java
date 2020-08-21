package com.diary.jimin.wellve.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.diary.jimin.wellve.fragment.Page1Fragment;
import com.diary.jimin.wellve.fragment.Page2Fragment;
import com.diary.jimin.wellve.fragment.Page3Fragment;
import com.diary.jimin.wellve.fragment.Page4Fragment;
import com.diary.jimin.wellve.fragment.Page5Fragment;
import com.diary.jimin.wellve.R;
import com.diary.jimin.wellve.adapter.VPAdapter;
import com.google.android.material.tabs.TabLayout;

public class CommunityActivity extends AppCompatActivity {


    private ViewPager vp;
    private VPAdapter vpAdapter;
    private TabLayout tab;
    private Button communitySearchButton;
    private Button communityWriteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);


        vp = findViewById(R.id.categoryViewPager);
        communitySearchButton = findViewById(R.id.communitySearchButton);
        communityWriteButton = findViewById(R.id.communityWriteButton);
        getTabs();


        communityWriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CommunityActivity.this, PostActivity.class);
                startActivity(intent);
            }
        });


    }

    public void getTabs() {

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                vpAdapter = new VPAdapter(getSupportFragmentManager());

                vpAdapter.addFragment(Page1Fragment.getInstance(),"전체");
                vpAdapter.addFragment(Page2Fragment.getInstance(),"자유");
                vpAdapter.addFragment(Page3Fragment.getInstance(),"QnA");
                vpAdapter.addFragment(Page4Fragment.getInstance(),"식당");
                vpAdapter.addFragment(Page5Fragment.getInstance(),"문학");

                vp.setAdapter(vpAdapter);

                tab = findViewById(R.id.tab);
                tab.setupWithViewPager(vp);
            }
        });


    }
}
