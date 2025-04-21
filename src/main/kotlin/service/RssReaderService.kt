package service

import model.Post
import org.w3c.dom.Document
import org.w3c.dom.Element
import util.rssReader

class RssReaderService() {
    fun getXml(url: String): Document {
        return rssReader(url)
    }
    fun createPost(xmls: List<Document>): List<Post> {
        val list = mutableListOf<Post>()
        xmls.forEach {
            val posts = convertXmlToPost(it)
            list.addAll(posts)
        }
        return list
    }
}

private fun convertXmlToPost(document: Document): MutableList<Post> {
    val list = mutableListOf<Post>()
    val items = document.getElementsByTagName("item")
    for (i in 0..items.length - 1) {
        val item = items.item(0)
        val element = item as Element
        val title = element.getElementsByTagName("title").item(0)
        val description = element.getElementsByTagName("description").item(0)
        val link = element.getElementsByTagName("link").item(0)

        val post = Post(title.textContent, description.textContent, link.textContent)
        list.add(post)
    }
    return list
}