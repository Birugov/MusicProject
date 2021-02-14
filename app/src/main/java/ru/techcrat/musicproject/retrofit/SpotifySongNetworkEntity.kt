package ru.techcrat.musicproject.retrofit

import com.google.gson.annotations.SerializedName

data class SpotifySongNetworkEntity(
    @SerializedName("items")
    val items: List<Item>
)

data class Item(
    @SerializedName("added_at")
    val added_at: String,
    @SerializedName("track")
    val track: Track
)

data class Track(
    @SerializedName("album")
    val album: Album,
    @SerializedName("artists")
    val artists: List<ArtistX>,
    @SerializedName("available_markets")
    val available_markets: List<String>,
    @SerializedName("disc_number")
    val disc_number: Int,
    @SerializedName("duration_ms")
    val duration_ms: Int,
    @SerializedName("explicit")
    val explicit: Boolean,
    @SerializedName("external_ids")
    val external_ids: ExternalIds,
    @SerializedName("external_urls")
    val external_urls: ExternalUrlsXXX,
    @SerializedName("href")
    val href: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("is_local")
    val is_local: Boolean,
    @SerializedName("name")
    val name: String,
    @SerializedName("popularity")
    val popularity: Int,
    @SerializedName("preview_url")
    val preview_url: String,
    @SerializedName("track_number")
    val track_number: Int,
    @SerializedName("type")
    val type: String,
    @SerializedName("uri")
    val uri: String
)


data class Album(
    @SerializedName("album_type")
    val album_type: String,
    @SerializedName("artists")
    val artists: List<Artist>,
    @SerializedName("available_markets")
    val available_markets: List<String>,
    @SerializedName("external_urls")
    val external_urls: ExternalUrlsX,
    @SerializedName("href")
    val href: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("images")
    val images: List<Image>,
    @SerializedName("name")
    val name: String,
    @SerializedName("release_date")
    val release_date: String,
    @SerializedName("release_date_precision")
    val release_date_precision: String,
    @SerializedName("total_tracks")
    val total_tracks: Int,
    @SerializedName("type")
    val type: String,
    @SerializedName("uri")
    val uri: String
)

data class ArtistX(
    @SerializedName("external_urls")
    val external_urls: ExternalUrlsXX,
    @SerializedName("href")
    val href: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("uri")
    val uri: String
)

data class ExternalIds(
    @SerializedName("isrc")
    val isrc: String
)

data class ExternalUrlsXXX(
    @SerializedName("spotify")
    val spotify: String
)

data class Artist(
    @SerializedName("external_urls")
    val external_urls: ExternalUrls,
    @SerializedName("href")
    val href: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("uri")
    val uri: String
)

data class ExternalUrlsX(
    @SerializedName("spotify")
    val spotify: String
)

data class Image(
    @SerializedName("height")
    val height: Int,
    @SerializedName("url")
    val url: String,
    @SerializedName("width")
    val width: Int
)

data class ExternalUrls(
    @SerializedName("spotify")
    val spotify: String
)

data class ExternalUrlsXX(
    @SerializedName("spotify")
    val spotify: String
)
