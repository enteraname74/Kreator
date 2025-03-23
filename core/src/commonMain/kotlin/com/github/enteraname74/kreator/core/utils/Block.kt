package com.github.enteraname74.kreator.core.utils

class Block(
    val tabLevel: Int,
    private val name: String,
    private val content: List<BlockContent>
) {
    private val blockTab: String =
        "    ".repeat(tabLevel)

    private val blockContentTab: String =
        "    ".repeat(tabLevel+1)

    override fun toString(): String {
        val builder = StringBuilder()
        builder.appendLine("$blockTab$name {")

        content.forEach {
            when (it) {
                is BlockContent.Raw -> builder.appendLine("$blockContentTab$it")
                is BlockContent.SubBlock -> builder.appendLine("$it")
            }
        }

        builder.append("$blockTab}")

        return builder.toString()
    }
}

interface BlockContent {
    data class Raw(val string: String) : BlockContent {
        override fun toString(): String =
            string
    }
    data class SubBlock(val subBlock: Block) : BlockContent {
        override fun toString(): String =
            subBlock.toString()
    }
}

class BlockBuilder(
    val level: Int,
) {
    val subLevel: Int
        get() = level + 1

    private val allContent = ArrayList<BlockContent>()

    fun add(raw: Any) {
        allContent.add(BlockContent.Raw(raw.toString()))
    }

    fun add(raw: String) {
        allContent.add(BlockContent.Raw(raw))
    }

    fun whiteSpace() {
        allContent.add(BlockContent.Raw(""))
    }

    fun subBlock(
        name: String,
        contentBuilder: BlockBuilder.() -> Unit,
    ) {
        allContent.add(
            BlockContent.SubBlock(
                Block(
                    tabLevel = subLevel,
                    name = name,
                    content = BlockBuilder(level = subLevel).apply { contentBuilder(this) }.build(),
                )
            )
        )
    }

    fun build(): List<BlockContent> =
        allContent
}

fun block(
    name: String,
    tabLevel: Int = 0,
    contentBuilder: BlockBuilder.() -> Unit,
): Block =
    Block(
        tabLevel = tabLevel,
        name = name,
        content = BlockBuilder(level = tabLevel).apply { contentBuilder(this) }.build(),
    )