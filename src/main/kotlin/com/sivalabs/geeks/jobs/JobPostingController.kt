package com.sivalabs.geeks.jobs

import com.sivalabs.geeks.events.NewJobPostedEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
class JobPostingController(
    private val repository: JobPostingRepository,
    private val applicationEventPublisher: ApplicationEventPublisher
) {

    @GetMapping("/jobs")
    fun getAll(model: Model): String {
        val sort = Sort.by(Sort.Direction.DESC,"createdAt")
        model.addAttribute("jobs", repository.findAll(sort))
        return "jobs"
    }

    @GetMapping("/jobs/new")
    fun showNewJobPostingForm(model: Model): String {
        model.addAttribute("jobPosting", JobPosting())
        return "newJobPosting"
    }

    @PostMapping("/jobs")
    fun createJobPosting(@ModelAttribute("jobPosting") jobPosting: JobPosting,
                   bindingResult: BindingResult,
                   redirectAttributes: RedirectAttributes): String {
        if(bindingResult.hasErrors()) {
            return "newJobPosting"
        }
        repository.save(jobPosting)
        applicationEventPublisher.publishEvent(NewJobPostedEvent(jobPosting))
        redirectAttributes.addFlashAttribute("msg", "JobPosting saved successfully")
        return "redirect:/jobs"
    }
}
