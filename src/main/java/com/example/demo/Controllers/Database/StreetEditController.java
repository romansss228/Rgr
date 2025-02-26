package com.example.demo.Controllers.Database;

import com.example.demo.Classes.Street;
import com.example.demo.Classes.StreetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
@Controller
@RequestMapping("/street-edit")
public class StreetEditController {

    @Autowired
    private StreetService streetService;

    @GetMapping
    public String showStreetEditPage(Model model) {
        List<Street> streets = streetService.getAllStreet();
        streets.sort((s1, s2) -> Integer.compare(s1.getStreetId(), s2.getStreetId()));
        model.addAttribute("streets", streets);
        return "street-edit";
    }

    @PostMapping("/update")
    public String updateDepartment(@RequestParam("street_id") int id,
                                   @RequestParam("street_name") String name,
                                   RedirectAttributes redirectAttributes) {
        try {
            streetService.updateStreet(id, name);
            redirectAttributes.addFlashAttribute("message", "Street №" + id +" updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "Failed to update street: " + e.getMessage());
        }
        return "redirect:/street-edit";
    }
}
