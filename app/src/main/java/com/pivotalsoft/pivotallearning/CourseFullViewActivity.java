package com.pivotalsoft.pivotallearning;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.pivotalsoft.pivotallearning.Adapters.CourseVideosListAdapter;
import com.pivotalsoft.pivotallearning.Items.CourseVideosItem;
import com.pivotalsoft.pivotallearning.R;

import java.util.ArrayList;
import java.util.List;

public class CourseFullViewActivity extends AppCompatActivity {
    private RecyclerView recyclerCourses,recyclerNewCourses;
    private CourseVideosListAdapter coursesAdapter;
    private List<CourseVideosItem> coursesItemList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_full_view);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Course One ");
        recyclerCourses = (RecyclerView) findViewById(R.id.recyclerCourses);
        coursesItemList = new ArrayList<>();
        coursesAdapter = new CourseVideosListAdapter(this, coursesItemList);

        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerCourses.setLayoutManager(mLayoutManager1);
        recyclerCourses.setItemAnimator(new DefaultItemAnimator());
        recyclerCourses.setAdapter(coursesAdapter);

    prepareCoursesData();
    }

    private void prepareCoursesData() {
        CourseVideosItem movie = new CourseVideosItem("Physical World","Scope and excietment of physics  33:00 min");
        coursesItemList.add(movie);

        movie = new CourseVideosItem("Physical World","Scope and excietment of physics  33:00 min");
        coursesItemList.add(movie);

        movie = new CourseVideosItem("Physical World","Scope and excietment of physics  33:00 min");
        coursesItemList.add(movie);

        movie = new CourseVideosItem("Physical World","Scope and excietment of physics  33:00 min");
        coursesItemList.add(movie);



        coursesAdapter.notifyDataSetChanged();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //finish();
                onBackPressed();
                break;
        }
        return true;
    }
}
