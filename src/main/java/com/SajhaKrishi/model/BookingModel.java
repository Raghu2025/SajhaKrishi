package com.SajhaKrishi.model;

public class BookingModel extends Base {

	private int equipmentId;
	private String equipmentName;
	private String imagePath;
	private String categoryName;
	private int kisanId;
	private int ownerId;
	private String startDate;
	private String endDate;
	private int totalDays;

	private double pricePerDay;
	private double totalPrice;
	private double depositAmount;

	private String status;
	private String statusFlag;
	private String paymentStatus;

	private String pickupAddress;
	private String notes;

	private String bookedAt;

	public BookingModel() {
		super("A");
	}

	public BookingModel(int id) {
		super(id, "A");
	}

	public BookingModel(int equipmentId, int kisanId, int ownerId, String startDate, String endDate, int totalDays,
			double pricePerDay, double totalPrice, double depositAmount, String status, String paymentStatus,
			String pickupAddress, String notes) {
		super("A");
		this.initiate(equipmentId, kisanId, ownerId, startDate, endDate, totalDays, pricePerDay, totalPrice,
				depositAmount, status, paymentStatus, pickupAddress, notes);
	}

	private void initiate(int equipmentId, int kisanId, int ownerId, String startDate, String endDate, int totalDays,
			double pricePerDay, double totalPrice, double depositAmount, String status, String paymentStatus,
			String pickupAddress, String notes) {
		this.equipmentId = equipmentId;
		this.kisanId = kisanId;
		this.ownerId = ownerId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.totalDays = totalDays;
		this.pricePerDay = pricePerDay;
		this.totalPrice = totalPrice;
		this.depositAmount = depositAmount;
		this.status = status;
		this.paymentStatus = paymentStatus;
		this.pickupAddress = pickupAddress;
		this.notes = notes;
	}

	// ════════════════════════════
	// Getters & Setters
	// ════════════════════════════
	public int getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(int equipmentId) {
		this.equipmentId = equipmentId;
	}

	public int getKisanId() {
		return kisanId;
	}

	public void setKisanId(int kisanId) {
		this.kisanId = kisanId;
	}

	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public int getTotalDays() {
		return totalDays;
	}

	public void setTotalDays(int totalDays) {
		this.totalDays = totalDays;
	}

	public double getPricePerDay() {
		return pricePerDay;
	}

	public void setPricePerDay(double pricePerDay) {
		this.pricePerDay = pricePerDay;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public double getDepositAmount() {
		return depositAmount;
	}

	public void setDepositAmount(double depositAmount) {
		this.depositAmount = depositAmount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusFlag() {
		return statusFlag;
	}

	public void setStatusFlag(String statusFlag) {
		this.statusFlag = statusFlag;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getPickupAddress() {
		return pickupAddress;
	}

	public void setPickupAddress(String pickupAddress) {
		this.pickupAddress = pickupAddress;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getBookedAt() {
		return bookedAt;
	}

	public void setBookedAt(String bookedAt) {
		this.bookedAt = bookedAt;
	}

	public String getEquipmentName() {
		return equipmentName;
	}

	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

}