package com.exercicios.spacefighter

import android.graphics.Paint
import android.graphics.Rect

class Shot(private val startX: Int, private val startY: Int) {
    var x = startX
    var y = startY
    private val speed = 20
    val width = 10
    val height = 20
    val paint = Paint().apply { color = android.graphics.Color.RED }
    val detectCollision: Rect
        get() = Rect(x, y, x + width, y + height)

    fun update() {
        x += speed
    }
}