package com.example.myapplication

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    var wynik = 0
    var wylosowana_liczba = 0
    var liczba_strzalow = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val builder = AlertDialog.Builder(this@MainActivity)
        builder.setTitle("Komunikat:")

        builder.setPositiveButton("OK"){ dialogInterface: DialogInterface, i: Int ->}

        val dialog: AlertDialog = builder.create()

        val pole_liczba = findViewById<EditText>(R.id.pole_liczba)
        val strzal = findViewById<Button>(R.id.strzal)
        val strzaly = findViewById<TextView>(R.id.strzaly_2)
        val punkty = findViewById<TextView>(R.id.punkty_2)
        val nowa_gra = findViewById<Button>(R.id.nowa_gra)
        val nazwa_gracza = findViewById<TextView>(R.id.nazwa_gracza)
        val ranking_gra = findViewById<Button>(R.id.ranking_gra)
        val wyloguj = findViewById<Button>(R.id.wyloguj)

        wynik = intent.getIntExtra("punkty", 0)
        val login = intent.getStringExtra("login").toString()
        val haslo = intent.getStringExtra("haslo").toString()
        val db = DBHelper(this)
        nazwa_gracza.text = login

        fun nowa_gra() {
            punkty.text = wynik.toString()
            liczba_strzalow = 0
            strzaly.text = liczba_strzalow.toString()
            wylosowana_liczba = Random.nextInt(0,20)
        }

        fun restart() {
            wynik = 0
            punkty.text = wynik.toString()
            db.updateScore(login, wynik)
            liczba_strzalow = 0
            strzaly.text = liczba_strzalow.toString()
            wylosowana_liczba = Random.nextInt(0,20)
        }

        fun strzal(wylosowana_liczba: Int, wpisana_liczba: Int) {
            liczba_strzalow += 1
            if (wpisana_liczba == wylosowana_liczba) {
                strzaly.text = liczba_strzalow.toString()
                builder.setMessage("Udało się! Liczba strzałów: "+ liczba_strzalow)
                val dialog: AlertDialog = builder.create()
                dialog.show()
                if (liczba_strzalow == 1) {
                    wynik += 5
                    punkty.text = wynik.toString()
                    db.updateScore(login, wynik)
                    nowa_gra()
                }
                else if (liczba_strzalow <= 4) {
                    wynik += 3
                    punkty.text = wynik.toString()
                    db.updateScore(login, wynik)
                    nowa_gra()
                }
                else if (liczba_strzalow <= 6) {
                    wynik += 2
                    punkty.text = wynik.toString()
                    db.updateScore(login, wynik)
                    nowa_gra()
                }
                else if (liczba_strzalow <= 10) {
                    wynik += 1
                    punkty.text = wynik.toString()
                    db.updateScore(login, wynik)
                    nowa_gra()
                }
            }
            else {
                if (liczba_strzalow == 10) {
                    builder.setMessage("Przegrana!")
                    val dialog: AlertDialog = builder.create()
                    dialog.show()
                    restart()
                }
                else if (wpisana_liczba > wylosowana_liczba) {
                    Toast.makeText(applicationContext, "Twoja liczba jest mniejsza", Toast.LENGTH_SHORT).show()
                    strzaly.text = liczba_strzalow.toString()
                }
                else {
                    Toast.makeText(applicationContext, "Twoja liczba jest większa", Toast.LENGTH_SHORT).show()
                    strzaly.text = liczba_strzalow.toString()
                }
            }
        }

        nowa_gra()

        strzal.setOnClickListener() {
            if (pole_liczba.text.isEmpty()) {
                Toast.makeText(applicationContext, "Nie wpisano żadnej liczby!", Toast.LENGTH_SHORT).show()
            }
            else if (pole_liczba.text.length >= 3) {
                Toast.makeText(applicationContext, "Liczba nie może być tak duża!", Toast.LENGTH_SHORT).show()
            }
            else {
                var wpisana_liczba = pole_liczba.text.toString().toInt()
                if (wpisana_liczba < 0 || wpisana_liczba > 20) {
                    Toast.makeText(applicationContext, "Liczba spoza zakresu!", Toast.LENGTH_SHORT).show()
                }
                else {
                    strzal(wylosowana_liczba, wpisana_liczba)
                }
            }
        }
        nowa_gra.setOnClickListener() {
            nowa_gra()
        }

        ranking_gra.setOnClickListener() {
            val intent = Intent(this, RankingActivity::class.java)
            intent.putExtra("ekran", "gra")
            intent.putExtra("login", login)
            intent.putExtra("haslo", haslo)
            intent.putExtra("punkty", wynik)
            startActivity(intent)
        }

        wyloguj.setOnClickListener() {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onPause() {
        super.onPause()
    }
}