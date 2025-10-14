package com.app.examenapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class PaisDto(
    @SerializedName("name") val name: NameDto,
    @SerializedName("tld") val tld: List<String>?,
    @SerializedName("cca2") val cca2: String?,
    @SerializedName("ccn3") val ccn3: String?,
    @SerializedName("cioc") val cioc: String?,
    @SerializedName("independent") val independent: Boolean?,
    @SerializedName("status") val status: String?,
    @SerializedName("unMember") val unMember: Boolean?,
    @SerializedName("currencies") val currencies: Map<String, CurrencyDto>?,
    @SerializedName("idd") val idd: IddDto?,
    @SerializedName("capital") val capital: List<String>?,
    @SerializedName("altSpellings") val altSpellings: List<String>?,
    @SerializedName("region") val region: String?,
    @SerializedName("subregion") val subregion: String?,
    @SerializedName("languages") val languages: Map<String, String>?,
    @SerializedName("latlng") val latlng: List<Double>?,
    @SerializedName("landlocked") val landlocked: Boolean?,
    @SerializedName("borders") val borders: List<String>?,
    @SerializedName("area") val area: Double?,
    @SerializedName("demonyms") val demonyms: Map<String, DemonymDto>?,
    @SerializedName("translations") val translations: Map<String, TranslationDto>?,
    @SerializedName("flag") val flag: String?,
    @SerializedName("maps") val maps: MapsDto?,
    @SerializedName("population") val population: Long?,
    @SerializedName("gini") val gini: Map<String, Double>?,
    @SerializedName("fifa") val fifa: String?,
    @SerializedName("car") val car: CarDto?,
    @SerializedName("timezones") val timezones: List<String>?,
    @SerializedName("continents") val continents: List<String>?,
    @SerializedName("flags") val flags: ImageDto?,
    @SerializedName("coatOfArms") val coatOfArms: ImageDto?,
    @SerializedName("startOfWeek") val startOfWeek: String?,
    @SerializedName("capitalInfo") val capitalInfo: CapitalInfoDto?,
    @SerializedName("postalCode") val postalCode: PostalCodeDto?
) {
    data class NameDto(
        @SerializedName("common") val common: String,
        @SerializedName("official") val official: String,
        @SerializedName("nativeName") val nativeName: Map<String, NativeNameDto>?
    ) {
        data class NativeNameDto(
            @SerializedName("official") val official: String,
            @SerializedName("common") val common: String
        )
    }

    data class CurrencyDto(
        @SerializedName("symbol") val symbol: String?,
        @SerializedName("name") val name: String?
    )

    data class IddDto(
        @SerializedName("root") val root: String?,
        @SerializedName("suffixes") val suffixes: List<String>?
    )

    data class DemonymDto(
        @SerializedName("f") val female: String?,
        @SerializedName("m") val male: String?
    )

    data class TranslationDto(
        @SerializedName("official") val official: String?,
        @SerializedName("common") val common: String?
    )

    data class MapsDto(
        @SerializedName("googleMaps") val googleMaps: String?,
        @SerializedName("openStreetMaps") val openStreetMaps: String?
    )

    data class CarDto(
        @SerializedName("signs") val signs: List<String>?,
        @SerializedName("side") val side: String?
    )

    data class ImageDto(
        @SerializedName("png") val png: String?,
        @SerializedName("svg") val svg: String?,
        @SerializedName("alt") val alt: String?
    )

    data class CapitalInfoDto(
        @SerializedName("latlng") val latlng: List<Double>?
    )

    data class PostalCodeDto(
        @SerializedName("format") val format: String?,
        @SerializedName("regex") val regex: String?
    )
}
