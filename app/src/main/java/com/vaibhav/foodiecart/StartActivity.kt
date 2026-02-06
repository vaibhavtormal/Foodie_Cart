package com.vaibhav.foodiecart

import android.content.Intent
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vaibhav.foodiecart.databinding.ActivityStartBinding

class StartActivity : AppCompatActivity() {
    private val binding: ActivityStartBinding by lazy {
        ActivityStartBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        setContentView(binding.root)
        binding.root.post { startGlassAnimation() }
        binding.nextbtn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun startGlassAnimation() {
        animateFloat(binding.imageView2, 0.05f, 6000L)
        animateFloat(binding.glassCard, 0.02f, 7000L)
        animateBubble(binding.bubbleOne, 18f, 5000L)
        animateBubble(binding.bubbleTwo, -22f, 6500L)
        animateBubble(binding.bubbleThree, 16f, 5200L)
    }

    private fun animateFloat(view: View, scaleDelta: Float, duration: Long) {
        view.pivotX = view.width / 2f
        view.pivotY = view.height / 2f
        val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 1f, 1f + scaleDelta)
        val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f, 1f + scaleDelta)
        val alpha = PropertyValuesHolder.ofFloat(View.ALPHA, 0.95f, 1f)
        ObjectAnimator.ofPropertyValuesHolder(view, scaleX, scaleY, alpha).apply {
            this.duration = duration
            repeatMode = ObjectAnimator.REVERSE
            repeatCount = ObjectAnimator.INFINITE
            start()
        }
    }

    private fun animateBubble(view: View, translationDelta: Float, duration: Long) {
        ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, 0f, translationDelta).apply {
            this.duration = duration
            repeatMode = ObjectAnimator.REVERSE
            repeatCount = ObjectAnimator.INFINITE
            start()
        }
    }
}
