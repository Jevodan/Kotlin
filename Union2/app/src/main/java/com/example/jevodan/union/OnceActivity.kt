package com.example.jevodan.union

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.jevodan.union.ui.main.LogoutDialog
import com.firebase.ui.auth.AuthUI
import kotlinx.android.synthetic.main.item_menu_note.*
import kotlinx.android.synthetic.main.one_activity.*

class OnceActivity : AppCompatActivity(), LogoutDialog.LogoutListener {

    var navController: NavController? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.one_activity)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)

    }


    override fun onLogout() {
        this?.let {
            AuthUI.getInstance()
                .signOut(it)
        }?.addOnCompleteListener {
            val action = MainFragmentDirections.logout()
            navController?.navigate(action)
        }
    }


}
