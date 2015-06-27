package org.scystl.calculadorabasica;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
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

    private TextView textResult;
    private HashMap<String, Operation> operations;
    private Operation currentOperation;
    private boolean isEditingFirstValue;
    private float firstValue = 0;
    private float secondValue = 0;
    private boolean isEditingFirstDigit = true;


    /**Private methods**/
    private void init() {
        this.isEditingFirstValue = true;
        this.textResult = (TextView)findViewById(R.id.txt_result);
        this.textResult.setText("");
        this.operations = new HashMap<>();
        this.operations.put("+", new AditionOperation());
        this.operations.put("-", new SubtractionOperation());
        this.operations.put("*", new MultiplyOperation());
        this.operations.put("/", new DivisionOperation());
    }

    private void displayInTextResult(String text) {
        this.textResult.setText(text);
    }

    private void setValue(String value) {
        if(isEditingFirstValue) {
            firstValue = Float.parseFloat(value);
        }else {
            secondValue = Float.parseFloat(value);
        }
    }

    /**Protected methods**/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    /**Public methods**/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onTouchNumber(View view) {
        Button numberButton = (Button) view;
        String number = numberButton.getText().toString();
        String newTextResult;
        if(isEditingFirstDigit) {
            newTextResult = number;
            isEditingFirstDigit = false;
        }else{
            newTextResult = this.textResult.getText().toString() + number;
        }
        this.setValue(newTextResult);
        this.displayInTextResult(newTextResult);
    }

    public void onTouchOperation(View view){
        Button operationButton = (Button) view;
        String operationText = operationButton.getText().toString();
        Operation selectedOperation = this.operations.get(operationText);

        if(!this.isEditingFirstValue) {
            if(this.currentOperation != null){
                this.firstValue = this.currentOperation.calculate(this.firstValue, this.secondValue);
            }else{
                this.firstValue = this.secondValue;
            }
        }
        this.isEditingFirstValue  = false;
        this.displayInTextResult("");
        this.currentOperation = selectedOperation;
    }

    public void onTouchDot(View view) {
        String currentText = this.textResult.getText().toString();
        if(!currentText.contains(".")) {
            displayInTextResult(currentText+".");
        }
    }

    public void calculate(View view) {
        if (this.currentOperation != null) {
            float result = this.currentOperation.calculate(this.firstValue, this.secondValue);
            this.firstValue = result;
            this.isEditingFirstValue = true;
            this.isEditingFirstDigit = true;
            this.displayInTextResult(result + "");
            this.currentOperation = null;
        }
    }
}
