package com.example.class_project;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Map;

public class SelectActivity extends Activity {

    public enum Gender {
        MALE, FEMALE;

        public static int getGenderID(String value) {
            if (value.equals(MALE.name())) {
                return R.id.male;
            } else {
                return R.id.female;
            }
        }
    }

    public enum Neck {
        NORMAL, STRAIGHT, FORWARD;

        public static int getNeckID(String value) {
            if (value.equals(NORMAL.name())) {
                return R.id.normalNeck;
            } else if (value.equals(STRAIGHT.name())) {
                return R.id.straightNeck;
            } else {
                return R.id.forwardNeck;
            }
        }
    }

    public enum Shoulder {
        NORMAL, FLAT, SLOPE;

        public static int getShoulderID(String value) {
            if (value.equals(NORMAL.name())) {
                return R.id.normalShoulder;
            } else if (value.equals(FLAT.name())) {
                return R.id.flatShoulder;
            } else {
                return R.id.slopeShoulder;
            }
        }
    }

    public enum Back {
        NORMAL, ANTERIOR, POSTERIOR;

        public static int getBackID(String value) {
            if (value.equals(NORMAL.name())) {
                return R.id.normalPelvicTilt;
            } else if (value.equals(ANTERIOR.name())) {
                return R.id.anteriorPelvicTilt;
            } else {
                return R.id.posteriorPelvicTilt;
            }
        }
    }

    Gender gender;
    Float height, weight;
    Neck neck;
    Shoulder shoulder;
    Back back;

    ImageView[] infos = new ImageView[3];

    Button btnOK;

    EditText getWeight, getHeight;

    RadioGroup selectGender, selectNeck, selectShoulder, selectBack;

    RadioButton[] RdoBtns = new RadioButton[9];
    LinearLayout[] RdoBtnInfos = new LinearLayout[9];

    Integer[] RdoBtnIDs = {R.id.normalNeck, R.id.straightNeck, R.id.forwardNeck,
            R.id.flatShoulder, R.id.normalShoulder, R.id.slopeShoulder,
            R.id.normalPelvicTilt, R.id.anteriorPelvicTilt, R.id.posteriorPelvicTilt};

    Integer[] RdoBtnInfoIDs = {R.id.normalNeckInfo, R.id.straightNeckInfo, R.id.forwardNeckInfo,
            R.id.flatShoulderInfo, R.id.normalShoulderInfo, R.id.slopeShoulderInfo,
            R.id.normalPelvicTiltInfo, R.id.anteriorPelvicTiltInfo, R.id.posteriorPelvicTiltInfo};

    Integer[] infoIDs = {R.id.neckInfo, R.id.shoulderInfo, R.id.backInfo};

    Class[] infoActivity = {popupactivity.class, popupactivity1.class, popupactivity2.class};

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    public void setPrevData() {
        // ????????? ????????????
        String savedKey;
        String savedValue;
        Map<String, ?> values = pref.getAll();

        for (Map.Entry<String, ?> value: values.entrySet()) {
            savedKey = value.getKey();
            savedValue = value.getValue().toString();
            switch(savedKey) {
                case "height":
                    getHeight.setText(savedValue);
                    break;
                case "weight":
                    getWeight.setText(savedValue);
                    break;
                case "gender":
                    selectGender.check(Gender.getGenderID(savedValue));
                    break;
                case "neck":
                    selectNeck.check(Neck.getNeckID(savedValue));
                    break;
                case "shoulder":
                    selectShoulder.check(Shoulder.getShoulderID(savedValue));
                    break;
                case "back":
                    selectBack.check(Back.getBackID(savedValue));
                    break;
                default:
                    Log.d("setPrevData", savedKey + ' ' + savedValue);
            }
        }
    }

    public void getSelectedData() {
        // ?????? ????????????
        switch(selectGender.getCheckedRadioButtonId()) {
            case R.id.male:
                gender = Gender.MALE;
                break;
            case R.id.female:
                gender = Gender.FEMALE;
                break;
        }

        // ???, ?????? ????????????
        try {
            weight = Float.parseFloat(getWeight.getText().toString());
            height = Float.parseFloat(getHeight.getText().toString());
        } catch (Exception e) {
            weight = 0.0f;
            height = 0.0f;
        }

        // ?????? ????????????
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
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        setTitle("???????????? ??????");

        btnOK = (Button) findViewById(R.id.btnOK);

        getWeight = (EditText) findViewById(R.id.weight);
        getHeight = (EditText) findViewById(R.id.height);

        selectGender = (RadioGroup) findViewById(R.id.gender);
        selectNeck = (RadioGroup) findViewById(R.id.selectNeck);
        selectShoulder = (RadioGroup) findViewById(R.id.selectShoulder);
        selectBack = (RadioGroup) findViewById(R.id.selectBack);

        TextView goBack = (TextView) findViewById(R.id.goBack);

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { finish(); }
        });

        for(int i = 0; i < infoIDs.length; ++i) {
            int index = i;
            infos[index] = (ImageView) findViewById(infoIDs[index]);

            infos[index].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent popup = new Intent(getApplicationContext(), infoActivity[index]);
                    startActivity(popup);
                }
            });
        }

        // ???, ??????, ???????????? ?????????
        for (int i = 0; i < RdoBtnIDs.length; ++i) {
            int index = i;
            RdoBtns[index] = (RadioButton) findViewById(RdoBtnIDs[index]);
            RdoBtnInfos[index] = (LinearLayout) findViewById(RdoBtnInfoIDs[index]);

            RdoBtnInfos[index].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (RdoBtns[index].isChecked()) {
//                    ?????? ???????????????
                        RdoBtns[index].setChecked(false);
                    } else {
//                    ?????? ??? ???????????????
                        RdoBtns[index].setChecked(true);
                    }
                }
            });
        }

        pref = getSharedPreferences("pref", MODE_PRIVATE);
        editor = pref.edit();

        Intent main_intent = getIntent();
        Boolean getData = main_intent.getBooleanExtra("getOldData", true);
        if (getData) {
            setPrevData();
        }
        getSelectedData();


        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ?????? ????????????
                // ??? ????????????
                // ?????? ????????????
                // ??????(???, ??????, ??????) ????????????
                // ????????? ???, ???????????? ????????? ??????
                // ???????????? ????????? ???????????? ???????????? ??????

                // ?????? ??? ????????????
                getSelectedData();

                // ????????? ??????
                // bmi
                /*
                    ?????? ??????
                    ?????????        18.5 ??????
                    ????????????      18.5?????? 23??????
                    ?????????        23?????? 25??????
                    ??????          25??????
                        ???  ???    25?????? 30??????
                        ?????????    30?????? 35??????
                        ???  ???    35 ??????
                */

                // ????????? ????????? ??????
                editor.putFloat("weight", weight);
                editor.putFloat("height", height);
                editor.putString("gender", gender.name());
                editor.putString("neck", neck.name());
                editor.putString("shoulder", shoulder.name());
                editor.putString("back", back.name());
                editor.commit();

                // ?????? ???????????? ??????
                /*
                    ?????? ???????????? ????????? ?????????
                    ?????????, ????????? ??????, ???, ??????, ??????
                 */
                Intent result = new Intent(getApplicationContext(), ResultActivity.class);
                result.putExtra("weight", weight);
                result.putExtra("height", height);
                result.putExtra("neck", neck.name());
                result.putExtra("shoulder", shoulder.name());
                result.putExtra("back", back.name());

                startActivity(result);
            }
        });

    }
}