package com.vaibhav.foodiecart
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.vaibhav.foodiecart.databinding.ActivityMainBinding
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //these is use for send notification for app
       /* var dataString:String = ""
        if (intent!=null){
            if (intent.hasExtra("key1")){
                for( key in intent.extras!!.keySet())
                {
                    dataString = dataString+intent.extras!!.getString(key)+"\n"
                }
                binding.textview.text = dataString
            }
        }*/
     /*   FirebaseMessaging.getInstance().token.addOnCompleteListener {
            if (it.isSuccessful){
                Log.d("Mytag", it.result.toString())
            }
        }*/
      //  window.statusBarColor = Color.parseColor("#1FC57A")

        var NavController: NavController = findNavController(R.id.fragmentContainerView2)
        var bottomnav = findViewById<BottomNavigationView>(R.id.bottomNavigationView2)
        bottomnav.setupWithNavController(NavController)
        binding.Notificationbellicon.setOnClickListener {
            val bottomSheetDialog = Notification_Bottom_Fragment()
            bottomSheetDialog.show(supportFragmentManager, "Test")
        }

    }
}