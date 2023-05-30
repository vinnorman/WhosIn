package com.gonativecoders.whosin.ui.home.teammembers

import android.content.Context
import android.content.Intent

fun Context.invite(teamName: String, teamId: String) {
    val share = Intent.createChooser(Intent(Intent.ACTION_SEND).apply {
        putExtra(Intent.EXTRA_TEXT, """
            Hey! I'm using "Who's In" to sync up with my hybrid teammates.
            
            Join the team - "$teamName"
            Team Id: $teamId
            
            Get the app here: 
            https://play.google.com/store/apps/details?id=com.gonativecoders.whosin
        """.trimIndent())
        type = "text/plain"
        putExtra(Intent.EXTRA_TITLE, """Invite to join "$teamName" on Who's In"""")
    }, "Join $teamName on Who's In")
    startActivity(share)
}