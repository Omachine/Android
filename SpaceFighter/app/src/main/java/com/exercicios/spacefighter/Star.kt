package com.exercicios.spacefighter

import kotlin.random.Random

class Star(width: Int, height: Int) {

    var x = Random.nextInt(width)
    var y = Random.nextInt(height)
    var speed = Random.nextInt(15) + 1
    val maxX = width
    val maxY = height
    var collected = false

    fun update(playerSpeed: Int) {
        if (!collected) {
            x -= playerSpeed + speed
            if (x < 0) {
                x = maxX
                y = Random.nextInt(maxY)
                speed = Random.nextInt(15) + 1
            }
        }
    }

    val starWidth: Int
        get() = Random.nextInt(10) + 1
}