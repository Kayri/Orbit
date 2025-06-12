package com.mehdiatique.core.navigation_contract

object ActionNav {
    const val ROUTE_DETAIL = "action_detail"
    const val ARG_ACTION_ID = "actionId"
    const val ARG_CONTACT_ID = "contactId"

    fun detailRoute(actionId: Long? = null, contactId: Long? = null): String {
        val args = listOfNotNull(
            actionId?.let { "$ARG_ACTION_ID=$it" },
            contactId?.let { "$ARG_CONTACT_ID=$it" }
        )
        return if (args.isEmpty()) ROUTE_DETAIL else "$ROUTE_DETAIL?${args.joinToString("&")}"
    }

    /**
     * Full route pattern for navigation graph declaration.
     */
    fun routePattern(): String =
        "$ROUTE_DETAIL?$ARG_ACTION_ID={$ARG_ACTION_ID}&$ARG_CONTACT_ID={$ARG_CONTACT_ID}"
}