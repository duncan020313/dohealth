package com.example.gogym

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, "71806012d1352c857a9e30c10c8109ad")
    }
}