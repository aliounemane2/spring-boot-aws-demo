package com.sivalabs.geeks.events

import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class NewJobPostedEventHandler {

    @EventListener
    fun handleContextStart(event: NewJobPostedEvent) {
        println("Handling NewJobPostedEvent: ${event.jobPosting.title}")
    }
}
