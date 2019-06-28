package com.kikyoung.recipe.base

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.kikyoung.recipe.R
import com.kikyoung.recipe.util.extension.observeChanges
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber
import kotlin.reflect.KClass

abstract class BaseFragment<ViewModelType : BaseViewModel>(clazz: KClass<ViewModelType>) : Fragment() {

    val viewModel: ViewModelType by sharedViewModel(clazz)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.serverErrorLiveData().observeChanges(this) {
            Timber.e(it.message)
            showSnackBar(R.string.common_error_server)
        }

        viewModel.networkErrorLiveData().observeChanges(this) {
            Timber.e(it.message)
            showSnackBar(R.string.common_error_network)
        }
    }

    private fun showSnackBar(@StringRes messageId: Int) {
        Snackbar.make(view!!, getString(messageId), Snackbar.LENGTH_LONG).show()
    }
}
