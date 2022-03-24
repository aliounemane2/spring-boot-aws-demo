package com.sivalabs.geeks.profiles

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional

@Transactional
interface ProfileRepository : JpaRepository<Profile, Long> {
    @Query("UPDATE Profile p set p.imageName=?2 where p.id=?1")
    @Modifying
    fun updateImageName(id: Long, filename: String)
}
