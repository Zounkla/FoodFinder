package com.example.foodfinder.data.model.dto

data class OverpassTagsDto(
    val addr_city: String?,
    val addr_country: String?,
    val addr_housenumber: String?,
    val addr_postcode: String?,
    val addr_street: String?,
    val amenity: String?,
    val brand: String?,
    val brand_wikidata: String?,
    val brand_wikipedia: String?,
    val cuisine: String?,
    val delivery: String?,
    val drive_through: String?,
    val name: String?,
    val opening_hours: String?,
    val operator: String?,
    val outdoor_seating: String?,
    val payment_cash: String?,
    val payment_cb: String?,
    val payment_credit_cards: String?,
    val phone: String?,
    val ref_FR_SIRET: String?,
    val smoking: String?,
    val takeaway: String?,
    val website: String?,
    val wheelchair: String?,
    val wifi: String?
) {
}