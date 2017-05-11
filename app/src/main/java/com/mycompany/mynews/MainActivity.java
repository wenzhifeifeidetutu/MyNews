package com.mycompany.mynews;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
//    private TextView socialTextView;
//    private TextView amusementTextView;
//    private TextView gymTextView;
//    private TextView scienceTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


//        socialTextView = (TextView) findViewById(R.id.society_news);
//        amusementTextView = (TextView) findViewById(R.id.amusement_news);
//        gymTextView = (TextView)findViewById(R.id.gym_news);
//        scienceTextView = (TextView)findViewById(R.id.technology_news);
//
//
//        socialTextView.setOnClickListener(this);
//        amusementTextView.setOnClickListener(this);
//        gymTextView.setOnClickListener(this);
//        scienceTextView.setOnClickListener(this);
        ViewPager viewPager = (ViewPager)findViewById(R.id.view_pager);

        myViewPagerAdapter myViewPagerAdapter1 = new myViewPagerAdapter(this, getSupportFragmentManager()) ;

        viewPager.setAdapter(myViewPagerAdapter1);

        TabLayout tabLayout = (TabLayout)findViewById(R.id.Tabs);

        tabLayout.setupWithViewPager(viewPager);


    }

//    @Override
//    public void onClick(View v) {
//        Intent intent;
//       switch (v.getId()){
//           case R.id.society_news: intent = new Intent(MainActivity.this, SocialNews.class);
//               startActivity(intent);
//               break;
//           case R.id.amusement_news: intent = new Intent(MainActivity.this, AmusementNews.class);
//               startActivity(intent);
//               break;
//           case R.id.gym_news: intent = new Intent(MainActivity.this, GymNews.class);
//               startActivity(intent);
//               break;
//           case R.id.technology_news: intent = new Intent(MainActivity.this, ScienceNews.class);
//               startActivity(intent);
//               break;
//
//
//       }
//    }
}
