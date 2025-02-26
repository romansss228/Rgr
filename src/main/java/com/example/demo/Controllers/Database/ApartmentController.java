package com.example.demo.Controllers.Database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Controller
public class ApartmentController {
    private final DataSource dataSource;

    @Autowired
    public ApartmentController(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @GetMapping("/apartment")
    public String listApartment(Model model) {
        StringBuilder apartmentResults = new StringBuilder();
        String sql = "SELECT * FROM apartment ;";

        try (var connection = dataSource.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (!rs.isBeforeFirst()) {
                apartmentResults.append("There are no records in the apartment table.");
            } else {
                while (rs.next()) {
                    int apartment_id = rs.getInt("apartment_id");
                    int apartment_number = rs.getInt("apartment_number");

                    apartmentResults.append(String.format("ID: %d, Number: %d<br/>", apartment_id, apartment_number));
                }
            }
        } catch (SQLException e) {
            apartmentResults.append("Error while getting list of apartment: ").append(e.getMessage());
        }

        model.addAttribute("apartmentResults", apartmentResults.toString());
        return "apartment";
    }


    @GetMapping("/apartment-create")
    public String showCreateApartmentForm() {
        return "apartment-create";
    }

    @PostMapping("/apartment-create")
    public String createApartment(@RequestParam("apartment_number") int number,
                                   Model model) {
        String sql = "INSERT INTO apartment (apartment_number) VALUES (?)";

        try (var connection = dataSource.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, number);

            pstmt.executeUpdate();
            model.addAttribute("message", "New entry created successfully!");

        } catch (SQLException e) {
            model.addAttribute("message", "Error while creating new entry: " + e.getMessage());
        }

        return "redirect:/apartment-edit";
    }


}
