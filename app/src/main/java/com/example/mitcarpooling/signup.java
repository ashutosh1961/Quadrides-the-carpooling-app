package com.example.mitcarpooling;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class signup extends AppCompatActivity {

    private EditText editTextname, editTextemail, editTextpasswd, editTextmobile ;
    private ProgressBar progressBar;
    private RadioGroup radioGroupRegisterGender;
    private RadioButton radioButtonRegisterGenderSelected;
    private static final String TAG = "signup";
    private Button signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);

        radioGroupRegisterGender = findViewById(R.id.radio_group_register_gender);
        radioGroupRegisterGender.clearCheck();
         ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), this::onApplyWindowInsets);


        progressBar = findViewById(R.id.progress_bar);
        editTextemail = findViewById(R.id.editText_email);
        editTextpasswd = findViewById(R.id.editText_passwd);
        editTextname = findViewById(R.id.editText_name);
        editTextmobile = findViewById(R.id.editText_mobile);
        signup = findViewById(R.id.cbutton);


    }


    private WindowInsetsCompat onApplyWindowInsets(View v, WindowInsetsCompat insets) {
        Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
        v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedGenderId = radioGroupRegisterGender.getCheckedRadioButtonId();
                radioButtonRegisterGenderSelected = findViewById(selectedGenderId);

                String textName = editTextname.getText().toString();
                String textMobile = editTextmobile.getText().toString();
                String textPasswd = editTextpasswd.getText().toString();
                String textGender = radioButtonRegisterGenderSelected.getText().toString();
                String textEmail = editTextemail.getText().toString();

                if (!textEmail.matches("[A-Za-z0-9._%+-]+@mit\\.asia$")) {
                    Toast.makeText(com.example.mitcarpooling.signup.this, "Please enter a valid MIT Asia email",Toast.LENGTH_LONG).show();
                    editTextemail.setError("Invalid MIT email address");
                    editTextemail.requestFocus();
                    return;
                }


                //validate mobile number
                String mobileRegex = "[6-9][0-9]{9}";
                Matcher mobileMatcher;
                Pattern mobilePattern = Pattern.compile(mobileRegex);
                mobileMatcher = mobilePattern.matcher(textMobile);


                if (TextUtils.isEmpty(textName)) {
                    Toast.makeText(signup.this, "Please enter your full name", Toast.LENGTH_LONG).show();
                    editTextname.setError("Full name is required");
                    editTextname.requestFocus();
                } else if (TextUtils.isEmpty(textEmail)) {
                    Toast.makeText(signup.this, "Please enter your email", Toast.LENGTH_LONG).show();
                    editTextemail.setError("Email is required");
                    editTextemail.requestFocus();
                } else if (radioGroupRegisterGender.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(signup.this, "Please select your gender", Toast.LENGTH_LONG).show();
                    radioButtonRegisterGenderSelected.setError("Gender is required");
                    radioButtonRegisterGenderSelected.requestFocus();
                } else if (TextUtils.isEmpty(textMobile)) {
                    Toast.makeText(signup.this, "Please enter your mobile no.", Toast.LENGTH_LONG).show();
                    editTextmobile.setError("Mobile no. is required");
                    editTextmobile.requestFocus();

                } else if (textMobile.length() != 10) {
                    editTextmobile.setError("Mobile no. should be 10 digits");
                    editTextmobile.requestFocus();

                }else if (!mobileMatcher.find()) {
                    editTextmobile.setError("Mobile no. is not valid");
                    editTextmobile.requestFocus();
                }else if (TextUtils.isEmpty(textPasswd)) {
                    Toast.makeText(signup.this, "Please enter your password", Toast.LENGTH_LONG).show();
                    editTextpasswd.setError("Password is required");
                    editTextpasswd.requestFocus();
                } else {
                    textGender = radioButtonRegisterGenderSelected.getText().toString();
                    progressBar.setVisibility(View.VISIBLE);
                    registeruser(textName, textMobile, textGender, textEmail, textPasswd);
                }
            }

            private void registeruser(String textName, String textMobile, String textGender, String textEmail, String textPasswd) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.createUserWithEmailAndPassword(textEmail, textPasswd).addOnCompleteListener(signup.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = auth.getCurrentUser();

                            //update display name of user
                            UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(textName).build();
                            firebaseUser.updateProfile(profileChangeRequest);

                            //enter user data into firebase database
                            ReadWriteUserDetails writeUserDetails = new ReadWriteUserDetails(textName, textMobile, textGender );


                            //extracting user reference form database for "Registered user"

                            DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered user");
                            referenceProfile.child(firebaseUser.getUid()).setValue(writeUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()){

                                        //send email verification
                                        firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()){
                                                    Toast.makeText(signup.this,"Authentication Successful. Please verify your email",Toast.LENGTH_LONG).show();

                                                    Intent intent = new Intent(signup.this, login.class);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                                else {
                                                    //kjgsdks
                                                    Toast.makeText(signup.this,"Authentication Failed",Toast.LENGTH_LONG).show();

                                                }
                                            }
                                        });





                                    } else {

                                        Toast.makeText(signup.this,"Authentication Failed",Toast.LENGTH_LONG).show();
                                    }
                                    progressBar.setVisibility(View.GONE);
                                }
                            });

                        }

                     else {
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                editTextpasswd.setError("Your email is invalid or already in use. kindly re-enter.");
                                editTextpasswd.requestFocus();
                            }
                            catch (FirebaseAuthUserCollisionException e) {
                                editTextpasswd.setError("User is already registered with this email. Use another email");
                                editTextpasswd.requestFocus();
                            }
                            catch (Exception e) {
                                Log.e(TAG, e.getMessage());
                                Toast.makeText(com.example.mitcarpooling.signup.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                            progressBar.setVisibility(View.GONE);
                        }

                    }
                });
            }

        });

        TextView login = findViewById(R.id.loginbtn2);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(signup.this, login.class));
            }
        });
        return insets;
    }
}