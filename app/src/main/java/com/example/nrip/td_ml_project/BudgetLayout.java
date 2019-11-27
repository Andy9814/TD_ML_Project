package com.example.nrip.td_ml_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.MediaController;

import com.example.nrip.td_ml_project.models.BudgetCalculator;
import com.example.nrip.td_ml_project.models.BudgetEntry;
import com.google.android.material.chip.Chip;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageButton;
import pl.droidsonroids.gif.GifImageView;

public class BudgetLayout extends AppCompatActivity {



    String TAG ="LogCatDemo";
    ArrayList<BudgetEntry> budgetEntries;
    BigDecimal weeklyBudget;
    BudgetCalculator bc;



    Chip priceChip,taxChip;
    Animation animation;
    AnimationSet animationSet,animationSetT,animationSetcd ;
    GifImageView gifImageButton;
    private BigDecimal price = BigDecimal.ZERO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_layout);
        priceChip= findViewById(R.id.priceChip);
        taxChip= findViewById(R.id.TaxChip);
        gifImageButton = findViewById(R.id.gifImageView);

        Intent intt = getIntent();
        Bundle extras = intt.getExtras();


        validatePrice(extras.getString("costImage"));
        priceChip.setText("Price :"+ extras.getString("costImage"));

        this.onLoadData();


//        AnimationSet as = new AnimationSet(true);
//        TranslateAnimation animation = new TranslateAnimation(
//                Animation.ABSOLUTE, 0.0f, Animation.ABSOLUTE, 0.0f,
//                Animation.ABSOLUTE, 0.0f, Animation.ABSOLUTE, -150.0f);
//        animation.setDuration(500);
//        as.addAnimation(animation);



        for(int i  = 0 ; i < 2 ; ++i){
            if(i  == 0) {
                Animation fadeIn = new AlphaAnimation(0, 1);
                fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
                fadeIn.setDuration(6000);
                animationSet = new AnimationSet(true); //change to false
                animationSet.addAnimation(fadeIn);
                priceChip.setAnimation(animationSet);
            }
            else{
                Animation fadeIn = new AlphaAnimation(0, 1);
                fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
                fadeIn.setDuration(10000);
                animationSetT = new AnimationSet(true); //change to false
                animationSetT.addAnimation(fadeIn);
                taxChip.setAnimation(animationSetT);
            }
        }
        gifImageButton.setImageResource(R.drawable.tick);
        Drawable drawable = gifImageButton.getDrawable();
        if (drawable instanceof Animatable) {
            ((Animatable) drawable).start();
        }
//        final MediaController mc = new MediaController(this);
//        mc.setMediaPlayer((GifDrawable) gifImageButton.getDrawable());
//        mc.setAnchorView(gifImageButton);
//        gifImageButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mc.show();
//            }
//        });
//        TranslateAnimation animation1 = new TranslateAnimation(
//                Animation.ABSOLUTE, 0.0f, Animation.ABSOLUTE, 0.0f,
//                Animation.ABSOLUTE, 150.0f, Animation.ABSOLUTE, 0.0f);
//        animation1.setDuration(200);
//        animation1.setStartOffset(200);
//        as.addAnimation(animation1);

//        priceChip.startAnimation(as);
//        taxChip.startAnimation(as);
    }


    private void validatePrice(String x){
        Log.d("String is  === =========   ",x);
        boolean foundDollar = false;
        for (int i = 0; i < x.length(); i++){
            char c = x.charAt(i);
            if(c =='$'){
                foundDollar = true;
            }

            if(foundDollar){
                price = new BigDecimal(c);
                Log.d("Price is  === =========   ",price.toString());
            }
        }
    }

    public void onLoadData() {
        bc = new BudgetCalculator();
        try {
            // read in the file
            InputStream ins = getResources().openRawResource(
                    getResources().getIdentifier("csv35639",
                            "raw", getPackageName()));

            // parse the data from the file
            budgetEntries = bc.parseData(ins);

            // calculate budget
            weeklyBudget = bc.calcBudget(budgetEntries);

        } catch (IOException e) {
            Log.d(TAG, e.toString());
        } catch (ParseException e) {
            Log.d(TAG, e.toString());
            e.printStackTrace();
        }
    }
}
