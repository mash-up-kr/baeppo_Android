package com.mashup.ipdam.ui.splash

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.mashup.base.BaseActivity
import com.mashup.base.schedulers.SchedulerProvider
import com.mashup.ipdam.R
import com.mashup.ipdam.databinding.ActivitySplashBinding
import com.mashup.ipdam.ui.login.LoginActivity
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {
    override var logTag: String = "SplashActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Observable.timer(2000L, TimeUnit.MILLISECONDS)
            .observeOn(SchedulerProvider.ui())
            .subscribe({
                val intent = Intent(this@SplashActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }, {
                Log.e(logTag, it.message ?: "Splash Exception")
            }).addToDisposable()
    }
}
