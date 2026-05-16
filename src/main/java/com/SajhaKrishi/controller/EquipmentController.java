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
@MultipartConfig(fileSizeThreshold = 1024 * 1024, // 1MB — file written to disk after this
		maxFileSize = 1024 * 1024 * 10, // 10MB max per file
		maxRequestSize = 1024 * 1024 * 15 // 15MB max whole request
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

		if (pathInfo == null || pathInfo.equals("/") || pathInfo.equals(ApiConstant.LIST)) {
			handleList(request, response);

		} else if (pathInfo.equals(ApiConstant.ADD)) {
			handleAddPage(request, response);

		} else if (pathInfo.equals(ApiConstant.EDIT)) {
			handleEditPage(request, response);

		} else if (pathInfo.equals(ApiConstant.DELETE)) {
			handleDelete(request, response);

		} else if (pathInfo.equals("/view")) {
			handleView(request, response);

		} else {
			response.sendRedirect(request.getContextPath() + ApiConstant.KISSAN_EQUIPMENT + ApiConstant.LIST);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		handleSave(request, response);
	}

	/**
	 * Equipment list
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void handleList(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {

	    HttpSession session = request.getSession(false);
	    User user = (User) session.getAttribute(ApiConstant.USER_SESSION_KEY);

	    boolean isAdmin = user.getRoleId() == 1;

	    List<EquipmentModel> equipmentList;

	    if (isAdmin) {
	        // Admin sees all equipment
	        equipmentList = equipmentDao.getAllEquipment();
	    } else {
	        // Owner sees only their equipment
	        equipmentList = equipmentDao.getEquipmentByOwner(user.getId());
	    }

	    request.setAttribute("equipmentList",  equipmentList);
	    request.setAttribute("isAdmin",        isAdmin);
	    request.setAttribute("selectedNavItem", "equipment");
	    request.setAttribute("contentPage",    PageConstant.EQUIPMENT_LIST);
	    request.getRequestDispatcher(PageConstant.LAYOUT).forward(request, response);
	}

	/**
	 * Equipment Add
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void handleAddPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		loadFormData(request, response);
	}

	private void loadFormData(HttpServletRequest request, HttpServletResponse response)
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
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void handleSave(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		User owner = (User) session.getAttribute(ApiConstant.USER_SESSION_KEY);

		// 1. Determine if this is an Edit or Add based on ID presence
		String idParam = request.getParameter("id");
		boolean isEdit = idParam != null && !idParam.isEmpty();
		int id = isEdit ? Integer.parseInt(idParam) : 0;

		try {
			// 2. Fetch existing for Edit (to keep image/owner)
			EquipmentModel equipment = isEdit ? equipmentDao.getEquipmentById(id) : new EquipmentModel();

			if (isEdit && equipment == null) {
				response.sendRedirect(request.getContextPath() + ApiConstant.OWNER_EQUIPMENT);
				return;
			}

			// 3. Extract & Validate Common Fields
			String name = request.getParameter("name"); // Handle your minor naming diffs
			double priceDay = ValidationUtil.parseDouble(request.getParameter("pricePerDay"));

			if (name == null || name.trim().isEmpty() || priceDay <= 0) {
				request.setAttribute("error", "Name is required and price must be positive.");
				forwardToForm(request, response, isEdit);
				return;
			}

			// 4. Update Model Fields
			equipment.setName(name);
			equipment.setCategoryId(request.getParameter("category"));
			equipment.setDescription(request.getParameter("description"));
			equipment.setBrand(request.getParameter("brand"));
			equipment.setManufactureYear(ValidationUtil.parseInt(request.getParameter("manufactureYear")));
			equipment.setPricePerDay(priceDay);
			equipment.setPricePerHour(ValidationUtil.parseDouble(request.getParameter("pricePerHour")));
			equipment.setDepositAmount(ValidationUtil.parseDouble(request.getParameter("depositAmount")));
			equipment.setAvailabilityStatus(request.getParameter("availabilityStatus"));
			equipment.setDistrict(request.getParameter("district"));
			equipment.setMunicipality(request.getParameter("municipality"));
			equipment.setAddress(request.getParameter("address"));
			equipment.setCondition(request.getParameter("condition"));
			equipment.setSpecifications(request.getParameter("specifications"));
			equipment.setFuelType(request.getParameter("fuelType"));

			// 5. Image Logic (Keep old if new is empty)
			String newImagePath = handleImageUpload(request, response);
			if (newImagePath != null && !newImagePath.isEmpty()) {
				equipment.setImagePath(newImagePath);
			}

			// 6. Set Metadata
			if (!isEdit) {
				equipment.setOwnerId(owner.getId());
				equipment.setStatus("A");
			}

			// 7. Database Operation
			boolean success = isEdit ? equipmentDao.updateEquipment(equipment) : equipmentDao.addEquipment(equipment);

			if (success) {
				request.getSession().setAttribute("success", "Equipment saved successfully!");
				response.sendRedirect(request.getContextPath() + ApiConstant.OWNER_EQUIPMENT);
			} else {
				request.setAttribute("error", "Database operation failed.");
				request.setAttribute("equipment", equipment);
				forwardToForm(request, response, isEdit);
			}

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "An error occurred: " + e.getMessage());
			forwardToForm(request, response, isEdit);
		}
	}

	/**
	 * Helper to handle the forwarding logic
	 * 
	 * @param request
	 * @param response
	 * @param isEdit
	 * @throws ServletException
	 * @throws IOException
	 */
	private void forwardToForm(HttpServletRequest request, HttpServletResponse response, boolean isEdit)
			throws ServletException, IOException {
		String path = isEdit ? PageConstant.EQUIPMENT_ADD : PageConstant.EQUIPMENT_ADD;
		if (!isEdit) {
			this.handleEditPage(request, response);
		} else {
			this.handleAddPage(request, response);
		}
	}

	/**
	 * Equipment Update
	 * 
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
				response.sendRedirect(request.getContextPath() + ApiConstant.OWNER_EQUIPMENT);
				return;
			}

			request.setAttribute("equipment", equipment);

			// Load dropdowns same as add page
			loadFormData(request, response);

		} catch (NumberFormatException e) {
			response.sendRedirect(request.getContextPath() + ApiConstant.OWNER_EQUIPMENT);
		}
	}

	/**
	 * Equipment View
	 * 
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
			request.getRequestDispatcher("/WEB-INF/views/owner/equipment-view.jsp").forward(request, response);

		} catch (NumberFormatException e) {
			response.sendRedirect(request.getContextPath() + "/owner/equipment/list");
		}
	}

	/**
	 * Equipment Delete
	 * 
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

		response.sendRedirect(request.getContextPath() + ApiConstant.OWNER_EQUIPMENT);
	}

	/**
	 * Equipment upload
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
//    private String handleImageUpload(HttpServletRequest request, HttpServletResponse response)
//            throws IOException, ServletException {
//
//        Part filePart = request.getPart("image");
//        String staticImagePath = ApiConstant.SAVED_IMAGE_PATH + ApiConstant.EQUIPMENT_SAVED_IMAGE_PATH;
//
//        if (filePart == null || filePart.getSize() == 0) {
//            return null;
//        }
//
//        // Get original filename
//        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
//        String uniqueName = System.currentTimeMillis() + "_" + fileName;
//
//        System.out.println(staticImagePath);
//        File dir = new File(staticImagePath);
//        if (!dir.exists()) dir.mkdirs();
//
//        filePart.write(staticImagePath + File.separator + uniqueName);
//
//        return ApiConstant.EQUIPMENT_SAVED_IMAGE_PATH  + "/" + uniqueName;
//    }
	private String handleImageUpload(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		Part filePart = request.getPart("image");

		if (filePart == null || filePart.getSize() == 0) {
			return null;
		}

		String uploadDir = getServletContext().getRealPath(ApiConstant.EQUIPMENT_SAVED_IMAGE_PATH + "/");
		File dir = new File(uploadDir);
		if (!dir.exists())
			dir.mkdirs();

		String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
		String uniqueName = System.currentTimeMillis() + "_" + fileName;

		filePart.write(uploadDir + File.separator + uniqueName);

		return ApiConstant.EQUIPMENT_SAVED_IMAGE_PATH + "/" + uniqueName;
	}
}