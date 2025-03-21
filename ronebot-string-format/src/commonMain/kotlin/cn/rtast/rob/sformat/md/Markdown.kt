/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/19
 */

package cn.rtast.rob.sformat.md

import org.intellij.markdown.flavours.MarkdownFlavourDescriptor
import org.intellij.markdown.html.HtmlGenerator
import org.intellij.markdown.parser.MarkdownParser


public fun String.toHtml(flavor: MarkdownFlavourDescriptor = Flavor.GFM): String {
    return HtmlGenerator(this, MarkdownParser(flavor).buildMarkdownTreeFromString(this), flavor).generateHtml()
}