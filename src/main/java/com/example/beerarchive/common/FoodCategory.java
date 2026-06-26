package com.example.beerarchive.common;

public enum FoodCategory {
    치킨("🍗"),
    피자("🍕"),
    육류("🥩"),
    해산물("🦐"),
    스낵("🧀"),
    튀김("🍤"),
    디저트("🍰");

    private final String emoji;

    FoodCategory(String emoji) { this.emoji = emoji; }
    public String getEmoji() { return emoji; }
}