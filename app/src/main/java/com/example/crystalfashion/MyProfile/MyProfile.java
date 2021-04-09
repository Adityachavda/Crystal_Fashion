package com.example.crystalfashion.MyProfile;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.crystalfashion.Models.WishListResponse;
import com.example.crystalfashion.R;
import com.example.crystalfashion.Storage.SharedPrefManager;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class MyProfile extends Fragment {

    public MyProfile() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private ImageView imageView_profile_picture;
    private TextView textView_profile_name;
    private TextView textView_profile_third_party;
    ProgressBar progressBar;
    private String URL = "https://incomparable-vector.000webhostapp.com/Crystal_Fashion/";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_profile, container, false);
        progressBar=view.findViewById(R.id.progress_bar_profile);
        imageView_profile_picture=view.findViewById(R.id.profile_picture_image_view);
        textView_profile_name=view.findViewById(R.id.profile_name_txt_view);
        textView_profile_third_party=view.findViewById(R.id.profile_third_party_txt_view);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getActivity());
        if(AccessToken.getCurrentAccessToken()!=null) {
            GraphRequest request = GraphRequest.newMeRequest(
                    AccessToken.getCurrentAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(
                                JSONObject object,
                                GraphResponse response) {
                            try {
                                String Id = object.getString("id");
                                String Image_url = "https://graph.facebook.com/" + Id + "/picture?type=large";
//                            String first_name = object.getString("first_name");
                                String name = object.getString("name");
                                progressBar.setVisibility(View.VISIBLE);
                                Picasso.with(getActivity()).load(Image_url).fit().into(imageView_profile_picture, new Callback() {
                                    @Override
                                    public void onSuccess() {
                                        progressBar.setVisibility(View.INVISIBLE);
                                        textView_profile_name.setText(name);
                                        textView_profile_third_party.setText("Logged in Via: Facebook");
                                    }

                                    @Override
                                    public void onError() {

                                    }
                                });
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
            request.executeAsync();
        }else if(account!=null){
            Uri personPhoto = account.getPhotoUrl();
            String name = account.getDisplayName();
            progressBar.setVisibility(View.VISIBLE);
            Picasso.with(getActivity()).load(personPhoto).fit().into(imageView_profile_picture, new Callback() {
                @Override
                public void onSuccess() {
                    progressBar.setVisibility(View.INVISIBLE);
                    textView_profile_name.setText(name);
                    textView_profile_third_party.setText("Logged in Via: Google");
                }

                @Override
                public void onError() {

                }
            });
        }else {
            progressBar.setVisibility(View.VISIBLE);
            Picasso.with(getActivity()).load(URL+ SharedPrefManager.getUser().user_image).fit().into(imageView_profile_picture, new Callback() {
                @Override
                public void onSuccess() {
                    progressBar.setVisibility(View.INVISIBLE);
                    textView_profile_name.setText(SharedPrefManager.getUser().username);
                    textView_profile_third_party.setText("Logged in Via: App Account");
                }

                @Override
                public void onError() {

                }
            });
        }
        return view;
    }
}