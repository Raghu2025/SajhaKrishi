package com.SajhaKrishi.controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.SajhaKrishi.constant.ApiConstant;
import com.SajhaKrishi.constant.PageConstant;
import com.SajhaKrishi.dao.EquipmentDao;
import com.SajhaKrishi.model.EquipmentModel;

@WebServlet(ApiConstant.KISSAN_EQUIPMENT + "/*")
public class EquipmentListController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private EquipmentDao equipmentDao;

	@Override
	public void init() {
		equipmentDao = new EquipmentDao();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String pathInfo = request.getPathInfo();
		System.out.print("[][][][]]");

		if (pathInfo == null || pathInfo.equals("/") || pathInfo.equals("/list")) {
			handleBrowse(request, response);

		} else if (pathInfo.equals("/view")) {
			handleView(request, response);

		} else if (pathInfo.equals("/search")) {
//			handleSearch(request, response);

		} else if (pathInfo.equals("/filter")) {
			handleFilter(request, response);

		} else {
			response.sendRedirect(request.getContextPath() + "/kisan/equipment/list");
		}
	}

	private void handleBrowse(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 1. Capture all possible parameters
		String keyword = request.getParameter("keyword");
		String category = request.getParameter("category");
		String district = request.getParameter("district");

		List<EquipmentModel> equipmentList;

		// 2. Determine Data Retrieval Logic
		if (isNotEmpty(keyword)) {
			// Priority 1: Keyword Search
			equipmentList = equipmentDao.searchEquipment(keyword.trim());
			request.setAttribute("keyword", keyword);

		} else if (isNotEmpty(category) && isNotEmpty(district)) {
			// Priority 2: Multi-parameter Filter
			equipmentList = equipmentDao.getEquipmentByCategory(category);
			equipmentList.removeIf(e -> !e.getDistrict().equalsIgnoreCase(district));

		} else if (isNotEmpty(category)) {
			// Priority 3: Category Filter
			equipmentList = equipmentDao.getEquipmentByCategory(category);

		} else if (isNotEmpty(district)) {
			// Priority 4: District Filter
			equipmentList = equipmentDao.getEquipmentByDistrict(district);

		} else {
			// Default: Show All
			equipmentList = equipmentDao.getAllEquipment();
		}

		// 3. Set standard attributes for the View (JSP)
		request.setAttribute("equipmentList", equipmentList);
		request.setAttribute("totalCount", equipmentList.size());
		request.setAttribute("selectedCategory", category);
		request.setAttribute("selectedDistrict", district);

		// 4. Forward to the shared JSP view[cite: 1, 2]
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
//			int id = Integer.parseInt(request.getParameter("id"));
//			EquipmentModel equipment = equipmentDao.getEquipmentById(id);
//
//			if (equipment == null) {
//				request.getSession().setAttribute("error", "Equipment not found.");
//				response.sendRedirect(request.getContextPath() + "/kisan/equipment/list");
//				return;
//			}
//
//			request.setAttribute("equipment", equipment);
			request.getRequestDispatcher(PageConstant.EQUIPMENT_DETAIL).forward(request, response);

		} catch (NumberFormatException e) {
			response.sendRedirect(request.getContextPath() + "/kisan/equipment/list");
		}
	}

	private void handleFilter(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String category = request.getParameter("category");
		String district = request.getParameter("district");

		List<EquipmentModel> equipmentList;

		// Both filters applied
		if (isNotEmpty(category) && isNotEmpty(district)) {
			equipmentList = equipmentDao.searchEquipment(category);
			equipmentList.removeIf(e -> !e.getDistrict().equalsIgnoreCase(district));

			// Category only
		} else if (isNotEmpty(category)) {
			equipmentList = equipmentDao.getEquipmentByCategory(category);

			// District only
		} else if (isNotEmpty(district)) {
			equipmentList = equipmentDao.getEquipmentByDistrict(district);

			// No filter — show all
		} else {
			equipmentList = equipmentDao.getAllEquipment();
		}

		request.setAttribute("equipmentList", equipmentList);
		request.setAttribute("selectedCategory", category);
		request.setAttribute("selectedDistrict", district);
		request.setAttribute("totalCount", equipmentList.size());

		request.getRequestDispatcher("/WEB-INF/views/kisan/equipment-list.jsp").forward(request, response);
	}

	/**
	 * Helper method for cleaner logic
	 */
	private boolean isNotEmpty(String value) {
		return value != null && !value.trim().isEmpty();
	}
}