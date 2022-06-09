package com.example.class_project;

import androidx.annotation.Nullable;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    Button btnImportData, btnNewData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(getApplicationContext(), SelectActivity.class);

        btnImportData = (Button) findViewById(R.id.btnImportData);
        btnNewData = (Button) findViewById(R.id.btnNewData);

        btnImportData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("getOldData", true);
                startActivity(intent);
            }
        });

        btnNewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("getOldData", false);
                startActivity(intent);
            }
        });
    }
}