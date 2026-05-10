package com.SajhaKrishi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.SajhaKrishi.config.DBConnection;
import com.SajhaKrishi.model.EquipmentModel;

public class EquipmentDao {

    Connection conn;

    public EquipmentDao() {
        try {
            conn = DBConnection.getConnection();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // ════════════════════════════
    //  CREATE
    // ════════════════════════════
    public boolean addEquipment(EquipmentModel equipment) {
        String query = "INSERT INTO equipment (name, category_id, description, brand, manufacture_year, " +
                       "price_per_day, price_per_hour, deposit_amount, availability_status, " +
                       "available_from, available_to, district, municipality, address, " +
                       "condition_, specifications, fuel_type, image_path, owner_id, status) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        if (conn == null) {
            System.err.println("Database connection is null");
            return false;
        }

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1,  equipment.getName());
            pstmt.setString(2,  equipment.getCategoryId());
            pstmt.setString(3,  equipment.getDescription());
            pstmt.setString(4,  equipment.getBrand());
            pstmt.setInt(5,     equipment.getManufactureYear());
            pstmt.setDouble(6,  equipment.getPricePerDay());
            pstmt.setDouble(7,  equipment.getPricePerHour());
            pstmt.setDouble(8,  equipment.getDepositAmount());
            pstmt.setString(9,  equipment.getAvailabilityStatus());
            pstmt.setString(10, equipment.getAvailableFrom());
            pstmt.setString(11, equipment.getAvailableTo());
            pstmt.setString(12, equipment.getDistrict());
            pstmt.setString(13, equipment.getMunicipality());
            pstmt.setString(14, equipment.getAddress());
            pstmt.setString(15, equipment.getCondition());
            pstmt.setString(16, equipment.getSpecifications());
            pstmt.setString(17, equipment.getFuelType());
            pstmt.setString(18, equipment.getImagePath());
            pstmt.setInt(19,    equipment.getOwnerId());
            pstmt.setString(20, equipment.getStatus());

            int rowsInserted = pstmt.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Equipment added successfully");
                return true;
            } else {
                System.err.println("Equipment insertion failed");
                return false;
            }

        } catch (SQLException e) {
            System.err.println("Error while adding equipment: " + e.getMessage());
            return false;
        }
    }

    // ════════════════════════════
    //  READ — Single
    // ════════════════════════════
    public EquipmentModel getEquipmentById(int id) {
        String query = "SELECT * FROM equipment WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToEquipment(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching equipment by ID: " + e.getMessage());
        }
        return null;
    }

    // ════════════════════════════
    //  READ — All
    // ════════════════════════════
    public List<EquipmentModel> getAllEquipment() {
        List<EquipmentModel> equipmentList = new ArrayList<>();
        String query = "SELECT e.*, c.category_name " +
                "FROM equipment e " +
                "LEFT JOIN categories c ON e.category_id = c.id " +
                "WHERE e.status = 'A'";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                equipmentList.add(mapResultSetToEquipment(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching all equipment: " + e.getMessage());
        }
        return equipmentList;
    }

    // ════════════════════════════
    //  READ — By Owner
    // ════════════════════════════
    public List<EquipmentModel> getEquipmentByOwner(int ownerId) {
        List<EquipmentModel> equipmentList = new ArrayList<>();
        String query = "SELECT * FROM equipment WHERE owner_id = ? AND status = 'A'";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, ownerId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    equipmentList.add(mapResultSetToEquipment(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching equipment by owner: " + e.getMessage());
        }
        return equipmentList;
    }

    // ════════════════════════════
    //  READ — By Category
    // ════════════════════════════
    public List<EquipmentModel> getEquipmentByCategory(String category) {
        List<EquipmentModel> equipmentList = new ArrayList<>();
        String query = "SELECT * FROM equipment WHERE category = ? AND status = 'A' AND availability_status = 'Available'";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, category);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    equipmentList.add(mapResultSetToEquipment(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching equipment by category: " + e.getMessage());
        }
        return equipmentList;
    }

    // ════════════════════════════
    //  READ — By District
    // ════════════════════════════
    public List<EquipmentModel> getEquipmentByDistrict(String district) {
        List<EquipmentModel> equipmentList = new ArrayList<>();
        String query = "SELECT * FROM equipment WHERE district = ? AND status = 'A' AND availability_status = 'Available'";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, district);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    equipmentList.add(mapResultSetToEquipment(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching equipment by district: " + e.getMessage());
        }
        return equipmentList;
    }

    // ════════════════════════════
    //  READ — Search
    // ════════════════════════════
    public List<EquipmentModel> searchEquipment(String keyword) {
        List<EquipmentModel> equipmentList = new ArrayList<>();
        String query = "SELECT * FROM equipment WHERE status = 'A' AND " +
                       "(equipment_name LIKE ? OR category LIKE ? OR district LIKE ? OR brand LIKE ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            String like = "%" + keyword + "%";
            pstmt.setString(1, like);
            pstmt.setString(2, like);
            pstmt.setString(3, like);
            pstmt.setString(4, like);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    equipmentList.add(mapResultSetToEquipment(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error searching equipment: " + e.getMessage());
        }
        return equipmentList;
    }

    // ════════════════════════════
    //  UPDATE
    // ════════════════════════════
    public boolean updateEquipment(EquipmentModel equipment) {
        String query = "UPDATE equipment SET equipment_name=?, category=?, description=?, brand=?, " +
                       "manufacture_year=?, price_per_day=?, price_per_hour=?, deposit_amount=?, " +
                       "availability_status=?, available_from=?, available_to=?, district=?, " +
                       "municipality=?, address=?, condition_status=?, specifications=?, " +
                       "fuel_type=?, image_path=? WHERE id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1,  equipment.getName());
            pstmt.setString(2,  equipment.getCategoryId());
            pstmt.setString(3,  equipment.getDescription());
            pstmt.setString(4,  equipment.getBrand());
            pstmt.setInt(5,     equipment.getManufactureYear());
            pstmt.setDouble(6,  equipment.getPricePerDay());
            pstmt.setDouble(7,  equipment.getPricePerHour());
            pstmt.setDouble(8,  equipment.getDepositAmount());
            pstmt.setString(9,  equipment.getAvailabilityStatus());
            pstmt.setString(10, equipment.getAvailableFrom());
            pstmt.setString(11, equipment.getAvailableTo());
            pstmt.setString(12, equipment.getDistrict());
            pstmt.setString(13, equipment.getMunicipality());
            pstmt.setString(14, equipment.getAddress());
            pstmt.setString(15, equipment.getCondition());
            pstmt.setString(16, equipment.getSpecifications());
            pstmt.setString(17, equipment.getFuelType());
            pstmt.setString(18, equipment.getImagePath());
            pstmt.setInt(19,    equipment.getId());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error updating equipment: " + e.getMessage());
            return false;
        }
    }

    // ════════════════════════════
    //  UPDATE — Availability Only
    // ════════════════════════════
    public boolean updateAvailabilityStatus(int id, String status) {
        String query = "UPDATE equipment SET availability_status = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, status);
            pstmt.setInt(2, id);

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error updating availability: " + e.getMessage());
            return false;
        }
    }

    // ════════════════════════════
    //  DELETE — Soft Delete
    // ════════════════════════════
    public boolean deleteEquipment(int id) {
        String query = "UPDATE equipment SET status = 'I' WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting equipment: " + e.getMessage());
            return false;
        }
    }

    // ════════════════════════════
    //  MAPPER
    // ════════════════════════════
    private EquipmentModel mapResultSetToEquipment(ResultSet rs) throws SQLException {
        EquipmentModel equipment = new EquipmentModel(rs.getInt("id"));
        equipment.setName(rs.getString("name"));
        equipment.setCategoryId(rs.getString("category_id"));
//        equipment.setCategoryName(rs.getString("category_name"));
        equipment.setDescription(rs.getString("description"));
        equipment.setBrand(rs.getString("brand"));
        equipment.setManufactureYear(rs.getInt("manufacture_year"));
        equipment.setPricePerDay(rs.getDouble("price_per_day"));
        equipment.setPricePerHour(rs.getDouble("price_per_hour"));
        equipment.setDepositAmount(rs.getDouble("deposit_amount"));
        equipment.setAvailabilityStatus(rs.getString("availability_status"));
        equipment.setAvailableFrom(rs.getString("available_from"));
        equipment.setAvailableTo(rs.getString("available_to"));
        equipment.setDistrict(rs.getString("district"));
        equipment.setMunicipality(rs.getString("municipality"));
        equipment.setAddress(rs.getString("address"));
        equipment.setCondition(rs.getString("condition_"));
        equipment.setSpecifications(rs.getString("specifications"));
        equipment.setFuelType(rs.getString("fuel_type"));
        equipment.setImagePath(rs.getString("image_path"));
        equipment.setOwnerId(rs.getInt("owner_id"));
        equipment.setStatus(rs.getString("status"));
        return equipment;
    }
}