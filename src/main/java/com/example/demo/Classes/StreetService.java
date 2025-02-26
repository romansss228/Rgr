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

public class StreetService {
    @Autowired
    private DataSource dataSource;

    public Street getStreetById(int id) {
        String sql = "SELECT street_id, street_name " +
                "FROM street WHERE street_id = ?";

        try (var connection = dataSource.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Street(rs.getInt("street_id"), rs.getString("street_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void updateStreet(int id, String name) {
        String sql = "UPDATE street SET street_name = ? WHERE street_id = ?";

        try (var connection = dataSource.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<Street> getAllStreet() {
        List<Street> streets = new ArrayList<>();
        String sql = "SELECT * FROM street";

        try (var connection = dataSource.getConnection();
             var stmt = connection.createStatement();
             var rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Street street = new Street();
                street .setStreetId(rs.getInt("street_id"));
                street .setStreetName(rs.getString("street_name"));
                streets.add(street );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return streets;
    }
}