package com.example.demo.Classes;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ApartmentService {
    @Autowired
    private DataSource dataSource;

    public Apartment getApartmentById(int id) {
        String sql = "SELECT apartment_id, apartment_number " +
                "FROM apartment WHERE apartment_id = ?";

        try (var connection = dataSource.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Apartment(rs.getInt("apartment_id"), rs.getInt("apartment_number"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateApartment(int id, int number) {
        String sql = "UPDATE apartment SET apartment_number = ? WHERE apartment_id = ?";

        try (var connection = dataSource.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, number);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Apartment> getAllApartment() {
        List<Apartment> apartments = new ArrayList<>();
        String sql = "SELECT * FROM apartment";

        try (var connection = dataSource.getConnection();
             var stmt = connection.createStatement();
             var rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Apartment apartment = new Apartment();
                apartment.setApartmentId(rs.getInt("apartment_id"));
                apartment.setApartmentNum(rs.getInt("apartment_number"));
                apartments.add(apartment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return apartments;
    }
}