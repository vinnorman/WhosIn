package com.gonativecoders.whosin.ui.home.teammembers

import android.net.Uri
import com.google.firebase.dynamiclinks.ktx.androidParameters
import com.google.firebase.dynamiclinks.ktx.dynamicLink
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.ktx.Firebase

fun generateInviteLink(): Uri {
    val link = Firebase.dynamicLinks.dynamicLink {
        link = Uri.parse("https://gonativecoders.com/invite")
        domainUriPrefix = "https://gonativecoders.com"
        // Open links with this app on Android
        androidParameters("com.gonativecoders.whosin") {

        }
    }

    return link.uri
}