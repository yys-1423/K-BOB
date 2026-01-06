package com.SPISE.k_bob;

public class FoodItem {
    private String name;
    private int imageResId;
    private String description;
    private String keyIngredients;
    private String detailedDescription;
    private String majorIngredients;
    private String veganType;
    private String dietaryRestrictions;
    private String dietaryIncluded;

    public FoodItem(String name, int imageResId, String description, String keyIngredients, String detailedDescription,
                    String majorIngredients, String veganType, String dietaryRestrictions, String dietaryIncluded) {
        this.name = name;
        this.imageResId = imageResId;
        this.description = description;
        this.keyIngredients = keyIngredients;
        this.detailedDescription = detailedDescription;
        this.majorIngredients = majorIngredients;
        this.veganType = veganType;
        this.dietaryRestrictions = dietaryRestrictions;
        this.dietaryIncluded = dietaryIncluded;
    }

    public String getName() { return name; }
    public int getImageResId() { return imageResId; }
    public String getDescription() { return description; }
    public String getKeyIngredients() { return keyIngredients; }
    public String getDetailedDescription() { return detailedDescription; }
    public String getMajorIngredients() { return majorIngredients; }
    public String getVeganType() { return veganType; }
    public String getDietaryRestrictions() { return dietaryRestrictions; }
    public String getdietaryIncluded() { return dietaryIncluded; }
}