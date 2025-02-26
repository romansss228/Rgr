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
public class HouseService {
    @Autowired
    private DataSource dataSource;

    public  House getHouseById(int id) {
        String sql = "SELECT house_id, house_number " +
                "FROM house WHERE house_id = ?";

        try (var connection = dataSource.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new  House(rs.getInt("house_id"), rs.getInt("house_number"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateHouse(int id, int number) {
        String sql = "UPDATE house SET house_number = ? WHERE house_id = ?";

        try (var connection = dataSource.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, number);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<House> getAllHouse() {
        List<House> houses = new ArrayList<>();
        String sql = "SELECT * FROM house";

        try (var connection = dataSource.getConnection();
             var stmt = connection.createStatement();
             var rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                House house = new House();
                house.setHouseId(rs.getInt("house_id"));
                house.setHouseNum(rs.getInt("house_number"));
                houses.add(house);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return houses;
    }
}