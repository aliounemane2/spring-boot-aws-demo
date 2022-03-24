package com.sivalabs.geeks.jobs

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional

@Transactional
interface JobPostingRepository : JpaRepository<JobPosting, Long>
