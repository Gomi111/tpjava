package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import Model.Employe;
import Model.Holiday;
import Model.Holiday.HolidayType;

public class HolidayDAOImpl implements GenericDAOI<Holiday> {
    private Connection conn;


    public HolidayDAOImpl() {
        this.conn= connexion.getConnexion();
    }

    @Override
    public void add(Holiday H) {
        String query = "INSERT INTO holiday(employe_id,type, start_date, end_date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, H.getIdE());
            stmt.setString(2, H.getType().name());

            stmt.setString(3, H.getStartDate().toString());
            stmt.setString(4, H.getEndDate().toString());
            // stmt.setInt(5, H.getBalance());

            int rowsInserted = stmt.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Holiday added successfully!");

            }
        } catch (SQLException e) {
            System.out.println("Error adding holiday: " + e.getMessage());
        }

    }




    @Override
    public void update(Holiday H) {

        // Calculer used_balance
        // int usedBalance = calculateUsedBalance(H.getStartDate(), H.getEndDate());
        //H.setBalance(usedBalance);  // Mettre à jour l'attribut used_balance

        String query = "UPDATE holiday SET employe_id=? , type=?, start_date=?, end_date=? WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, H.getIdE());
            stmt.setString(2, H.getType().name());
            stmt.setString(3, H.getStartDate());
            stmt.setString(4, H.getEndDate());
            stmt.setInt(5, H.getIdC());

            //stmt.setInt(5, H.getUsed_balance());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }}

    private int calculateUsedBalance(String startDate, String endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate start = LocalDate.parse(startDate, formatter);
        LocalDate end = LocalDate.parse(endDate, formatter);

        // Calculer le nombre de jours entre les deux dates
        long daysBetween = ChronoUnit.DAYS.between(start, end);

        return (int) daysBetween;

    }





    @Override
    public void delete(int id) {
        try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM holiday WHERE id = ?")) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();

        }

    }

    @Override
    public List<Holiday> findAll() {
        List<Holiday> holidays = new ArrayList<>();
        String query = "SELECT * FROM holiday h, Employe e where h.employe_id = e.id";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                holidays.add(new Holiday(
                        rs.getInt("id"),
                        rs.getInt("employe_id"),
                        HolidayType.valueOf(rs.getString("type")),
                        rs.getString("start_date"),
                        rs.getString("end_date")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return holidays;
    }


    @Override
    public Holiday findById(int employe_id) {
        String query = "SELECT * FROM holiday WHERE  employe_id= ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, employe_id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Holiday(
                        rs.getInt("id"),
                        rs.getInt("employe_id"),
                        HolidayType.valueOf(rs.getString("type")),
                        rs.getString("start_date"),
                        rs.getString("end_date")

                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }





}
