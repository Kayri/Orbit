package com.mehdiatique.core.data.model

/**
 * Domain model representing a note.
 */
data class Note(
    val id: Long,
    val content: String,
    val title: String,
    val createdAt: Long,
    val updatedAt: Long? = null,
    val owner: Contact? = null,
    val task: Task? = null,
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