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
public class HouseController {

    private final DataSource dataSource;

    @Autowired
    public HouseController(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @GetMapping("/house")
    public String listHouse(Model model) {
        StringBuilder houseResults = new StringBuilder();
        String sql = "SELECT * FROM house ;";

        try (var connection = dataSource.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (!rs.isBeforeFirst()) {
                houseResults.append("There are no records in the house table.");
            } else {
                while (rs.next()) {
                    int house_id = rs.getInt("house_id");
                    int house_number = rs.getInt("house_number");

                    houseResults.append(String.format("ID: %d, Name: %d<br/>", house_id, house_number));
                }
            }
        } catch (SQLException e) {
            houseResults.append("Error while getting list of house: ").append(e.getMessage());
        }

        model.addAttribute("houseResults", houseResults.toString());
        return "house";
    }


    @GetMapping("/house-create")
    public String showCreateHouseForm() {
        return "house-create";
    }

    @PostMapping("/house-create")
    public String createHouse(@RequestParam("house_number") int number,
                                   Model model) {
        String sql = "INSERT INTO house (house_number) VALUES (?)";

        try (var connection = dataSource.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, number);

            pstmt.executeUpdate();
            model.addAttribute("message", "New entry created successfully!");

        } catch (SQLException e) {
            model.addAttribute("message", "Error while creating new entry: " + e.getMessage());
        }

        return "redirect:/house-edit";
    }


}
