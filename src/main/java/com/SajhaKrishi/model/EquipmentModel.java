package com.SajhaKrishi.model;

public class EquipmentModel extends Base {
	private String name; 
	private String categoryIs;           
	private String description;         
	private String brand;               
	private int manufactureYear;        

	private double pricePerDay;         
	private double pricePerHour;        
	private double depositAmount;      

	private String availabilityStatus;
	private String availableFrom;       
	private String availableTo;       

	private String district;            
	private String municipality;        
	private String address;            

	private String condition;           
	private String specifications;      
	private String fuelType;            

	private String imagePath;         
	private int ownerId;             


	public EquipmentModel(String name, String categoryIs, String description, String brand,
			int manufactureYear, double pricePerDay, double pricePerHour,
			double depositAmount, String availabilityStatus,
			String availableFrom, String availableTo,
			String district, String municipality, String address,
			String condition, String specifications, String fuelType,
			String imagePath, int ownerId) {

		super("A");

		this.name = name;
		this.categoryIs = categoryIs;
		this.description = description;
		this.brand = brand;
		this.manufactureYear = manufactureYear;

		this.pricePerDay = pricePerDay;
		this.pricePerHour = pricePerHour;
		this.depositAmount = depositAmount;

		this.availabilityStatus = availabilityStatus;
		this.availableFrom = availableFrom;
		this.availableTo = availableTo;

		this.district = district;
		this.municipality = municipality;
		this.address = address;

		this.condition = condition;
		this.specifications = specifications;
		this.fuelType = fuelType;

		this.imagePath = imagePath;
		this.ownerId = ownerId;
	}
	public EquipmentModel(int id, String name, String categoryIs, String description, String brand,
			int manufactureYear, double pricePerDay, double pricePerHour,
			double depositAmount, String availabilityStatus,
			String availableFrom, String availableTo,
			String district, String municipality, String address,
			String condition, String specifications, String fuelType,
			String imagePath, int ownerId) {

		super("A");
		this.categoryIs = id;
		this.name = name;
		this.categoryIs = categoryIs;
		this.description = description;
		this.brand = brand;
		this.manufactureYear = manufactureYear;

		this.pricePerDay = pricePerDay;
		this.pricePerHour = pricePerHour;
		this.depositAmount = depositAmount;

		this.availabilityStatus = availabilityStatus;
		this.availableFrom = availableFrom;
		this.availableTo = availableTo;

		this.district = district;
		this.municipality = municipality;
		this.address = address;

		this.condition = condition;
		this.specifications = specifications;
		this.fuelType = fuelType;

		this.imagePath = imagePath;
		this.ownerId = ownerId;
	}


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCategoryIs() {
		return categoryIs;
	}
	public void setCategoryIs(String categoryIs) {
		this.categoryIs = categoryIs;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public int getManufactureYear() {
		return manufactureYear;
	}
	public void setManufactureYear(int manufactureYear) {
		this.manufactureYear = manufactureYear;
	}
	public double getPricePerDay() {
		return pricePerDay;
	}
	public void setPricePerDay(double pricePerDay) {
		this.pricePerDay = pricePerDay;
	}
	public double getPricePerHour() {
		return pricePerHour;
	}
	public void setPricePerHour(double pricePerHour) {
		this.pricePerHour = pricePerHour;
	}
	public double getDepositAmount() {
		return depositAmount;
	}
	public void setDepositAmount(double depositAmount) {
		this.depositAmount = depositAmount;
	}
	public String getAvailabilityStatus() {
		return availabilityStatus;
	}
	public void setAvailabilityStatus(String availabilityStatus) {
		this.availabilityStatus = availabilityStatus;
	}
	public String getAvailableFrom() {
		return availableFrom;
	}
	public void setAvailableFrom(String availableFrom) {
		this.availableFrom = availableFrom;
	}
	public String getAvailableTo() {
		return availableTo;
	}
	public void setAvailableTo(String availableTo) {
		this.availableTo = availableTo;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getMunicipality() {
		return municipality;
	}
	public void setMunicipality(String municipality) {
		this.municipality = municipality;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public String getSpecifications() {
		return specifications;
	}
	public void setSpecifications(String specifications) {
		this.specifications = specifications;
	}
	public String getFuelType() {
		return fuelType;
	}
	public void setFuelType(String fuelType) {
		this.fuelType = fuelType;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public int getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

}
