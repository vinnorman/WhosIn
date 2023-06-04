package com.gonativecoders.whosin.core.util

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build

val Context.isFreshInstall
    get() = with(packageInfo) { firstInstallTime == lastUpdateTime }


val Context.packageInfo: PackageInfo
    get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        packageManager.getPackageInfo(packageName, PackageManager.PackageInfoFlags.of(0))
    } else {
        @Suppress("DEPRECATION") packageManager.getPackageInfo(packageName, 0)
    }