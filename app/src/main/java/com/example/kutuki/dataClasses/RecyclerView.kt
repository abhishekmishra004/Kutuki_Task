package com.example.kutuki.dataClasses

data class categoryData(val name:String, val url:String)

data class videoViewData(val data: HashMap<String,ArrayList<videoDataModel>?>)

data class videoDataModel(val title :String,val description:String,val thumbnailURL:String,val videoURL:String)