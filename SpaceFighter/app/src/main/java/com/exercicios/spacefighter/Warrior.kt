package com.exercicios.spacefighter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect

class Warrior(context: Context, width: Int, height: Int) {

    var x = 100
    var y = 100
    private var counter = 0
    private val spriteBitmap: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.image_360)
        .let { Bitmap.createScaledBitmap(it, 800, 200, true) }
    val bitmap: Bitmap
        get() {
            counter = (counter + 1) % 40
            return getBitmapFrame(counter / 10)
        }
    val detectCollision: Rect
        get() = Rect(x, y, x + bitmap.width, y + bitmap.height)

    private val maxX = width
    private val maxY = height - bitmap.height

    private fun getBitmapFrame(frame: Int): Bitmap {
        val frameWidth = spriteBitmap.width / 4
        return Bitmap.createBitmap(spriteBitmap, frame.coerceAtMost(3) * frameWidth, 0, frameWidth, spriteBitmap.height)
    }

    fun update() {
        y = y.coerceIn(0, maxY)
    }
}