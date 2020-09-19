package com.example.simplecalculator;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String LOG = "MyLog";
    private Calculator calculator;
    private TextView tvResult;
    private EditText etFirstValue, etSecondValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etFirstValue = findViewById(R.id.editTextFirstValue);
        etSecondValue = findViewById(R.id.editTextSecondValue);
        tvResult = findViewById(R.id.tvResult);
        calculator = new Calculator();
    }

    public void compute(Calculator.Operation operation) {
        double firstValue, secondValue;
        try {
            firstValue = getDoubleOperand(etFirstValue);
            secondValue = getDoubleOperand(etSecondValue);
        } catch (NumberFormatException e) {
            Log.e(LOG, "NumberFormatException", e);
            tvResult.setText(R.string.computationError);
            return;
        }
        double result = 0.0;
        try {
            switch (operation) {
                case SUM:
                    result = calculator.sum(firstValue, secondValue);
                    break;
                case SUB:
                    result = calculator.sub(firstValue, secondValue);
                    break;
                case MULT:
                    result = calculator.mult(firstValue, secondValue);
                    break;
                case DIV:
                    result = calculator.div(firstValue, secondValue);
                    break;
                case POW:
                    result = calculator.pow(firstValue, secondValue);
                    break;
            }
            setDoubleOperand(tvResult, result);
        } catch (IllegalArgumentException ie) {
            showExceptionMessage(ie);
        } catch (Exception e) {
            showExceptionMessage(e);
        }
    }

    private void showExceptionMessage(Exception e) {
        Toast toast = Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT);
        changeToastColor(toast);
        toast.show();
    }

    private void changeToastColor(Toast toast) {
        toast.getView().setBackgroundColor(Color.RED);
        toast.getView().setPadding(5,5,5,5);
    }

    private void setDoubleOperand(TextView textView, double result) {
        textView.setText(String.valueOf(result));
    }

    private double getDoubleOperand(EditText editText) {
        return Double.parseDouble(editText.getText().toString());
    }

    public void add(View view) {
        compute(Calculator.Operation.SUM);
    }

    public void sub(View view) {
        compute(Calculator.Operation.SUB);
    }

    public void mult(View view) {
        compute(Calculator.Operation.MULT);
    }

    public void div(View view) {
        compute(Calculator.Operation.DIV);
    }

    public void pow(View view) {
        compute(Calculator.Operation.POW);
    }
}