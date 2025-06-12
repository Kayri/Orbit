package com.mehdiatique.core.navigation_contract

object ContactNav {
    const val ROUTE_DETAIL = "contact_detail"
    const val ARG_CONTACT_ID = "contactId"

    fun detailRoute(contactId: Long?): String {
        return if (contactId != null) "$ROUTE_DETAIL?$ARG_CONTACT_ID=$contactId" else ROUTE_DETAIL
    }

    /**
     * Full route pattern for navigation graph declaration.
     */
    fun routePattern(): String =
        "$ROUTE_DETAIL?$ARG_CONTACT_ID={$ARG_CONTACT_ID}"
}