package com.SajhaKrishi.controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.SajhaKrishi.constant.ApiConstant;
import com.SajhaKrishi.constant.PageConstant;
import com.SajhaKrishi.dao.CategoryDao;
import com.SajhaKrishi.model.CategoryModel;
import com.SajhaKrishi.model.User;

/**
 * Servlet implementation class CategoryController Handles CRUD operations for
 * equipment categories
 */
@WebServlet(ApiConstant.ADMIN_CATEGORY + "/*")
public class CategoryController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private CategoryDao categoryDao;

	@Override
	public void init() {
		categoryDao = new CategoryDao();
	}

	/**
	 * Handle GET requests (list, add, edit, delete, view)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Verify admin session
		if (!isAdminUser(request, response)) {
			return;
		}

		String pathInfo = request.getPathInfo();

		if (pathInfo == null || pathInfo.equals("/") || pathInfo.equals(ApiConstant.LIST)) {
			handleList(request, response);
		} else if (pathInfo.equals(ApiConstant.ADD)) {
			handleAddPage(request, response);
		} else if (pathInfo.equals(ApiConstant.EDIT)) {
			handleEditPage(request, response);
		} else if (pathInfo.equals(ApiConstant.DELETE)) {
			handleDelete(request, response);
		} else if (pathInfo.equals(ApiConstant.DETAIL)) {
			handleDetail(request, response);
		} else {
			response.sendRedirect(request.getContextPath() + ApiConstant.ADMIN_CATEGORY + ApiConstant.LIST);
		}
	}

	/**
	 * Handle POST requests (save)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Verify admin session
		if (!isAdminUser(request, response)) {
			return;
		}

		handleSave(request, response);
	}

	/**
	 * ════════════════════════════════════════════════════════════════ LIST — Fetch
	 * and display all categories
	 * ════════════════════════════════════════════════════════════════
	 */
	private void handleList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			List<CategoryModel> categoryList = categoryDao.getAllCategoriesAdmin();
			request.setAttribute("categoryList", categoryList);
			request.setAttribute("selectedNavItem", "category");
			request.setAttribute("contentPage", PageConstant.CATEGORY_LIST);
			request.getRequestDispatcher(PageConstant.LAYOUT).forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "Error loading categories: " + e.getMessage());
			request.getRequestDispatcher(PageConstant.LAYOUT).forward(request, response);
		}
	}

	/**
	 * ════════════════════════════════════════════════════════════════ ADD PAGE —
	 * Show add category form
	 * ════════════════════════════════════════════════════════════════
	 */
	private void handleAddPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			request.setAttribute("selectedNavItem", "category");
			request.setAttribute("contentPage", PageConstant.CATEGORY_ADD);
			request.getRequestDispatcher(PageConstant.LAYOUT).forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "Error loading form: " + e.getMessage());
			request.getRequestDispatcher(PageConstant.LAYOUT).forward(request, response);
		}
	}

	/**
	 * ════════════════════════════════════════════════════════════════ EDIT PAGE —
	 * Load category and show edit form
	 * ════════════════════════════════════════════════════════════════
	 */
	private void handleEditPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String idParam = request.getParameter("id");
			if (idParam == null || idParam.isEmpty()) {
				response.sendRedirect(request.getContextPath() + ApiConstant.ADMIN_CATEGORY + ApiConstant.LIST);
				return;
			}

			int id = Integer.parseInt(idParam);
			CategoryModel category = categoryDao.getCategoryById(id);

			if (category == null) {
				request.setAttribute("error", "Category not found.");
				handleList(request, response);
				return;
			}

			request.setAttribute("category", category);
			request.setAttribute("selectedNavItem", "category");
			request.setAttribute("contentPage", PageConstant.CATEGORY_ADD);
			request.getRequestDispatcher(PageConstant.LAYOUT).forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "Error loading category: " + e.getMessage());
			request.getRequestDispatcher(PageConstant.LAYOUT).forward(request, response);
		}
	}

	/**
	 * ════════════════════════════════════════════════════════════════ SAVE — Add
	 * or Update category (POST handler)
	 * ════════════════════════════════════════════════════════════════
	 */
	private void handleSave(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String idParam = request.getParameter("id");
			boolean isEdit = idParam != null && !idParam.isEmpty();
			int id = isEdit ? Integer.parseInt(idParam) : 0;

			String name = request.getParameter("name");
			String description = request.getParameter("description");

			// Validation
			if (name == null || name.trim().isEmpty()) {
				request.setAttribute("error", "Category name is required.");
				if (isEdit) {
					request.setAttribute("category", categoryDao.getCategoryById(id));
				}
				request.setAttribute("selectedNavItem", "category");
				request.setAttribute("contentPage", PageConstant.CATEGORY_ADD);
				request.getRequestDispatcher(PageConstant.LAYOUT).forward(request, response);
				return;
			}

			// Check for duplicate name (excluding current category during edit)
			CategoryModel existing = categoryDao.getCategoryByName(name);
			if (existing != null && existing.getId() != id) {
				request.setAttribute("error", "A category with this name already exists.");
				if (isEdit) {
					request.setAttribute("category", categoryDao.getCategoryById(id));
				}
				request.setAttribute("selectedNavItem", "category");
				request.setAttribute("contentPage", PageConstant.CATEGORY_ADD);
				request.getRequestDispatcher(PageConstant.LAYOUT).forward(request, response);
				return;
			}

			// Create or fetch category model
			CategoryModel category = isEdit ? categoryDao.getCategoryById(id) : new CategoryModel();
			if (category == null && isEdit) {
				response.sendRedirect(request.getContextPath() + ApiConstant.ADMIN_CATEGORY + ApiConstant.LIST);
				return;
			}

			category.setName(name);
			if (description != null && !description.trim().isEmpty()) {
				// Note: CategoryModel may not have description field yet, add if needed
			}

			// Perform database operation
			boolean success = isEdit ? categoryDao.updateCategory(category) : categoryDao.addCategory(category);

			if (success) {
				request.getSession().setAttribute("successMessage",
						"Category " + (isEdit ? "updated" : "added") + " successfully!");
				response.sendRedirect(request.getContextPath() + ApiConstant.ADMIN_CATEGORY + ApiConstant.LIST);
			} else {
				request.setAttribute("error", "Database operation failed. Please try again.");
				request.setAttribute("category", category);
				request.setAttribute("selectedNavItem", "category");
				request.setAttribute("contentPage", PageConstant.CATEGORY_ADD);
				request.getRequestDispatcher(PageConstant.LAYOUT).forward(request, response);
			}

		} catch (NumberFormatException e) {
			request.setAttribute("error", "Invalid category ID format.");
			try {
				handleList(request, response);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "An error occurred: " + e.getMessage());
			request.setAttribute("selectedNavItem", "category");
			request.setAttribute("contentPage", PageConstant.CATEGORY_ADD);
			try {
				request.getRequestDispatcher(PageConstant.LAYOUT).forward(request, response);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * ════════════════════════════════════════════════════════════════ DELETE —
	 * Soft delete category
	 * ════════════════════════════════════════════════════════════════
	 */
	private void handleDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String idParam = request.getParameter("id");
			if (idParam == null || idParam.isEmpty()) {
				response.sendRedirect(request.getContextPath() + ApiConstant.ADMIN_CATEGORY + ApiConstant.LIST);
				return;
			}

			int id = Integer.parseInt(idParam);
			boolean success = categoryDao.deleteCategory(id);

			if (success) {
				request.getSession().setAttribute("successMessage", "Category deleted successfully!");
			} else {
				request.getSession().setAttribute("errorMessage", "Failed to delete category. Please try again.");
			}

			response.sendRedirect(request.getContextPath() + ApiConstant.ADMIN_CATEGORY + ApiConstant.LIST);

		} catch (NumberFormatException e) {
			request.getSession().setAttribute("errorMessage", "Invalid category ID format.");
			response.sendRedirect(request.getContextPath() + ApiConstant.ADMIN_CATEGORY + ApiConstant.LIST);
		} catch (Exception e) {
			e.printStackTrace();
			request.getSession().setAttribute("errorMessage", "An error occurred: " + e.getMessage());
			try {
				response.sendRedirect(request.getContextPath() + ApiConstant.ADMIN_CATEGORY + ApiConstant.LIST);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * ════════════════════════════════════════════════════════════════ DETAIL —
	 * View single category details
	 * ════════════════════════════════════════════════════════════════
	 */
	private void handleDetail(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String idParam = request.getParameter("id");
			if (idParam == null || idParam.isEmpty()) {
				response.sendRedirect(request.getContextPath() + ApiConstant.ADMIN_CATEGORY + ApiConstant.LIST);
				return;
			}

			int id = Integer.parseInt(idParam);
			CategoryModel category = categoryDao.getCategoryById(id);

			if (category == null) {
				response.sendRedirect(request.getContextPath() + ApiConstant.ADMIN_CATEGORY + ApiConstant.LIST);
				return;
			}

			request.setAttribute("category", category);
			request.setAttribute("selectedNavItem", "category");
			request.setAttribute("contentPage", PageConstant.CATEGORY_DETAIL);
			request.getRequestDispatcher(PageConstant.LAYOUT).forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath() + ApiConstant.ADMIN_CATEGORY + ApiConstant.LIST);
		}
	}

	/**
	 * ════════════════════════════════════════════════════════════════ HELPER —
	 * Verify admin user is logged in
	 * ════════════════════════════════════════════════════════════════
	 */
	private boolean isAdminUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession(false);
		if (session == null) {
			response.sendRedirect(request.getContextPath() + ApiConstant.LOGIN);
			return false;
		}

		User user = (User) session.getAttribute(ApiConstant.USER_SESSION_KEY);
		if (user == null || user.getRoleId() != 1) { // 1 = Admin role
			response.sendRedirect(request.getContextPath() + ApiConstant.UNAUTHORIZED);
			return false;
		}

		return true;
	}
}
