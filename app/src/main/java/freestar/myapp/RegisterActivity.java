package freestar.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mPsw;
    private EditText mConfirmPsw;
    private EditText mAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ActivityCollector.addActivity(this);
        TextView register = (TextView) findViewById(R.id.tv_register);
        mPsw = (EditText) findViewById(R.id.et_psw);
        mConfirmPsw = (EditText) findViewById(R.id.et_confirm_psw);
        mAccount = (EditText) findViewById(R.id.et_account);
        register.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        String account = mAccount.getText().toString().trim();
        String psw = mPsw.getText().toString().trim();
        String confirmPsw = mConfirmPsw.getText().toString().trim();
        MyDB myDB = new MyDB(this);
        if (TextUtils.isEmpty(account) || TextUtils.isEmpty(psw)) {
            Toast.makeText(this, "帐号或者密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        } else if (!psw.equals(confirmPsw)) {
            Toast.makeText(this, "两次输入的密码不同", Toast.LENGTH_SHORT).show();
            return;
        }
        if (myDB.add(account, psw)) {
            Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "帐号已存在，再想一个呗", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
