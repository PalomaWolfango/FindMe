package ipvc.estg.findme

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.content.Intent
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatDelegate
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      //  supportActionBar?.hide()
        setContentView(R.layout.activity_main)



        login_button_login.setOnClickListener {
            val email = insertUser.text.toString()
            val password = insertPassword.text.toString()


            Log.d("Login", "tentar login : $email/***")
            Log.d("Login", "tentar login : $password/***")
        }

    }

}