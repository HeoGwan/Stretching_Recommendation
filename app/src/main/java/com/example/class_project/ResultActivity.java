package com.example.class_project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.Result;

public class ResultActivity extends Activity {
    class Stretching {
        FrameLayout[] images = new FrameLayout[9];
        String[] stretchings = { "glutes", "hamstring", "iliopsoas", "infraspinatus", "levator_scapula",
                "pectoralis_minor", "t_raise", "wall_slide", "y_raise" };

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

            this.initStrecthing();
        }

        public void initStrecthing() {
            for (FrameLayout image: images) {
                image.setVisibility(View.GONE);
            }
        }

        public void showStretching(String stretchingName) {
            int index = Arrays.binarySearch(stretchings, stretchingName);
            images[index].setVisibility(View.VISIBLE);
        }
    }

    Button btnBack;

    Float bmi;
    String weight, upperBody, lowerBody, neck, shoulder, back;

    Stretching stretching;


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
        stretching.showStretching("levator_scapula");
        if (type.equals("FORWARD")) {
            stretching.showStretching("pectoralis_minor");
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
            stretching.showStretching("pectoralis_minor");
            stretching.showStretching("t_raise");
            stretching.showStretching("y_raise");
        } else if (type.equals("NORMAL")) {
            stretching.showStretching("pectoralis_minor");
        } else if (type.equals("SLOPE")) {
            stretching.showStretching("wall_slide");
            stretching.showStretching("infraspinatus");
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
            stretching.showStretching("glutes");
        } else if (type.equals("ANTERIOR")) {
            stretching.showStretching("hamstring");
            stretching.showStretching("iliopsoas");
        } else if (type.equals("POSTERIOR")) {
            stretching.showStretching("iliopsoas");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        setTitle("추천 스트레칭");

        stretching = new Stretching();


        // 뒤로가기 버튼 초기화
        TextView goBack = (TextView) findViewById(R.id.goBack);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { finish(); }
        });

        // 선택된 값 가져오기
        Intent intent = getIntent();

        bmi = intent.getFloatExtra("bmi", 0.0f);
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
        if (bmi < 18.5) {
            // 저체중
            weight = "저체중";
        } else if (18.5 <= bmi && bmi < 23) {
            // 정상
            weight = "정상체중";
        } else if (23 <= bmi && bmi < 25) {
            // 과체중
            weight = "과체중";
        } else {
            // 비만
            weight = "비만";
        }


        try {
            neck = intent.getStringExtra("neck");
            shoulder = intent.getStringExtra("shoulder");
            back = intent.getStringExtra("back");
        } catch (Exception e) {
            Log.d("getIntentData: ", e.toString());
        }


        // 스트레칭 보여주기
        showNeckStretching(neck);
        showShoulderStretching(shoulder);
        showBackStretching(back);


        // 되돌아가기 버튼 초기화
        btnBack = (Button) findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
