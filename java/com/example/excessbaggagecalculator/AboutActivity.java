package com.example.excessbaggagecalculator;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.card.MaterialCardView;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        // Toolbar setup with Back Arrow
        Toolbar toolbar = findViewById(R.id.aboutToolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // Handle Toolbar Back Arrow Click
        toolbar.setNavigationOnClickListener(v -> finish());

        // Handle Bottom "Back to Calculator" Button Click
        Button btnBack = findViewById(R.id.btnBackToCalculator);
        btnBack.setOnClickListener(v -> finish());

        // Handle Clickable GitHub Card / URL
        TextView tvGithubUrl = findViewById(R.id.tvGithubUrl);
        MaterialCardView cardGithub = findViewById(R.id.cardGithub);

        cardGithub.setOnClickListener(v -> openGithubUrl(tvGithubUrl.getText().toString()));
    }

    private void openGithubUrl(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }
}