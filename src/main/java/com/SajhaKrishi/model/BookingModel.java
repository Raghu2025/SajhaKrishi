package com.SajhaKrishi.model;

public class BookingModel extends Base {

    private int equipmentId;
    private int kisanId;            
    private int ownerId;            // FK → users table (the owner)

    private String startDate;       // yyyy-MM-dd
    private String endDate;         // yyyy-MM-dd
    private int    totalDays;       // calculated from start - end

    private double pricePerDay;     // snapshot of price at time of booking
    private double totalPrice;      // pricePerDay * totalDays
    private double depositAmount;   // deposit paid upfront

    private String status;          // "Pending", "Confirmed", "Cancelled", "Completed"
    private String paymentStatus;   // "Unpaid", "Paid", "Refunded"

    private String pickupAddress;   // where to collect the equipment
    private String notes;           // any special request from kisan

    private String bookedAt;

   
    public BookingModel() {
        super("A");
    }

    public BookingModel(int id) {
        super(id, "A");
    }

    public BookingModel(int equipmentId, int kisanId, int ownerId,
                        String startDate, String endDate, int totalDays,
                        double pricePerDay, double totalPrice, double depositAmount,
                        String status, String paymentStatus,
                        String pickupAddress, String notes) {
        super("A");
        this.initiate(equipmentId, kisanId, ownerId, startDate, endDate, totalDays, pricePerDay, totalPrice, depositAmount, status, paymentStatus, pickupAddress, notes);
    }
    
    private void initiate(int equipmentId, int kisanId, int ownerId,
            String startDate, String endDate, int totalDays,
            double pricePerDay, double totalPrice, double depositAmount,
            String status, String paymentStatus,
            String pickupAddress, String notes) {
        this.equipmentId    = equipmentId;
        this.kisanId        = kisanId;
        this.ownerId        = ownerId;
        this.startDate      = startDate;
        this.endDate        = endDate;
        this.totalDays      = totalDays;
        this.pricePerDay    = pricePerDay;
        this.totalPrice     = totalPrice;
        this.depositAmount  = depositAmount;
        this.status         = status;
        this.paymentStatus  = paymentStatus;
        this.pickupAddress  = pickupAddress;
        this.notes          = notes;
    }

    // ════════════════════════════
    //  Getters & Setters
    // ════════════════════════════
    public int getEquipmentId() { return equipmentId; }
    public void setEquipmentId(int equipmentId) { this.equipmentId = equipmentId; }

    public int getKisanId() { return kisanId; }
    public void setKisanId(int kisanId) { this.kisanId = kisanId; }

    public int getOwnerId() { return ownerId; }
    public void setOwnerId(int ownerId) { this.ownerId = ownerId; }

    public String getStartDate() { return startDate; }
    public void setStartDate(String startDate) { this.startDate = startDate; }

    public String getEndDate() { return endDate; }
    public void setEndDate(String endDate) { this.endDate = endDate; }

    public int getTotalDays() { return totalDays; }
    public void setTotalDays(int totalDays) { this.totalDays = totalDays; }

    public double getPricePerDay() { return pricePerDay; }
    public void setPricePerDay(double pricePerDay) { this.pricePerDay = pricePerDay; }

    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }

    public double getDepositAmount() { return depositAmount; }
    public void setDepositAmount(double depositAmount) { this.depositAmount = depositAmount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }

    public String getPickupAddress() { return pickupAddress; }
    public void setPickupAddress(String pickupAddress) { this.pickupAddress = pickupAddress; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public String getBookedAt() { return bookedAt; }
    public void setBookedAt(String bookedAt) { this.bookedAt = bookedAt; }

    // ════════════════════════════
    //  toString
    // ════════════════════════════
//    @Override
//    public String toString() {
//        return "BookingModel{" +
//                "equipmentId="   + equipmentId   +
//                ", kisanId="     + kisanId        +
//                ", startDate='"  + startDate      + '\'' +
//                ", endDate='"    + endDate        + '\'' +
//                ", totalDays="   + totalDays      +
//                ", totalPrice="  + totalPrice     +
//                ", status='"     + status         + '\'' +
//                ", paymentStatus='" + paymentStatus + '\'' +
//                '}';
//    }
}