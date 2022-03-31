package com.example.moviedb.Api.Repository

import android.app.ActivityManager

enum class Status{
    RUNNING,
    SUCCES,
    FAILED,
}

class Network ( val status: Status, val msg: String ){

    companion object{

        val LOADED: Network
        val LOADİNG: Network
        val EROR: Network
        val ENDOFLİST : Network

        init {

            LOADED = Network(Status.SUCCES, "Başarılı Bir Şekilde Yüklendi")
            LOADİNG = Network(Status.RUNNING,"Yükleniyor")
            EROR = Network(Status.FAILED,"Bağlantı Hatası")
            ENDOFLİST = Network(Status.FAILED, "Sayfanın Sonuna Ulaştınız")
        }

    }
}