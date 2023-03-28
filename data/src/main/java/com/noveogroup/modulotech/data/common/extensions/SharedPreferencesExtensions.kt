package com.noveogroup.modulotech.data.common.extensions

import android.content.SharedPreferences

inline fun SharedPreferences.edit(block: SharedPreferences.Editor.() -> Unit) {
    val editor = edit()
    block(editor)
    editor.apply()
}
