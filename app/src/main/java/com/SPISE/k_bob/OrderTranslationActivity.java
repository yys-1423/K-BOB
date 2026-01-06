package com.SPISE.k_bob;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.widget.TextView;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class OrderTranslationActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "profiles_preferences";
    private static final String ACTIVE_PROFILE = "active_profile";
    private String currentProfileId;
    private String currentProfileName;
    private Set<String> avoidIngredients;
    private Set<String> unrestrictedIngredients;
    private Map<String, String> ingredientTranslationMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_translation);

        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        currentProfileId = preferences.getString(ACTIVE_PROFILE, "");

        if (!currentProfileId.isEmpty()) {
            loadProfilePreferences(preferences);
            generateTranslation();
        }
    }

    private void loadProfilePreferences(SharedPreferences preferences) {
        avoidIngredients = new HashSet<>();
        unrestrictedIngredients = new HashSet<>();
        ingredientTranslationMap = new HashMap<>();
        initializeIngredientTranslationMap();

        if (preferences.getBoolean(currentProfileId + "_avoid_beef", false)) {
            avoidIngredients.add("beef");
        } else {
            unrestrictedIngredients.add("beef");
        }
        if (preferences.getBoolean(currentProfileId + "_avoid_pork", false)) {
            avoidIngredients.add("pork");
        } else {
            unrestrictedIngredients.add("pork");
        }
        if (preferences.getBoolean(currentProfileId + "_avoid_shellfish", false)) {
            avoidIngredients.add("shellfish");
        } else {
            unrestrictedIngredients.add("shellfish");
        }
        if (preferences.getBoolean(currentProfileId + "_avoid_fish", false)) {
            avoidIngredients.add("fish");
        } else {
            unrestrictedIngredients.add("fish");
        }
        if (preferences.getBoolean(currentProfileId + "_avoid_peanut", false)) {
            avoidIngredients.add("peanut");
        } else {
            unrestrictedIngredients.add("peanut");
        }
        if (preferences.getBoolean(currentProfileId + "_avoid_chicken", false)) {
            avoidIngredients.add("chicken");
        } else {
            unrestrictedIngredients.add("chicken");
        }
        if (preferences.getBoolean(currentProfileId + "_avoid_lamb", false)) {
            avoidIngredients.add("lamb");
        } else {
            unrestrictedIngredients.add("lamb");
        }
        if (preferences.getBoolean(currentProfileId + "_avoid_egg", false)) {
            avoidIngredients.add("egg");
        } else {
            unrestrictedIngredients.add("egg");
        }
        if (preferences.getBoolean(currentProfileId + "_avoid_dairy", false)) {
            avoidIngredients.add("dairy");
        } else {
            unrestrictedIngredients.add("dairy");
        }
        if (preferences.getBoolean(currentProfileId + "_avoid_flour", false)) {
            avoidIngredients.add("flour");
        } else {
            unrestrictedIngredients.add("flour");
        }

        if (preferences.getBoolean(currentProfileId + "_avoid_tree_nuts", false)) {
            avoidIngredients.add("tree nuts");
        } else {
            unrestrictedIngredients.add("tree nuts");
        }
        if (preferences.getBoolean(currentProfileId + "_avoid_soy", false)) {
            avoidIngredients.add("soy");
        } else {
            unrestrictedIngredients.add("soy");
        }
        if (preferences.getBoolean(currentProfileId + "_avoid_sesame", false)) {
            avoidIngredients.add("sesame");
        } else {
            unrestrictedIngredients.add("sesame");
        }
        if (preferences.getBoolean(currentProfileId + "_avoid_wheat", false)) {
            avoidIngredients.add("wheat");
        } else {
            unrestrictedIngredients.add("wheat");
        }
        if (preferences.getBoolean(currentProfileId + "_avoid_corn", false)) {
            avoidIngredients.add("corn");
        } else {
            unrestrictedIngredients.add("corn");
        }
        if (preferences.getBoolean(currentProfileId + "_avoid_gluten", false)) {
            avoidIngredients.add("gluten");
        } else {
            unrestrictedIngredients.add("gluten");
        }
        if (preferences.getBoolean(currentProfileId + "_avoid_mustard", false)) {
            avoidIngredients.add("mustard");
        } else {
            unrestrictedIngredients.add("mustard");
        }
        if (preferences.getBoolean(currentProfileId + "_avoid_celery", false)) {
            avoidIngredients.add("celery");
        } else {
            unrestrictedIngredients.add("celery");
        }
        if (preferences.getBoolean(currentProfileId + "_avoid_sulfites", false)) {
            avoidIngredients.add("sulfites");
        } else {
            unrestrictedIngredients.add("sulfites");
        }
        if (preferences.getBoolean(currentProfileId + "_avoid_lupin", false)) {
            avoidIngredients.add("lupin");
        } else {
            unrestrictedIngredients.add("lupin");
        }
    }

    private void initializeIngredientTranslationMap() {
        ingredientTranslationMap.put("beef", "쇠고기");
        ingredientTranslationMap.put("pork", "돼지고기");
        ingredientTranslationMap.put("shellfish", "조개류");
        ingredientTranslationMap.put("fish", "생선");
        ingredientTranslationMap.put("peanut", "땅콩");
        ingredientTranslationMap.put("chicken", "닭고기");
        ingredientTranslationMap.put("lamb", "양고기");
        ingredientTranslationMap.put("egg", "계란");
        ingredientTranslationMap.put("dairy", "유제품");
        ingredientTranslationMap.put("flour", "밀가루");
        ingredientTranslationMap.put("tree nuts", "견과류");
        ingredientTranslationMap.put("soy", "대두");
        ingredientTranslationMap.put("sesame", "참깨");
        ingredientTranslationMap.put("wheat", "밀");
        ingredientTranslationMap.put("corn", "옥수수");
        ingredientTranslationMap.put("gluten", "글루텐");
        ingredientTranslationMap.put("mustard", "겨자");
        ingredientTranslationMap.put("celery", "셀러리");
        ingredientTranslationMap.put("sulfites", "아황산염");
        ingredientTranslationMap.put("lupin", "루핀");
    }

    private void generateTranslation() {
        TextView translationTextView = findViewById(R.id.translation_text_recommendation);
        TextView translationTextKoreanView = findViewById(R.id.translation_text_korean_view);
        TextView translationTextPronunciationView = findViewById(R.id.translation_text_pronunciation_view);

        String veganType = getVeganType();

        StringBuilder avoidIngredientsStr = new StringBuilder();
        StringBuilder avoidIngredientsKorean = new StringBuilder();
        StringBuilder avoidIngredientsPronunciation = new StringBuilder();
        StringBuilder unrestrictedIngredientsStr = new StringBuilder();
        StringBuilder unrestrictedIngredientsKorean = new StringBuilder();
        StringBuilder unrestrictedIngredientsPronunciation = new StringBuilder();

        for (String ingredient : avoidIngredients) {
            avoidIngredientsStr.append(ingredient).append(", ");
            avoidIngredientsKorean.append(ingredientTranslationMap.get(ingredient)).append(", ");
            avoidIngredientsPronunciation.append(getKoreanPronunciation(ingredient)).append(", ");
        }

        for (String ingredient : unrestrictedIngredients) {
            unrestrictedIngredientsStr.append(ingredient).append(", ");
            unrestrictedIngredientsKorean.append(ingredientTranslationMap.get(ingredient)).append(", ");
            unrestrictedIngredientsPronunciation.append(getKoreanPronunciation(ingredient)).append(", ");
        }

        // Remove trailing comma and space
        if (avoidIngredientsStr.length() > 0) {
            avoidIngredientsStr.setLength(avoidIngredientsStr.length() - 2);
        }
        if (unrestrictedIngredientsStr.length() > 0) {
            unrestrictedIngredientsStr.setLength(unrestrictedIngredientsStr.length() - 2);
        }
        if (avoidIngredientsKorean.length() > 0) {
            avoidIngredientsKorean.setLength(avoidIngredientsKorean.length() - 2);
        }
        if (unrestrictedIngredientsKorean.length() > 0) {
            unrestrictedIngredientsKorean.setLength(unrestrictedIngredientsKorean.length() - 2);
        }
        if (avoidIngredientsPronunciation.length() > 0) {
            avoidIngredientsPronunciation.setLength(avoidIngredientsPronunciation.length() - 2);
        }
        if (unrestrictedIngredientsPronunciation.length() > 0) {
            unrestrictedIngredientsPronunciation.setLength(unrestrictedIngredientsPronunciation.length() - 2);
        }

        String translation = String.format(Locale.getDefault(),
                "In English:\n\"Please eliminate %s if they are included in the ingredients.\"\n\n",
                avoidIngredientsStr.toString());

        String koreanTranslation = String.format(Locale.getDefault(),
                "In Korean:\n\"혹시 %s이 재료에 포함되어 있다면 빼주세요\"\n",
                avoidIngredientsKorean.toString());

        String pronunciationTranslation = String.format(Locale.getDefault(),
                "In speaking:\n\"Hoksi %s Jaeryoe Pohamdoeeo Itda-myeon, BBaejuseyo\"",
                avoidIngredientsPronunciation.toString());

        translationTextView.setText(translation);
        translationTextKoreanView.setText(koreanTranslation);
        translationTextPronunciationView.setText(pronunciationTranslation);
    }

    private String getVeganType() {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        if (preferences.getBoolean(currentProfileId + "_is_vegan", false)) {
            return "Vegan";
        } else if (preferences.getBoolean(currentProfileId + "_is_lacto", false)) {
            return "Lacto";
        } else if (preferences.getBoolean(currentProfileId + "_is_ovo", false)) {
            return "Ovo";
        } else if (preferences.getBoolean(currentProfileId + "_is_lacto_ovo", false)) {
            return "Lacto-ovo";
        } else if (preferences.getBoolean(currentProfileId + "_is_pollotarian", false)) {
            return "Pollotarian";
        } else if (preferences.getBoolean(currentProfileId + "_is_pescatarian", false)) {
            return "Pescatarian";
        }
        return "Unknown";
    }

    private String getKoreanPronunciation(String ingredient) {
        switch (ingredient) {
            case "beef":
                return "soegogi";
            case "pork":
                return "dwaejigogi";
            case "shellfish":
                return "jogaelyu";
            case "fish":
                return "saengseon";
            case "peanut":
                return "ttangkong";
            case "chicken":
                return "dalgogi";
            case "lamb":
                return "yanggogi";
            case "egg":
                return "gyeran";
            case "dairy":
                return "yuchaepum";
            case "flour":
                return "milgaru";
            case "tree nuts":
                return "gyeonggwa";
            case "soy":
                return "kong";
            case "sesame":
                return "chamkkae";
            case "wheat":
                return "mil";
            case "corn":
                return "oksusu";
            case "gluten":
                return "geulluten";
            case "mustard":
                return "gyeoja";
            case "celery":
                return "saelleori";
            case "sulfites":
                return "ahwangsan";
            case "lupin":
                return "lupin";
            default:
                return ingredient;
        }
    }
}
