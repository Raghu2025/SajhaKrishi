package com.SajhaKrishi.controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.SajhaKrishi.dao.EquipmentDao;
import com.SajhaKrishi.model.EquipmentModel;

@WebServlet("/kisan/equipment/*")
public class EquipmentListController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private EquipmentDao equipmentDao;

    @Override
    public void init() {
        equipmentDao = new EquipmentDao();
    }

    // ════════════════════════════════════════
    //  GET — Display pages
    // ════════════════════════════════════════
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/") || pathInfo.equals("/list")) {
            handleList(request, response);

        } else if (pathInfo.equals("/view")) {
            handleView(request, response);

        } else if (pathInfo.equals("/search")) {
            handleSearch(request, response);

        } else if (pathInfo.equals("/filter")) {
            handleFilter(request, response);

        } else {
            response.sendRedirect(request.getContextPath() + "/kisan/equipment/list");
        }
    }

    // ════════════════════════════════════════
    //  LIST — Browse all available equipment
    // ════════════════════════════════════════
    private void handleList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<EquipmentModel> equipmentList = equipmentDao.getAllEquipment();

        request.setAttribute("equipmentList", equipmentList);
        request.setAttribute("totalCount", equipmentList.size());

        request.getRequestDispatcher("/WEB-INF/views/kisan/equipment-list.jsp")
               .forward(request, response);
    }

    // ════════════════════════════════════════
    //  VIEW — Single equipment detail page
    // ════════════════════════════════════════
    private void handleView(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            EquipmentModel equipment = equipmentDao.getEquipmentById(id);

            if (equipment == null) {
                request.getSession().setAttribute("error", "Equipment not found.");
                response.sendRedirect(request.getContextPath() + "/kisan/equipment/list");
                return;
            }

            request.setAttribute("equipment", equipment);
            request.getRequestDispatcher("/WEB-INF/views/kisan/equipment-view.jsp")
                   .forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/kisan/equipment/list");
        }
    }

    // ════════════════════════════════════════
    //  SEARCH — Search by keyword
    // ════════════════════════════════════════
    private void handleSearch(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String keyword = request.getParameter("keyword");

        if (keyword == null || keyword.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/kisan/equipment/list");
            return;
        }

        List<EquipmentModel> equipmentList = equipmentDao.searchEquipment(keyword.trim());

        request.setAttribute("equipmentList", equipmentList);
        request.setAttribute("keyword", keyword);
        request.setAttribute("totalCount", equipmentList.size());

        request.getRequestDispatcher("/WEB-INF/views/kisan/equipment-list.jsp")
               .forward(request, response);
    }

    // ════════════════════════════════════════
    //  FILTER — Filter by category or district
    // ════════════════════════════════════════
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

        request.getRequestDispatcher("/WEB-INF/views/kisan/equipment-list.jsp")
               .forward(request, response);
    }

    // ════════════════════════════════════════
    //  HELPER
    // ════════════════════════════════════════
    private boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }
}