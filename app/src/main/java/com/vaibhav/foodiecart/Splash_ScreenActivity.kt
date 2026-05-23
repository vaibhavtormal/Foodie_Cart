package com.vaibhav.foodiecart
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.google.firebase.auth.FirebaseAuth

class Splash_ScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        Handler(Looper.getMainLooper()).postDelayed({
            val destination = if (FirebaseAuth.getInstance().currentUser != null) {
                MainActivity::class.java
            } else {
                LoginActivity::class.java
            }

            val intent = Intent(this, destination)
            startActivity(intent)
            finish()
        }, 3000)
    }
}


