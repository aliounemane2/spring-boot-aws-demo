package com.sivalabs.geeks

import com.sivalabs.geeks.domain.Profile
import com.sivalabs.geeks.domain.ProfileRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class DataInitializer(private val repository: ProfileRepository) : CommandLineRunner {

    override fun run(vararg args: String?) {
        val profile1 = Profile(null, "Siva", "siva@gmail.com", "https://sivalabs.in", "sivaprasadreddy", "sivalabs", "SivaLabs", null)
        val profile2 = Profile(null, "Marco Behler", "MarcoBehler@gmail.com", "https://www.marcobehler.com",  "", "MarcoBehler","", null)

        repository.save(profile1)
        repository.save(profile2)
    }
}
