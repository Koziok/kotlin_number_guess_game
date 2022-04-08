package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val login = findViewById<EditText>(R.id.login)
        val haslo = findViewById<EditText>(R.id.haslo)
        val zaloguj = findViewById<Button>(R.id.zaloguj)
        val zarejestruj = findViewById<Button>(R.id.zarejestruj)
        val ranking_login = findViewById<Button>(R.id.ranking_login)

        zaloguj.setOnClickListener() {
            val db = DBHelper(this)

            if (login.text.isEmpty() || haslo.text.isEmpty())
            {
                Toast.makeText(applicationContext, "Pola nie mogą być puste!", Toast.LENGTH_SHORT).show()
            }
            else
            {
                val user = db.login(login.text.toString(), haslo.text.toString())
                if (user != null)
                {
                    Toast.makeText(applicationContext, "Logowanie się powiodło!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("login", user?.login)
                    intent.putExtra("haslo", user?.haslo)
                    intent.putExtra("punkty", user?.punkty)
                    startActivity(intent)
                }
                else
                {
                    Toast.makeText(applicationContext, "Podano złe dane logowanie lub taki użytkownik nie istnieje!", Toast.LENGTH_SHORT).show()
                }
                login.getText().clear()
                haslo.getText().clear()
            }
        }

        zarejestruj.setOnClickListener() {
            val db = DBHelper(this)

            if (login.text.isEmpty() || haslo.text.isEmpty())
            {
                Toast.makeText(applicationContext, "Pola nie mogą być puste!", Toast.LENGTH_SHORT).show()
            }
            else
            {
                val user = db.findUser(login.text.toString())
                if (user != null)
                {
                    Toast.makeText(applicationContext, "Taki użytkownik już istnieje!", Toast.LENGTH_SHORT).show()

                }
                else
                {
                    db.addUser(User(login.text.toString(), haslo.text.toString(), 0))
                    Toast.makeText(applicationContext, "Zarejestrowano nowego użytkownika!", Toast.LENGTH_SHORT).show()
                }
                login.getText().clear()
                haslo.getText().clear()
            }
        }

        ranking_login.setOnClickListener() {
            val intent = Intent(this, RankingActivity::class.java)
            intent.putExtra("ekran", "login")
            startActivity(intent)
        }

    }

    override fun onPause() {
        super.onPause()
    }

}