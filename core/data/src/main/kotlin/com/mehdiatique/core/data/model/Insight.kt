package com.mehdiatique.core.data.model


/**
 * Domain model representing an insight (memory/note).
 */
data class Insight(
    val id: Long,
    val content: String = "",
    val createdAt: Long = 0,
    val updatedAt: Long? = null,
    val owner: Contact? = null,
    val actionId: Long? = null
)


/** Idea for later:
 *
 * tags:	List	For categorizing insight (e.g. “meeting”, “idea”)
 * isPinned:	Boolean	For prioritizing or keeping insight at top
 * colorHex:	String	To visually separate insights (like Google Keep)
 * isSynced:	Boolean	For future sync/cloud storage feature
 * attachments:	List	For URIs to images, files, etc. (future-proof)
 * */