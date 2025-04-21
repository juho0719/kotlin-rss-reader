package service

import model.Post
import org.w3c.dom.Document
import org.w3c.dom.Element
import util.rssReader
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*

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

    fun sort(keyword: String?, posts: List<Post>): List<Post> {
        if (keyword.isNullOrBlank()) {
            return posts.sortedByDescending { it.pubDate }
                        .take(10)
        }
        return posts.filter { it.title.contains(keyword, ignoreCase = false) }
                    .sortedByDescending { it.pubDate }
                    .take(10)
    }
}

private fun convertXmlToPost(document: Document): MutableList<Post> {
    val list = mutableListOf<Post>()
    val items = document.getElementsByTagName("item")
    for (i in 0..items.length - 1) {
        val item = items.item(i)
        val element = item as Element
        val title = element.getElementsByTagName("title").item(0)
        val description = element.getElementsByTagName("description").item(0)
        val link = element.getElementsByTagName("link").item(0)
        val pubDate = element.getElementsByTagName("pubDate").item(0)
        val formatter = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH)
        val parsed = formatter.parse(pubDate.textContent)
        val localDateTime = ZonedDateTime.ofInstant(parsed.toInstant(), ZoneId.systemDefault()).toLocalDateTime()


        val post = Post(title.textContent, description.textContent, link.textContent, localDateTime)
        list.add(post)
    }
    return list
}