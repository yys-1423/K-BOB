package com.SPISE.k_bob;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// hong
public class FoodSuggestionActivity extends AppCompatActivity {

    private List<FoodItem> foodItemList = new ArrayList<>();
    private FoodListAdapter adapter;
    private static final String PREFS_NAME = "profiles_preferences";
    private static final String ALL_PROFILES_PREFS = "all_profiles";
    private static final String ACTIVE_PROFILE = "active_profile";
    private String activeProfileId;
    String[] dietaryPreferencesKeys = new String[] {
            "_avoid_beef",
            "_avoid_pork",
            "_avoid_shellfish",
            "_avoid_fish",
            "_avoid_peanut",
            "_avoid_chicken",
            "_avoid_lamb",
            "_avoid_egg",
            "_avoid_flour",
            "_avoid_dairy",
            "_avoid_tree_nuts",
            "_avoid_soy",
            "_avoid_sesame",
            "_avoid_wheat",
            "_avoid_corn",
            "_avoid_gluten",
            "_avoid_mustard",
            "_avoid_celery",
            "_avoid_sulfites",
            "_avoid_lupin"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_suggestion);

        ImageView backIcon = findViewById(R.id.back_icon);
        backIcon.setOnClickListener(v -> finish());

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        populateFoodItems();

        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        activeProfileId = preferences.getString("active_profile", "");
        Map<String, Boolean> userPreferences = new HashMap<>();
        for (String key : dietaryPreferencesKeys) {
            userPreferences.put(key, preferences.getBoolean(activeProfileId + key, false));
        }
        Log.d("---","str: " +userPreferences);
        Log.d("---","str: " +foodItemList);
        foodItemList = foodItemList.stream()
                .filter(item -> shouldIncludeItem(item, userPreferences))
                .collect(Collectors.toList());

        Log.d("---","str: " +foodItemList);
        adapter = new FoodListAdapter(foodItemList, this::showDeleteConfirmationDialog);
        recyclerView.setAdapter(adapter);
    }
    private boolean shouldIncludeItem(FoodItem item, Map<String, Boolean> userPreferences) {
        if (userPreferences.get("_avoid_beef") && item.getdietaryIncluded().toLowerCase().contains("beef")) {
            return false;
        }
        if (userPreferences.get("_avoid_pork") && item.getdietaryIncluded().toLowerCase().contains("pork")) {
            return false;
        }
        if (userPreferences.get("_avoid_shellfish") && item.getdietaryIncluded().toLowerCase().contains("shellfish")) {
            return false;
        }
        if (userPreferences.get("_avoid_fish") && item.getdietaryIncluded().toLowerCase().contains("fish")) {
            return false;
        }
        if (userPreferences.get("_avoid_soy") && item.getdietaryIncluded().toLowerCase().contains("soy")) {
            return false;
        }
        if (userPreferences.get("_avoid_sesame") && item.getdietaryIncluded().toLowerCase().contains("sesame")) {
            return false;
        }
        if (userPreferences.get("_avoid_gluten") && item.getdietaryIncluded().toLowerCase().contains("gluten")) {
            return false;
        }
        if (userPreferences.get("_avoid_tree_nuts") && item.getdietaryIncluded().toLowerCase().contains("tree_nuts")) {
            return false;
        }
        if (userPreferences.get("_avoid_wheat") && item.getdietaryIncluded().toLowerCase().contains("wheat")) {
            return false;
        }
        if (userPreferences.get("_avoid_egg") && item.getdietaryIncluded().toLowerCase().contains("egg")) {
            return false;
        }
        return true;
    }
    private void populateFoodItems() {
        foodItemList.add(new FoodItem(
                getString(R.string.bibimbap_name),
                R.drawable.bibimbap,
                getString(R.string.bibimbap_description),
                getString(R.string.bibimbap_tags),
                getString(R.string.bibimbap_long_description),
                getString(R.string.bibimbap_ingredients),
                getString(R.string.bibimbap_dietary_info),
                getString(R.string.bibimbap_restrictions),
                "sesame, soy, egg"

        ));
        foodItemList.add(new FoodItem(
                getString(R.string.japchae_name),
                R.drawable.japchae,
                getString(R.string.japchae_description),
                getString(R.string.japchae_tags),
                getString(R.string.japchae_long_description),
                getString(R.string.japchae_ingredients),
                getString(R.string.japchae_dietary_info),
                getString(R.string.japchae_restrictions),
                "sesame, soy, egg"
        ));
        foodItemList.add(new FoodItem(
                getString(R.string.kongguksu_name),
                R.drawable.kongguksu,
                getString(R.string.kongguksu_description),
                getString(R.string.kongguksu_tags),
                getString(R.string.kongguksu_long_description),
                getString(R.string.kongguksu_ingredients),
                getString(R.string.kongguksu_dietary_info),
                getString(R.string.kongguksu_restrictions),
                "soy, wheat, sesame"
        ));
        foodItemList.add(new FoodItem(
                getString(R.string.doenjang_jjigae_name),
                R.drawable.doenjang_jjigae,
                getString(R.string.doenjang_jjigae_description),
                getString(R.string.doenjang_jjigae_tags),
                getString(R.string.doenjang_jjigae_long_description),
                getString(R.string.doenjang_jjigae_ingredients),
                getString(R.string.doenjang_jjigae_dietary_info),
                getString(R.string.doenjang_jjigae_restrictions),
                "soy"

        ));
        foodItemList.add(new FoodItem(
                getString(R.string.sundubu_jjigae_name),
                R.drawable.sundubu_jjigae,
                getString(R.string.sundubu_jjigae_description),
                getString(R.string.sundubu_jjigae_tags),
                getString(R.string.sundubu_jjigae_long_description),
                getString(R.string.sundubu_jjigae_ingredients),
                getString(R.string.sundubu_jjigae_dietary_info),
                getString(R.string.sundubu_jjigae_restrictions),
                "soy, egg"
        ));
        foodItemList.add(new FoodItem(
                getString(R.string.kimchi_bokkeumbap_name),
                R.drawable.kimchi_bokkeumbap,
                getString(R.string.kimchi_bokkeumbap_description),
                getString(R.string.kimchi_bokkeumbap_tags),
                getString(R.string.kimchi_bokkeumbap_long_description),
                getString(R.string.kimchi_bokkeumbap_ingredients),
                getString(R.string.kimchi_bokkeumbap_dietary_info),
                getString(R.string.kimchi_bokkeumbap_restrictions),
                "sesame, soy, egg, pork"
        ));
        foodItemList.add(new FoodItem(
                getString(R.string.yachae_kimbap_name),
                R.drawable.yachae_kimbap,
                getString(R.string.yachae_kimbap_description),
                getString(R.string.yachae_kimbap_tags),
                getString(R.string.yachae_kimbap_long_description),
                getString(R.string.yachae_kimbap_ingredients),
                getString(R.string.yachae_kimbap_dietary_info),
                getString(R.string.yachae_kimbap_restrictions),
                "sesame, soy, egg"
        ));
        foodItemList.add(new FoodItem(
                getString(R.string.tofu_jorim_name),
                R.drawable.tofu_jorim,
                getString(R.string.tofu_jorim_description),
                getString(R.string.tofu_jorim_tags),
                getString(R.string.tofu_jorim_long_description),
                getString(R.string.tofu_jorim_ingredients),
                getString(R.string.tofu_jorim_dietary_info),
                getString(R.string.tofu_jorim_restrictions),
                "sesame, soy"
        ));
        foodItemList.add(new FoodItem(
                getString(R.string.hobakjuk_name),
                R.drawable.hobakjuk,
                getString(R.string.hobakjuk_description),
                getString(R.string.hobakjuk_tags),
                getString(R.string.hobakjuk_long_description),
                getString(R.string.hobakjuk_ingredients),
                getString(R.string.hobakjuk_dietary_info),
                getString(R.string.hobakjuk_restrictions),
                "tree_nuts"
        ));
        foodItemList.add(new FoodItem(
                getString(R.string.buchimgae_name),
                R.drawable.buchimgae,
                getString(R.string.buchimgae_description),
                getString(R.string.buchimgae_tags),
                getString(R.string.buchimgae_long_description),
                getString(R.string.buchimgae_ingredients),
                getString(R.string.buchimgae_dietary_info),
                getString(R.string.buchimgae_restrictions),
                "wheat, gluten, egg"
        ));
        foodItemList.add(new FoodItem(
                getString(R.string.dubu_kimchi_name),
                R.drawable.dubu_kimchi,
                getString(R.string.dubu_kimchi_description),
                getString(R.string.dubu_kimchi_tags),
                getString(R.string.dubu_kimchi_long_description),
                getString(R.string.dubu_kimchi_ingredients),
                getString(R.string.dubu_kimchi_dietary_info),
                getString(R.string.dubu_kimchi_restrictions),
                "soy, sesame"

        ));
        foodItemList.add(new FoodItem(
                getString(R.string.nokdujeon_name),
                R.drawable.nokdujeon,
                getString(R.string.nokdujeon_description),
                getString(R.string.nokdujeon_tags),
                getString(R.string.nokdujeon_long_description),
                getString(R.string.nokdujeon_ingredients),
                getString(R.string.nokdujeon_dietary_info),
                getString(R.string.nokdujeon_restrictions),
                "sesame, soy, egg"
        ));
        foodItemList.add(new FoodItem(
                getString(R.string.tteokbokki_name),
                R.drawable.tteokbokki,
                getString(R.string.tteokbokki_description),
                getString(R.string.tteokbokki_tags),
                getString(R.string.tteokbokki_long_description),
                getString(R.string.tteokbokki_ingredients),
                getString(R.string.tteokbokki_dietary_info),
                getString(R.string.tteokbokki_restrictions),
                "fish, wheat, gluten, soy"
        ));
        foodItemList.add(new FoodItem(
                getString(R.string.ssambap_name),
                R.drawable.ssambap,
                getString(R.string.ssambap_description),
                getString(R.string.ssambap_tags),
                getString(R.string.ssambap_long_description),
                getString(R.string.ssambap_ingredients),
                getString(R.string.ssambap_dietary_info),
                getString(R.string.ssambap_restrictions),
                "soy, sesame"
        ));
        foodItemList.add(new FoodItem(
                getString(R.string.kimchi_jjigae_name),
                R.drawable.kimchi_jjigae,
                getString(R.string.kimchi_jjigae_description),
                getString(R.string.kimchi_jjigae_tags),
                getString(R.string.kimchi_jjigae_long_description),
                getString(R.string.kimchi_jjigae_ingredients),
                getString(R.string.kimchi_jjigae_dietary_info),
                getString(R.string.kimchi_jjigae_restrictions),
                "soy, pork"
        ));
        foodItemList.add(new FoodItem(
                getString(R.string.gaji_bokkeum_name),
                R.drawable.gaji_bokkeum,
                getString(R.string.gaji_bokkeum_description),
                getString(R.string.gaji_bokkeum_tags),
                getString(R.string.gaji_bokkeum_long_description),
                getString(R.string.gaji_bokkeum_ingredients),
                getString(R.string.gaji_bokkeum_dietary_info),
                getString(R.string.gaji_bokkeum_restrictions),
                "soy, sesame"
        ));
        foodItemList.add(new FoodItem(
                getString(R.string.yachae_bibim_guksu_name),
                R.drawable.yachae_bibim_guksu,
                getString(R.string.yachae_bibim_guksu_description),
                getString(R.string.yachae_bibim_guksu_tags),
                getString(R.string.yachae_bibim_guksu_long_description),
                getString(R.string.yachae_bibim_guksu_ingredients),
                getString(R.string.yachae_bibim_guksu_dietary_info),
                getString(R.string.yachae_bibim_guksu_restrictions),
                "soy, wheat, sesame"
        ));
        foodItemList.add(new FoodItem(
                getString(R.string.kongnamul_gukbap_name),
                R.drawable.kongnamul_gukbap,
                getString(R.string.kongnamul_gukbap_description),
                getString(R.string.kongnamul_gukbap_tags),
                getString(R.string.kongnamul_gukbap_long_description),
                getString(R.string.kongnamul_gukbap_ingredients),
                getString(R.string.kongnamul_gukbap_dietary_info),
                getString(R.string.kongnamul_gukbap_restrictions),
                "soy, sesame"
        ));
        foodItemList.add(new FoodItem(
                getString(R.string.sigeumchi_doenjang_guk_name),
                R.drawable.sigeumchi_doenjang_guk,
                getString(R.string.sigeumchi_doenjang_guk_description),
                getString(R.string.sigeumchi_doenjang_guk_tags),
                getString(R.string.sigeumchi_doenjang_guk_long_description),
                getString(R.string.sigeumchi_doenjang_guk_ingredients),
                getString(R.string.sigeumchi_doenjang_guk_dietary_info),
                getString(R.string.sigeumchi_doenjang_guk_restrictions),
                "soy, sesame"
        ));
        foodItemList.add(new FoodItem(
                getString(R.string.chwinamul_bap_name),
                R.drawable.chwinamul_bap,
                getString(R.string.chwinamul_bap_description),
                getString(R.string.chwinamul_bap_tags),
                getString(R.string.chwinamul_bap_long_description),
                getString(R.string.chwinamul_bap_ingredients),
                getString(R.string.chwinamul_bap_dietary_info),
                getString(R.string.chwinamul_bap_restrictions),
                "soy, sesame"
        ));
    }

    private void showDeleteConfirmationDialog(FoodItem foodItem) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Food")
                .setMessage("Are you sure you want to delete " + foodItem.getName() + "?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    foodItemList.remove(foodItem);
                    adapter.notifyDataSetChanged();
                })
                .setNegativeButton("No", null)
                .show();
    }
}