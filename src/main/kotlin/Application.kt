import service.RssReaderService

fun main() {
    val rssReaderService = RssReaderService()

    val kurly = "https://helloworld.kurly.com/feed.xml";
    val hmg = "https://developers.hyundaimotorgroup.com/blog/rss";

    val kurlyXml = rssReaderService.getXml(kurly)
    val hmgXml = rssReaderService.getXml(hmg)
    val posts = RssReaderService().createPost(listOf(kurlyXml, hmgXml))
    val sortedPosts = rssReaderService.sort(null, posts)

    sortedPosts.forEach { println(it) }
}