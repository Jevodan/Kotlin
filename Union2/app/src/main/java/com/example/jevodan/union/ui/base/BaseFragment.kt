package com.example.jevodan.union.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer

abstract class BaseFragment<T, S : BaseViewState<T>> : Fragment() {

    abstract val viewModel: BaseViewModel<T, S>
    abstract val layoutRes: Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutRes, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.viewState().observe(this, Observer<S> {
            if (it == null)
                return@Observer  // Возвращаемся из Обсервера, т.е указываем откуда возвращаемся

            it.error?.let {
                renderError(it.message)
                return@Observer
            }
            renderData(it.data)
        })
    }

    abstract fun renderData(data: T)

    protected fun renderError(error: String?) {
        error?.let { showError(it) }
    }

    protected fun showError(errorMessage: String) {
        Toast.makeText(context, errorMessage!!, Toast.LENGTH_SHORT).show()

    }

}