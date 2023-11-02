package com.itis.itistasks.model

import com.itis.itistasks.base.holder.BaseModel

 class DateModel(
     day: Int
): BaseModel() {

     private var date: String = ""

     init {
         date = when(day) {
             1 -> "01.10.23"
             10 -> "10.01.23"
             19 -> "19.01.23"
             28 -> "28.01.23"
             37 -> "29.01.23"
             else -> "30.01.23"
         }
     }

     fun getDate(): String {
         return date
     }
}