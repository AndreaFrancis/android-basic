package org.scystl.calculadorabasica;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.scystl.operations.AditionOperation;
import org.scystl.operations.DivisionOperation;
import org.scystl.operations.MultiplyOperation;
import org.scystl.operations.Operation;
import org.scystl.operations.SubtractionOperation;

import java.util.HashMap;


public class MainActivity extends ActionBarActivity {

    private TextView mResultTextView;
    private HashMap<String, Operation> mOperations;
    private Operation mCurrentOperation;
    private boolean mIsEditingFirstValue;
    private boolean mIsEditingFirstDigit;
    private float mFirstValue;
    private float mSecondValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onTouchNumber(View view) {
        Button numberButton = (Button) view;
        String number = numberButton.getText().toString();
        String newTextResult = newTextResult = mResultTextView.getText().toString() + number;
        if (mIsEditingFirstDigit) {
            newTextResult = number;
            mIsEditingFirstDigit = false;
        }
        setValue(newTextResult);
    }

    public void onTouchOperation(View view) {
        Button operationButton = (Button) view;
        String operationText = operationButton.getText().toString();
        Operation selectedOperation = mOperations.get(operationText);
        if (!mIsEditingFirstValue) {
            if (mCurrentOperation != null) {
                mFirstValue = mCurrentOperation.calculate(mFirstValue, mSecondValue);
            } else {
                mFirstValue = mSecondValue;
            }
        }
        mIsEditingFirstValue = false;
        displayInResultText("");
        mCurrentOperation = selectedOperation;
    }

    public void onTouchDot(View view) {
        String currentText = mResultTextView.getText().toString();
        if (!currentText.contains(".")) {
            displayInResultText(currentText + ".");
        }
    }

    public void calculate(View view) {
        if (mCurrentOperation != null) {
            float result = mCurrentOperation.calculate(mFirstValue, mSecondValue);
            mFirstValue = result;
            displayInResultText(result + "");
            setUpInitialValues();
        }
    }

    private void init() {
        mFirstValue = 0;
        mSecondValue = 0;
        mResultTextView = (TextView) findViewById(R.id.txt_result);
        mResultTextView.setText("");

        //Adding supported operations
        mOperations = new HashMap<>();
        mOperations.put("+", new AditionOperation());
        mOperations.put("-", new SubtractionOperation());
        mOperations.put("*", new MultiplyOperation());
        mOperations.put("/", new DivisionOperation());

        setUpInitialValues();
    }

    private void setUpInitialValues() {
        mIsEditingFirstDigit = true;
        mIsEditingFirstValue = true;
        mCurrentOperation = null;
    }

    private void displayInResultText(String text) {
        mResultTextView.setText(text);
    }

    private void setValue(String value) {
        if (mIsEditingFirstValue) {
            mFirstValue = Float.parseFloat(value);
        } else {
            mSecondValue = Float.parseFloat(value);
        }
        displayInResultText(value);
    }
}
