package com.example.demo.Controllers.Database;

import com.example.demo.Classes.Apartment;
import com.example.demo.Classes.ApartmentService;
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
@RequestMapping("/apartment-edit")
public class ApartmentEditController {

    @Autowired
    private ApartmentService apartmentService;

    @GetMapping
    public String showApartmentEditPage(Model model) {
        List<Apartment> apartments = apartmentService.getAllApartment();
        apartments.sort((s1, s2) -> Integer.compare(s1.getApartmentId(), s2.getApartmentId()));
        model.addAttribute("apartments", apartments);
        return "apartment-edit";
    }

    @PostMapping("/update")
    public String updateApartment(@RequestParam("apartment_id") int id,
                                   @RequestParam("apartment_number") int number,
                                   RedirectAttributes redirectAttributes) {
        try {
            apartmentService.updateApartment(id, number);
            redirectAttributes.addFlashAttribute("message", "Apartment №" + id +" updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "Failed to update apartment: " + e.getMessage());
        }
        return "redirect:/apartment-edit";
    }
}
