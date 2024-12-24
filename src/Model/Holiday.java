package Model;

import java.util.Date;

public class Holiday {
    private int idC;
    private int idE;
    private String startDate;
    private String endDate;
    private HolidayType type;
    private int Balance;


    public int getBalance() {
        return Balance;
    }

    public void setBalance(int balance) {
        Balance = balance;
    }

    public Holiday(int idC, int idE,HolidayType type, String startDate, String endDate ) {
        this.idC = idC;
        this.idE = idE;
        this.startDate = startDate;
        this.endDate = endDate;
        this.type = type;
    }

    public Holiday(int idE, HolidayType type, String strartDate, String endDate) {
        this.idE = idE;
        this.startDate = strartDate;
        this.endDate = endDate;
        this.type = type;
    }




    // Getters and setters
    public int getIdC() {
        return idC;
    }

    public void setIdC(int idC) {
        this.idC = idC;
    }

    public int getIdE() {
        return idE;
    }

    public void setIdE(int idE) {
        this.idE = idE;
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

    public HolidayType getType() {
        return type;
    }

    public void setType(HolidayType type) {
        this.type = type;
    }



    public enum HolidayType {
        CONGE_PAYEE,
        CONGE_NON_PAYEE,
        CONGE_MALADIER
    }
}





