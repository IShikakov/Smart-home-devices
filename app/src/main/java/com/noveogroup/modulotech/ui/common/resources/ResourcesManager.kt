package com.noveogroup.modulotech.ui.common.resources

import android.content.Context
import androidx.annotation.StringRes

class ResourcesManager(private val context: Context) {

    fun resolveString(@StringRes resId: Int, vararg args: Any): String {
        return context.getString(resId, *args)
    }
}
