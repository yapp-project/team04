package yapp14th.co.kr.myplant.ui.main.tab3_mypage

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import yapp14th.co.kr.myplant.R

class SplashViewModel(app: Application) : AndroidViewModel(app) {
    var imgSplash = R.drawable.img_splash

    override fun onCleared() {
        super.onCleared()
    }
}