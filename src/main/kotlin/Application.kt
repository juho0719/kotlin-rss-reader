import service.RssReaderService
import view.InputView
import view.OutputView

fun main() {
    val rssReaderService = RssReaderService()

    val kurly = "https://helloworld.kurly.com/feed.xml";
    val hmg = "https://developers.hyundaimotorgroup.com/blog/rss";

    val kurlyXml = rssReaderService.getXml(kurly)
    val hmgXml = rssReaderService.getXml(hmg)
    val posts = RssReaderService().createPost(listOf(kurlyXml, hmgXml))

    while (true) {
        val keyword = InputView.inputKeyword()
        val sortedPosts = RssReaderService().sort(keyword, posts)
        OutputView.result(sortedPosts)
    }
}