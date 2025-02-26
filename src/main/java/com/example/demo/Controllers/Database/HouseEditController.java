package com.example.demo.Controllers.Database;

import com.example.demo.Classes.House;
import com.example.demo.Classes.HouseService;
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
@RequestMapping("/house-edit")
public class HouseEditController {

    @Autowired
    private HouseService houseService;

    @GetMapping
    public String showHouseEditPage(Model model) {
        List<House> houses = houseService.getAllHouse();
        houses.sort((s1, s2) -> Integer.compare(s1.getHouseId(), s2.getHouseId()));
        model.addAttribute("houses", houses);
        return "house-edit";
    }

    @PostMapping("/update")
    public String updateDepartment(@RequestParam("house_id") int id,
                                   @RequestParam("house_number") int number,
                                   RedirectAttributes redirectAttributes) {
        try {
            houseService.updateHouse(id, number);
            redirectAttributes.addFlashAttribute("message", "House №" + id +" updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "Failed to update department: " + e.getMessage());
        }
        return "redirect:/house-edit";
    }
}