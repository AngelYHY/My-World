package freestar.myapp;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

public class BirthdayActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mName;
    private List<Birthday> mList;
    private BirthdayAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birthday);
        mName = (EditText) findViewById(R.id.et_name);
        TextView add = (TextView) findViewById(R.id.tv_add);
        add.setOnClickListener(this);

        initData();

        RecyclerView rv = (RecyclerView) findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mAdapter = new BirthdayAdapter(R.layout.rv_birthday, mList, this);
        rv.setAdapter(mAdapter);
    }

    private void initData() {
        MyDB myDB = new MyDB(getApplicationContext());
        mList = myDB.getBirthdayList();
    }

    @Override
    public void onClick(View v) {
        final String name = mName.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "名字不能为空", Toast.LENGTH_SHORT).show();
        } else {
            final Calendar calendar = Calendar.getInstance();
            new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    calendar.set(year, month, dayOfMonth);
                    String format = DateFormat.format("yyyy-MM-dd", calendar).toString();
                    MyDB myDB = new MyDB(getApplicationContext());
                    myDB.addBirthday(name, format);
                    initData();
                    mAdapter.setNewData(mList);
                    mName.setText("");
                }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        }
    }

}
