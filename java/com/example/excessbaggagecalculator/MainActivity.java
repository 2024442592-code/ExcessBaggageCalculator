package com.example.excessbaggagecalculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText etWeight, etRate;
    private TextInputLayout inputLayoutWeight, inputLayoutRate;
    private Spinner spinnerTicketClass;
    private TextView tvExcessWeight, tvExcessCharge, tvTotalPayable;
    private Button btnCalculate, btnClear;

    // TODO: Replace with your actual GitHub repository URL once created
    private final String GITHUB_URL = "https://github.com/2024442592-code/ExcessBaggageCalculator";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Toolbar setup
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // UI View Binding
        etWeight = findViewById(R.id.etWeight);
        etRate = findViewById(R.id.etRate);
        inputLayoutWeight = findViewById(R.id.inputLayoutWeight);
        inputLayoutRate = findViewById(R.id.inputLayoutRate);
        spinnerTicketClass = findViewById(R.id.spinnerTicketClass);
        tvExcessWeight = findViewById(R.id.tvExcessWeight);
        tvExcessCharge = findViewById(R.id.tvExcessCharge);
        tvTotalPayable = findViewById(R.id.tvTotalPayable);
        btnCalculate = findViewById(R.id.btnCalculate);
        btnClear = findViewById(R.id.btnClear);

        // Setup Ticket Class Spinner Options
        String[] classes = {"Economy Class (20 kg limit)", "Business Class (40 kg limit)"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, classes);
        spinnerTicketClass.setAdapter(adapter);

        btnCalculate.setOnClickListener(v -> calculateFee());
        btnClear.setOnClickListener(v -> clearFields());
    }

    private void calculateFee() {
        inputLayoutWeight.setError(null);
        inputLayoutRate.setError(null);

        String weightStr = etWeight.getText().toString().trim();
        String rateStr = etRate.getText().toString().trim();

        // Error handling & validation
        if (weightStr.isEmpty()) {
            inputLayoutWeight.setError("Please enter the baggage weight");
            return;
        }
        if (rateStr.isEmpty()) {
            inputLayoutRate.setError("Please enter the rate per kg");
            return;
        }

        try {
            double weight = Double.parseDouble(weightStr);
            double rate = Double.parseDouble(rateStr);

            if (weight < 0) {
                inputLayoutWeight.setError("Weight cannot be negative");
                return;
            }
            if (rate < 0) {
                inputLayoutRate.setError("Rate cannot be negative");
                return;
            }

            // Determine limit X: 20kg for Economy (index 0), 40kg for Business (index 1)
            int selectedPosition = spinnerTicketClass.getSelectedItemPosition();
            double limitX = (selectedPosition == 0) ? 20.0 : 40.0;

            // Calculations according to rubric formulas
            double excessWeight = Math.max(0.0, weight - limitX);
            double excessCharge = excessWeight * rate;
            double totalPayable = excessCharge + (0.08 * excessCharge);

            // Display formatted outputs
            tvExcessWeight.setText(String.format(Locale.getDefault(), "Excess Baggage Weight: %.2f kg", excessWeight));
            tvExcessCharge.setText(String.format(Locale.getDefault(), "Excess Baggage Charge: RM %.2f", excessCharge));
            tvTotalPayable.setText(String.format(Locale.getDefault(), "Total Amount Payable: RM %.2f", totalPayable));

            Toast.makeText(this, "Calculation completed!", Toast.LENGTH_SHORT).show();

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid input number format.", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearFields() {
        etWeight.setText("");
        etRate.setText("");
        spinnerTicketClass.setSelection(0);
        tvExcessWeight.setText("Excess Baggage Weight: 0.00 kg");
        tvExcessCharge.setText("Excess Baggage Charge: RM 0.00");
        tvTotalPayable.setText("Total Amount Payable: RM 0.00");
        inputLayoutWeight.setError(null);
        inputLayoutRate.setError(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_share) {
            shareAppUrl();
            return true;
        } else if (id == R.id.action_about) {
            startActivity(new Intent(this, AboutActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void shareAppUrl() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Excess Baggage Fee App");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out the Excess Baggage Calculator application: " + GITHUB_URL);
        startActivity(Intent.createChooser(shareIntent, "Share App URL via"));
    }
}