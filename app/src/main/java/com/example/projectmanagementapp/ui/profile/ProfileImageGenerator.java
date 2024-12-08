package com.example.projectmanagementapp.ui.profile;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;

import java.util.Random;

public class ProfileImageGenerator {

    public static BitmapDrawable generateInitialsDrawable(Context context, String name, int size) {
        // Extract the first letter of the name
        String initial = name != null && !name.isEmpty() ? name.substring(0, 1).toUpperCase() : "?";

        // Create a bitmap to draw on
        Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        // Set background color (random)
        Paint backgroundPaint = new Paint();
        Random random = new Random();
        int r = random.nextInt(256); // Random red value (0-255)
        int g = random.nextInt(256); // Random green value (0-255)
        int b = random.nextInt(256); // Random blue value (0-255)
        backgroundPaint.setColor(Color.rgb(r, g, b)); // Random color
        backgroundPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(size / 2f, size / 2f, size / 2f, backgroundPaint);

        // Set text paint
        Paint textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(size / 2f); // Adjust text size
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        textPaint.setTextAlign(Paint.Align.CENTER);

        // Draw the initial
        Rect textBounds = new Rect();
        textPaint.getTextBounds(initial, 0, initial.length(), textBounds);
        canvas.drawText(initial, size / 2f, size / 2f + textBounds.height() / 2f, textPaint);

        return new BitmapDrawable(context.getResources(), bitmap);
    }
}
