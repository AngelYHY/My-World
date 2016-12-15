package freestar.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 *主界面
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private List<Diary> mList;
    private DiaryAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //销毁有记录的 activity
        ActivityCollector.finishAll();
        ActivityCollector.addActivity(this);
        CircleImageView civ = (CircleImageView) findViewById(R.id.civ);
        TextView diary = (TextView) findViewById(R.id.tv_diary);
        TextView birthday = (TextView) findViewById(R.id.tv_birthday);

        diary.setOnClickListener(this);
        birthday.setOnClickListener(this);
        civ.setOnClickListener(this);

        initData();

        //初始化 RecyclerView 并 设置数据源
        RecyclerView rv = (RecyclerView) findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mAdapter = new DiaryAdapter(R.layout.rv_diary, mList);
        rv.setAdapter(mAdapter);

    }

    //从数据库中获取数据 按照 并时间最新的排序
    private void initData() {
        MyDB myDB = new MyDB(getApplicationContext());
        mList = myDB.getDiaryList();
        Collections.reverse(mList);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.civ:
                intent = new Intent(this, InfoActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_birthday:
                intent = new Intent(this, BirthdayActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_diary:
                intent = new Intent(this, DiaryActivity.class);
                startActivityForResult(intent, 1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1) {
            //如果日记添加成功，就重新获取数据源并设置
            initData();
            mAdapter.setNewData(mList);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

}
