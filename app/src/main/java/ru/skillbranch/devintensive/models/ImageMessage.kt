package ru.skillbranch.devintensive.models

import ru.skillbranch.devintensive.extensions.humanizeDiff
import java.util.*

class ImageMessage (
    id:String,
    form:User?,
    chat: Chat,
    isIncoming: Boolean = false,
    date: Date = Date(),
    var image:String?
):BaseMessage(id, form, chat, isIncoming, date) {
    override fun formatMessage(): String = "id:$id ${form?.firstName} " +
            "${if(isIncoming) "получил" else "отправил"} изображение \"$image\" ${date.humanizeDiff()}"

}