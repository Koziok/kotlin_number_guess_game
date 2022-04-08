package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView

class RankingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ranking)

        val ranking = findViewById<ListView>(R.id.ranking)
        val powrot = findViewById<Button>(R.id.powrot)

        val punkty = intent.getIntExtra("punkty", 0)
        val login = intent.getStringExtra("login").toString()
        val haslo = intent.getStringExtra("haslo").toString()
        val ekran = intent.getStringExtra("ekran").toString()

        val db = DBHelper(this)

        var users = listOf<User>()
        users = db.getTop10()

        val list = arrayOfNulls<String>(users.size)

        for (i in users.indices) {
            val user = users[i]
            list[i] = user.punkty.toString() + " - " + user.login.toString()
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)
        ranking.adapter = adapter

        powrot.setOnClickListener() {
            if(ekran.equals("gra"))
            {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("login", login)
                intent.putExtra("haslo", haslo)
                intent.putExtra("punkty", punkty)
                startActivity(intent)
            }
            else if(ekran.equals("login"))
            {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }
    override fun onPause() {
        super.onPause()
    }
}