package com.example.myapplication

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val query = ("CREATE TABLE " +
                TABLE_NAME + "("
                + ID_COL + " INTEGER PRIMARY KEY," +
                LOGIN_COL + " TEXT," +
                PASSWD_COL + " TEXT," +
                SCORE_COL + " INTEGER" + ")")
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    fun addUser(user: User){
        val db = this.writableDatabase
        val values = ContentValues()

        values.put(LOGIN_COL, user.login)
        values.put(PASSWD_COL, user.haslo)
        values.put(SCORE_COL, user.punkty)

        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun findUser(login : String): User? {
        val db = this.writableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $LOGIN_COL = \"$login\""
        val cursor = db.rawQuery(query, null)
        var user: User? = null

        if(cursor.moveToFirst()) {
            cursor.moveToFirst()

            val id_db = Integer.parseInt(cursor.getString(0))
            val login_db = cursor.getString(1)
            val haslo_db = cursor.getString(2)
            val punkty_db = Integer.parseInt(cursor.getString(3))
            user = User(id_db, login_db, haslo_db, punkty_db)
            cursor.close()
        }
        db.close()
        return user
    }

    fun login(login: String, haslo: String): User? {
        val db = this.writableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $LOGIN_COL = \"$login\" AND $PASSWD_COL = \"$haslo\""
        val cursor = db.rawQuery(query, null)
        var user: User? = null

        if(cursor.moveToFirst()) {
            cursor.moveToFirst()

            val id_db = Integer.parseInt(cursor.getString(0))
            val login_db = cursor.getString(1)
            val haslo_db = cursor.getString(2)
            val punkty_db = Integer.parseInt(cursor.getString(3))
            user = User(id_db, login_db, haslo_db, punkty_db)
            cursor.close()
        }
        db.close()
        return user
    }

    fun updateScore(login: String, nowe_punkty: Int) {
        val db = this.writableDatabase

        val values = ContentValues()
        values.put(SCORE_COL, nowe_punkty)

        db.update(TABLE_NAME, values, "login = ?", arrayOf(login))
        db.close()
    }

    fun getTop10(): List<User> {
        val db = this.writableDatabase
        val query = "SELECT * FROM $TABLE_NAME ORDER BY $SCORE_COL DESC LIMIT 10"
        val cursor = db.rawQuery(query, null)
        val users = mutableListOf<User>()

        if(cursor.moveToFirst())
        {
            do {
                var user: User? = null
                user = User(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), Integer.parseInt(cursor.getString(3)))
                users.add(user)
            } while(cursor.moveToNext())
            cursor.close()
        }
        db.close()
        return users
    }

    companion object{
        private val DATABASE_NAME = "gra_losujaca.db"
        private val DATABASE_VERSION = 1
        val TABLE_NAME = "uzytkownicy"
        val ID_COL = "id"
        val LOGIN_COL = "login"
        val PASSWD_COL = "haslo"
        val SCORE_COL = "punkty"
    }
}