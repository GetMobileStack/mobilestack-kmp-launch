package com.zenithapps.mobilestack.android.provider

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import com.zenithapps.mobilestack.provider.OSCapabilityProvider

class AndroidCapabilityProvider(private val activity: Activity) : OSCapabilityProvider {
    override fun openUrl(url: String) {
        activity.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    override fun getPlatform(): OSCapabilityProvider.Platform {
        return OSCapabilityProvider.Platform.ANDROID
    }

    override fun getAppVersion(): String {
        return activity.getPackageInfo().versionName
    }

    override fun managePurchases() {
        openUrl("https://play.google.com/store/account/subscriptions")
    }

    private fun Context.getPackageInfo(): PackageInfo {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            packageManager.getPackageInfo(packageName, PackageManager.PackageInfoFlags.of(0))
        } else {
            packageManager.getPackageInfo(packageName, 0)
        }
    }
}