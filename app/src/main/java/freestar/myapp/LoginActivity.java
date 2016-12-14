package freestar.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mAccount;
    private EditText mPsw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActivityCollector.addActivity(this);
        TextView login = (TextView) findViewById(R.id.tv_login);
        TextView register = (TextView) findViewById(R.id.tv_register);
        mAccount = (EditText) findViewById(R.id.et_account);
        mPsw = (EditText) findViewById(R.id.et_psw);
        login.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String account = mAccount.getText().toString().trim();
        String psw = mPsw.getText().toString().trim();
        switch (view.getId()) {
            case R.id.tv_login:
                if (TextUtils.isEmpty(account) || TextUtils.isEmpty(psw)) {
                    Toast.makeText(this, "帐号或密码不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    MyDB mydb = new MyDB(getApplicationContext());
                    if (mydb.confirm(account, psw)) {
                        MyApp.account=account;
                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(this, "帐号或密码有误", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.tv_register:
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
