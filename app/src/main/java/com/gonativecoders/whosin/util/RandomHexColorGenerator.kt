package com.gonativecoders.whosin.util

import com.gonativecoders.whosin.ui.theme.*

val colors = arrayOf(
    Red700,
    Pink700,
    Purple700,
    DeepPurple700,
    Indigo700,
    Blue700,
    LightBlue700,
    Cyan700,
    Teal700,
    Green700,
    LightGreen700,
    Lime700,
    Yellow700,
    Amber700,
    Orange700,
    DeepOrange700,
    Brown700,
)

fun getRandomHexColor(): String {
    return colors.random().value.toString()
}