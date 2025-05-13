package com.mehdiatique.core.navigation_contract

object InsightNav {
    const val ROUTE_DETAIL = "insight_detail"
    const val ARG_INSIGHT_ID = "insightId"
    const val ARG_CONTACT_ID = "contactId"

    fun detailRoute(insightId: Long? = null, contactId: Long? = null): String {
        val args = listOfNotNull(
            insightId?.let { "$ARG_INSIGHT_ID=$it" },
            contactId?.let { "$ARG_CONTACT_ID=$it" }
        )
        return if (args.isEmpty()) ROUTE_DETAIL else "$ROUTE_DETAIL?${args.joinToString("&")}"
    }

    /**
     * Full route pattern for navigation graph declaration.
     */
    fun routePattern(): String =
        "$ROUTE_DETAIL?$ARG_INSIGHT_ID={$ARG_INSIGHT_ID}&$ARG_CONTACT_ID={$ARG_CONTACT_ID}"
}