package com.SajhaKrishi.controller;

import java.io.IOException;
import java.io.File;
import java.nio.file.Paths;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import com.SajhaKrishi.constant.ApiConstant;
import com.SajhaKrishi.constant.DropdownConstant;
import com.SajhaKrishi.constant.PageConstant;
import com.SajhaKrishi.dao.CategoryDao;
import com.SajhaKrishi.dao.EquipmentDao;
import com.SajhaKrishi.model.CategoryModel;
import com.SajhaKrishi.model.EquipmentModel;
import com.SajhaKrishi.model.User;
import com.SajhaKrishi.utils.ValidationUtil;

@WebServlet(ApiConstant.OWNER_EQUIPMENT + "/*")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,       // 1MB — file written to disk after this
    maxFileSize       = 1024 * 1024 * 10,  // 10MB max per file
    maxRequestSize    = 1024 * 1024 * 15   // 15MB max whole request
)
public class EquipmentController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private EquipmentDao equipmentDao;
    private CategoryDao categoryDao;

    @Override
    public void init() {
        equipmentDao = new EquipmentDao();
        categoryDao = new CategoryDao();
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/") || pathInfo.equals("/list")) {
            handleList(request, response);

        } else if (pathInfo.equals("/add")) {
            handleAddPage(request, response);

        } else if (pathInfo.equals("/edit")) {
            handleEditPage(request, response);

        } else if (pathInfo.equals("/delete")) {
            handleDelete(request, response);

        } else if (pathInfo.equals("/view")) {
            handleView(request, response);

        } else {
            response.sendRedirect(request.getContextPath() + "/owner/equipment/list");
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();

        if (pathInfo.equals("/add")) {
            handleAdd(request, response);

        } else if (pathInfo.equals("/edit")) {
            handleEdit(request, response);

        } else {
            response.sendRedirect(request.getContextPath() + "/owner/equipment/list");
        }
    }

    /**
     * Equipment list
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void handleList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        HttpSession session = request.getSession(false);
//        User user = (User) session.getAttribute("loggedInUser");
//        request.setAttribute("equipmentList", equipmentDao.getEquipmentByOwner(user.getId()));
    	request.setAttribute("selectedNavItem", "equipment");
		request.setAttribute("contentPage", PageConstant.EQUIPMENT_LIST);
		request.getRequestDispatcher(PageConstant.LAYOUT).forward(request, response);
    }


    /**
     * Equipment Add
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void handleAddPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	List<CategoryModel> categoryList = categoryDao.getAllCategories();
    	request.setAttribute("district", DropdownConstant.DISTRICT);
    	request.setAttribute("categoryList", categoryList);
    	request.setAttribute("selectedNavItem", "equipment");
		request.setAttribute("contentPage", PageConstant.EQUIPMENT_ADD);
		request.getRequestDispatcher(PageConstant.LAYOUT).forward(request, response);
    }


    /**
     * Equipment Add form handle
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void handleAdd(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User owner = (User) session.getAttribute("user");
        System.out.print(owner.toString());

        try {
            // — Get form fields —
            String equipmentName      = request.getParameter("name");
            String category           = request.getParameter("category");
            String description        = request.getParameter("description");
            String brand              = request.getParameter("brand");
            int    manufactureYear    = ValidationUtil.parseInt(request.getParameter("manufactureYear"));
            double pricePerDay        = ValidationUtil.parseDouble(request.getParameter("pricePerDay"));
            double pricePerHour       = ValidationUtil.parseDouble(request.getParameter("pricePerHour"));
            double depositAmount      = ValidationUtil.parseDouble(request.getParameter("depositAmount"));
            String availabilityStatus = request.getParameter("availabilityStatus");
            String availableFrom      = request.getParameter("availableFrom");
            String availableTo        = request.getParameter("availableTo");
            String district           = request.getParameter("district");
            String municipality       = request.getParameter("municipality");
            String address            = request.getParameter("address");
            String condition          = request.getParameter("condition");
            String specifications     = request.getParameter("specifications");
            String fuelType           = request.getParameter("fuelType");

            System.out.print("raghu: " + equipmentName);
            // — Validation —
            if (equipmentName == null || equipmentName.trim().isEmpty()) {
                request.setAttribute("error", "Equipment name is required.");
                this.handleAddPage(request, response);
                return;
            }

            if (pricePerDay <= 0) {
                request.setAttribute("error", "Price per day must be greater than 0.");
                this.handleAddPage(request, response);
                return;
            }

            // — Handle image upload —
            String imagePath = handleImageUpload(request, response);

            // — Build model —
            EquipmentModel equipment = new EquipmentModel();
            equipment.setName(equipmentName);
            equipment.setCategoryId(category);
            equipment.setDescription(description);
            equipment.setBrand(brand);
            equipment.setManufactureYear(manufactureYear);
            equipment.setPricePerDay(pricePerDay);
            equipment.setPricePerHour(pricePerHour);
            equipment.setDepositAmount(depositAmount);
            equipment.setAvailabilityStatus(availabilityStatus);
            equipment.setAvailableFrom(availableFrom);
            equipment.setAvailableTo(availableTo);
            equipment.setDistrict(district);
            equipment.setMunicipality(municipality);
            equipment.setAddress(address);
            equipment.setCondition(condition);
            equipment.setSpecifications(specifications);
            equipment.setFuelType(fuelType);
            equipment.setImagePath(imagePath);
            equipment.setOwnerId(owner.getId());
            equipment.setStatus("A");

            // — Save to DB —
            boolean success = equipmentDao.addEquipment(equipment);

            if (success) {
                request.getSession().setAttribute("success", "Equipment added successfully!");
                response.sendRedirect(request.getContextPath() + ApiConstant.OWNER_EQUIPMENT);
            } else {
                request.setAttribute("error", "Failed to add equipment. Please try again.");
                this.handleAddPage(request, response);
            }

        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid number format. Please check your inputs.");
            this.handleAddPage(request, response);
        } catch (Exception e) {
        	e.printStackTrace();
			System.err.println("Error adding equipment: " + e.getMessage());
			request.setAttribute("error", "Something went wrong. Please try again.");
			this.handleAddPage(request, response);
        }
    }

    /**
     * Equipment Update
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void handleEditPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            EquipmentModel equipment = equipmentDao.getEquipmentById(id);

            if (equipment == null) {
                response.sendRedirect(request.getContextPath() + "/owner/equipment/list");
                return;
            }

            request.setAttribute("equipment", equipment);
            request.getRequestDispatcher("/WEB-INF/views/owner/equipment-edit.jsp")
                   .forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/owner/equipment/list");
        }
    }

    /**
     * Equipment Edit
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void handleEdit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int id = Integer.parseInt(request.getParameter("id"));

            // — Get existing equipment (to keep old image if no new one uploaded) —
            EquipmentModel existing = equipmentDao.getEquipmentById(id);
            if (existing == null) {
                response.sendRedirect(request.getContextPath() + "/owner/equipment/list");
                return;
            }

            // — Get form fields —
            String equipmentName      = request.getParameter("equipmentName");
            String category           = request.getParameter("category");
            String description        = request.getParameter("description");
            String brand              = request.getParameter("brand");
            int    manufactureYear    = Integer.parseInt(request.getParameter("manufactureYear"));
            double pricePerDay        = Double.parseDouble(request.getParameter("pricePerDay"));
            double pricePerHour       = Double.parseDouble(request.getParameter("pricePerHour"));
            double depositAmount      = Double.parseDouble(request.getParameter("depositAmount"));
            String availabilityStatus = request.getParameter("availabilityStatus");
            String availableFrom      = request.getParameter("availableFrom");
            String availableTo        = request.getParameter("availableTo");
            String district           = request.getParameter("district");
            String municipality       = request.getParameter("municipality");
            String address            = request.getParameter("address");
            String condition          = request.getParameter("condition");
            String specifications     = request.getParameter("specifications");
            String fuelType           = request.getParameter("fuelType");

            // — Handle image — keep old if no new image uploaded —
            String imagePath = handleImageUpload(request, response);
            if (imagePath == null || imagePath.isEmpty()) {
                imagePath = existing.getImagePath(); // keep old image
            }

            // — Build updated model —
            EquipmentModel equipment = new EquipmentModel(id);
            equipment.setName(equipmentName);
            equipment.setCategoryId(category);
            equipment.setDescription(description);
            equipment.setBrand(brand);
            equipment.setManufactureYear(manufactureYear);
            equipment.setPricePerDay(pricePerDay);
            equipment.setPricePerHour(pricePerHour);
            equipment.setDepositAmount(depositAmount);
            equipment.setAvailabilityStatus(availabilityStatus);
            equipment.setAvailableFrom(availableFrom);
            equipment.setAvailableTo(availableTo);
            equipment.setDistrict(district);
            equipment.setMunicipality(municipality);
            equipment.setAddress(address);
            equipment.setCondition(condition);
            equipment.setSpecifications(specifications);
            equipment.setFuelType(fuelType);
            equipment.setImagePath(imagePath);
            equipment.setOwnerId(existing.getOwnerId());

            // — Save to DB —
            boolean success = equipmentDao.updateEquipment(equipment);

            if (success) {
                request.getSession().setAttribute("success", "Equipment updated successfully!");
                response.sendRedirect(request.getContextPath() + "/owner/equipment/list");
            } else {
                request.setAttribute("error", "Failed to update equipment.");
                request.setAttribute("equipment", equipment);
                request.getRequestDispatcher("/WEB-INF/views/owner/equipment-edit.jsp")
                       .forward(request, response);
            }

        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid input. Please check your fields.");
            request.getRequestDispatcher("/WEB-INF/views/owner/equipment-edit.jsp")
                   .forward(request, response);
        } catch (Exception e) {
            System.err.println("Error updating equipment: " + e.getMessage());
            request.setAttribute("error", "Something went wrong. Please try again.");
            request.getRequestDispatcher("/WEB-INF/views/owner/equipment-edit.jsp")
                   .forward(request, response);
        }
    }


    /**
     * Equipment View
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void handleView(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            EquipmentModel equipment = equipmentDao.getEquipmentById(id);

            if (equipment == null) {
                response.sendRedirect(request.getContextPath() + "/owner/equipment/list");
                return;
            }

            request.setAttribute("equipment", equipment);
            request.getRequestDispatcher("/WEB-INF/views/owner/equipment-view.jsp")
                   .forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/owner/equipment/list");
        }
    }


    /**
     * Equipment Delete
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void handleDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            boolean success = equipmentDao.deleteEquipment(id);

            if (success) {
                request.getSession().setAttribute("success", "Equipment deleted successfully!");
            } else {
                request.getSession().setAttribute("error", "Failed to delete equipment.");
            }

        } catch (NumberFormatException e) {
            request.getSession().setAttribute("error", "Invalid equipment ID.");
        }

        response.sendRedirect(request.getContextPath() + "/owner/equipment/list");
    }


    /**
     * Equipment upload
     * @param request
     * @param response
     * @return
     * @throws IOException
     * @throws ServletException
     */
    private String handleImageUpload(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        Part filePart = request.getPart("image");
        

        if (filePart == null || filePart.getSize() == 0) {
            return null; // no image uploaded
        }

        // Get original filename
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        String uniqueName = System.currentTimeMillis() + "_" + fileName;

        // Save to /uploads/equipment/ inside webapp
        String uploadDir = getServletContext().getRealPath("/uploads/equipment/");
        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();

        filePart.write(uploadDir + File.separator + uniqueName);

        return "uploads/equipment/" + uniqueName; // relative path for JSP <img src>
    }
}