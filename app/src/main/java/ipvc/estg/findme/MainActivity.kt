package ipvc.estg.findme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    //Classe Main
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)

        //teste commit
        //teste git

    }

    fun irMenu(view: View) {
        val intent = Intent(this, MenuTesteActivity::class.java)
        startActivity(intent)
    }
}