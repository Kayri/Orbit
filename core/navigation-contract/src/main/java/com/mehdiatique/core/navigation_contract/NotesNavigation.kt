package com.mehdiatique.core.navigation_contract

object NotesNav {
    const val ROUTE_DETAIL = "note_detail"
    const val ARG_NOTE_ID = "noteId"
    const val ARG_CONTACT_ID = "contactId"

    fun detailRoute(noteId: Long? = null, contactId: Long? = null): String {
        val args = listOfNotNull(
            noteId?.let { "$ARG_NOTE_ID=$it" },
            contactId?.let { "$ARG_CONTACT_ID=$it" }
        )
        return if (args.isEmpty()) ROUTE_DETAIL else "$ROUTE_DETAIL?${args.joinToString("&")}"
    }

    /**
     * Full route pattern for navigation graph declaration.
     */
    fun routePattern(): String =
        "$ROUTE_DETAIL?$ARG_NOTE_ID={$ARG_NOTE_ID}&$ARG_CONTACT_ID={$ARG_CONTACT_ID}"
}