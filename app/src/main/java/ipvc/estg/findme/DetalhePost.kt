package ipvc.estg.findme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log


class DetalhePost : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhe_post)


        if(intent.hasExtra("idCaso")){
            val idCaso:String = intent.getStringExtra("idCaso").toString()
            Log.e("teste", idCaso)
        }

    }
}