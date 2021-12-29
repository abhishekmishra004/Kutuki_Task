package com.example.kutuki.Model

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class firstResponse(val code: Int,val response : response)

data class response(val videoCategories: videoCategory)

data class videoCategory(
    @SerializedName("a1545c7c-6dd0-43fd-8da2-f511ad724e60") val data1 : CategoryResponse,
    @SerializedName("19d3f08d-2b84-497b-b1ec-4b02ce645475") val data2 : CategoryResponse,
    @SerializedName("b75ea5c0-7789-4195-a8eb-b5b1ef1de39b") val data3 : CategoryResponse,
    @SerializedName("fdcf981c-103d-4f03-b06a-9e92fadd746d") val data4 : CategoryResponse,
    @SerializedName("412f794f-d334-4a7a-9c72-b2f275823df0") val data5 : CategoryResponse,
    @SerializedName("95742cf6-fbce-4ccf-839c-f896867e8760") val data6 : CategoryResponse,
    @SerializedName("2ce8f052-4b3b-4fb8-8162-54a704db8cfd") val data7 : CategoryResponse,
    @SerializedName("d69a3d2e-39bc-440e-9bbf-01ad73074a79") val data8 : CategoryResponse,
    @SerializedName("4a743b84-09aa-479f-a76c-fcef5c220c48") val data9 : CategoryResponse,
    @SerializedName("c91e99ed-2955-4e41-a1ee-41201cd8b4ad") val data10 : CategoryResponse,
    @SerializedName("3aff72f9-e3c0-4560-9fe0-1e1d77bcfe9b") val data11 : CategoryResponse,
    @SerializedName("56b5894a-3db7-4894-8ffa-4b717b85d81d") val data12 : CategoryResponse,
    @SerializedName("60bd4a42-ebbe-40ca-8aea-e670eb656038") val data13 : CategoryResponse,
    @SerializedName("2ae59c2a-7a7a-4e3e-963f-b2315080d493") val data14 : CategoryResponse,
    @SerializedName("82276eb2-b508-409e-8955-a2c464b1818c") val data15 : CategoryResponse,
)

data class CategoryResponse(val name:String,val image:String)



data class videoResponseModel(val code: Int,val response : videoResponse)

data class videoResponse(val videos: videoList)

data class videoList(
    @SerializedName("fe319f7b-fdd2-41a8-bd96-8adab440fdf6") val data1 : videoData,
    @SerializedName("037a0f89-7c50-4bba-9738-2e775aeab048") val data2 : videoData,
    @SerializedName("70517ec9-052c-4f20-83c1-aa41974508b8") val data3 : videoData,
    @SerializedName("53dd78a7-bd45-4f80-a91c-62f682e8da60") val data4 : videoData,
    @SerializedName("2e793145-d0fc-46a5-a192-d2397b7ca50f") val data5 : videoData,
    @SerializedName("7bd0c8af-b6cf-45d7-934f-d55ca007f856") val data6 : videoData,
    @SerializedName("bc44db16-0c4c-481f-9ecb-1cf9557a1b1a") val data7 : videoData,
    @SerializedName("7ddfc9f2-71d5-4b1d-9aa4-46cf0db089d1") val data8 : videoData,
    @SerializedName("f34a8e64-1f57-4c2d-865a-37b5e89fd006") val data9 : videoData,
    @SerializedName("be651e23-9fd1-428e-8a7f-7c0c8c9fe149") val data10 : videoData,
    @SerializedName("9e59d8cd-9d99-444a-beac-5523862ced50") val data11 : videoData,
    @SerializedName("ea93c38e-b73a-48e1-b889-ae421df62a32") val data12 : videoData,
    @SerializedName("dd871655-0a74-4cf4-af89-3e05fc6d09a7") val data13 : videoData,
    @SerializedName("497ba5e8-8ddf-49d3-8966-025a25de31d6") val data14 : videoData,
    @SerializedName("6b6311de-c760-4158-a591-531ce661c3d0") val data15 : videoData,
)

data class videoData(val title :String,val description:String,val thumbnailURL:String,val videoURL:String,val categories:String)
