package com.cookbook.app.data.remote

import org.jsoup.Jsoup

data class ParsedRecipe(
    val title: String,
    val description: String,
    val ingredients: List<String>,
    val instructions: List<String>,
    val imageUrls: List<String>,
    val cookingTimeMinutes: Int,
    val servings: Int,
    val category: String
)

class AniaGotujeParser {

    fun isValidUrl(url: String): Boolean =
        Regex("""https?://(www\.)?aniagotuje\.pl/przepis/[\w-]+""").containsMatchIn(url.trim())

    fun parse(url: String): ParsedRecipe {
        val doc = Jsoup.connect(url.trim())
            .userAgent("Mozilla/5.0 (Linux; Android 10) AppleWebKit/537.36")
            .timeout(15_000)
            .get()

        val title = doc.selectFirst("h1[itemprop=name]")?.text().orEmpty()

        val description = doc.selectFirst("meta[itemprop=description]")
            ?.attr("content").orEmpty()

        val ingredients = doc
            .select("span[itemprop=recipeIngredient] span.ingredient-name")
            .map { it.text() }
            .filter { it.isNotBlank() }

        // Each step: combine step name + detailed text paragraphs into one instruction string
        val instructions = doc.select("div.step").mapNotNull { step ->
            val name = step.selectFirst("span[itemprop=name]")?.text()
                ?: return@mapNotNull null
            val detail = step.select("div[itemprop=text] p")
                .joinToString(" ") { it.text() }
                .trim()
            if (detail.isNotBlank()) "$name: $detail" else name
        }

        val totalMinutes = doc.selectFirst("meta[itemprop=totalTime]")
            ?.attr("content")
            ?.let { parseIsoDuration(it) }
            ?: 30

        val servings = doc.selectFirst("meta[itemprop=recipeYield]")
            ?.attr("content")
            ?.let { Regex("""\d+""").find(it)?.value?.toIntOrNull() }
            ?: 2

        val rawCategory = doc.selectFirst("meta[itemprop=recipeCategory]")
            ?.attr("content").orEmpty()

        // Main image + step images from aniagotuje CDN (skip 360-wide thumbnails)
        val mainImage = doc.selectFirst("meta[itemprop=image]")?.attr("content")
        val stepImages = doc
            .select("div.step-text img[src*=cdn.aniagotuje.com/pictures/articles/]")
            .map { it.attr("src") }
            .filter { "-360x" !in it && "-v-360" !in it }

        val allImages = (listOfNotNull(mainImage) + stepImages).distinct()

        return ParsedRecipe(
            title = title,
            description = description,
            ingredients = ingredients,
            instructions = instructions,
            imageUrls = allImages,
            cookingTimeMinutes = totalMinutes,
            servings = servings,
            category = mapCategory(rawCategory)
        )
    }

    // ISO 8601 duration: PT0H20M, PT20M, PT1H30M
    private fun parseIsoDuration(iso: String): Int {
        val h = Regex("""(\d+)H""").find(iso)?.groupValues?.get(1)?.toIntOrNull() ?: 0
        val m = Regex("""(\d+)M""").find(iso)?.groupValues?.get(1)?.toIntOrNull() ?: 0
        return h * 60 + m
    }

    private fun mapCategory(raw: String): String {
        val lower = raw.lowercase()
        return when {
            "śniadanie" in lower || "sniadanie" in lower               -> "Breakfast"
            "deser" in lower || "ciast" in lower || "tort" in lower    -> "Desserts"
            "sałat" in lower || "salat" in lower                       -> "Salads"
            "pasta" in lower || "makaron" in lower                     -> "Pasta"
            "zupa" in lower                                            -> "Soups"
            "grill" in lower || "pieczen" in lower || "pieczeni" in lower -> "Grilled"
            else                                                       -> "Breakfast"
        }
    }
}
