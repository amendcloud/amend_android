package com.tactlab.cloudamendsample.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

import com.tactlab.cloudamendsample.R;
import com.tactlab.cloudamendsample.adapter.AmendSubOptionsAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyAmendSubOptions extends AppCompatActivity {
    List<String> items = new ArrayList<String>();
    private ListView lvMyAmendSubView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myamend_subview);
        init();
        initToolBar("Amend sub options");
        Bundle b = getIntent().getExtras();
        if (b != null) {
            int position = b.getInt("POSITION");
            addItems(position);
        }
    }

    private void addItems(int position) {
        switch (position) {
            case 0:

                items.add("Set Image size - Width");
                items.add("Set Image size - Height");
                items.add("Set Image size - Width & Height");
                items.add("Fit Image - Fit XY");
                items.add("Fit Image - Fit Width");
                items.add("Fit Image - Fit Height");
                items.add("Fit Image - Fit Fill");
                items.add("Fit Image - Fit Inside");
                items.add("Align Image - Align Left");
                items.add("Align Image - Align Right");
                items.add("Align Image - Align Center");
                items.add("Align Image - Align Top");
                items.add("Align Image - Align Bottom");
                items.add("Set Origin");
                setAdapter();

                break;

            case 1:

                items.add("For Rounded Rectangle");
                items.add("For Circle");
                setAdapter();

                break;

            case 2:

                items.add("Set Quality");
                setAdapter();

                break;


            case 3:

                items.add("Set GrayScale");
                setAdapter();

                break;


            case 4:

                items.add("Set Invert");
                setAdapter();

                break;


            case 5:

                items.add("Brightness & Contrast");
                items.add("Brightness");
                items.add("Contrast");
                setAdapter();

                break;

            case 6:

                items.add("X - For Horizontal Flip");
                items.add("Y - For Vertical Flip");
                items.add("XY - For Horizontal or Vertical Flip");
                setAdapter();

                break;

            case 7:

                items.add("Rotation Value - Angle");
                setAdapter();

                break;

            case 8:

                items.add("Insert Text , XY , Size , Style");
                setAdapter();

                break;


            case 9:

                items.add("Insert Image");
                items.add("Insert Image , X_ , Y_");
                setAdapter();

                break;
        }
    }

    //initializing the list view
    void init() {
        lvMyAmendSubView = (ListView) findViewById(R.id.lvMyAmendSubView);
    }

    // initializing the toolbar
    private void initToolBar(String title) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(title);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }

    //this function sets the list items for the circular Adapter
    private void setAdapter() {
        AmendSubOptionsAdapter amendAdapter = new AmendSubOptionsAdapter(MyAmendSubOptions.this, items);
        lvMyAmendSubView.setAdapter(amendAdapter);
    }

}
