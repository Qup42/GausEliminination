package mundhahs.julian

import kotlinx.html.TagConsumer
import kotlinx.html.consumers.onFinalize
import kotlinx.html.dom.createTree
import org.w3c.dom.*

fun Node.prepend(block: TagConsumer<HTMLElement>.() -> Unit): List<HTMLElement> =
        ArrayList<HTMLElement>().let { result ->
            ownerDocumentExt.createTree().onFinalize { it, partial -> if(!partial) { result.add(0, it); insertBefore(it, firstChild!!)/*  */ } }.block()

            result
        }

val HTMLElement.prepend: TagConsumer<HTMLElement>
    get() = ownerDocumentExt.createTree().onFinalize { element, partial -> if(!partial) { this@prepend.insertBefore(element, firstChild!!) } }

private val Node.ownerDocumentExt: Document
    get() = when {
        this is Document -> this
        else -> ownerDocument ?: throw IllegalStateException("Node has no ownerDocument")
    }