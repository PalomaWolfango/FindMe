package ipvc.estg.findme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import ipvc.estg.findme.ui.inicio.InicioFragment
import kotlinx.android.synthetic.main.activity_menu_teste.*

class MenuTesteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_teste)

        val fr: Fragment = InicioFragment()
        val fm: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fm.beginTransaction()
        fragmentTransaction.replace(R.id.fl_wrapper, fr)
        fragmentTransaction.commit()
    }
}