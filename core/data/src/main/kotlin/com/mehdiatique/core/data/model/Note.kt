package com.mehdiatique.core.data.model

/**
 * Domain model representing a note.
 */
data class Note(
    val id: Long,
    val content: String,
    val title: String?,
    val ownerId: Long?,
    val createdAt: Long,
    val updatedAt: Long?
)


/** Idea for later:
 *
 * tags:	List	For categorizing notes (e.g. “meeting”, “idea”)
 * isPinned:	Boolean	For prioritizing or keeping notes at top
 * isArchived:	Boolean	For hiding old notes without deleting
 * colorHex:	String	To visually separate notes (like Google Keep)
 * isSynced:	Boolean	For future sync/cloud storage feature
 * attachments:	List	For URIs to images, files, etc. (future-proof)
 * */