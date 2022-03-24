package com.sivalabs.geeks.profiles

import org.springframework.core.io.ClassPathResource
import org.springframework.data.domain.Sort
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.util.StreamUtils
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import javax.servlet.http.HttpServletResponse

@Controller
class ProfileController(
    private val repository: ProfileRepository,
    private val imageService: ImageService
) {

    @GetMapping("")
    fun index(): String {
        return "redirect:/profiles"
    }

    @GetMapping("/profiles")
    fun getAll(model: Model): String {
        val sort = Sort.by("name")
        model.addAttribute("profiles", repository.findAll(sort))
        return "profiles"
    }

    @GetMapping("/profiles/new")
    fun createProfileForm(model: Model): String {
        model.addAttribute("profile", Profile())
        return "newProfile"
    }

    @PostMapping("/profiles")
    fun updateById(
        @ModelAttribute("profile") profile: Profile,
        bindingResult: BindingResult,
        redirectAttributes: RedirectAttributes
    ): String {
        if (bindingResult.hasErrors()) {
            return "newProfile"
        }
        val savedProfile = repository.save(profile)
        redirectAttributes.addFlashAttribute("msg", "Profile saved successfully")
        return "redirect:/profiles/${savedProfile.id}"
    }

    @GetMapping("/profiles/{id}")
    fun getById(@PathVariable id: Long, model: Model): String {
        val profile = repository.findById(id)
        if (profile.isEmpty) {
            return "404"
        }
        model.addAttribute("profile", profile.get())
        return "profile"
    }

    @PutMapping("/profiles/{id}")
    fun updateById(
        @PathVariable id: Long,
        @ModelAttribute("profile") profile: Profile,
        bindingResult: BindingResult,
        redirectAttributes: RedirectAttributes
    ): String {
        if (bindingResult.hasErrors()) {
            return "profile"
        }
        val existingProfile = repository.findById(id).orElseThrow()
        existingProfile.name = profile.name
        existingProfile.email = profile.email
        existingProfile.blog = profile.blog
        existingProfile.github = profile.github
        existingProfile.twitter = profile.twitter
        existingProfile.youtube = profile.youtube
        repository.save(existingProfile)
        redirectAttributes.addFlashAttribute("msg", "Profile updated successfully")
        return "redirect:/profiles/$id"
    }

    @GetMapping("/profiles/{id}/photo")
    fun getProfilePic(@PathVariable id: Long, response: HttpServletResponse) {
        val profile = repository.findById(id).orElseThrow()
        val inputStream = if (profile.imageName == null) {
            val imgFile = ClassPathResource("/static/images/no-photo.png")
            imgFile.inputStream
        } else {
            imageService.download(profile.imageName!!)
        }
        response.contentType = MediaType.IMAGE_PNG_VALUE
        StreamUtils.copy(inputStream!!, response.outputStream)
    }

    @PostMapping("/profiles/{id}/photo")
    fun uploadProfilePic(
        @PathVariable id: Long,
        @RequestParam("file") multipartFile: MultipartFile
    ): String {
        var filename = multipartFile.originalFilename!!
        val extn = filename.substring(filename.lastIndexOf("."))
        filename = "${id}${extn}"
        imageService.upload(filename, multipartFile.inputStream)
        repository.updateImageName(id, filename)
        return "redirect:/profiles/$id"
    }
}
