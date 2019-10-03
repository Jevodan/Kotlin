package com.example.jevodan.union.ui.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.example.jevodan.union.R
import com.example.jevodan.union.data.errors.NoAuthException
import com.example.jevodan.union.ui.register.RegisterFragmentDirections
import com.firebase.ui.auth.AuthUI

abstract class BaseFragment<T, S : BaseViewState<T>> : Fragment() {

    abstract val viewModel: BaseViewModel<T, S>
    abstract val layoutRes: Int?

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        layoutRes?.let {
            return inflater.inflate(it, container, false)
        }
        return null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.viewState().observe(this, Observer<S> {
            if (it == null)
                return@Observer  // Возвращаемся из Обсервера, т.е указываем откуда возвращаемся

            it.error?.let {
                renderError(it)
                return@Observer
            }
            renderData(it.data)
        })
    }

    abstract fun renderData(data: T)

    protected fun renderError(error: Throwable?) {
        when (error) {
            is NoAuthException -> {
                startLogin()
            }
            else -> error?.let { showError(it) }
        }
    }

    private fun startLogin() {
        val provider = listOf(
            AuthUI.IdpConfig.GoogleBuilder().build()
        )
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setLogo(R.drawable.ic_account_circle_deep_purple_500_18dp)
                .setAvailableProviders(provider)
                .build()
            , 12500
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 12500 && resultCode != Activity.RESULT_OK) {
            Log.d("финиш", "финиш")
            activity?.finish()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    protected fun showError(errorMessage: Throwable) {
        Toast.makeText(context, errorMessage.message, Toast.LENGTH_SHORT).show()

    }

}