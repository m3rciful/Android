package com.example.merciful.mycalc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;
import android.widget.Toast; // для сообщений

public class MainActivity extends AppCompatActivity {

    EditText numberField;
    TextView operationField;
    Double operand = null;
    String lastOperation = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calc_main);

        //записываем значения из полей в переменные
        numberField = (EditText)findViewById(R.id.numberField);
        operationField = (TextView)findViewById(R.id.operationField);

    }

    @Override //сохраняем состояние переменных
    protected void onSaveInstanceState(Bundle outState) {

        outState.putString("OPERATION", lastOperation);

        if(operand != null) {

            outState.putDouble("OPERAND", operand);

        }

        super.onSaveInstanceState(outState);

    }

    @Override //получаем сохранненый результат
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);
        operand = savedInstanceState.getDouble("OPERAND");

        if(operand > 0) {

            lastOperation = savedInstanceState.getString("OPERATION");
            //resultField.setText(operand.toString());
            //operationField.setText(lastOperation);

        } else {

            String operation = operationField.getText().toString();

            operand = null;
            operation = null;

            operationField.setText("");

        }
    }

    // Функция для кнопок с цифрами (0-9)
    public void onNumberClick(View view) {

        Button button = (Button)view;
        numberField.append(button.getText()); // Элементу 'numberFiled' передается текст из кнопок.

        if(lastOperation.equals('=') && operand != null) {
            operand = null;
        }

    }

    //функция обработки нажатия кнопки операции
    public void onOperationClick(View view) {

        Button button = (Button) view;
        String op = button.getText().toString(); // операция взятая из текста кнопки.
        String number = numberField.getText().toString(); // число из формы 'numberField' форматированное в String

        // Если число больше нуля.
        if (number.length() > 0) {

            try {
                // Обращение к функции с математическими подсчетами
                performOperation(Double.valueOf(number), op);

            } catch (NumberFormatException ex) {

                numberField.setText("");

            }

            lastOperation = op;
            // Выводит инфомрацио об операции типа "1 + " вверху.
            if (lastOperation.equals("=")) {
                operationField.setText("");
            } else {
                operationField.setText(operand + " " + lastOperation + " ");
            }
        }
        else {
            // Сообщение об ошибке
            Toast.makeText(getApplicationContext(),"Please enter a number :D",Toast.LENGTH_LONG).show();
        }
    }

    // Функция очищается все формы
    public void onClearClick(View view) {

        String number = numberField.getText().toString();
        String operation = operationField.getText().toString();

        if(number.length() > 0 || operation.length() > 0) {

            numberField.setText("");
            operationField.setText("");
            operand = null;

        }
    }

    // Выполняет математические действия с числом
    public void performOperation(Double number, String operation) {

        if (operand == null) {

            operand = number;

        } else {

            if (lastOperation.equals("=")) {
                lastOperation = operation;
            }

            switch (lastOperation) {

                case "=":
                    operand = number;
                    break;

                case "÷":
                    if (number == 0) {
                        operand = 0.0;
                    } else {
                        operand /= number;
                    }
                    break;

                case "×":
                    operand *= number;
                    break;

                case "+":
                    operand += number;
                    break;

                case "-":
                    operand -= number;
                    break;

            }
        }

        // Если это ответ показывает в поле 'numberFiled', если необходимо ввести второе число отчищает форму.
        if (operation.equals("=")) {
            numberField.setText(operand.toString());
        }
        else {
            numberField.setText("");
        }

    }
}