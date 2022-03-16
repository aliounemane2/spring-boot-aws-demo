package com.sivalabs.geeks.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Profile (
    @Id @GeneratedValue
    var id : Long?,
    var name: String,
    var email: String,
    var blog: String,
    var github: String,
    var twitter: String,
    var youtube: String,
    var imageName: String?
)
