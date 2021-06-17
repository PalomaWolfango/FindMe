package ipvc.estg.findme

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.graphics.drawable.toBitmap
import ipvc.estg.findme.API.EndPoints
import ipvc.estg.findme.API.OutputReports
import ipvc.estg.findme.API.ServiceBuilder
import ipvc.estg.findme.login.MainActivity
import kotlinx.android.synthetic.main.activity_inserir_aparecimento.*
import kotlinx.android.synthetic.main.activity_inserir_desaparecimento.*
import kotlinx.android.synthetic.main.activity_inserir_desaparecimento.photo_btn
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.util.*
import java.util.Base64;

private const val REQUEST_CODE = 43
private val IMAGE_PICK_CODE=1005;
private val PERMISSION_CODE=1006;

class InserirAparecimento : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inserir_aparecimento)


        photo_btn.setOnClickListener{
            val tirarPhotoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            if(tirarPhotoIntent.resolveActivity(this.packageManager) != null){
                startActivityForResult(tirarPhotoIntent, REQUEST_CODE)
            }else{
                Toast.makeText(this@InserirAparecimento, "eee", Toast.LENGTH_SHORT).show()
            }
        }

        inserirReportAP.setOnClickListener{

            val tamanho =  findViewById<EditText>(R.id.ed_txt_tamanhoAP)

            val raca = findViewById<EditText>(R.id.ed_txt_racaAP)

            val sexo = findViewById<EditText>(R.id.ed_txt_sexoAP)
            val localizacao =  findViewById<EditText>(R.id.ed_txt_localizacaoAP)
            val data = findViewById<EditText>(R.id.ed_txt_dataAP)
            var descricao =  findViewById<EditText>(R.id.ed_txt_descricaoAP)
            val image = findViewById<ImageView>(R.id.imagemAP)



            if(TextUtils.isEmpty(raca.text) || TextUtils.isEmpty(tamanho.text) || TextUtils.isEmpty(sexo.text) || TextUtils.isEmpty(localizacao.text) || TextUtils.isEmpty(data.text) || TextUtils.isEmpty(descricao.text) ){
                Toast.makeText(this, "Preencha os campos", Toast.LENGTH_LONG).show()
            }else{

                val request = ServiceBuilder.buildService(EndPoints::class.java)
                val bitmap = image.drawable.toBitmap()
                val byteArrayOutputStream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream)
                val encodedImage = Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray())

                val call = request.adicionarPonto(raca.text.toString() , tamanho.text.toString() , sexo.text.toString() , localizacao.text.toString(), data.text.toString(), descricao.text.toString(), "" , encodedImage, "fewf",  1)


                call.enqueue(object : Callback<OutputReports> {
                    override fun onResponse(call: Call<OutputReports>, response: Response<OutputReports>) {
                        if (response.isSuccessful) {
                            Log.d("***", "funcionou insert")
                                val intent = Intent(applicationContext, MainActivity::class.java)
                                 startActivity(intent)
                                 finish()
                        }
                    }

                    override fun onFailure(call: Call<OutputReports>, t: Throwable) {
                        Log.d("arrr", "ErrorOccur:  ${t.message}" )
                    }
                })
            }
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if(requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK){
            val takenImage = data?.extras?.get("data") as Bitmap
            imagemAP.setImageBitmap(takenImage)
        }
        else if( requestCode == IMAGE_PICK_CODE && resultCode == Activity.RESULT_OK ){
            imagemAP.setImageURI(data?.data)
        }
        else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}