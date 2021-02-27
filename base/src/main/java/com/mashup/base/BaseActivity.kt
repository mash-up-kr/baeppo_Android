package com.mashup.base

import android.os.Bundle

import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.mashup.base.ext.setStatusBarTransparent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseActivity<T : ViewDataBinding>(
    @LayoutRes private val layoutRes: Int
) : AppCompatActivity() {

    protected lateinit var binding: T

    abstract var logTag: String

    private val compositeDisposable by lazy { CompositeDisposable() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, layoutRes)
        binding.lifecycleOwner = this

        setStatusBarTransparent()
        initLayout()
        observeViewModel()
    }

    open fun initLayout() {}

    open fun observeViewModel() {}

    override fun onDestroy() {
        super.onDestroy()

        compositeDisposable.dispose()
    }

    protected fun Disposable.addToDisposable() = compositeDisposable.add(this)
}