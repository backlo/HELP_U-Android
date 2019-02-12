package com.example.help_u;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
