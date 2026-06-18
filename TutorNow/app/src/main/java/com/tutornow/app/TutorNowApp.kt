package com.tutornow.app

import android.app.Application
import com.tutornow.app.data.local.SessionManager
import com.tutornow.app.data.network.RetrofitClient

class TutorNowApp : Application() {

    lateinit var sessionManager: SessionManager

    override fun onCreate() {
        super.onCreate()
        sessionManager = SessionManager(applicationContext)
        RetrofitClient.init(sessionManager)
        instance = this
    }

    companion object {
        lateinit var instance: TutorNowApp
            private set
    }
}
