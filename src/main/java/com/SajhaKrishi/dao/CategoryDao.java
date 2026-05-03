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

    // ════════════════════════════
    //  CREATE
    // ════════════════════════════
    public boolean addCategory(CategoryModel category) {
        String query = "INSERT INTO categories (category_name, description, icon, status) " +
                       "VALUES (?, ?, ?, ?)";

        if (conn == null) {
            System.err.println("Database connection is null");
            return false;
        }

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, category.getName());
            pstmt.setString(2, category.getStatus());

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

    // ════════════════════════════
    //  READ — Single by ID
    // ════════════════════════════
    public CategoryModel getCategoryById(int id) {
        String query = "SELECT * FROM categories WHERE id = ?";

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

    // ════════════════════════════
    //  READ — Single by Name
    // ════════════════════════════
    public CategoryModel getCategoryByName(String name) {
        String query = "SELECT * FROM categories WHERE category_name = ? AND status = 'A'";

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

    // ════════════════════════════
    //  READ — All Active
    // ════════════════════════════
    public List<CategoryModel> getAllCategories() {
        List<CategoryModel> categoryList = new ArrayList<>();
        String query = "SELECT * FROM categories WHERE status = 'A' ORDER BY category_name ASC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                categoryList.add(mapResultSetToCategory(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching all categories: " + e.getMessage());
        }
        return categoryList;
    }

    // ════════════════════════════
    //  READ — All including inactive
    //  (Admin only)
    // ════════════════════════════
    public List<CategoryModel> getAllCategoriesAdmin() {
        List<CategoryModel> categoryList = new ArrayList<>();
        String query = "SELECT * FROM categories ORDER BY category_name ASC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                categoryList.add(mapResultSetToCategory(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching all categories for admin: " + e.getMessage());
        }
        return categoryList;
    }

    // ════════════════════════════
    //  CHECK — Name exists
    //  Prevent duplicate categories
    // ════════════════════════════
    public boolean isCategoryNameExists(String name) {
        String query = "SELECT COUNT(*) FROM categories WHERE category_name = ?";

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

    // ════════════════════════════
    //  UPDATE
    // ════════════════════════════
    public boolean updateCategory(CategoryModel category) {
        String query = "UPDATE categories SET category_name=?, description=?, icon=? WHERE id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, category.getName());
            pstmt.setString(2, category.getStatus());
            pstmt.setInt(4,    category.getId());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error updating category: " + e.getMessage());
            return false;
        }
    }

    // ════════════════════════════
    //  DELETE — Soft Delete
    // ════════════════════════════
    public boolean deleteCategory(int id) {
        String query = "UPDATE categories SET status = 'I' WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting category: " + e.getMessage());
            return false;
        }
    }

    // ════════════════════════════
    //  MAPPER
    // ════════════════════════════
    private CategoryModel mapResultSetToCategory(ResultSet rs) throws SQLException {
        CategoryModel category = new CategoryModel();
        category.setId(rs.getInt("id"));
        category.setName(rs.getString("category_name"));
        category.setStatus(rs.getString("status"));
        return category;
    }
}