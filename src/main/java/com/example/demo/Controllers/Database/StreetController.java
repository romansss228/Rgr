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
public class StreetController {

    private final DataSource dataSource;

    @Autowired
    public StreetController(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @GetMapping("/street")
    public String listStreet(Model model) {
        StringBuilder streetResults = new StringBuilder();
        String sql = "SELECT * FROM street ;";

        try (var connection = dataSource.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (!rs.isBeforeFirst()) {
                streetResults.append("There are no records in the street table.");
            } else {
                while (rs.next()) {
                    int street_id = rs.getInt("street_id");
                    String street_name = rs.getString("street_name");

                    streetResults.append(String.format("ID: %d, Name: %s<br/>", street_id, street_name));
                }
            }
        } catch (SQLException e) {
            streetResults.append("Error while getting list of street: ").append(e.getMessage());
        }
        model.addAttribute("streetResults", streetResults.toString());
        return "street";
    }

    @GetMapping("/street-create")
    public String showCreateStreetForm() {
        return "street-create";
    }

    @PostMapping("/street-create")
    public String createStreet(@RequestParam("street_name") String name,
                                   Model model) {
        String sql = "INSERT INTO street (street_name) VALUES (?)";

        try (var connection = dataSource.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, name);

            pstmt.executeUpdate();
            model.addAttribute("message", "New entry created successfully!");

        } catch (SQLException e) {
            model.addAttribute("message", "Error while creating new entry: " + e.getMessage());
        }

        return "redirect:/street-edit";
    }


}
