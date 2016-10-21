package com.maxplus.sostudy.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.maxplus.sostudy.R;

public class ChooseClassActivity extends Activity implements View.OnClickListener {
    private ImageButton back;
    private Button confirm;
    private ImageView add, delete;
    private EditText et_class;
    private TextView tv_schoose_class;
    int sclass = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_class);
        initView();
    }

    private void initView() {
        back = (ImageButton) findViewById(R.id.bback_Button);
        back.setOnClickListener(this);
        confirm = (Button) findViewById(R.id.confirm);
        confirm.setOnClickListener(this);
        add = (ImageView) findViewById(R.id.add);
        add.setOnClickListener(this);
        delete = (ImageView) findViewById(R.id.delete);
        delete.setOnClickListener(this);
        et_class = (EditText) findViewById(R.id.et_class);
        sclass = Integer.parseInt(et_class.getText().toString());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bback_Button:
                finish();
                break;
            case R.id.delete:
                et_class = (EditText) findViewById(R.id.et_class);
                sclass = Integer.parseInt(et_class.getText().toString());
                if (sclass - 1 > 0) {
                    sclass = sclass - 1;
                    et_class.setText("" + sclass);
                } else {
                    Toast.makeText(this, "班级必须大于等于1", Toast.LENGTH_SHORT).show();
                    et_class.setText("" + 1);
                }
                break;
            case R.id.add:
                et_class = (EditText) findViewById(R.id.et_class);
                sclass = Integer.parseInt(et_class.getText().toString());
                if (sclass + 1 > 99) {
                    Toast.makeText(this, "班级必须小于100", Toast.LENGTH_SHORT).show();
                    et_class.setText("" + sclass);
                } else {
                    sclass = sclass + 1;
                    et_class.setText("" + sclass);
                }
                break;
            case R.id.confirm:
                Intent intent = this.getIntent();
//                Bundle bundle =
                Bundle bundle = new Bundle();
                bundle.putString("sclass", "" + sclass);//添加要返回给页面1的数据
                intent.putExtras(bundle);
                this.setResult(2, intent);//返回页面1
                this.finish();
                break;
        }
    }
}
