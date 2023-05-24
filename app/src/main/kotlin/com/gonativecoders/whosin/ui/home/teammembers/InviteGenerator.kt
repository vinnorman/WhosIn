package com.gonativecoders.whosin.ui.home.teammembers

import android.content.Context
import android.content.Intent

fun Context.invite(teamName: String) {
    val share = Intent.createChooser(Intent(Intent.ACTION_SEND).apply {
        putExtra(Intent.EXTRA_TEXT, "https://gonativecoders.com/whosin")
        type = "text/plain"
        putExtra(Intent.EXTRA_TITLE, "Invite to join $teamName on Who's In")
    }, "Join $teamName on Who's In")
    startActivity(share)
}