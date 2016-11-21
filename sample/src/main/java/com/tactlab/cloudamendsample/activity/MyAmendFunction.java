package com.tactlab.cloudamendsample.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tactlab.cloudamendsample.R;
import com.tactlab.cloudamend.Amend;
import com.tactlab.cloudamend.AmendOptions;
import com.tactlab.cloudamend.Effect;
import com.tactlab.cloudamend.Overlay;
import com.tactlab.cloudamend.RequestListener;
import com.tactlab.cloudamend.Transform;
import com.tactlab.cloudamendsample.utils.SharedPreference;
import com.tactlab.cloudamendsample.utils.Support;
import com.trend.progress.ProgressDialog;

import org.json.JSONException;


public class MyAmendFunction extends AppCompatActivity implements View.OnClickListener {

    private ImageView ivProcessedImage;
    private SharedPreference sp;
    private String function_name;
    private EditText etWidth;
    private EditText etHeight;
    private AmendOptions options;
    private EditText etX;
    private EditText etY;
    private EditText etText;
    private EditText etSize;
    TextView tvWidth;
    TextView tvHeight;
    private ProgressDialog pd;
    boolean hideKeyboard = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myamendfunction);
        sp = new SharedPreference(this);
        pd = new ProgressDialog(this);
        pd.setTranslucent();
        initToolBar("Amend");

        Bundle b = getIntent().getExtras();
        if (b != null) {
            function_name = b.getString("FUNCTION_NAME");
            findViews(function_name);
            setTitle(function_name + "");
        }
        pd.setCancelable(new ProgressDialog.OnCancel() {
            @Override
            public void onCancelDone() throws JSONException {
                onBackPressed();
            }
        });
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


    private void findViews(String function_name) {
        ivProcessedImage = (ImageView) findViewById(R.id.ivProcessedImage);
        Button bGenerate = (Button) findViewById(R.id.bGenerate);
        etWidth = (EditText) findViewById(R.id.etWidth);
        etHeight = (EditText) findViewById(R.id.etHeight);
        etX = (EditText) findViewById(R.id.etX);
        etY = (EditText) findViewById(R.id.etY);
        etText = (EditText) findViewById(R.id.etText);
        etSize = (EditText) findViewById(R.id.etSize);

        tvWidth = (TextView) findViewById(R.id.tvWidth);
        tvHeight = (TextView) findViewById(R.id.tvHeight);

        LinearLayout llWidth = (LinearLayout) findViewById(R.id.llWidth);
        LinearLayout llHeight = (LinearLayout) findViewById(R.id.llHeight);
        LinearLayout llX = (LinearLayout) findViewById(R.id.llX);
        LinearLayout llY = (LinearLayout) findViewById(R.id.llY);
        LinearLayout llText = (LinearLayout) findViewById(R.id.llText);
        LinearLayout llSize = (LinearLayout) findViewById(R.id.llSize);


        bGenerate.setOnClickListener(this);

        switch (function_name) {
            case "Set Image size - Width":

                llWidth.setVisibility(View.VISIBLE);

                break;

            case "Set Image size - Height":

                llHeight.setVisibility(View.VISIBLE);

                break;


            case "Set Image size - Width & Height":

                llWidth.setVisibility(View.VISIBLE);
                llHeight.setVisibility(View.VISIBLE);

                break;

            case "Fit Image - Fit XY":

                llWidth.setVisibility(View.VISIBLE);
                llHeight.setVisibility(View.VISIBLE);

                break;
            case "Fit Image - Fit Width":

                llWidth.setVisibility(View.VISIBLE);
                llHeight.setVisibility(View.VISIBLE);

                break;
            case "Fit Image - Fit Height":

                llWidth.setVisibility(View.VISIBLE);
                llHeight.setVisibility(View.VISIBLE);

                break;
            case "Fit Image - Fit Fill":

                llWidth.setVisibility(View.VISIBLE);
                llHeight.setVisibility(View.VISIBLE);

                break;
            case "Fit Image - Fit Inside":

                llWidth.setVisibility(View.VISIBLE);
                llHeight.setVisibility(View.VISIBLE);


                break;

            case "Align Image - Align Left":

                llWidth.setVisibility(View.VISIBLE);
                llHeight.setVisibility(View.VISIBLE);


                break;
            case "Align Image - Align Right":

                llWidth.setVisibility(View.VISIBLE);
                llHeight.setVisibility(View.VISIBLE);

                break;

            case "Align Image - Align Center":

                llWidth.setVisibility(View.VISIBLE);
                llHeight.setVisibility(View.VISIBLE);


                break;

            case "Align Image - Align Top":

                llWidth.setVisibility(View.VISIBLE);
                llHeight.setVisibility(View.VISIBLE);

                break;

            case "Align Image - Align Bottom":


                llWidth.setVisibility(View.VISIBLE);
                llHeight.setVisibility(View.VISIBLE);

                break;

            case "Set Origin":

                llWidth.setVisibility(View.VISIBLE);
                llHeight.setVisibility(View.VISIBLE);
                llX.setVisibility(View.VISIBLE);
                llY.setVisibility(View.VISIBLE);

                break;

            case "For Rounded Rectangle":

                // change label name
                tvWidth.setText("Set Radius");
                llWidth.setVisibility(View.VISIBLE);

                break;

            case "For Circle":


                break;


            case "Set Quality":

                // change label name
                tvWidth.setText("Set Quality");
                llWidth.setVisibility(View.VISIBLE);


                break;

            case "Set GrayScale":
                hideKeyboard = true;

                break;

            case "Set Invert":
                hideKeyboard = true;

                break;

            case "Brightness & Contrast":

                tvWidth.setText("Set Brightness");
                llWidth.setVisibility(View.VISIBLE);
                tvHeight.setText("Set Contrast");
                llHeight.setVisibility(View.VISIBLE);

                break;

            case "Brightness":


                tvWidth.setText("Set Brightness");
                llWidth.setVisibility(View.VISIBLE);


                break;

            case "Contrast":

                tvWidth.setText("Set Contrast");
                llWidth.setVisibility(View.VISIBLE);


                break;

            case "X - For Horizontal Flip":


                break;

            case "Y - For Vertical Flip":


                break;

            case "XY - For Horizontal or Vertical Flip":


                break;

            case "Rotation Value - Angle":

                tvWidth.setText("Set Rotation");
                llWidth.setVisibility(View.VISIBLE);

                break;

            case "Insert Text , XY , Size , Style":

                llText.setVisibility(View.VISIBLE);
                llX.setVisibility(View.VISIBLE);
                llY.setVisibility(View.VISIBLE);
                llSize.setVisibility(View.VISIBLE);

                break;

            case "Insert Image":


                break;

            case "Insert Image , X_ , Y_":

                llX.setVisibility(View.VISIBLE);
                llY.setVisibility(View.VISIBLE);


                break;
        }
    }


    @Override
    public void onClick(View v) {


        int w = 0;
        int h = 0;
        int x = 0;
        int y = 0;
        int s = 0;
        try {

            if (!etWidth.getText().toString().equals("")) {
                w = Integer.parseInt(etWidth.getText().toString());
            }
            if (!etHeight.getText().toString().trim().equals("")) {
                h = Integer.parseInt(etHeight.getText().toString());
            }
            if (!etX.getText().toString().trim().equals("")) {
                x = Integer.parseInt(etX.getText().toString());
            }
            if (!etY.getText().toString().trim().equals("")) {
                y = Integer.parseInt(etY.getText().toString());
            }
            if (!etSize.getText().toString().trim().equals("")) {
                s = Integer.parseInt(etSize.getText().toString().trim());
            }


            switch (function_name) {

                case "Set Image size - Width":
                    options = new AmendOptions().transform(new Transform().width(w));
                    break;

                case "Set Image size - Height":
                    options = new AmendOptions().transform(new Transform().height(h));
                    break;

                case "Set Image size - Width & Height":
                    options = new AmendOptions().transform(new Transform().width(w).height(h));
                    break;

                case "Fit Image - Fit XY":
                    options = new AmendOptions().transform(new Transform().width(w).height(h).fit(Amend.FIT_XY));
                    break;
                case "Fit Image - Fit Width":
                    options = new AmendOptions().transform(new Transform().width(w).height(h).fit(Amend.FIT_WIDTH));
                    break;
                case "Fit Image - Fit Height":
                    options = new AmendOptions().transform(new Transform().width(w).height(h).fit(Amend.FIT_HEIGHT));
                    break;

                case "Fit Image - Fit Fill":
                    options = new AmendOptions().transform(new Transform().width(w).height(h).fit(Amend.FIT_FILL));
                    break;

                case "Fit Image - Fit Inside":
                    options = new AmendOptions().transform(new Transform().width(w).height(h).fit(Amend.FIT_INSIDE));
                    break;

                case "Align Image - Align Left":
                    options = new AmendOptions().transform(new Transform().width(w).height(h).align(Amend.LEFT));
                    break;
                case "Align Image - Align Right":
                    options = new AmendOptions().transform(new Transform().width(w).height(h).align(Amend.RIGHT));
                    break;

                case "Align Image - Align Center":
                    options = new AmendOptions().transform(new Transform().width(w).height(h).align(Amend.CENTER));
                    break;

                case "Align Image - Align Top":
                    options = new AmendOptions().transform(new Transform().width(w).height(h).align(Amend.TOP));
                    break;

                case "Align Image - Align Bottom":
                    options = new AmendOptions().transform(new Transform().width(w).height(h).align(Amend.BOTTOM));
                    break;

                case "Set Origin":
                    options = new AmendOptions().transform(new Transform().width(w).height(h).x(Integer.parseInt(etX.getText().toString())).y(Integer.parseInt(etY.getText().toString())));
                    break;

                case "For Rounded Rectangle":
                    options = new AmendOptions().radius(w);
                    break;

                case "For Circle":
                    hideKeyboard = true;
                    options = new AmendOptions().radius(Amend.MAX);
                    break;


                case "Set Quality":
                    options = new AmendOptions().quality(w);
                    break;

                case "Set GrayScale":
                    hideKeyboard = true;
                    options = new AmendOptions().grayscale(true);
                    break;

                case "Set Invert":
                    hideKeyboard = true;
                    options = new AmendOptions().invert(true);
                    break;

                case "Brightness & Contrast":
                    options = new AmendOptions().effect(new Effect().brightness(w).contrast(h));
                    break;

                case "Brightness":
                    options = new AmendOptions().effect(new Effect().brightness(w));
                    break;

                case "Contrast":
                    options = new AmendOptions().effect(new Effect().contrast(w));
                    break;

                case "X - For Horizontal Flip":
                    hideKeyboard = true;
                    options = new AmendOptions().flip(Amend.FLIP_X);
                    break;

                case "Y - For Vertical Flip":
                    hideKeyboard = true;
                    options = new AmendOptions().flip(Amend.FLIP_Y);
                    break;

                case "XY - For Horizontal or Vertical Flip":
                    hideKeyboard = true;
                    options = new AmendOptions().flip(Amend.FLIP_XY);
                    break;

                case "Rotation Value - Angle":
                    options = new AmendOptions().rotate(w);
                    break;

                case "Insert Text , XY , Size , Style":
                    if (s <= 0) {
                        Support.showAlertDialog(MyAmendFunction.this, "Size can't be zero");
                    } else {
                        // style is left in this
                        options = new AmendOptions().overlay(new Overlay().text(etText.getText().toString()).x(x).y(y).size(s));
                    }
                    break;

                case "Insert Image":
                    options = new AmendOptions().overlay(new Overlay().image("amend"));
                    break;

                case "Insert Image , X_ , Y_":
                    options = new AmendOptions().overlay(new Overlay().image("amend").x(x).y(y));
                    break;
            }

            if (options != null) {
                Amend.with(this).load(sp.getValueString("KEY_IMAGEID"), options).listener(new RequestListener() {
                    @Override
                    public void onStarted() {
                        pd.show();
                    }

                    @Override
                    public void onSuccess(Bitmap d) {
                        pd.dismiss();
                    }

                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                        pd.dismiss();
                    }
                }).into(ivProcessedImage);
            }
            if (!hideKeyboard) {
                InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
