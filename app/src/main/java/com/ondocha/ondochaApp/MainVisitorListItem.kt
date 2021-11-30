package com.ondocha.ondochaApp

/**
 * 데이터 가져올 때 파이어스토어 문서 내에 있는 모든 필드 가져오기 -> RecordInfoListItem.kt 참고
 *
 */
data class MainVisitorListItem(
    var time: String? = null,
    var Ftem: String? = null,
    var Atem: String? = null,
    var Code: String? = null,
    var date: String? = null)
