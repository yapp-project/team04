package yapp14th.co.kr.myplant.ui.splash

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import yapp14th.co.kr.myplant.R

class SplashViewModel(app: Application) : AndroidViewModel(app) {
    var imgSplash = R.drawable.img_splash_background
    var img_logo = R.drawable.img_logo

    override fun onCleared() {
        super.onCleared()
    }
}