package ipvc.estg.findme

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.graphics.drawable.toBitmap
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import ipvc.estg.findme.API.EndPoints
import ipvc.estg.findme.API.OutputReports
import ipvc.estg.findme.API.ServiceBuilder
import ipvc.estg.findme.models.User
import kotlinx.android.synthetic.main.activity_inserir_desaparecimento.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.util.*
import java.util.Base64;

private const val REQUEST_CODE = 42
private val IMAGE_PICK_CODE=1000;
private val PERMISSION_CODE=1001;

class InserirDesaparecimento : AppCompatActivity() {

    companion object {
        var currentUser: User? = null
        var id:String = ""
        val TAG = "LatestMessages"
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inserir_desaparecimento)

        fetchCurrentUser()

        photo_btn.setOnClickListener{
            val tirarPhotoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            if(tirarPhotoIntent.resolveActivity(this.packageManager) != null){
                startActivityForResult(tirarPhotoIntent, REQUEST_CODE)
            }else{
                Toast.makeText(this@InserirDesaparecimento, "eee", Toast.LENGTH_SHORT).show()
            }
        }

        inserirReport.setOnClickListener{

            val tamanho =  findViewById<EditText>(R.id.ed_txt_tamanho)

            val raca = findViewById<EditText>(R.id.ed_txt_raca)

            val sexo = findViewById<EditText>(R.id.ed_txt_sexo)
            val localizacao =  findViewById<EditText>(R.id.ed_txt_localizacao)
            val data = findViewById<EditText>(R.id.ed_txt_data)
            var descricao =  findViewById<EditText>(R.id.ed_txt_descricao)
            var chip =  findViewById<EditText>(R.id.ed_txt_n_chip)
            val image = findViewById<ImageView>(R.id.imagem)



            if(TextUtils.isEmpty(raca.text) || TextUtils.isEmpty(tamanho.text) || TextUtils.isEmpty(sexo.text) || TextUtils.isEmpty(chip.text)  || TextUtils.isEmpty(localizacao.text) || TextUtils.isEmpty(data.text) || TextUtils.isEmpty(descricao.text) ){
                Toast.makeText(this, "Preencha os campos", Toast.LENGTH_LONG).show()


                Log.d("***", "raca:" +raca.text.toString())
                Log.d("***", "tamanho:" +tamanho.text.toString())
                Log.d("***", "sexo:" +sexo.text.toString())
                Log.d("***", "localizacao:" +localizacao.text.toString())
                Log.d("***", "data:" +data.text.toString())
                Log.d("***", "descricao:" +descricao.text.toString())
                Log.d("***", "chip:" +chip.text.toString())
            }else{
                var tipoReport =0


                val request = ServiceBuilder.buildService(EndPoints::class.java)
                val bitmap = image.drawable.toBitmap()
                val byteArrayOutputStream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream)
                val encodedImage = Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray())

                val call = request.adicionarPonto(raca.text.toString() , tamanho.text.toString() , sexo.text.toString() , localizacao.text.toString(), data.text.toString(), descricao.text.toString(), chip.text.toString() , encodedImage, id,  0)

                call.enqueue(object : Callback<OutputReports> {
                    override fun onResponse(call: Call<OutputReports>, response: Response<OutputReports>) {
                        if (response.isSuccessful) {
                            Log.d("***", "funcionou insert")
                            val intent = Intent(applicationContext, AnuncioActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }

                    override fun onFailure(call: Call<OutputReports>, t: Throwable) {
                        //Toast.makeText(applicationContext, "${t.message}", Toast.LENGTH_SHORT).show()
                        Log.d("arrr", "ErrorOccur:  ${t.message}" )
                        val intent = Intent(applicationContext, AnuncioActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                })
            }
        }

      /* galeria_btn.setOnClickListener{
            //permissoes
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                    //permissao negada
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
                    requestPermissions(permissions, PERMISSION_CODE)
                }else{
                    //ja tem permissoes
               //     pickImageGallery()
                }
            }else{
                //SO baixo
              //  pickImageGallery()
            }
        }*/

    }

    private fun pickImageGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)

    }


   /* override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            PERMISSION_CODE -> {
                if(grantResults.size >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    pickImageGallery()
                } else{
                    Toast.makeText(this, "getString(R.string.naopermitiu)" , Toast.LENGTH_SHORT).show()
                }
            }
        }
    }*/

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if(requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK){
            val takenImage = data?.extras?.get("data") as Bitmap
            imagem.setImageBitmap(takenImage)
        }
        else if( requestCode == IMAGE_PICK_CODE && resultCode == Activity.RESULT_OK ){
            imagem.setImageURI(data?.data)
        }
        else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun fetchCurrentUser() {
        val uid = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(p0: DataSnapshot) {
                currentUser = p0.getValue(User::class.java)
                id = currentUser?.uid.toString()
                Log.d("testeeee", "CurrentUser ${currentUser?.uid}")


            }

            override fun onCancelled(p0: DatabaseError) {
            }
        })
    }

}