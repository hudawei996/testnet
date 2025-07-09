package com.goals.testnet

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * 应用的 Application 类。
 * 使用 @HiltAndroidApp 注解来启用 Hilt 的代码生成。
 */
@HiltAndroidApp
class App : Application() {

}
