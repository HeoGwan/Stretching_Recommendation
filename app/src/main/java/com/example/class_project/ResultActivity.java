package com.example.class_project;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class ResultActivity extends Activity {
    class Stretching {
        FrameLayout[] images = new FrameLayout[9];

        /*  둔근, 햄스트링, 장요근, 극하근, 견갑거근,
            소흉근, t레이즈, 월슬라이드, y레이즈*/
        String[] stretchings = { "glutes", "hamstring", "iliopsoas", "infraspinatus", "levator_scapula",
                "pectoralis_minor", "t_raise", "wall_slide", "y_raise" };
        String[] stretchingVideos = {
                "https://youtu.be/9_AlY7Xz1Ww", // 둔근 스트레칭
                "https://youtu.be/hIWSco8luzo?t=106", // 햄스트링 스트레칭
                "https://youtu.be/VDzL-v5ehCQ", // 장요근 스트레칭
                "https://youtu.be/DwaDXnMQ9E8", // 극하근 스트레칭
                "https://youtu.be/fcQSiV22LOI", // 견갑거근 스트레칭
                "https://youtu.be/MY0d52eZM7o?t=172", // 소흉근 스트레칭
                "https://youtu.be/qgWM63Px6hc", // t레이즈
                "https://youtu.be/ukec3cnSOJM", // 월 슬라이드
                "https://youtu.be/dS7AlObWeYI" // y레이즈
        };

        Stretching() {
            images[0] = (FrameLayout) findViewById(R.id.glutesImage);
            images[1] = (FrameLayout) findViewById(R.id.hamstringImage);
            images[2] = (FrameLayout) findViewById(R.id.iliopsoasImage);
            images[3] = (FrameLayout) findViewById(R.id.infraspinatusImage);
            images[4] = (FrameLayout) findViewById(R.id.levatorScapulaImage);
            images[5] = (FrameLayout) findViewById(R.id.pectoralisMinorImage);
            images[6] = (FrameLayout) findViewById(R.id.tRaiseImage);
            images[7] = (FrameLayout) findViewById(R.id.wallSlideImage);
            images[8] = (FrameLayout) findViewById(R.id.yRaiseImage);

            for(int i = 0; i < images.length; ++i) {
                int index = i;
                images[index].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent video = new Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse(stretchingVideos[index]));

                        startActivity( video );
                    }
                });
            }

            this.initStrecthing();
        }

        public void initStrecthing() {
            for (FrameLayout image: images) {
                image.setVisibility(View.GONE);
            }
        }

        /*
            소흉근: pectoralis_minor
            견갑거근: levator_scapula
            극하근: infraspinatus
            장요근: iliopsoas
            둔근: glutes
        */
        private void showNeckStretching(String type) {
        /*
            목
                일자(STRAIGHT), 정상(NORMAL): 견갑거근
                거북목(FORWARD): 견갑거근, 소흉근
        */
            visibleStretching("levator_scapula");
            if (type.equals("FORWARD")) {
                visibleStretching("pectoralis_minor");
            }
        }

        private void showShoulderStretching(String type) {
        /*
            어깨
                상견(FLAT): 소흉근, t레이즈, y레이즈
                중견(NORMAL): 소흉근
                하견(SLOPE): 월 슬라이드, 극하근
        */
            if (type.equals("FLAT")) {
                visibleStretching("pectoralis_minor");
                visibleStretching("t_raise");
                visibleStretching("y_raise");
            } else if (type.equals("NORMAL")) {
                visibleStretching("pectoralis_minor");
            } else if (type.equals("SLOPE")) {
                visibleStretching("wall_slide");
                visibleStretching("infraspinatus");
            }
        }

        private void showBackStretching(String type) {
        /*
            허리
                정상(NORMAL):         둔근
                전방경사(ANTERIOR):    햄스트링, 장요근
                후방경사(POSTERIOR):   장요근
        */
            if (type.equals("NORMAL")) {
                visibleStretching("glutes");
            } else if (type.equals("ANTERIOR")) {
                visibleStretching("hamstring");
                visibleStretching("iliopsoas");
            } else if (type.equals("POSTERIOR")) {
                visibleStretching("iliopsoas");
            }
        }

        private void visibleStretching(String stretchingName) {
            int index = Arrays.binarySearch(stretchings, stretchingName);
            images[index].setVisibility(View.VISIBLE);
        }

        public void showStretching(String neck, String shoulder, String back) {
            showNeckStretching(neck);
            showShoulderStretching(shoulder);
            showBackStretching(back);
        }
    }

    TextView showBMI, showBody;
    TextView underweight, normalweight, overweight, obese,
            underweightValue, normalweightValue, overweightValue, obeseValue;

    Float height, weight, bmi;
    String showWeight, neck, shoulder, back;

    Stretching stretching;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        setTitle("추천 스트레칭");

        stretching = new Stretching();

        showBMI = (TextView) findViewById(R.id.showBMI);
        showBody = (TextView) findViewById(R.id.showBody);
        underweight = (TextView) findViewById(R.id.underweight);
        normalweight = (TextView) findViewById(R.id.normalweight);
        overweight = (TextView) findViewById(R.id.overweight);
        obese = (TextView) findViewById(R.id.obese);
        underweightValue = (TextView) findViewById(R.id.underweightValue);
        normalweightValue = (TextView) findViewById(R.id.normalweightValue);
        overweightValue = (TextView) findViewById(R.id.overweightValue);
        obeseValue = (TextView) findViewById(R.id.obeseValue);

        // 선택된 값 가져오기
        Intent intent = getIntent();
        height = intent.getFloatExtra("height", 0.0f);
        weight = intent.getFloatExtra("weight", 0.0f);

        try {
            float proceedHeight = height / 100;

            bmi = weight / (proceedHeight * proceedHeight);
            bmi = (float) (Math.round(bmi * 100) / 100.0);
            Log.d("bmi: ", Float.toString(bmi));
        } catch (Exception e) {
            Log.d("bmi: ", e.toString());
            bmi = 0.0f;
        }

        if (bmi == 0.0f) {
            Log.d("getBmi: ", "비만도를 가져오지 못했습니다.");
        }
        /*
            비만 기준
            저체중        18.5 미만
            정상체중      18.5이상 23미만
            과체중        23이상 25미만
            비만          25이상
                경  도    25이상 30미만
                중정도    30이상 35미만
                고  도    35 이상
        */
        showBMI.setText(bmi.toString());
        if (bmi < 18.5) {
            // 저체중
            showWeight = "저체중";
            showBMI.setTextColor(Color.BLUE);
            underweight.setTextColor(Color.RED);
            underweightValue.setTextColor(Color.RED);
        } else if (18.5 <= bmi && bmi < 23) {
            // 정상
            showWeight = "정상체중";
            showBMI.setTextColor(Color.BLACK);
            normalweight.setTextColor(Color.RED);
            normalweightValue.setTextColor(Color.RED);
        } else if (23 <= bmi && bmi < 25) {
            // 과체중
            showWeight = "과체중";
            showBMI.setTextColor(Color.YELLOW);
            overweight.setTextColor(Color.RED);
            overweightValue.setTextColor(Color.RED);
        } else {
            // 비만
            showWeight = "비만";
            showBMI.setTextColor(Color.RED);
            obese.setTextColor(Color.RED);
            obeseValue.setTextColor(Color.RED);
        }
        showBody.setText(showWeight);

        try {
            neck = intent.getStringExtra("neck");
            shoulder = intent.getStringExtra("shoulder");
            back = intent.getStringExtra("back");
        } catch (Exception e) {
            Log.d("getIntentData: ", e.toString());
        }


        // 스트레칭 보여주기
        stretching.showStretching(neck, shoulder, back);


        // 뒤로가기 버튼 초기화
        TextView goBack = (TextView) findViewById(R.id.goBack);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { finish(); }
        });

        // 되돌아가기 버튼 초기화
        Button btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { finish(); }
        });

        // 체중 기록 확인
        TextView recordedWeight = (TextView) findViewById(R.id.recordedWeight);
        recordedWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent recordWeight = new Intent(getApplicationContext(), RecordWeightActivity.class);
                startActivity(recordWeight);
            }
        });

        // 체중 기록 하기
        Button recordBody = (Button) findViewById(R.id.recordBody);
        recordBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences bmiRecord;
                SharedPreferences.Editor editor;
                Integer recordCount;

                bmiRecord = getSharedPreferences("bmiRecord", MODE_PRIVATE);
                editor = bmiRecord.edit();

                recordCount = bmiRecord.getInt("recordCount", -1) + 1;

                /*
                    데이터: "yyyy:MM:dd#키#몸무게#bmi#체중"
                */
                try {
                    String recordBMI = "";
                    String split = "#";
                    LocalDateTime now = LocalDateTime.now();
                    String date = now.format(DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss"));
                    recordBMI += date;
                    recordBMI += split;
                    recordBMI += height.toString();
                    recordBMI += split;
                    recordBMI += weight.toString();
                    recordBMI += split;
                    recordBMI += bmi.toString();
                    recordBMI += split;
                    recordBMI += showWeight;

                    editor.putString("recordBmi_" + recordCount.toString(), recordBMI);
                    editor.putInt("recordCount", recordCount);
                    editor.commit();

                    Toast.makeText(getApplicationContext(), "체중을 기록하였습니다.", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Log.e("recordBody", e.toString());
                    Toast.makeText(getApplicationContext(), "체중을 기록하지 못하였습니다.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
