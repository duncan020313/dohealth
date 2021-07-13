package com.example.frontenddevelop

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, "eabb9d778126b390bb1426ac45541b56")
    }
}
