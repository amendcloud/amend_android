package com.tactlab.cloudamendsample.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

import com.tactlab.cloudamendsample.R;
import com.tactlab.cloudamendsample.adapter.AmendOptionsAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyAmendOptions extends AppCompatActivity {
    private ListView lvAmendOptions;
    List<String> items = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amend_options);
        initToolBar("Amend options");
        init();
        addItems();
        setAdapter();
    }

    private void addItems() {
        items.add("Crop & Resize image");
        items.add("Crop to rounded rectangle/circle ");
        items.add("Set quality");
        items.add("Grayscale");
        items.add("Invert");
        items.add("Brightness & Contrast");
        items.add("Flip");
        items.add("Rotate");
        items.add("Text Overlay");
        items.add("Image Overlay");
    }

    //initializing the list view
    void init() {
        lvAmendOptions = (ListView) findViewById(R.id.lvAmendOptions);
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
        AmendOptionsAdapter amendAdapter = new AmendOptionsAdapter(MyAmendOptions.this, items);
        lvAmendOptions.setAdapter(amendAdapter);
    }

}
