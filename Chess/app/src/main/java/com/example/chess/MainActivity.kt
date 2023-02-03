package com.example.chess

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.chess.fragments.GameOverFragment
import com.example.chess.fragments.MainDeskFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragmentContainer, MainDeskFragment())
                .commit()
        }
    }
}