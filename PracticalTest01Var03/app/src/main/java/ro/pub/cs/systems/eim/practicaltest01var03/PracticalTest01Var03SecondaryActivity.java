package ro.pub.cs.systems.eim.practicaltest01var03;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PracticalTest01Var03SecondaryActivity extends AppCompatActivity {

    Button buttonCorrect, buttonIncorrect;
    TextView textViewOperation;

    private ButtonClickListener buttonClickListener = new ButtonClickListener();
    private class ButtonClickListener implements View.OnClickListener {
        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.buttonCorrect:
                    setResult(RESULT_OK);
                    break;
                case R.id.buttonIncorrect:
                    setResult(RESULT_CANCELED);
                    break;
            }
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var03_secondary);

        textViewOperation = (TextView) findViewById(R.id.textViewOperation);
        Intent intent = getIntent();
        if (intent != null && intent.getExtras().containsKey(Constants.OPERATION_AND_RESULT)) {
            textViewOperation.setText(intent.getStringExtra(Constants.OPERATION_AND_RESULT));
        }

        buttonCorrect = (Button)findViewById(R.id.buttonCorrect);
        buttonCorrect.setOnClickListener(buttonClickListener);
        buttonIncorrect = (Button)findViewById(R.id.buttonIncorrect);
        buttonIncorrect.setOnClickListener(buttonClickListener);
    }
}
