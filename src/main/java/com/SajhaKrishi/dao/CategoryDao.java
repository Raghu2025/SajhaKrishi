package com.SajhaKrishi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.SajhaKrishi.config.DBConnection;
import com.SajhaKrishi.model.CategoryModel;

public class CategoryDao {

    Connection conn;

    public CategoryDao() {
        try {
            conn = DBConnection.getConnection();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Add Category
     * @param category
     * @return
     */
    public boolean addCategory(CategoryModel category) {
        String query = "INSERT INTO category (name, status) VALUES (?, ?)";

        if (conn == null) {
            System.err.println("Database connection is null");
            return false;
        }

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, category.getName());
            pstmt.setString(2, "A"); // Default status is Active

            int rowsInserted = pstmt.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Category added successfully");
                return true;
            } else {
                System.err.println("Category insertion failed");
                return false;
            }

        } catch (SQLException e) {
            System.err.println("Error while adding category: " + e.getMessage());
            return false;
        }
    }


    /**
     * 
     *
     * @return
     */
    public List<CategoryModel> getAllCategories() {
        List<CategoryModel> categoryList = new ArrayList<>();
        String query = "SELECT * FROM category WHERE status = 'A' ORDER BY name ASC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                categoryList.add(mapResultSetToCategory(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching all category: " + e.getMessage());
        }
        return categoryList;
    }
    

    /**
     * @param id
     * @return
     */
    public CategoryModel getCategoryById(int id) {
        String query = "SELECT * FROM category WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToCategory(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching category by ID: " + e.getMessage());
        }
        return null;
    }

    
    /**
     * 
     * @return
     */
    public List<CategoryModel> getAllCategoriesAdmin() {
        List<CategoryModel> categoryList = new ArrayList<>();
        String query = "SELECT * FROM category ORDER BY name ASC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                categoryList.add(mapResultSetToCategory(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching all category for admin: " + e.getMessage());
        }
        return categoryList;
    }

    /**
     * 
     * @param name
     * @return
     */
    public boolean isCategoryNameExists(String name) {
        String query = "SELECT COUNT(*) FROM category WHERE name = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, name);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error checking category name: " + e.getMessage());
        }
        return false;
    }


    /**
     * 
     */
    public boolean updateCategory(CategoryModel category) {
        String query = "UPDATE category SET name=? WHERE id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, category.getName());
            pstmt.setInt(2, category.getId());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error updating category: " + e.getMessage());
            return false;
        }
    }
    

    /**
     * 
     * @param name
     * @return
     */
    public CategoryModel getCategoryByName(String name) {
        String query = "SELECT * FROM category WHERE name = ? AND status = 'A'";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, name);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToCategory(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching category by name: " + e.getMessage());
        }
        return null;
    }


    /**
     * 
     * @param id
     * @return
     */
    public boolean deleteCategory(int id) {
        String query = "UPDATE category SET status = 'I' WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting category: " + e.getMessage());
            return false;
        }
    }


    /**
     * 
     * @param rs
     * @return
     * @throws SQLException
     */
    private CategoryModel mapResultSetToCategory(ResultSet rs) throws SQLException {
        CategoryModel category = new CategoryModel();
        category.setId(rs.getInt("id"));
        category.setName(rs.getString("name"));
        category.setStatus(rs.getString("status"));
        return category;
    }
}