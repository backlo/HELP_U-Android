package com.example.help_u;

import android.content.Intent;
import android.os.Bundle;
<<<<<<< HEAD
import android.view.View;
import android.view.WindowManager;
=======
import android.support.v7.app.AppCompatActivity;
>>>>>>> 1cf031a0d4f2fee77b4df34f793f51ca983a10f6
import android.widget.Button;

import com.example.help_u.Provider.ProviderMainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.sign_login)
    Button sign_login;
    @BindView(R.id.sign_register)
    Button sign_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
<<<<<<< HEAD
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
=======
>>>>>>> 1cf031a0d4f2fee77b4df34f793f51ca983a10f6
        ButterKnife.bind(this);

    }

    @OnClick(R.id.sign_login)
    public void login(){
        //Intent i = new Intent(LoginActivity.this, RequestMainActivity.class );
        Intent i = new Intent(LoginActivity.this, ProviderMainActivity.class);
        startActivity(i);
        finish();
    }

    @OnClick(R.id.sign_register)
    public void register(){
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class );
        startActivity(intent);
        finish();
    }
}
