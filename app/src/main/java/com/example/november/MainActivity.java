package com.example.november;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.button01)
    Button button01;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        ButterKnife.bind(this);
        initValue();
    }
    private void initValue(){
        button01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"李易峰",Toast.LENGTH_LONG).show();
            }
        });
    }
    @OnClick(R.id.button02)
    void btn2Click(){
        Toast.makeText(MainActivity.this,"李钟硕",Toast.LENGTH_LONG).show();
    }
}
