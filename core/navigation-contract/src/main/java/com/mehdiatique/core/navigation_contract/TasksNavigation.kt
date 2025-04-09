package com.mehdiatique.core.navigation_contract

object TasksNav {
    const val ROUTE_DETAIL = "task_detail"
    const val ARG_TASK_ID = "taskId"
    const val ARG_CONTACT_ID = "contactId"

    fun detailRoute(taskId: Long? = null, contactId: Long? = null): String {
        val args = listOfNotNull(
            taskId?.let { "$ARG_TASK_ID=$it" },
            contactId?.let { "$ARG_CONTACT_ID=$it" }
        )
        return if (args.isEmpty()) ROUTE_DETAIL else "$ROUTE_DETAIL?${args.joinToString("&")}"
    }

    /**
     * Full route pattern for navigation graph declaration.
     */
    fun routePattern(): String =
        "$ROUTE_DETAIL?$ARG_TASK_ID={$ARG_TASK_ID}&$ARG_CONTACT_ID={$ARG_CONTACT_ID}"
}