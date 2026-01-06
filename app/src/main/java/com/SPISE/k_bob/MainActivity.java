// MainActivity.java
package com.SPISE.k_bob;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import android.app.ProgressDialog;

public class MainActivity extends AppCompatActivity {
    private static final int PICK_IMAGE = 100;
    private static final int REQUEST_CODE_ADD_PROFILE = 101;
    private static final String PREFS_NAME = "profiles_preferences";
    private static final String DARK_MODE_PREF = "dark_mode";
    private static final String ACTIVE_PROFILE = "active_profile";
    private String currentProfileId;
    private String currentProfileName;
    private Set<String> avoidIngredients;

    private Set<String> avoidIngredients_english;
    String secretKey = "TG11RFpzY3dHaWFtbFFZVkdhUHRpWElxaU9PSE9Mb0k=";
    private Map<String, String> ingredientTranslationMap = new HashMap<>();

    private void initializeIngredientTranslationMap() {
        ingredientTranslationMap.put("대두", "soy");
        ingredientTranslationMap.put("우유", "milk");
        ingredientTranslationMap.put("쇠고기", "beef");
        ingredientTranslationMap.put("소고기", "beef");
        ingredientTranslationMap.put("비프", "beef");
        ingredientTranslationMap.put("돼지고기", "pork");
        ingredientTranslationMap.put("돈육", "pork");
        ingredientTranslationMap.put("포크", "pork");
        ingredientTranslationMap.put("조개류", "shellfish");
        ingredientTranslationMap.put("갑각류", "shellfish");
        ingredientTranslationMap.put("새우", "shrimp");
        ingredientTranslationMap.put("게", "crab");
        ingredientTranslationMap.put("바닷가재", "lobster");
        ingredientTranslationMap.put("어류", "fish");
        ingredientTranslationMap.put("어패류", "fish");
        ingredientTranslationMap.put("물고기", "fish");
        ingredientTranslationMap.put("생선", "fish");
        ingredientTranslationMap.put("땅콩", "peanut");
        ingredientTranslationMap.put("피넛", "peanut");
        ingredientTranslationMap.put("땅콩버터", "peanut butter");
        ingredientTranslationMap.put("닭고기", "chicken");
        ingredientTranslationMap.put("치킨", "chicken");
        ingredientTranslationMap.put("계육", "chicken");
        ingredientTranslationMap.put("양고기", "lamb");
        ingredientTranslationMap.put("램", "lamb");
        ingredientTranslationMap.put("램고기", "lamb");
        ingredientTranslationMap.put("계란", "egg");
        ingredientTranslationMap.put("달걀", "egg");
        ingredientTranslationMap.put("알류", "egg");
        ingredientTranslationMap.put("에그", "egg");
        ingredientTranslationMap.put("유제품", "dairy");
        ingredientTranslationMap.put("치즈", "cheese");
        ingredientTranslationMap.put("버터", "butter");
        ingredientTranslationMap.put("요구르트", "yogurt");
        ingredientTranslationMap.put("밀가루", "flour");
        ingredientTranslationMap.put("소맥", "wheat");
        ingredientTranslationMap.put("견과류", "tree nuts");
        ingredientTranslationMap.put("아몬드", "almond");
        ingredientTranslationMap.put("캐슈넛", "cashew nut");
        ingredientTranslationMap.put("헤이즐넛", "hazelnut");
        ingredientTranslationMap.put("피칸", "pecan");
        ingredientTranslationMap.put("콩", "soy");
        ingredientTranslationMap.put("소야", "soy");
        ingredientTranslationMap.put("두유", "soy milk");
        ingredientTranslationMap.put("깨", "sesame");
        ingredientTranslationMap.put("세사미", "sesame");
        ingredientTranslationMap.put("옥수수", "corn");
        ingredientTranslationMap.put("콘", "corn");
        ingredientTranslationMap.put("옥수수가루", "corn flour");
        ingredientTranslationMap.put("밀글루텐", "wheat gluten");
        ingredientTranslationMap.put("글루텐단백질", "gluten protein");
        ingredientTranslationMap.put("머스타드", "mustard");
        ingredientTranslationMap.put("셀러리", "celery");
        ingredientTranslationMap.put("아황산염", "sulfites");
        ingredientTranslationMap.put("이산화황", "sulfur dioxide");
        ingredientTranslationMap.put("루핀콩", "lupin beans");
        ingredientTranslationMap.put("루핀씨", "lupin seeds");
    }
    private void loadProfilePreferences() {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        currentProfileId = preferences.getString(ACTIVE_PROFILE, "");
        currentProfileName = preferences.getString(currentProfileId + "_name", "No Profile Selected");
        boolean isDarkMode = preferences.getBoolean(DARK_MODE_PREF, false);
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
    private void loadUserPreferences() {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        avoidIngredients = new HashSet<>();
        avoidIngredients_english = new HashSet<>();

        if (preferences.getBoolean(currentProfileId + "_avoid_beef", false)) {
            avoidIngredients.add("쇠고기");
            avoidIngredients.add("소고기");
            avoidIngredients.add("비프");
            avoidIngredients_english.add("beef");
        }
        if (preferences.getBoolean(currentProfileId + "_avoid_pork", false)) {
            avoidIngredients.add("돼지고기");
            avoidIngredients.add("돈육");
            avoidIngredients.add("포크");
            avoidIngredients_english.add("pork");
        }
        if (preferences.getBoolean(currentProfileId + "_avoid_shellfish", false)) {
            avoidIngredients.add("조개류");
            avoidIngredients.add("갑각류");
            avoidIngredients.add("새우");
            avoidIngredients.add("게");
            avoidIngredients.add("바닷가재");
            avoidIngredients_english.add("shellfish");
        }
        if (preferences.getBoolean(currentProfileId + "_avoid_fish", false)) {
            avoidIngredients.add("어류");
            avoidIngredients.add("어패류");
            avoidIngredients.add("물고기");
            avoidIngredients.add("생선");
            avoidIngredients_english.add("fish");
        }
        if (preferences.getBoolean(currentProfileId + "_avoid_peanut", false)) {
            avoidIngredients.add("땅콩");
            avoidIngredients.add("피넛");
            avoidIngredients.add("땅콩버터");
            avoidIngredients_english.add("peanut");
        }
        if (preferences.getBoolean(currentProfileId + "_avoid_chicken", false)) {
            avoidIngredients.add("닭고기");
            avoidIngredients.add("치킨");
            avoidIngredients.add("계육");
            avoidIngredients_english.add("chicken");
        }
        if (preferences.getBoolean(currentProfileId + "_avoid_lamb", false)) {
            avoidIngredients.add("양고기");
            avoidIngredients.add("램");
            avoidIngredients.add("램고기");
            avoidIngredients_english.add("lamb");
        }
        if (preferences.getBoolean(currentProfileId + "_avoid_egg", false)) {
            avoidIngredients.add("계란");
            avoidIngredients.add("달걀");
            avoidIngredients.add("에그");
            avoidIngredients.add("알류");
            avoidIngredients_english.add("egg");
        }
        if (preferences.getBoolean(currentProfileId + "_avoid_dairy", false)) {
            avoidIngredients.add("우유");
            avoidIngredients.add("유제품");
            avoidIngredients.add("치즈");
            avoidIngredients.add("버터");
            avoidIngredients.add("요구르트");
            avoidIngredients_english.add("dairy");
        }
        if (preferences.getBoolean(currentProfileId + "_avoid_flour", false)) {
            avoidIngredients.add("밀가루");
            avoidIngredients.add("밀");
            avoidIngredients.add("소맥");
            avoidIngredients_english.add("flour");
        }

        if (preferences.getBoolean(currentProfileId + "_avoid_tree_nuts", false)) {
            avoidIngredients.add("호두");
            avoidIngredients.add("견과류");
            avoidIngredients.add("아몬드");
            avoidIngredients.add("캐슈넛");
            avoidIngredients.add("헤이즐넛");
            avoidIngredients.add("피칸");
            avoidIngredients_english.add("tree_nuts");
        }
        if (preferences.getBoolean(currentProfileId + "_avoid_soy", false)) {
            avoidIngredients.add("대두");
            avoidIngredients.add("콩");
            avoidIngredients.add("소야");
            avoidIngredients.add("두유");
            avoidIngredients_english.add("soy");
        }
        if (preferences.getBoolean(currentProfileId + "_avoid_sesame", false)) {
            avoidIngredients.add("참깨");
            avoidIngredients.add("깨");
            avoidIngredients.add("세사미");
            avoidIngredients_english.add("sesame");
        }
        if (preferences.getBoolean(currentProfileId + "_avoid_wheat", false)) {
            avoidIngredients.add("밀");
            avoidIngredients.add("소맥");
            avoidIngredients.add("밀가루");
            avoidIngredients_english.add("wheat");
        }
        if (preferences.getBoolean(currentProfileId + "_avoid_corn", false)) {
            avoidIngredients.add("옥수수");
            avoidIngredients.add("콘");
            avoidIngredients.add("옥수수가루");
            avoidIngredients_english.add("corn");
        }
        if (preferences.getBoolean(currentProfileId + "_avoid_gluten", false)) {
            avoidIngredients.add("글루텐");
            avoidIngredients_english.add("gluten");
        }
        if (preferences.getBoolean(currentProfileId + "_avoid_mustard", false)) {
            avoidIngredients.add("겨자");
            avoidIngredients.add("머스타드");

        }
        if (preferences.getBoolean(currentProfileId + "_avoid_celery", false)) avoidIngredients.add("샐러리");
        if (preferences.getBoolean(currentProfileId + "_avoid_sulfites", false)) {
            avoidIngredients.add("황");
            avoidIngredients.add("아황산염");
            avoidIngredients.add("이산화황");
        }
        if (preferences.getBoolean(currentProfileId + "_avoid_lupin", false)) avoidIngredients.add("루핀");
    }
    private void setupUi() {
        setContentView(R.layout.activity_main);
        if (getSupportActionBar() != null) getSupportActionBar().hide();
        LocaleHelper.loadLocale(this);
        setContentView(R.layout.activity_main);
        // Initialize other UI components here
    }

    private void setupListeners() {
        // Set up the click listener for the Upload Picture icon
        findViewById(R.id.uploadPicture1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
        findViewById(R.id.setting1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(
                                R.anim.slide_in_right,  // Enter animation
                                R.anim.slide_out_right,  // Exit animation
                                R.anim.slide_in_right,  // Pop enter animation (optional)
                                R.anim.slide_out_right   // Pop exit animation (optional)
                        )
                        .replace(R.id.fragment_container, new SettingsFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        // Set up the click listener for the Food Suggestion icon
        findViewById(R.id.foodSuggestion1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FoodSuggestionActivity.class));
            }
        });

        // Set up the click listener for the Order Translation icon
        findViewById(R.id.orderTranslation1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, OrderTranslationActivity.class);
                intent.putExtra("activeProfileId", currentProfileId);
                startActivity(intent);
            }
        });
    }

    private void openProfileManagement() {
        Intent intent = new Intent(this, PersonalSettingsActivity.class);
        intent.putExtra("add","True");
        startActivityForResult(intent, REQUEST_CODE_ADD_PROFILE);  // Define REQUEST_CODE_ADD_PROFILE as a constant
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("%%%%","wowowowow"+REQUEST_CODE_ADD_PROFILE+"||"+resultCode+"||"+RESULT_OK);
        if (requestCode == REQUEST_CODE_ADD_PROFILE && resultCode == RESULT_OK) {
            // Reload profile preferences if a new profile has been added
            loadProfilePreferences();
            setupUi();
            setupListeners();
        }
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            File imageFile = uriToFile(imageUri);
            setTheme(R.style.AppTheme); // Switch to the main theme immediately after the splash screen
            ProgressDialog progressDialog = ProgressDialog.show(this, "Processing", "OCR in progress...", true);
            if (imageFile != null && imageFile.exists()) {
                performOcrRequest(imageFile, new OcrResultCallback() {
                    @Override
                    public void onSuccess(String result) {
                        //Toast.makeText(MainActivity.this, "Success: " + result, Toast.LENGTH_LONG).show();
                        //Log.d("OCR_SUCCESS", "OCR Result~: " + result);
                        List<String> the_result = determineLegality(result);
                        progressDialog.dismiss();
                        if (the_result.isEmpty())
                            openEdibleActivity();
                        else if(the_result.toString().equals("[False1]"))
                            openNotDetectedActivity();
                        else
                            openNotEdibleActivity(the_result);
                    }

                    @Override
                    public void onError(String error) {
                        Toast.makeText(MainActivity.this, "Error: " + error, Toast.LENGTH_LONG).show();
                    }
                });

            } else {
                Toast.makeText(this, "Error accessing image file.", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private List<String> determineLegality(String result) {
        // Extract inferred text from OCR result
        List<String> inferTexts = OcrResultParser.extractInferText(result);
        StringBuilder the_result = new StringBuilder();
        for (String text : inferTexts) {
            the_result.append(text);
        }
        Log.d("OcrResultParser", "Processing text: " + the_result.toString());

        List<String> contained_illegal_ingredient = new ArrayList<>();

        // Convert the StringBuilder to a String
        String fullText = the_result.toString();

        // Check if "함유" is present
        if (fullText.contains("함유")) {
            int index = fullText.indexOf("함유");

            // Extract the part of the text before "함유"
            String beforeHamyu = fullText.substring(0, index).trim();

            // Find the last occurrence of any of the specified tokens
            int lastTokenIndex = -1;
            String[] tokens = { ".", "알레르기", "원재료명", "외국산", "소금", "식품", "중국산", "포도당", "조절제", "추출물", "풍미", "중국", "비타민"};
            for (String token : tokens) {
                int tokenIndex = beforeHamyu.lastIndexOf(token);
                if (tokenIndex > lastTokenIndex) {
                    lastTokenIndex = tokenIndex;
                }
            }

            // If a token is found, extract the part after the last token
            String relevantText;
            if (lastTokenIndex != -1) {
                relevantText = beforeHamyu.substring(lastTokenIndex + 1).trim();
            } else {
                relevantText = beforeHamyu;
            }
            Log.d("OcrResultParser", "Processed text: " + relevantText);
            // Check each ingredient in the relevant part
            for (String ingredient : avoidIngredients) {
                if (relevantText.contains(ingredient)) {
                    contained_illegal_ingredient.add(ingredient);
                }
            }
        }
        else{
            Log.d("wwwwww","flflfll");
            contained_illegal_ingredient.add("False1");
        }

        return contained_illegal_ingredient;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadProfilePreferences();
        loadUserPreferences();
        initializeIngredientTranslationMap();
        if (currentProfileId.isEmpty()) {
            openProfileManagement();
        }
        setupUi();
        setupListeners();
    }


    @Override
    public void onResume() {
        super.onResume();
        loadProfilePreferences();
        loadUserPreferences();
    }

    private File uriToFile(Uri imageUri) {
        File tempFile = null;
        try {
            // Create a temporary file
            tempFile = File.createTempFile("upload", ".jpg", getExternalCacheDir());
            tempFile.deleteOnExit();
            try (InputStream is = getContentResolver().openInputStream(imageUri);
                 OutputStream os = new FileOutputStream(tempFile)) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = is.read(buffer)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }
            }
        } catch (IOException e) {
            Log.e("TAG", "Error writing temp file", e);
        }
        return tempFile;
    }

    // Open the gallery to select a photo
    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE);
    }

    // Function to call the OCR API and process the result
    private void performOcrRequest(File imageFile, OcrResultCallback callback) {
        Retrofit retrofit = RetrofitClient.getClient();
        ApiService apiService = retrofit.create(ApiService.class);

        // Create RequestBody for image file
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), imageFile);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", imageFile.getName(), requestBody);

        // Create JSON message
        JSONObject json = new JSONObject();
        try {
            json.put("version", "V2");
            json.put("requestId", UUID.randomUUID().toString());
            json.put("timestamp", System.currentTimeMillis());
            JSONObject image = new JSONObject();
            image.put("format", "jpg");
            image.put("name", "demo");
            JSONArray images = new JSONArray();
            images.put(image);
            json.put("images", images);
        } catch (Exception e) {
            callback.onError(e.getMessage());
            return;
        }

        RequestBody message = RequestBody.create(MediaType.parse("multipart/form-data"), json.toString());

        // Call API
        Call<ResponseBody> call = apiService.performOcrRequest(secretKey, body, message);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String responseString = response.body().string();
                        callback.onSuccess(responseString);
                    } catch (IOException e) {
                        callback.onError(e.getMessage());
                    }
                } else {
                    callback.onError("API call failed: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }
    private void openEdibleActivity() {
        Log.d("%%%%","Edible");
        Intent intent = new Intent(this, EdibleActivity.class);
        startActivity(intent);
    }
    private void openNotDetectedActivity() {
        Log.d("%%%%","NotDetected");
        Intent intent = new Intent(this, NotDetectedActivity.class);
        startActivity(intent);
    }
    private void openNotEdibleActivity(List<String> the_result) {
        Log.d("%%%%","Not Edible");
        Intent intent = new Intent(this, NotEdibleActivity.class);

        StringBuilder translatedResult = new StringBuilder();
        for (String ingredient : the_result) {
            String translated = ingredientTranslationMap.get(ingredient);
            if (translated != null) {
                translatedResult.append(ingredient).append(" (").append(translated).append("), ");
            } else {
                translatedResult.append(ingredient).append(", ");
            }
        }

        // Remove the last comma and space
        if (translatedResult.length() > 2) {
            translatedResult.setLength(translatedResult.length() - 2);
        }

        intent.putExtra("stringA", " " + translatedResult.toString());
        startActivity(intent);

    }
}
