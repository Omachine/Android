package com.exercicios.spacefighter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect

class Player(context: Context, width: Int, height: Int) {

    var x = 0
    var y = 0
    var targetY = 0
    var speed = 0
    var maxX = 0
    var maxY = 0
    var minX = 0
    var minY = 0

    var bitmap: Bitmap
    var boosting = false

    private val GRAVITY = -10
    private val MAX_SPEED = 45
    private val MIN_SPEED = 7

    var detectCollision: Rect

    val shots = mutableListOf<Shot>()
    private val maxShots = 5
    private var remainingShots = 5

    init {
        bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.player)

        minX = 0
        maxX = width

        maxY = height - bitmap.height
        minY = 0

        x = 75
        y = 50
        targetY = y

        speed = 2

        detectCollision = Rect(x, y, bitmap.width, bitmap.height)
    }

    fun update() {
        if (boosting) speed += 6
        else speed -= 3
        if (speed > MAX_SPEED) speed = MAX_SPEED
        if (speed < MIN_SPEED) speed = MIN_SPEED

        if (y < targetY) {
            y += speed
            if (y > targetY) y = targetY
        } else if (y > targetY) {
            y -= speed
            if (y < targetY) y = targetY
        }

        if (y < minY) y = minY
        if (y > maxY) y = maxY

        detectCollision.left = x
        detectCollision.top = y
        detectCollision.right = x + bitmap.width
        detectCollision.bottom = y + bitmap.height

        val iterator = shots.iterator()
        while (iterator.hasNext()) {
            val shot = iterator.next()
            shot.update()
            if (shot.x > maxX) {
                iterator.remove()
                remainingShots++
            }
        }
    }

    fun updateTargetY(newY: Int) {
        targetY = newY.coerceIn(minY, maxY)
    }

    fun shoot() {
        if (remainingShots > 0) {
            shots.add(Shot(x + bitmap.width, y + bitmap.height / 2))
            remainingShots--
        }
    }
}