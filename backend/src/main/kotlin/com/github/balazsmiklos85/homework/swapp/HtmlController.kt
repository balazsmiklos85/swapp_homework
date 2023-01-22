package com.github.balazsmiklos85.homework.swapp

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HtmlController {
    @GetMapping("/")
    fun redirect(model: Model): String { //TODO it should redirect to the nodejs host
        model["title"] = "Generate invoice"
        return "generate_invoice"
    }
}
