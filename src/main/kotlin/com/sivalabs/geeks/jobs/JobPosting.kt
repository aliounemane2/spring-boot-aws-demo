package com.sivalabs.geeks.jobs

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "job_postings")
class JobPosting (
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "job_id_generator")
    @SequenceGenerator(name = "job_id_generator", sequenceName = "job_id_seq", allocationSize = 1)
    var id : Long?,
    var company: String,
    var title: String,
    var description: String,
    var createdAt: LocalDateTime = LocalDateTime.now(),
    var updatedAt: LocalDateTime?
) {
    constructor() : this(null, "","","", LocalDateTime.now(), null)
}
