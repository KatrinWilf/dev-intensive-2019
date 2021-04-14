package ru.skillbranch.devintensive.extensions

fun String.truncate(value:Int=16):String{
   var res:String = ""
   res = this.substring(0,value)
   res = when {this != res -> res.trimEnd()+"..." else ->res }
   return res

}

fun String.stripHtml():String{
   var listS = this.split(">")
   var noTagsString: String = buildString {
      for (partOfString in listS) {
         when {
            partOfString.isNullOrBlank() ->append("")
            partOfString.first() == '<' ->append("")
            else -> append(partOfString.substringBefore("<"))
         }
      }
   }

   var listWord = noTagsString.split(" ")
   var resString: String = buildString {
      for (partOfString in listWord) {
         when {
            partOfString.isNullOrBlank()->append("")
            partOfString.first()=='&' ->append("")
            partOfString.first()=='\'' ->append("")
            else -> append(partOfString+" ")
         }
      }
   }

   return resString.trimEnd()
}