package com.worgle.worgle.data

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "channel", strict = false)
data class WordleResponse(
    @field:ElementList(name = "item", inline = true)
    var items: List<WordleItem>? = null
)

@Root(name = "item", strict = false)
data class WordleItem(
    @field:Element(name = "word")
    var word: String? = null,

    @field:Element(name = "definition", required = false)
    var definition: String? = null
)