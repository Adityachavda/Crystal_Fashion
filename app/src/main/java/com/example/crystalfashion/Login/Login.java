/* Login Screen Activity:- */
package com.example.crystalfashion.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.crystalfashion.API_Client.API_Client;
import com.example.crystalfashion.HomePage.HomePage;
import com.example.crystalfashion.Models.LoginResponse;
import com.example.crystalfashion.R;
import com.example.crystalfashion.Register.Register;
import com.example.crystalfashion.Storage.SharedPrefManager;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
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

import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    /* Create Variables For Facebook Login:- */
    private LoginButton facebookloginButton;
    private CallbackManager callbackManager;
    TextView Account_Signup_TextView,Forgot_password_TextView;
    Button Login_Button;
    EditText email_editEditText,password_EditText;
    FragmentManager fragmentManager;
    forgot_password forgot_password_fragment;
    FragmentTransaction fragmentTransaction;

    /* Create variables for Google Login:- */
    private GoogleSignInClient mGoogleSignInClient;

    /* On Create Method OF this Activity:- */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        /* Reference our Variable with Our Design Elements By IDs. */
        Login_Button=findViewById(R.id.Login_btn);
        email_editEditText=findViewById(R.id.text_input_username_email_edit_text_login);
        password_EditText=findViewById(R.id.text_input_password_edit_text_login);

        Account_Signup_TextView = findViewById(R.id.Account_sign_up_text_view);
        Forgot_password_TextView = findViewById(R.id.forgot_password_text_view);
        facebookloginButton = findViewById(R.id.Facebook_btn);


        Forgot_password_TextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager=getSupportFragmentManager();
                forgot_password_fragment=new forgot_password();
                fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.fragment_container,forgot_password_fragment,"Forgot Password");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        Login_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Loginuser();
            }
        });


        /*Goto Homepage If user Click On Sign Up TextView:- */
        Account_Signup_TextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent GotoRegister = new Intent(Login.this, Register.class);
                startActivity(GotoRegister);
            }
        });


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




        /*Facebook Sign in Process Starts Form This Line:- */
        callbackManager = CallbackManager.Factory.create();
        facebookloginButton.setPermissions(Arrays.asList("email"));

        /*This method Handels The Things when we start processing facebook Signin :- */
        facebookloginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                /*Goto HomePage*/
                Intent GotoHomePage = new Intent(Login.this, HomePage.class);
                startActivity(GotoHomePage);
                /*Get user Information From FaceBook*/
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



    private void Loginuser(){
        String email = email_editEditText.getText().toString().trim();
        String password = password_EditText.getText().toString().trim();

        if(email.isEmpty()){
            email_editEditText.setError("Enter Your Email");
            email_editEditText.requestFocus();
            return;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            email_editEditText.setError("Enter Valid Email");
            email_editEditText.requestFocus();
            return;
        }else if(password.isEmpty()){
            password_EditText.setError("Enter Your Password");
            password_EditText.requestFocus();
            return;
        }
        else{
            ProgressDialog progressDialog=new ProgressDialog(Login.this);
            progressDialog.setMessage("Please Wait..");
            progressDialog.show();
            Call<LoginResponse> loginResponseCall= API_Client.getInstance().getApi().loginUser("login",email,password);
            loginResponseCall.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    LoginResponse loginResponse=response.body();
                    Toast.makeText(Login.this, "Welcome :- "+loginResponse.user.username, Toast.LENGTH_SHORT).show();

                    SharedPrefManager.getInstance(Login.this).saveUser(loginResponse.user);

                    progressDialog.dismiss();
                    Intent GotoHomePage = new Intent(Login.this,HomePage.class);
                    startActivity(GotoHomePage);
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Toast.makeText(Login.this, "Welcome :- "+"Unable To Sign In", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });
        }
    }

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


    /* This is onStart Activity, Always Run First When Our Activity is About to Start:- */
    /* we can check here that user signed in or not:- */
    @Override
    protected void onStart() {

        if(SharedPrefManager.getInstance(Login.this).isLoggedIn()){
            Intent GoToHomePage = new Intent(Login.this, HomePage.class);
            startActivity(GoToHomePage);
        }

        if(AccessToken.getCurrentAccessToken()!=null){
            /*Goto HomePage*/
            Intent GoToHomePage = new Intent(Login.this, HomePage.class);
            startActivity(GoToHomePage);
        }

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
//            String personName = acct.getDisplayName();
//            String personGivenName = acct.getGivenName();
//            String personFamilyName = acct.getFamilyName();
//            String personEmail = acct.getEmail();
//            String personId = acct.getId();
//            Uri personPhoto = acct.getPhotoUrl();
            /*Goto HomePage*/
            Intent GoToHomePage = new Intent(Login.this, HomePage.class);
            startActivity(GoToHomePage);
        } else {
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
            /*Goto HomePage After Sign In in Google*/
            Intent GoToHomePage = new Intent(Login.this, HomePage.class);
            startActivity(GoToHomePage);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("TAG", "signInResult:failed code=" + e.getStatusCode());
        }
    }
}