package com.gamepoch.profileui;

import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import android.widget.ImageView;
import com.gamepoch.profileui.utils.PollingUtils;

/**
 * ConstraintLayout demo
 */

public class MainActivity extends AppCompatActivity {

    private boolean isOpen = false;
    private ConstraintSet layout1, layout2;
    private ConstraintLayout constraintLayout;
    private ImageView imageViewPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        System.out.println("Start polling service...");
        PollingUtils.startPollingService(this, 10, PollingService.class, PollingService.ACTION);

        // changing the status bar color to transparent
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        layout1 = new ConstraintSet();
        layout2 = new ConstraintSet();

        imageViewPhoto = findViewById(R.id.photo);
        constraintLayout = findViewById(R.id.constraint_layout);
        layout2.clone(this, R.layout.profile_expended);
        layout1.clone(constraintLayout);

        imageViewPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isOpen) {
                    TransitionManager.beginDelayedTransition(constraintLayout);
                    layout2.applyTo(constraintLayout);
                    isOpen = !isOpen;
                } else {
                    TransitionManager.beginDelayedTransition(constraintLayout);
                    layout1.applyTo(constraintLayout);
                    isOpen = !isOpen;
                }
            }
        });

    }

}
