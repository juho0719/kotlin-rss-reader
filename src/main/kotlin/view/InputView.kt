package view

object InputView {
    fun inputKeyword(): String? {
        println("검색어를 입력하세요 (없으면 전체 출력): ")
        return readln()
    }
}