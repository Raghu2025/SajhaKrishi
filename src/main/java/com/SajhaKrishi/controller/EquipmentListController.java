package com.SajhaKrishi.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.SajhaKrishi.constant.ApiConstant;
import com.SajhaKrishi.constant.DropdownConstant;
import com.SajhaKrishi.constant.PageConstant;
import com.SajhaKrishi.dao.CategoryDao;
import com.SajhaKrishi.dao.EquipmentDao;
import com.SajhaKrishi.model.CategoryModel;
import com.SajhaKrishi.model.EquipmentModel;

@WebServlet(ApiConstant.KISSAN_EQUIPMENT + "/*")
public class EquipmentListController extends HttpServlet {

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
			handleBrowse(request, response);

		} else if (pathInfo.equals(ApiConstant.DETAIL)) {
			handleView(request, response);

		} else {
			response.sendRedirect(request.getContextPath() + ApiConstant.KISSAN_EQUIPMENT);
		}
	}

	private void handleBrowse(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Capture all parameters
		String keyword = request.getParameter("keyword");
		String category = request.getParameter("category");
		String district = request.getParameter("district");

		// Call the Dynamic DAO method
		// This replaces all the priority-based if-else logic
		List<EquipmentModel> equipmentList = equipmentDao.searchEquipment(category, district, keyword);

		// Set attributes for the View
		request.setAttribute("equipmentList", equipmentList);
		request.setAttribute("totalCount", equipmentList.size());

		// Keep these so the search form can "remember" what the user typed/selected
		request.setAttribute("keyword", keyword);
		request.setAttribute("selectedCategory", category);
		request.setAttribute("selectedDistrict", district);

		// Forward to JSP
		List<CategoryModel> categoryList = categoryDao.getAllCategories();
		request.setAttribute("categoryList", categoryList);
		request.setAttribute("district", DropdownConstant.DISTRICT);
		request.getRequestDispatcher(PageConstant.BROWSE).forward(request, response);
	}

	/**
	 * View Page
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
				request.getSession().setAttribute("error", "Equipment not found.");
				response.sendRedirect(request.getContextPath() + ApiConstant.KISSAN_EQUIPMENT);
				return; 
			}
			String detailPath = ApiConstant.KISSAN_EQUIPMENT + ApiConstant.DETAIL + "?id=" + id;
			String encodedReturnUrl = URLEncoder.encode(detailPath, StandardCharsets.UTF_8);
			request.setAttribute("encodedReturnUrl", encodedReturnUrl);

			request.setAttribute("equipment", equipment);
			request.getRequestDispatcher(PageConstant.EQUIPMENT_DETAIL).forward(request, response);

		} catch (NumberFormatException e) {
			response.sendRedirect(request.getContextPath() + ApiConstant.KISSAN_EQUIPMENT + ApiConstant.LIST);
		}
	}

	/**
	 * Helper method for cleaner logic
	 */
	private boolean isNotEmpty(String value) {
		return value != null && !value.trim().isEmpty();
	}
}