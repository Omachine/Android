package com.exercicios.spacefighter

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView

class GameView : SurfaceView, Runnable {

    private var playing = false
    private var gameThread: Thread? = null
    private lateinit var surfaceHolder: SurfaceHolder
    private lateinit var canvas: Canvas
    private val paint = Paint()
    private val stars = ArrayList<Star>()
    private val enemies = ArrayList<Enemy>()
    private lateinit var player: Player
    private lateinit var boom: Boom
    private lateinit var warrior: Warrior
    private var lives = 3
    private var collectedStars = 0
    var onGameOver: (Int) -> Unit = { _ -> }

    private lateinit var highScoreManager: HighScoreManager

    private fun init(context: Context, width: Int, height: Int) {
        surfaceHolder = holder
        highScoreManager = HighScoreManager(context)
        repeat(100) { stars.add(Star(width, height)) }
        repeat(3) { enemies.add(Enemy(context, width, height)) }
        player = Player(context, width, height)
        warrior = Warrior(context, width, height)
        boom = Boom(context, width, height)
    }

    constructor(context: Context?, width: Int, height: Int) : super(context) {
        init(context!!, width, height)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(context!!, 0, 0)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context!!, 0, 0)
    }

    fun resume() {
        playing = true
        gameThread = Thread(this)
        gameThread?.start()
    }

    override fun run() {
        while (playing) {
            update()
            draw()
            control()
        }
    }

    private fun update() {
        boom.x = -300
        boom.y = -300
        stars.forEach { star ->
            star.update(player.speed)
            if (!star.collected && Rect.intersects(player.detectCollision, Rect(star.x, star.y, star.x + star.starWidth, star.y + star.starWidth))) {
                star.collected = true
                collectedStars++
            }
        }
        enemies.forEach { enemy ->
            enemy.update(player.speed)
            if (Rect.intersects(player.detectCollision, enemy.detectCollision)) {
                boom.x = enemy.x
                boom.y = enemy.y
                enemy.x = -300
                lives -= 1
                if (lives <= 0) {
                    playing = false
                    highScoreManager.getHighScore { currentHighScore ->
                        if (collectedStars > currentHighScore) {
                            highScoreManager.saveHighScore(collectedStars)
                        }
                        Handler(Looper.getMainLooper()).post {
                            if (!callGameOverOnce) {
                                onGameOver(collectedStars)
                                callGameOverOnce = true
                            }
                            gameThread?.join()
                        }
                    }
                }
            }
        }
        player.update()
        warrior.update()

        val shotIterator = player.shots.iterator()
        while (shotIterator.hasNext()) {
            val shot = shotIterator.next()
            val enemyIterator = enemies.iterator()
            while (enemyIterator.hasNext()) {
                val enemy = enemyIterator.next()
                if (Rect.intersects(shot.detectCollision, enemy.detectCollision)) {
                    boom.x = enemy.x
                    boom.y = enemy.y
                    enemy.x = -300
                    shotIterator.remove()
                    break
                }
            }
        }
    }

    private fun draw() {
        if (surfaceHolder.surface.isValid) {
            canvas = surfaceHolder.lockCanvas()
            canvas.drawColor(Color.BLACK)
            paint.color = Color.YELLOW
            stars.forEach { star ->
                if (!star.collected) {
                    paint.strokeWidth = star.starWidth.toFloat()
                    canvas.drawPoint(star.x.toFloat(), star.y.toFloat(), paint)
                }
            }
            canvas.drawBitmap(player.bitmap, player.x.toFloat(), player.y.toFloat(), paint)
            enemies.forEach { enemy ->
                canvas.drawBitmap(enemy.bitmap, enemy.x.toFloat(), enemy.y.toFloat(), paint)
            }
            canvas.drawBitmap(boom.bitmap, boom.x.toFloat(), boom.y.toFloat(), paint)
            canvas.drawBitmap(warrior.bitmap, warrior.x.toFloat(), warrior.y.toFloat(), paint)
            paint.textSize = 42f
            canvas.drawText("Lives: $lives", 10f, 100f, paint)
            canvas.drawText("Stars Collected: $collectedStars", 10f, 150f, paint)

            player.shots.forEach { shot ->
                canvas.drawRect(shot.x.toFloat(), shot.y.toFloat(), (shot.x + shot.width).toFloat(), (shot.y + shot.height).toFloat(), shot.paint)
            }

            surfaceHolder.unlockCanvasAndPost(canvas)
        }
    }

    private var callGameOverOnce = false
    private fun control() {
        Thread.sleep(17)
        if (lives == 0) {
            playing = false
            highScoreManager.getHighScore { currentHighScore ->
                if (collectedStars > currentHighScore) {
                    highScoreManager.saveHighScore(collectedStars)
                }
                Handler(Looper.getMainLooper()).post {
                    if (!callGameOverOnce) {
                        onGameOver(collectedStars)
                        callGameOverOnce = true
                    }
                    gameThread?.join()
                }
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                if (event.x > width / 2) {
                    player.shoot()
                } else {
                    player.updateTargetY(event.y.toInt())
                }
            }
            MotionEvent.ACTION_UP -> {
                player.boosting = false
            }
        }
        return true
    }
}