package com.example.help_u;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.edit_id)
    EditText edit_id;
    @BindView(R.id.edit_name)
    EditText edit_name;
    @BindView(R.id.edit_phonenum)
    EditText edit_phonenum;
    @BindView(R.id.edit_psw)
    EditText edit_psw;
    @BindView(R.id.edit_psw_checker)
    TextView edit_psw_checker;
    @BindView(R.id.edit_psw_second)
    EditText edit_psw_second;
    @BindView(R.id.edit_residentnum)
    EditText edit_residentnum;
    @BindView(R.id.signup_btn)
    Button signup_btn;
    @BindView(R.id.help_radio)
    RadioButton help_radio;
    @BindView(R.id.provider_radio)
    RadioButton provider_radio;
    @BindView(R.id.overlap_check_btn)
    Button overlap_check_btn;
    @BindView(R.id.overrlap_result_txt)
    TextView overrlap_result_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    //아이디 중복체크 버튼
    @OnClick(R.id.overlap_check_btn)
    public void overLapCheck(){
//        if(edit_id.getText() == null){
//            overrlap_result_txt.setText("아이디를 먼저 입력해주세요");
//        }else if(){
//            //중복 되는 경우 있을때
//        }else{
//            //중복체크 통과
//        }
    }
    //회원가입 완료 버튼
    @OnClick(R.id.signup_btn)
    public void signup_btn(){
        //다른속성 Null 체크해야됨
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    //이미 아이디가 있을경우 바로 로그인화면으로
    @OnClick(R.id.textViewSignin)
    public void goToLogin(){
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
