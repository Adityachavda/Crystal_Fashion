package com.example.crystalfashion.Login;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.crystalfashion.API_Client.API_Client;
import com.example.crystalfashion.Models.Default_Response;
import com.example.crystalfashion.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class forgot_password extends Fragment {

    EditText editText_email;
    Button forgot_password_button;

    public forgot_password() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forgot_password, container, false);

        editText_email=view.findViewById(R.id.text_input_email_edit_text_forgot_password);
        forgot_password_button=view.findViewById(R.id.forgot_pass_button);

        forgot_password_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Submit_email();
            }
        });

        return view;
    }

    private void Submit_email(){
        String email=editText_email.getText().toString().trim();
        if(email.isEmpty()){
            editText_email.setError("Enter Your Attached Email");
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editText_email.setError("Enter Valid Email");
        }
        else{
            ProgressDialog progressDialog=new ProgressDialog(getActivity());
            progressDialog.setMessage("Please Wait..");
            progressDialog.show();
            Call<Default_Response> default_responseCall= API_Client.getInstance().getApi().forgot_password("sendEmail",email);
            default_responseCall.enqueue(new Callback<Default_Response>() {
                @Override
                public void onResponse(Call<Default_Response> call, Response<Default_Response> response) {
                    Default_Response defaultResponse=response.body();
                    if(defaultResponse.getStatus()==201){
                        Toast.makeText(getActivity(), "This Email not Attached With Your Account", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }else{
                        Toast.makeText(getActivity(), defaultResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<Default_Response> call, Throwable t) {
                    Toast.makeText(getActivity(), "Try Again Later", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });
        }
    }
}