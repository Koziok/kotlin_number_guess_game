package com.example.myapplication

class User {
    var id: Int = 0
    var login: String? = null
    var haslo: String? = null
    var punkty: Int = 0

    constructor(id: Int, login: String, haslo: String, punkty: Int) {
        this.id = id
        this.login = login
        this.haslo = haslo
        this.punkty = punkty
    }

    constructor(login: String, haslo: String, punkty: Int) {
        this.login = login
        this.haslo = haslo
        this.punkty = punkty
    }
}