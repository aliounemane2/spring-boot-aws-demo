package com.sivalabs.geeks.profiles

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "profiles")
class Profile (
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profile_id_generator")
    @SequenceGenerator(name = "profile_id_generator", sequenceName = "profile_id_seq", allocationSize = 1)
    var id : Long?,
    var name: String,
    var email: String,
    var blog: String?,
    var github: String?,
    var twitter: String?,
    var youtube: String?,
    var imageName: String?,
    var createdAt: LocalDateTime = LocalDateTime.now(),
    var updatedAt: LocalDateTime?
) {
    constructor(): this(null, "", "", "", "", "", "", "", LocalDateTime.now(), null)
}
