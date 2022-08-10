package com.r4g3baby.simplemotd.util

import java.util.regex.Pattern


private const val COLOR_CHAR = '\u00A7'
private const val ALL_CODES = "0123456789AaBbCcDdEeFfKkLlMmNnOoRrXx"

private val hexPattern = Pattern.compile("&?#([A-Fa-f0-9]{6})|\\{#([A-Fa-f0-9]{6})}")

fun translateHexColorCodes(text: String): String {
    val matcher = hexPattern.matcher(text)
    val buffer = StringBuffer(text.length + 4 * 8)
    while (matcher.find()) {
        var group = matcher.group(1)
        if (group == null) group = matcher.group(2)

        matcher.appendReplacement(
            buffer, COLOR_CHAR.toString() + "x"
                + COLOR_CHAR + group[0] + COLOR_CHAR + group[1]
                + COLOR_CHAR + group[2] + COLOR_CHAR + group[3]
                + COLOR_CHAR + group[4] + COLOR_CHAR + group[5]
        )
    }
    return matcher.appendTail(buffer).toString()
}

fun translateAlternateColorCodes(textToTranslate: String, altColorChar: Char = '&'): String {
    val b = textToTranslate.toCharArray()
    for (i in b.indices - 1) {
        if (b[i] == altColorChar && ALL_CODES.indexOf(b[i + 1]) > -1) {
            b[i] = COLOR_CHAR
            b[i + 1] = b[i + 1].lowercaseChar()
        }
    }
    return String(b)
}