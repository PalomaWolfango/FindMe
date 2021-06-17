package ipvc.estg.findme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.ktx.messaging

class MainActivity : AppCompatActivity() {
    //Classe Main
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.e("TAG", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            val msg = token
            if (msg != null) {
                Log.e("TAG", msg)
            }
        })

        Firebase.messaging.subscribeToTopic("animais")
            .addOnCompleteListener { task ->
                var msg ="subscriot"
                if (!task.isSuccessful) {
                    msg = "nao subscriot"
                }
                Log.e("TAG", msg)
            }

        if(intent.hasExtra("idAnimal")){
            val ss:String = intent.getStringExtra("idAnimal").toString()

        }

        //teste commit
    }
}