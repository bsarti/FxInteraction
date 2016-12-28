package com.example.agorn.fxinteraction;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button b_request;
    private EditText et_request;
    private TextView tv_output;
    private TextView tv_possibilities;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b_request = (Button) findViewById(R.id.brequest);
        et_request = (EditText) findViewById(R.id.etrequest);
        tv_output = (TextView) findViewById(R.id.tvoptput);
        tv_possibilities = (TextView) findViewById(R.id.tvpossibilities);
    }
}
