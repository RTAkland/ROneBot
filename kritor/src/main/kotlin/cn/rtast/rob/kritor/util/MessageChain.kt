/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/29
 */


package cn.rtast.rob.kritor.util

import io.kritor.common.Element
import io.kritor.common.Element.ElementType
import io.kritor.common.element
import io.kritor.common.textElement

class MessageChain internal constructor(val elements: List<Element>) {

    class Builder {
        private val elements = mutableListOf<Element>()

        fun addText(text: String): Builder {
            elements.add(element {
                type = ElementType.TEXT
                this.text = textElement { this.text = text }
            })
            return this
        }

        fun build() = MessageChain(elements)
    }
}