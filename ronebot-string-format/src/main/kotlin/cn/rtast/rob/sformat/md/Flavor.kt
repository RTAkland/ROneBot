/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/19
 */

package cn.rtast.rob.sformat.md

import org.intellij.markdown.flavours.commonmark.CommonMarkFlavourDescriptor
import org.intellij.markdown.flavours.gfm.GFMFlavourDescriptor
import org.intellij.markdown.flavours.space.SFMFlavourDescriptor

public object Flavor {
    /**
     * JetBrains space风格的markdown
     */
    public val SFM: SFMFlavourDescriptor = SFMFlavourDescriptor()

    /**
     * GitHub风格的markdown
     */
    public val GFM: GFMFlavourDescriptor = GFMFlavourDescriptor()

    /**
     * 官方/通用风格的markdown
     */
    public val CMF: CommonMarkFlavourDescriptor = CommonMarkFlavourDescriptor()
}