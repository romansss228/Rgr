package com.example.demo.Controllers.Database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
@Controller
public class ApartmentDeleteController {
    private final DataSource dataSource;

    @Autowired
    public ApartmentDeleteController(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @GetMapping("/apartment-delete")
    public String getApartmentDeleteForm() {
        return "apartment-delete";
    }


    @PostMapping("/apartment-delete")
    public String deleteApartment(@RequestParam("apartment_id") int id, Model model) {
        String sql = "DELETE FROM apartment WHERE apartment_id = ?";

        try (var connection = dataSource.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                model.addAttribute("message", "Entry deleted successfully!");
            } else {
                model.addAttribute("message", "Entry not found or already deleted.");
            }
        } catch (SQLException e) {
            model.addAttribute("message", "Error while deleting entry: " + e.getMessage());
        }

        return "redirect:/apartment-edit";
    }
}

