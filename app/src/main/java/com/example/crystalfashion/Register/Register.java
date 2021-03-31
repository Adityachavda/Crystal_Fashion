package com.example.crystalfashion.Register;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.crystalfashion.API_Client.API_Client;
import com.example.crystalfashion.Login.Login;
import com.example.crystalfashion.Models.Default_Response;
import com.example.crystalfashion.R;

import java.io.ByteArrayOutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {

    ImageView imageView;
    Button image_select_button, register;
    Bitmap bitmap;
    EditText username_editText, email_editText, password_editText, confirm_password_editText, height_editText, weight_editText;
    RadioGroup skin_color_radio_group, body_type_radio_group;
    int PICK_IMAGE = 200;
    int flag = 1;
    String skin_color="";
    String body_type="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        image_select_button = findViewById(R.id.button_select_image);
        imageView = (ImageView) findViewById(R.id.img_ss);
        register = findViewById(R.id.Register_user_button);
        username_editText = findViewById(R.id.text_input_username_edit_text);
        email_editText = findViewById(R.id.text_input_email_edit_text);
        password_editText = findViewById(R.id.text_input_password_edit_text);
        confirm_password_editText = findViewById(R.id.conpasswordtext_input_password_edit_text);
        height_editText = findViewById(R.id.height_input_edit_text);
        weight_editText = findViewById(R.id.Weight_input_edit_text);
        skin_color_radio_group = findViewById(R.id.radiogroup1);
        body_type_radio_group = findViewById(R.id.radiogroup2);


        skin_color_radio_group.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            skin_color = radioButton.getText().toString();
        });

        body_type_radio_group.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            body_type = radioButton.getText().toString();
        });


        image_select_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGalary();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userRegister();
            }
        });

    }

    private String ImageToString() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 10, byteArrayOutputStream);
        } catch (Exception e) {
            flag = 0;
        }
        byte[] imagByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imagByte, Base64.DEFAULT);
    }

    private void openGalary() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(i, "Select Picture"), PICK_IMAGE);
    }

    private void userRegister() {

        String username = username_editText.getText().toString().trim();
        String email = email_editText.getText().toString().trim();
        String password = password_editText.getText().toString().trim();
        String confirm_password = confirm_password_editText.getText().toString().trim();
        String height = height_editText.getText().toString().trim();
        String weight = weight_editText.getText().toString().trim();
        String Image;


        Image = ImageToString();



        if (Image.isEmpty()) {
            Toast.makeText(this, "Select An Image", Toast.LENGTH_SHORT).show();
        } else if (username.isEmpty()) {
            username_editText.setError("Enter Username");
            username_editText.requestFocus();
            return;
        } else if (email.isEmpty()) {
            email_editText.setError("Enter Your Email");
            email_editText.requestFocus();
            return;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            email_editText.setError("Enter Valid Email");
            email_editText.requestFocus();
            return;
        } else if (password.isEmpty()) {
            password_editText.setError("Enter Password");
            password_editText.requestFocus();
            return;
        } else if (password.length() < 6) {
            password_editText.setError("assword should be atleast 6 character long");
            password_editText.requestFocus();
            return;
        } else if (confirm_password.isEmpty()) {
            confirm_password_editText.setError("Confirm Your Password");
            confirm_password_editText.requestFocus();
            return;
        } else if (!(confirm_password.equals(password))) {
            confirm_password_editText.setError("Password Not Matched");
            confirm_password_editText.requestFocus();
            return;
        } else if (skin_color_radio_group.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Select Skin Color", Toast.LENGTH_SHORT).show();
        } else if (height.isEmpty()) {
            height_editText.setError("Enter Your Height");
            height_editText.requestFocus();
            return;
        } else if (weight.isEmpty()) {
            weight_editText.setError("Enter Your Weight");
            weight_editText.requestFocus();
            return;
        } else if (body_type_radio_group.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Select Body Type", Toast.LENGTH_SHORT).show();
        } else {

            try {
                Log.d("Image", Image);
                Log.d("username", username);
                Log.d("email", email);
                Log.d("password", password);
                Log.d("Skin Tone", skin_color);
                Log.d("Height", height);
                Log.d("Weight", weight);
                Log.d("Body Type", body_type);


                ProgressDialog progressDialog = new ProgressDialog(Register.this);
                progressDialog.setMessage("Please Wait..");
                progressDialog.show();
                Call<Default_Response> defaultResponseCall = API_Client.getInstance().getApi().createUser(Image, username, email, password, skin_color, Float.parseFloat(height), Float.parseFloat(weight), body_type);
                defaultResponseCall.enqueue(new Callback<Default_Response>() {
                    @Override
                    public void onResponse(Call<Default_Response> call, Response<Default_Response> response) {
                        Default_Response defaultResponse = response.body();
                        Toast.makeText(Register.this, defaultResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        Intent GotoLogin = new Intent(Register.this, Login.class);
                        startActivity(GotoLogin);
                    }

                    @Override
                    public void onFailure(Call<Default_Response> call, Throwable t) {
                        Toast.makeText(Register.this, "Unable To Create User", Toast.LENGTH_SHORT).show();
                    }
                });

            }
            catch (Exception e){
                Log.d("Error", String.valueOf(e));
            }
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                imageView.setImageBitmap(bitmap);
                imageView.setVisibility(View.VISIBLE);
            } catch (Exception e) {
                Log.d("IMAGE_ERROR", String.valueOf(e));
            }
        }
    }
}