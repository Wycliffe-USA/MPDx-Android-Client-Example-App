# Keep the fields and methods BadgeDrawableUtils utilizes through reflection
-keepclassmembers class com.google.android.material.badge.BadgeDrawable {
    android.graphics.Rect badgeBounds;
    float badgeCenterX;
    float badgeCenterY;
    float cornerRadius;
    float halfBadgeWidth;
    float halfBadgeHeight;
    com.google.android.material.shape.MaterialShapeDrawable shapeDrawable;

    void calculateCenterAndBounds(...);
}
