package ipvc.estg.findme

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import ipvc.estg.findme.ui.inicio.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_menu_teste.*
import kotlinx.android.synthetic.main.fragment_inicio.view.*


class MenuTesteActivity : AppCompatActivity() {
    internal lateinit var viewpageradapter: ViewPagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_teste)

        viewpageradapter= ViewPagerAdapter(supportFragmentManager)
        viewPager.adapter=viewpageradapter  //Binding PagerAdapter with ViewPager
        tab_layout.setupWithViewPager(viewPager) //Binding ViewPager with TabLayout

        bottomNavigationView2.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.ic_pagina_inicial -> {
                }
                R.id.ic_adicionar -> {
                    val a = Intent(this, InserirAparecimento::class.java)
                    startActivity(a)
                }
                R.id.ic_chat -> {
                    //val b = Intent(this, ActivityChat::class.java)
                    //startActivity(b)
                }
                R.id.ic_conta -> {
                    //val c = Intent(this, ActivityConta::class.java)
                    //startActivity(c)
                }
            }
            false
        }

    }
}