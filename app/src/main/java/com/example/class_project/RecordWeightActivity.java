package com.example.class_project;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class RecordWeightActivity extends Activity {

    int recordCount;

    SharedPreferences bmiRecord;
    SharedPreferences.Editor editor;
    LinearLayout showRecord;

    private float getFDP(float value) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int dpi = displayMetrics.densityDpi;
        float density = displayMetrics.density;
        float result = (float) Math.round( value * density + 0.5 );
        return result;
    }

    private float getFDP(int value) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int dpi = displayMetrics.densityDpi;
        float density = displayMetrics.density;
        float result = (float) Math.round( value * density + 0.5 );
        return result;
    }

    private int getDP(float value) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int dpi = displayMetrics.densityDpi;
        float density = displayMetrics.density;
        int result = (int) Math.round( value * density + 0.5 );
        return result;
    }

    private int getDP(int value) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int dpi = displayMetrics.densityDpi;
        float density = displayMetrics.density;
        int result = (int) Math.round( value * density + 0.5 );
        return result;
    }

    private String getInfoString(int index) {
        /*
            index    : 문자열
                0    :
                1    : "키"
                2    : "몸무게"
                3    : "bmi"
                4    : "체중"
        */

        switch(index) {
            case 1:
                return "키:";
            case 2:
                return "몸무게:";
            case 3:
                return "BMI:";
            case 4:
                return "체중:";
            default:
                return "";
        }
    }

    private void showRecordBMI(String info) {
        /*
            데이터: "yyyy:MM:dd#키#몸무게#bmi#체중"
            infos[0] : "yyyy:MM:dd"
            infos[1] : "키"
            infos[2] : "몸무게"
            infos[3] : "bmi"
            infos[4] : "체중"
        */
        String[] infos = info.split("#");


        TableLayout table = new TableLayout(this);
        LinearLayout.LayoutParams tlParams = new LinearLayout.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT
        );
        tlParams.gravity = Gravity.CENTER_HORIZONTAL;
        tlParams.setMargins(getDP(20), getDP(10), getDP(20), getDP(20));
        table.setLayoutParams(tlParams);
        table.setGravity(Gravity.CENTER);
        table.setPadding(getDP(10), getDP(10), getDP(10), getDP(10));
        table.setBackgroundResource(R.drawable.record_background);

        TableRow[] tableRows = new TableRow[5];

        LinearLayout.LayoutParams trParams = new LinearLayout.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT
        );
        trParams.gravity = Gravity.CENTER;

        for (int i = 0; i < tableRows.length; ++i) {
            tableRows[i] = new TableRow(this);
            tableRows[i].setLayoutParams(trParams);

            if (i == 0) {
                TextView tv = new TextView(this);
                tv.setGravity(Gravity.LEFT);
                tv.setText(infos[i]);
                tv.setTextSize(getDP(6));

                tableRows[i].addView(tv);
            } else {
                TextView tvInfo = new TextView(this);
                tvInfo.setGravity(Gravity.LEFT);
                tvInfo.setText(getInfoString(i));
                tvInfo.setTextSize(getDP(8));

                tableRows[i].addView(tvInfo);

                TextView tvData = new TextView(this);
                tvData.setGravity(Gravity.RIGHT);
                tvData.setText(infos[i]);
                tvData.setTextSize(getDP(8));

                tableRows[i].addView(tvData);
            }

            table.addView(tableRows[i]);
        }

        showRecord.addView(table);
    }

    private void showEmpty() {
        TextView empty = new TextView(this);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.gravity = Gravity.CENTER;

        empty.setLayoutParams(params);
        empty.setText("기록이 없습니다.");
        empty.setTextSize(getDP(12));

        showRecord.addView(empty);
    }

    private void initShowRecord() {
        showRecord.removeAllViews();
    }

    private void setBottom() {
        showRecord.addView(makeLine());
        showRecord.addView(makeBackButton());
    }

    private View makeLine() {
        TextView line = new TextView(this);

        line.setBackgroundResource(R.color.darker_grey);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        params.height = 5;
        params.leftMargin = getDP(20);
        params.rightMargin = getDP(20);
        params.topMargin = getDP(20);

        line.setLayoutParams(params);

        return line;
    }

    private View makeBackButton() {
        Button btnBack = new Button(this);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.gravity = Gravity.CENTER;
        params.bottomMargin = getDP(20);
        params.topMargin = getDP(20);

        btnBack.setLayoutParams(params);
        btnBack.setBackgroundResource(R.drawable.button_design);
        btnBack.setText("나가기");

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { finish(); }
        });

        return btnBack;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_weight);

        showRecord = (LinearLayout) findViewById(R.id.showRecord);
        initShowRecord();

        try {
            bmiRecord = getSharedPreferences("bmiRecord", MODE_PRIVATE);
            editor = bmiRecord.edit();
            recordCount = bmiRecord.getInt("recordCount", -1);
        } catch (Exception e) {
            Log.e("RecordWeightActivity(onCreate): ", e.toString());
            recordCount = -1;
        }

        // 출력
        if (recordCount == -1) {
            showEmpty();
        } else {
            // 데이터 가져오기
            for (int i = 0; i <= recordCount; ++i) {
                String info = bmiRecord.getString("recordBmi_" + Integer.toString(i), "error");
                Log.d("bmiRecord.getString(): ", Integer.toString(i) + ' ' + info);
                try {
                    showRecordBMI(info);
                } catch (Exception e) {
                    Log.d("asdfsadfasdf", e.toString());
                }
            }
        }
        setBottom();

        // 기록 초기화
        TextView btnInitRecord = (TextView) findViewById(R.id.initRecord);
        btnInitRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("initRecord", "기록 초기화 기능");
                Toast.makeText(getApplicationContext(), "기록을 초기화 하였습니다.", Toast.LENGTH_SHORT).show();
                editor.clear();
                editor.commit();
            }
        });

        // 뒤로가기 버튼 초기화
        TextView btnGoBack = (TextView) findViewById(R.id.goBack);
        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { finish(); }
        });
    }
}
