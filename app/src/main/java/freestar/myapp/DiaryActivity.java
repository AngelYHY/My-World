package freestar.myapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * 我的日记
 */
public class DiaryActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mTitle;
    private EditText mContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);
        mTitle = (EditText) findViewById(R.id.et_title);
        mContent = (EditText) findViewById(R.id.et_content);
        Button cancel = (Button) findViewById(R.id.btn_cancel);
        Button publish = (Button) findViewById(R.id.btn_publish);
        cancel.setOnClickListener(this);
        publish.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String title = mTitle.getText().toString();
        String content = mContent.getText().toString();
        switch (v.getId()) {
            //取消
            case R.id.btn_cancel:
                finish();
                break;
            //保存
            case R.id.btn_publish:
                //添加记录
                MyDB myDB = new MyDB(getApplicationContext());
                myDB.addDiary(title, content);
                setResult(RESULT_OK, new Intent());
                finish();
                break;
        }
    }
}
