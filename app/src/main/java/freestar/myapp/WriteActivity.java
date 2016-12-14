package freestar.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
                myDB.updateSign(content);
                Intent intent = new Intent();
                intent.putExtra("OK", content);
                setResult(RESULT_OK, intent);
                break;
            }
            case "account":
                if (!TextUtils.isEmpty(content)) {
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
                myDB.updatePsw(content);
                Intent intent = new Intent();
                intent.putExtra("OK", content);
                setResult(RESULT_OK, intent);
                break;
            }
        }
        finish();
    }
}
