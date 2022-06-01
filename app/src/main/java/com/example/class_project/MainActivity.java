package com.example.class_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {

    public enum Form {
        LONG, SHORT, AVERAGE
    }

    public enum Gender {
        MALE, FEMALE
    }

    public enum Neck {
        NORMAL, STRAIGHT, FORWARD
    }

    public enum Shoulder {
        NORMAL, FLAT, SLOPE
    }

    public enum Back {
        NORMAL, ANTERIOR, POSTERIOR
    }

    Gender gender;
    Float height, weight;
    double bmi, broca;
    Neck neck;
    Shoulder shoulder;
    Back back;

    Form upperBody = Form.AVERAGE, lowerBody = Form.AVERAGE;

    ImageView showForm;

    Button btnOK;

    EditText getWeight, getHeight;

    RadioGroup selectUpperBody, selectLowerBody, selectGender,
            selectNeck, selectShoulder, selectBack;

    RadioButton[] RdoBtns = new RadioButton[9];
    LinearLayout[] RdoBtnInfos = new LinearLayout[9];

    Integer[] RdoBtnIDs = {R.id.normalNeck, R.id.straightNeck, R.id.forwardNeck,
                        R.id.flatShoulder, R.id.normalShoulder, R.id.slopeShoulder,
                        R.id.normalPelvicTilt, R.id.anteriorPelvicTilt, R.id.posteriorPelvicTilt};

    Integer[] RdoBtnInfoIDs = {R.id.normalNeckInfo, R.id.straightNeckInfo, R.id.forwardNeckInfo,
                    R.id.flatShoulderInfo, R.id.normalShoulderInfo, R.id.slopeShoulderInfo,
                    R.id.normalPelvicTiltInfo, R.id.anteriorPelvicTiltInfo, R.id.posteriorPelvicTiltInfo};

    public void ShowForm() {
        if (upperBody == Form.LONG) {
            if (lowerBody == Form.LONG) {
                showForm.setImageResource(R.drawable.long_long);
            } else if (lowerBody == Form.SHORT) {

            } else if (lowerBody == Form.AVERAGE) {

            }
        } else if (upperBody == Form.SHORT) {
            if (lowerBody == Form.LONG) {

            } else if (lowerBody == Form.SHORT) {

            } else if (lowerBody == Form.AVERAGE) {

            }
        } else if (upperBody == Form.AVERAGE) {
            if (lowerBody == Form.LONG) {

            } else if (lowerBody == Form.SHORT) {

            } else if (lowerBody == Form.AVERAGE) {
                showForm.setImageResource(R.drawable.avg_avg);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("스트레칭 추천");

        showForm = (ImageView) findViewById(R.id.showForm);

        btnOK = (Button) findViewById(R.id.btnOK);

        getWeight = (EditText) findViewById(R.id.weight);
        getHeight = (EditText) findViewById(R.id.height);

        selectUpperBody = (RadioGroup) findViewById(R.id.selectUpperBody);
        selectLowerBody = (RadioGroup) findViewById(R.id.selectLowerBody);
        selectGender = (RadioGroup) findViewById(R.id.gender);
        selectNeck = (RadioGroup) findViewById(R.id.selectNeck);
        selectShoulder = (RadioGroup) findViewById(R.id.selectShoulder);
        selectBack = (RadioGroup) findViewById(R.id.selectBack);

        // 체형 선택 라디오 버튼 초기화
        for (int i = 0; i < RdoBtnIDs.length; ++i) {
            int index = i;
            RdoBtns[index] = (RadioButton) findViewById(RdoBtnIDs[index]);
            RdoBtnInfos[index] = (LinearLayout) findViewById(RdoBtnInfoIDs[index]);

            RdoBtnInfos[index].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (RdoBtns[index].isChecked()) {
//                    체크 되어있다면
                        RdoBtns[index].setChecked(false);
                    } else {
//                    체크 안 되어있다면
                        RdoBtns[index].setChecked(true);
                    }
                }
            });
        }

        selectUpperBody.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch(radioGroup.getCheckedRadioButtonId()) {
                    case R.id.upperBodyLong:
                        upperBody = Form.LONG;
                        break;
                    case R.id.upperBodyShort:
                        upperBody = Form.SHORT;
                        break;
                    case R.id.upperBodyAverage:
                        upperBody = Form.AVERAGE;
                        break;
                }

                ShowForm();
            }
        });

        selectLowerBody.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch(radioGroup.getCheckedRadioButtonId()) {
                    case R.id.lowerBodyLong:
                        lowerBody = Form.LONG;
                        break;
                    case R.id.lowerBodyShort:
                        lowerBody = Form.SHORT;
                        break;
                    case R.id.lowerBodyAverage:
                        lowerBody = Form.AVERAGE;
                        break;
                }

                ShowForm();
            }
        });

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 성별 가져오기
                // 키 가져오기
                // 체중 가져오기
                // 체형(목, 어깨, 허리) 가져오기
                // 성별과 키, 체중으로 비만도 계산
                // 비만도와 체형을 기반으로 스트레칭 추천

                // 성별 가져오기
                switch(selectGender.getCheckedRadioButtonId()) {
                    case R.id.male:
                        gender = Gender.MALE;
                        break;
                    case R.id.female:
                        gender = Gender.FEMALE;
                        break;
                }

                // 키, 체중 가져오기
                try {
                    weight = Float.parseFloat(getWeight.getText().toString());
                    height = Float.parseFloat(getHeight.getText().toString());
                } catch (Exception e) {
                    weight = 1.0f;
                    height = 1.0f;
                }

                // 비만도 계산
                // bmi
                /*
                    비만도(%)= 표준 체중 대비 백분율(%) = 측정 체중/표준 체중 × 100
                        표준체중 계산 방법
                        - 남성: 키(m) × 키(m) × 22
                        - 여성: 키(m) × 키(m) × 21
                 */
                Float standardWeight = null;
                if (gender == Gender.MALE) {
                    standardWeight = height * height * 22;
                } else if (gender == Gender.FEMALE) {
                    standardWeight = height * height * 21;
                }

                try {
                    bmi = weight / standardWeight * 100;
                } catch (Exception e) {
                    bmi = 0.0f;
                }

                // broca
                /*
                    신장별 Broca 공식의 표준체중계산법
                        160 cm 이상 (신장 cm-100 ) × 0.9
                        160- 150 cm (신장 cm-150) ÷2 + 50
                        150 이하 (신장 cm-100 ) × 1.0
                 */
                if (height >= 160) {
                    broca = (height - 100) * 0.9;
                } else if (160 > height && height > 150) {
                    broca = (height - 150) / 2 + 50;
                } else {
                    broca = (height - 100) * 1.0;
                }


                // 체형 가져오기
                switch(selectNeck.getCheckedRadioButtonId()) {
                    case R.id.normalNeck:
                        neck = Neck.NORMAL;
                        break;
                    case R.id.straightNeck:
                        neck = Neck.STRAIGHT;
                        break;
                    case R.id.forwardNeck:
                        neck = Neck.FORWARD;
                        break;
                }

                switch(selectShoulder.getCheckedRadioButtonId()) {
                    case R.id.normalShoulder:
                        shoulder = Shoulder.NORMAL;
                        break;
                    case R.id.flatShoulder:
                        shoulder = Shoulder.FLAT;
                        break;
                    case R.id.slopeShoulder:
                        shoulder = Shoulder.SLOPE;
                        break;
                }

                switch(selectBack.getCheckedRadioButtonId()) {
                    case R.id.normalPelvicTilt:
                        back = Back.NORMAL;
                        break;
                    case R.id.anteriorPelvicTilt:
                        back = Back.ANTERIOR;
                        break;
                    case R.id.posteriorPelvicTilt:
                        back = Back.POSTERIOR;
                        break;
                }

                // 비만도와 체형을 기반으로 스트레칭 추천
                
                
                // 결과 화면으로 이동
                Intent result = new Intent(getApplicationContext(), ResultActivity.class);
                startActivity(result);
            }
        });

    }
}