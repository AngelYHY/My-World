package freestar.myapp;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Calendar;

public class InfoActivity extends AppCompatActivity implements View.OnClickListener {
    String[] sex = new String[]{"男", "女"};
    private TextView mAccount;
    private TextView mPsw;
    private TextView mBirthday;
    private TextView mSex;
    private TextView mSign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        ActivityCollector.addActivity(this);
        RelativeLayout account = (RelativeLayout) findViewById(R.id.rl_account);
        RelativeLayout psw = (RelativeLayout) findViewById(R.id.rl_psw);
        RelativeLayout sex = (RelativeLayout) findViewById(R.id.rl_sex);
        RelativeLayout birthday = (RelativeLayout) findViewById(R.id.rl_birthday);
        LinearLayout sign = (LinearLayout) findViewById(R.id.ll_sign);
        TextView quit = (TextView) findViewById(R.id.tv_quit);
        TextView introduce = (TextView) findViewById(R.id.tv_introduce);
        account.setOnClickListener(this);
        psw.setOnClickListener(this);
        sex.setOnClickListener(this);
        birthday.setOnClickListener(this);
        sign.setOnClickListener(this);
        quit.setOnClickListener(this);
        introduce.setOnClickListener(this);

        mAccount = (TextView) findViewById(R.id.tv_account);
        mPsw = (TextView) findViewById(R.id.tv_psw);
        mBirthday = (TextView) findViewById(R.id.tv_birthday);
        mSex = (TextView) findViewById(R.id.tv_sex);
        mSign = (TextView) findViewById(R.id.tv_sign);
        //从数据库获得用户信息并设置
        MyDB myDB = new MyDB(getApplicationContext());
        User user = myDB.getUser();
        mAccount.setText(MyApp.account);
        mPsw.setText(user.getPsw());
        mBirthday.setText(user.getBirthday());
        mSex.setText(user.getSex());
        mSign.setText(user.getSign());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.rl_account:
                intent = new Intent(this, WriteActivity.class);
                intent.putExtra("kind", "account");
                startActivityForResult(intent, 2);
                break;
            case R.id.rl_psw:
                intent = new Intent(this, WriteActivity.class);
                intent.putExtra("kind", "psw");
                startActivityForResult(intent, 3);
                break;
            case R.id.rl_birthday:
                //修改生日日期
                final Calendar calendar = Calendar.getInstance();
                new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year, month, dayOfMonth);
                        String format = DateFormat.format("yyyy-MM-dd", calendar).toString();
                        mBirthday.setText(format);
                        MyDB myDB = new MyDB(getApplicationContext());
                        myDB.updateBirthday(format);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.rl_sex:
                //修改性别
                new AlertDialog.Builder(this)
                        .setTitle("别选错了哦！")
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setSingleChoiceItems(sex, 0, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                mSex.setText(sex[which]);
                                MyDB myDB = new MyDB(getApplicationContext());
                                myDB.updateSex(sex[which]);
                                dialog.dismiss();
                            }
                        }).setNegativeButton("取消", null).show();
                break;
            case R.id.ll_sign:
                intent = new Intent(this, WriteActivity.class);
                intent.putExtra("kind", "sign");
                startActivityForResult(intent, 1);
                break;
            case R.id.tv_quit:
                //退出登录
                ActivityCollector.finishAll();
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_introduce:
                intent = new Intent(this, IntroduceActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String back = data.getStringExtra("OK");
            if (requestCode == 1) {
                //设置 修改后的个性签名
                mSign.setText(back);
            } else if (requestCode == 2) {
                //设置 修改后的帐号
                mAccount.setText(back);
            } else if (requestCode == 3) {
                //设置 修改后的密码
                mPsw.setText(back);
            }
        }
    }

}
