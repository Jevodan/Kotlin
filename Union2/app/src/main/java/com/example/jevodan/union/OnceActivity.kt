package com.example.jevodan.union

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation

class OnceActivity : AppCompatActivity() {

    var navController: NavController? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.one_activity)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)

    }

}
