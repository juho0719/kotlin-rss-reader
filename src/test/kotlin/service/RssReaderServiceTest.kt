package service

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import util.rssReader
import java.io.ByteArrayInputStream
import javax.xml.parsers.DocumentBuilderFactory

class RssReaderServiceTest {
    @Test
    @DisplayName("rss 읽어와 포스트를 생성한다.")
    fun createPost() {
        //given
        val xml = """
            <item>
                <title>test</title>
                <description>desc</description>
                <link>link</link>
            </item>
        """.trimIndent()
        val factory = DocumentBuilderFactory.newInstance()
        val builder =  factory.newDocumentBuilder()
        val inputStream = ByteArrayInputStream(xml.toByteArray(Charsets.UTF_8))
        val document = builder.parse(inputStream)

        //when
        val testPosts = RssReaderService().createPost(listOf(document))

        //then
        assertEquals("test", testPosts.get(0).title)
        assertEquals("desc", testPosts.get(0).description)
        assertEquals("link", testPosts.get(0).link)
    }

}