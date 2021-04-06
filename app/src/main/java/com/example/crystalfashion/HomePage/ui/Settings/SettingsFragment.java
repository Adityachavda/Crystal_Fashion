package com.example.crystalfashion.HomePage.ui.Settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.crystalfashion.Login.Login;
import com.example.crystalfashion.R;
import com.example.crystalfashion.Storage.SharedPrefManager;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


public class SettingsFragment extends Fragment {

    private Button SignOutAll;
    GoogleSignInClient googleSignInClient;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_settings, container, false);
        SignOutAll = root.findViewById(R.id.SignOutAll_Button);
        SignOutAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logOut();
                SharedPrefManager.getInstance(getActivity()).clearUser();
                try{
                    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestEmail()
                            .build();
                    googleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
                    GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getActivity());
                    googleSignInClient.signOut().addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Intent intent = new Intent(getActivity(), Login.class);
                            startActivity(intent);
                        }
                    });
                }catch (Exception e){
                }
            }
        });
        return root;
    }
}