package com.example.jevodan.union.ui.register

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.jevodan.union.R
import com.example.jevodan.union.ui.base.BaseFragment
import com.example.jevodan.union.ui.base.BaseViewModel
import kotlinx.android.synthetic.main.one_activity.*
import kotlinx.android.synthetic.main.register_fragment.*

class RegisterFragment : BaseFragment<Boolean?, RegisterViewState>() {


    override val viewModel: RegisterViewModel by lazy {
        ViewModelProviders.of(this).get(RegisterViewModel::class.java)
    }

    override val layoutRes: Int? = null

    override fun onResume() {
        super.onResume()
        Handler().postDelayed(
            {
                viewModel.requestUser()
            }, 1000L

        )
    }

    override fun renderData(data: Boolean?) {
        data?.takeIf { it }?.let {
            view?.let { it1 ->
                Navigation.findNavController(it1)
                    .navigate(RegisterFragmentDirections.successAutorization())
            } ?: let{ Log.d("фейл", "фейл")}
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.register_fragment, container, false)
    }

}
