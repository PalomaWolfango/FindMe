package ipvc.estg.findme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class AnuncioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anuncio)
    }


    fun button1(view: View) {
        val intent = Intent(this, InserirDesaparecimento::class.java)
        startActivity(intent)
    }

    fun button2(view: View) {
        val intent = Intent(this, InserirAparecimento::class.java)
        startActivity(intent)
    }
}