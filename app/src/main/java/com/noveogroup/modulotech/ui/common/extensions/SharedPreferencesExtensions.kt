package com.noveogroup.modulotech.ui.common

import android.content.SharedPreferences

inline fun SharedPreferences.edit(block: SharedPreferences.Editor.() -> Unit) {
    val editor = edit()
    block(editor)
    editor.apply()
}