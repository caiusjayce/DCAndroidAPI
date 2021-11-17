package com.cjbatausa.loginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cjbatausa.loginapp.api.Requestplaceholder;
import com.cjbatausa.loginapp.api.Retrofitbuilder;
import com.cjbatausa.loginapp.batausa.HomeActivity;
import com.cjbatausa.loginapp.batausa.Login;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    public EditText username, password;
    public MaterialButton loginBtn;
    public TextView result;
    public Retrofitbuilder retrofitbuilder;
    public Requestplaceholder requestplaceholder;
    private TextInputEditText register, forgetPassword;
    private CircularProgressIndicator login_progressbar;

    private static final int WIFI_PERMISSION = 100;
    private static final int STORAGE_PERMISSION_READ = 101;
    private static final int STORAGE_PERMISSION_WRITE = 102;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginBtn = findViewById(R.id.loginbtn);
        result = findViewById(R.id.r);


        retrofitbuilder = new Retrofitbuilder();
        requestplaceholder = retrofitbuilder.getRetrofit().create(Requestplaceholder.class);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText() != null && password.getText() != null) {
                    Call<Login> loginCall = requestplaceholder.login(new Login(null, username.getText().toString(), null, null, password.getText().toString()));

                    loginCall.enqueue(new Callback<Login>() {
                        @Override
                        public void onResponse(Call<Login> call, Response<Login> response) {
                            if (response.isSuccessful()) {
                                if (response.code() == 404) {
                                    Toast.makeText(LoginActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                                    Log.e("lOGIN ERROR", response.message());
                                } else {
                                    Toast.makeText(LoginActivity.this, "There was an error upon login in the API", Toast.LENGTH_SHORT).show();
                                    Log.e("lOGIN ERROR", response.message());
                                }
                            } else {
                                if (response.code() == 200) {
                                    Login loginResponse = response.body();
                                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                    intent.putExtra("USERID", loginResponse.getId());
                                    intent.putExtra("USERNAME", loginResponse.getUsername());
                                    intent.putExtra("Token", loginResponse.getToken());

                                    startActivity(intent);
                                    finish();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<Login> call, Throwable t) {
                            Toast.makeText(LoginActivity.this, "There was an error upon login in the API", Toast.LENGTH_SHORT).show();
                            Log.e("LOGIN ERROR", t.getMessage());
                        }
                    });
                } else {
                    Toast.makeText(LoginActivity.this, "Please fill up all to login!", Toast.LENGTH_SHORT).show();
                }
            }

        });

    }
}