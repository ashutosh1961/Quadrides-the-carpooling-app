package com.example.mitcarpooling;

// import static android.text.TextUtils.replace;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

/*
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

*/


import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

//  import org.w3c.dom.Text;

public class chooseride extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;


    homefragment homefragment = new homefragment();
    rulesfragment rulesfragment = new rulesfragment();
    profilefragment profilefragment = new profilefragment();
    //   private int selectedTab = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chooseride);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        bottomNavigationView = findViewById(R.id.bottom_navigation);


        loadFragment(new homefragment());

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.home) {
                    loadFragment(new homefragment());

                } else if (itemId == R.id.rules) {
                    loadFragment(new rulesfragment());

                } else {
                    loadFragment(new profilefragment());

                }

                return false;
            }

        });


    }

    private void loadFragment(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();

    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Exit App")
                .setMessage("Are you sure you want to exit the app?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, which) -> {
                    super.onBackPressed(); // Call finish() after the user confirms
                    finishAffinity();
                })
                .setNegativeButton("No", null)
                .show();

    }

}







/*   if (item.getItemId() == R.id.home) {
getSupportFragmentManager().beginTransaction().replace(R.id.container, new homefragment()).commit();
                    return true;
                            } else if (item.getItemId() == R.id.rules) {
getSupportFragmentManager().beginTransaction().replace(R.id.container, new rulesfragment()).commit();
                    return true;
                            } else if (item.getItemId() == R.id.profile) {
getSupportFragmentManager().beginTransaction().replace(R.id.container, new profilefragment()).commit();
                    return true;
                            }




*/












        /*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

        final LinearLayout homeLayout = findViewById(R.id.homeLayout);
        final LinearLayout rulesLayout = findViewById(R.id.rules_Layout);
        final LinearLayout profileLayout = findViewById(R.id.profileLayout);

        final ImageView homeImage = findViewById(R.id.homeImage);
        final ImageView rulesImage = findViewById(R.id.rulesImage);
        final ImageView profileImage = findViewById(R.id.profileImage);

        final TextView homeTxt = findViewById(R.id.homeTxt);
        final TextView rulesTxt = findViewById(R.id.rulesTxt);
        final TextView profileTxt = findViewById(R.id.profileTxt);


        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragmentContainer, new rulesfragment())
                .commit();


        homeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if (selectedTab != 1) {

                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                .replace(R.id.fragmentContainer, new rulesfragment())
                        .commit();


                rulesTxt.setVisibility(View.GONE);
                profileTxt.setVisibility(View.GONE);

                rulesImage.setImageResource(R.drawable.rule);
                profileImage.setImageResource(R.drawable.profilei);


                rulesLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                profileLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));


                homeTxt.setVisibility(View.VISIBLE);
                homeImage.setImageResource(R.drawable.homei_selected);
                homeLayout.setBackgroundResource(R.drawable.roundback_100);


                ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                scaleAnimation.setDuration(200);
                scaleAnimation.setFillAfter(true);
                homeLayout.startAnimation(scaleAnimation);

                selectedTab = 1;
                }
            }
        });


        rulesLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.fragmentContainer, new profilefragment())
                        .commit();


                if (selectedTab != 2) {
                    homeTxt.setVisibility(View.GONE);
                    profileTxt.setVisibility(View.GONE);

                    homeImage.setImageResource(R.drawable.homei);
                    profileImage.setImageResource(R.drawable.profilei);


                    homeLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    profileLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));


                    rulesTxt.setVisibility(View.VISIBLE);
                    rulesImage.setImageResource(R.drawable.rule_selected);
                    rulesLayout.setBackgroundResource(R.drawable.roundback_rule);


                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    rulesLayout.startAnimation(scaleAnimation);

                    selectedTab = 2;
                }

            }
        });

        profileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (selectedTab != 3) {
                    rulesTxt.setVisibility(View.GONE);
                    homeTxt.setVisibility(View.GONE);

                    rulesImage.setImageResource(R.drawable.rule);
                    homeImage.setImageResource(R.drawable.homei);


                    rulesLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    homeLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));


                    profileTxt.setVisibility(View.VISIBLE);
                    profileImage.setImageResource(R.drawable.profilei_selected);
                    profileLayout.setBackgroundResource(R.drawable.roundback_profile);


                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    profileLayout.startAnimation(scaleAnimation);

                    selectedTab = 3;
                }

            }
        });


    }

    private void commit() {
    }

    private void replace(int fragmentContainer, Class<rulesfragment> rulesfragmentClass, Object o) {

    }


   >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>.
   >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
