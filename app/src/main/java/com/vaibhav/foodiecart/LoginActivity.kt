package com.vaibhav.foodiecart

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.shashank.sony.fancytoastlib.FancyToast
import com.vaibhav.foodiecart.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()

        //innitialization of Firebase Auth
        auth = Firebase.auth
        //innitialization of Google Sign-In
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)

        //login with email and password
        binding.loginBtn.setOnClickListener {
            //get data from field
            email = binding.emailAddress.text.toString().trim()
            password = binding.passwordtext.text.toString().trim()


            if (email.isBlank() || password.isBlank()) {
                FancyToast.makeText(
                    this,
                    "Please Enter All Information\uD83E\uDD2D",
                    FancyToast.LENGTH_SHORT,
                    FancyToast.WARNING,
                    false
                )
                    .show()
            } else {
               loginUser()
            }

        }
        binding.donthavebtn.setOnClickListener {
            val intent = Intent(this, SigninActivity::class.java)
            startActivity(intent)
        }
        //google sign in
        binding.googlebutton.setOnClickListener {
            val signinIntent = googleSignInClient.signInIntent
            launcher.launch(signinIntent)
        }
    }


    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                if (task.isSuccessful) {
                    FancyToast.makeText(
                        this,
                        "Sign-In SuccessFull\uD83E\uDD29",
                        FancyToast.LENGTH_SHORT,
                        FancyToast.SUCCESS,
                        false
                    )
                        .show()
                    val account: GoogleSignInAccount? = task.result
                    val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
                    auth.signInWithCredential(credential).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        } else {
                            FancyToast.makeText(
                                this,
                                "Sign-In Failed\uD83D\uDE42",
                                FancyToast.LENGTH_SHORT,
                                FancyToast.ERROR,
                                false
                            )
                                .show()
                        }
                    }
                }
            } else {
                FancyToast.makeText(
                    this,
                    "Sign-In Failed\uD83D\uDE42",
                    FancyToast.LENGTH_SHORT,
                    FancyToast.ERROR,
                    false
                ).show()
            }
        }

    private fun loginUser() {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                FancyToast.makeText(
                    this,
                    "Login SuccessFully\uD83D\uDE0A",
                    FancyToast.LENGTH_SHORT,
                    FancyToast.SUCCESS,
                    false
                ).show()
                updateUi(auth.currentUser)
            } else {
                FancyToast.makeText(
                    this,
                    "Login SuccessFully\uD83D\uDE0A",
                    FancyToast.LENGTH_SHORT,
                    FancyToast.SUCCESS,
                    false
                ).show()
                updateUi(auth.currentUser)
            }
        }
    }
    override fun onStart() {
        super.onStart()
        val currentuser: FirebaseUser? = auth.currentUser
        if (currentuser != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun updateUi(user: FirebaseUser?) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}