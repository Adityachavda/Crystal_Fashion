/* Login Screen Activity:- */
package com.example.crystalfashion.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.crystalfashion.R;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.Arrays;

public class Login extends AppCompatActivity {

    /* Create Variables For Facebook Login:- */
    private LoginButton facebookloginButton;
    private CallbackManager callbackManager;

    /* Create variables for Google Login:- */
    private GoogleSignInClient mGoogleSignInClient;
    private Button google_signout;

    /* On Create Method OF this Activity:- */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        /* Reference our Variable with Out Design Elements By IDs. */
        facebookloginButton = findViewById(R.id.Facebook_btn);
        google_signout=findViewById(R.id.Google_signout);


        /*Google Sign in Process Starts From This Line:- */

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        SignInButton signInButton = findViewById(R.id.Google_btn);
        signInButton.setSize(SignInButton.SIZE_WIDE);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signIntent, 2020);
            }
        });


        google_signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signout_google();
            }
        });



        /*Facebook Sign in Process Starts Form This Line:- */

        callbackManager = CallbackManager.Factory.create();

        facebookloginButton.setPermissions(Arrays.asList("email"));

        /*This method Handels The Things when we start processing facebook Signin :- */
        facebookloginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                getFaceInfo();

            }

            @Override
            public void onCancel() {
                LoginManager.getInstance().logOut();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(Login.this, "Please Try Again Later", Toast.LENGTH_SHORT).show();
            }
        });

    }

    /*Now we are in the Method Declarations Scope(OutSide Of OnCreate Activity)*/
    /*Here You can define Methods and functions:- :) */


    /* getFaceInfo() is a Method From which we can get Information About Logged in User. Like id,firstname, lastname etc... */
    private void getFaceInfo() {
        GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        try {
                            Log.d("LOG_TAG", "fb json object: " + object);
                            Log.d("LOG_TAG", "fb graph response: " + response);

                            Toast.makeText(Login.this, "Id:- " + object.getString("id") + " Name:- " + object.getString("first_name"), Toast.LENGTH_SHORT).show();

                            String id = object.getString("id");
                            String first_name = object.getString("first_name");
                            String last_name = object.getString("last_name");
                            String birthday = object.getString("birthday");
                            String image_url = "http://graph.facebook.com/" + id + "/picture?type=large";

                            String email;
                            if (object.has("email")) {
                                email = object.getString("email");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,first_name,last_name"); // id,first_name,last_name,email,gender,birthday,cover,picture.type(large)
        request.setParameters(parameters);
        request.executeAsync();
    }

    /* signout_google() is a Method From Which We can Trigger Google SignOut:- */

    private void signout_google() {
        mGoogleSignInClient.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(Login.this, "Signed Out", Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    /* This is onStart Activity, Always Run First When Our Activity is About to Start:- */
    /* we can check here that user signed in or not:- */
    @Override
    protected void onStart() {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
//            String personName = acct.getDisplayName();
//            String personGivenName = acct.getGivenName();
//            String personFamilyName = acct.getFamilyName();
//            String personEmail = acct.getEmail();
//            String personId = acct.getId();
//            Uri personPhoto = acct.getPhotoUrl();
            Toast.makeText(this, "Signed In", Toast.LENGTH_SHORT).show();
        }else
        {
            Toast.makeText(this, "Not Signed In", Toast.LENGTH_SHORT).show();
        }
        super.onStart();
    }

    /* This is OnActivityResult method which will Called Whenever we use Intent With ActivityResult intent. */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2020) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResults(task);
        }
    }

    /* This is the method Which Will Execute If User Make Google Sign In From Clicking Google Sign In Button.*/
    private void handleSignInResults(Task<GoogleSignInAccount> task) {
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            // Signed in successfully, show authenticated UI.
            Toast.makeText(this, "Signed In Approved", Toast.LENGTH_SHORT).show();
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("TAG", "signInResult:failed code=" + e.getStatusCode());
        }
    }
}