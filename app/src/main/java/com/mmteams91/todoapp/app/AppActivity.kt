package com.mmteams91.todoapp.app

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.mmteams91.todoapp.R
import com.mmteams91.todoapp.features.auth.AuthFragment

class AppActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.app_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, AuthFragment.newInstance())
                    .commitNow()
        }
    }

}