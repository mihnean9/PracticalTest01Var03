package ro.pub.cs.systems.eim.practicaltest01var03;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PracticalTest01Var03MainActivity extends AppCompatActivity {

    EditText textOperand1, textOperand2;
    Button buttonAddition, buttonSubtraction, buttonToSecondary;
    TextView textViewResult;
    private IntentFilter intentFilter = new IntentFilter();

    private ButtonClickListener buttonClickListener = new ButtonClickListener();
    private class ButtonClickListener implements View.OnClickListener {

        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View view) {

            String a = textOperand1.getText().toString();
            String b = textOperand2.getText().toString();
            if (!Util.isInteger(a) || !Util.isInteger(b)) {
                textViewResult.setText("");
                Toast.makeText(getApplicationContext(), "One of the operands is missing or is not a valid number!", Toast.LENGTH_SHORT).show();
                return;
            }

            int aValue = Integer.parseInt(a);
            int bValue = Integer.parseInt(b);
            int result;
            Intent intent;

            switch(view.getId()) {
                case R.id.buttonAddition:
                    result = aValue + bValue;
                    textViewResult.setText(aValue + " + " + bValue + " = " + result);
                    intent = new Intent(getApplicationContext(), PracticalTest01Var03Service.class);
                    intent.putExtra(Constants.FIRST_OPERAND, aValue);
                    intent.putExtra(Constants.SECOND_OPERAND, bValue);
                    getApplicationContext().startService(intent);
                    break;

                case R.id.buttonSubtraction:
                    result = aValue - bValue;
                    textViewResult.setText(aValue + " - " + bValue + " = " + result);
                    intent = new Intent(getApplicationContext(), PracticalTest01Var03Service.class);
                    intent.putExtra(Constants.FIRST_OPERAND, aValue);
                    intent.putExtra(Constants.SECOND_OPERAND, bValue);
                    getApplicationContext().startService(intent);
                    break;

                case R.id.buttonNavigateToSecondActivity:
                    intent = new Intent(getApplicationContext(), PracticalTest01Var03SecondaryActivity.class);
                    String operation;
                    if (textViewResult.getText().toString().contains("+"))
                        operation = "ADDITION";
                    else if ((textViewResult.getText().toString().contains("-")))
                        operation = "SUBTRACTION";
                    else
                        operation = "NO OPERATION";
                    String s = operation + ": " + textViewResult.getText();
                    intent.putExtra(Constants.OPERATION_AND_RESULT, s);
                    startActivityForResult(intent, Constants.SECONDARY_ACTIVITY_REQUEST_CODE);
                    break;
            }
        }
    }

    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(Constants.BROADCAST_RECEIVER_TAG, intent.getStringExtra(Constants.BROADCAST_RECEIVER_EXTRA));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent(this, PracticalTest01Var03Service.class);
        stopService(intent);
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var03_main);

        textOperand1 = (EditText) findViewById(R.id.editText1);
        textOperand2 = (EditText) findViewById(R.id.editText2);
        buttonAddition = (Button) findViewById(R.id.buttonAddition);
        buttonSubtraction = (Button) findViewById(R.id.buttonSubtraction);
        textViewResult = (TextView) findViewById(R.id.textViewResult);
        buttonToSecondary = (Button) findViewById(R.id.buttonNavigateToSecondActivity);

        buttonAddition.setOnClickListener(buttonClickListener);
        buttonSubtraction.setOnClickListener(buttonClickListener);
        buttonToSecondary.setOnClickListener(buttonClickListener);

        for (int index = 0; index < Constants.actionTypes.length; index++) {
            intentFilter.addAction(Constants.actionTypes[index]);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(Constants.FIRST_OPERAND, textOperand1.getText().toString());
        savedInstanceState.putString(Constants.SECOND_OPERAND, textOperand2.getText().toString());
        savedInstanceState.putString(Constants.OPERATION_RESULT, textViewResult.getText().toString());
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState.containsKey(Constants.FIRST_OPERAND)) {
            textOperand1.setText(savedInstanceState.getString(Constants.FIRST_OPERAND));
        } else {
            textOperand1.setText("");
        }

        if (savedInstanceState.containsKey(Constants.SECOND_OPERAND)) {
            textOperand2.setText(savedInstanceState.getString(Constants.SECOND_OPERAND));
        } else {
            textOperand2.setText("");
        }

        if (savedInstanceState.containsKey(Constants.OPERATION_RESULT)) {
            textViewResult.setText(savedInstanceState.getString(Constants.OPERATION_RESULT));
        } else {
            textViewResult.setText("");
        }

        String s = "Operand1 : " + textOperand1.getText().toString() + "\n";
        s = s + "Operand2: " + textOperand2.getText().toString() + "\n";
        s = s + "Result: " + textViewResult.getText().toString();
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == Constants.SECONDARY_ACTIVITY_REQUEST_CODE) {
            Toast.makeText(this, "Secondary activity returned with code " + resultCode, Toast.LENGTH_LONG).show();
        }
    }

}