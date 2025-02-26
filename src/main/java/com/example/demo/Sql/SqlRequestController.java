package com.example.demo.Sql;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class SqlRequestController {

    private final DataSource dataSource;

    public SqlRequestController(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @PostMapping("/execute-sql")
    public String executeSqlQuery(@RequestParam("sqlQuery") String sqlQuery, Model model) {
        if (!isSafeQuery(sqlQuery)) {
            model.addAttribute("message", "Error: Invalid SQL query.");
            return "result";
        }

        try (var connection = dataSource.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sqlQuery)) {

            if (sqlQuery.trim().toLowerCase().startsWith("select")) {
                ResultSet rs = pstmt.executeQuery();
                List<Map<String, Object>> results = new ArrayList<>();

                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();

                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    for (int i = 1; i <= columnCount; i++) {
                        row.put(metaData.getColumnName(i), rs.getObject(i));
                    }
                    results.add(row);
                }

                model.addAttribute("results", results);
                return "result";
            } else {
                model.addAttribute("message", "Error: Only SELECT queries are allowed.");
                return "result";
            }

        } catch (SQLException e) {
            model.addAttribute("message", "Error executing SQL query: " + e.getMessage());
            return "result";
        }
    }


    private boolean isSafeQuery(String sqlQuery) {
        String lowerQuery = sqlQuery.trim().toLowerCase();

        if (!lowerQuery.startsWith("select")) {
            return false;
        }
        String[] forbiddenPatterns = {
                "--", ";", "drop", "delete", "insert", "update", "alter", "exec", "union", "or"
        };
        for (String pattern : forbiddenPatterns) {
            if (lowerQuery.contains(pattern)) {
                return false;
            }
        }
        return true;
    }
}