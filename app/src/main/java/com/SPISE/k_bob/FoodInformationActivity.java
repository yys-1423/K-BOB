package com.SPISE.k_bob;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class FoodInformationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_information);

        String foodName = getIntent().getStringExtra("foodName");
        int foodImageResId = getIntent().getIntExtra("foodImageResId", -1);
        String detailedDescription = getIntent().getStringExtra("detailedDescription");
        String majorIngredients = getIntent().getStringExtra("majorIngredients");
        String veganType = getIntent().getStringExtra("veganType");
        String dietaryRestrictions = getIntent().getStringExtra("dietaryRestrictions");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        ImageView foodImageView = findViewById(R.id.food_image);
        TextView foodNameTextView = findViewById(R.id.food_name);
        TextView descriptionTextView = findViewById(R.id.description_text);
        TextView keyComponentsTextView = findViewById(R.id.major_ingredients_text);
        TextView veganTypeTextView = findViewById(R.id.vegan_type_text);
        TextView dietaryRestrictionsTextView = findViewById(R.id.dietary_restrictions_text);
        Button viewInMapButton = findViewById(R.id.view_restaurants_button);

        foodImageView.setImageResource(foodImageResId);
        foodNameTextView.setText(foodName);
        descriptionTextView.setText(detailedDescription);
        keyComponentsTextView.setText(majorIngredients);
        veganTypeTextView.setText(veganType);
        dietaryRestrictionsTextView.setText(dietaryRestrictions);

        viewInMapButton.setOnClickListener(v -> {
            Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode(foodName + " restaurant"));
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        });
    }
}
