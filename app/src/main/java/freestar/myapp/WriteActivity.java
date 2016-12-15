package freestar.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 用来输入要修改的个性签名、帐号、密码的 activity
 */

public class WriteActivity extends AppCompatActivity implements View.OnClickListener {
    private String mKind;
    private EditText mContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        mKind = getIntent().getStringExtra("kind");
        mContent = (EditText) findViewById(R.id.et_content);
        TextView ok = (TextView) findViewById(R.id.tv_ok);
        ok.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String content = mContent.getText().toString();
        MyDB myDB = new MyDB(getApplicationContext());
        switch (mKind) {
            case "sign": {
                //修改个性签名
                myDB.updateSign(content);
                Intent intent = new Intent();
                intent.putExtra("OK", content);
                setResult(RESULT_OK, intent);
                break;
            }
            case "account":
                //就判断输入的帐号是否为空
                if (!TextUtils.isEmpty(content)) {
                    //判断新的帐号是否已存在，若不存在就更新帐号
                    if (myDB.queryAccount(content)) {
                        myDB.updateAccount(content);
                        Intent intent = new Intent();
                        intent.putExtra("OK", content);
                        setResult(RESULT_OK, intent);
                    } else {
                        Toast.makeText(this, "帐号已存在", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "帐号不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            case "psw": {
                //更新密码
                if (!TextUtils.isEmpty(content)) {
                    myDB.updatePsw(content);
                    Intent intent = new Intent();
                    intent.putExtra("OK", content);
                    setResult(RESULT_OK, intent);
                } else {
                    Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                break;
            }
        }
        finish();
    }
}
