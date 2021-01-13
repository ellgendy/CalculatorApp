package com.elgendy.calculatorapp;

/**
 Created by AhmedElgendy on 9/1/2021.
 */
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    boolean newOp = true;
    String op="+",oldNumber="";
    TextView textView;

    protected static final int RESULT_SPEECH = 1 ;
    private ImageButton imageBuMic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText=findViewById(R.id.editText);
        textView=findViewById(R.id.textView);
        imageBuMic=findViewById(R.id.imageBuMic);
        imageBuMic.setOnClickListener(v -> {
            Intent in = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            in.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            in.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"en-US");
            try{
                startActivityForResult(in,RESULT_SPEECH);
                editText.setText("");
            }catch (ActivityNotFoundException e){
                Toast.makeText(getApplicationContext(),"Your device doesn't support speech to text",Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case RESULT_SPEECH:
                if (resultCode == RESULT_OK && data !=null){
                    ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    editText.setText(text.get(0));
                }
                break;
        }
    }

    public void numberClick(View view) {
        if (newOp)
            editText.setText("");
        newOp=false;
        String number=editText.getText().toString();
        switch (view.getId()){
            case R.id.bu1:
                number +="1";
                break;
            case R.id.bu2:
                number +="2";
                break;
            case R.id.bu3:
                number +="3";
                break;
            case R.id.bu4:
                number +="4";
                break;
            case R.id.bu5:
                number +="5";
                break;
            case R.id.bu6:
                number +="6";
                break;
            case R.id.bu7:
                number +="7";
                break;
            case R.id.bu8:
                number +="8";
                break;
            case R.id.bu9:
                number +="9";
                break;
            case R.id.bu0:
                number +="0";
                break;
            case R.id.buDot:
                number +=".";
                break;
            case R.id.buPlusMinus:
                number ="-"+number;
                break;
        }
        editText.setText(number);
    }

    public void operatorClick(View view) {
        newOp=true;
        oldNumber=editText.getText().toString();
        switch (view.getId()){
            case R.id.buDivide:
                op="/";
                break;
            case R.id.buMultiply:
                op="*";
                break;
            case R.id.buPlus:
                op="+";
                break;
            case R.id.buMinus:
                op="-";
                break;
        }
        editText.setText(op);
    }
    public void equalClick(View view) {
        String newNumber=editText.getText().toString();
        Double result=0.0;
        switch (op){
            case "+":
                result = Double.parseDouble(oldNumber) + Double.parseDouble(newNumber);
                break;
            case "-":
                result = Double.parseDouble(oldNumber) - Double.parseDouble(newNumber);
                break;
            case "*":
                result = Double.parseDouble(oldNumber) * Double.parseDouble(newNumber);
                break;
            case "/":
                result = Double.parseDouble(oldNumber) / Double.parseDouble(newNumber);
                break;
        }
        editText.setText(result+"");
        textView.setText(oldNumber.toString()+op+newNumber.toString()+"="+result+"");

    }

    public void acClick(View view) {
        editText.setText("0");
        newOp=true;
    }

    public void percentClick(View view) {
        Double no=Double.parseDouble(editText.getText().toString())/100;
        editText.setText(no+"");
        newOp=true;
    }

}