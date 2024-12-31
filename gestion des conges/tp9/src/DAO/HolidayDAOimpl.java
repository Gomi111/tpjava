package DAO;
import model.Holiday;
import model.Type_holiday;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HolidayDAOimpl implements GenericDAOI<Holiday> {

    @Override
    public void add(Holiday e) {
        String checkSoldeSql = "SELECT solde FROM employe WHERE id = ?";
        String insertHolidaySql = "INSERT INTO holiday (id_employe, startdate, enddate, type) VALUES (?, ?, ?, ?)";
        String updateSoldeSql = "UPDATE employe SET solde = solde - ? WHERE id = ?";

        try (PreparedStatement checkStmt = DBConnexion.getConnexion().prepareStatement(checkSoldeSql)) {
            // R�cup�rer le solde de cong� de l'employ�
            checkStmt.setInt(1, e.getId_employe());
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                int solde = rs.getInt("solde");

                // Calculer le nombre de jours demand�s
                long daysBetween = java.time.temporal.ChronoUnit.DAYS.between(
                    e.getStartDate().toLocalDate(),
                    e.getEndDate().toLocalDate()
                );

                if (daysBetween > solde) {
                    System.err.println("Le solde de cong� est insuffisant.");
                    return;
                }

                // Ins�rer la demande de cong�
                try (PreparedStatement insertStmt = DBConnexion.getConnexion().prepareStatement(insertHolidaySql)) {
                    insertStmt.setInt(1, e.getId_employe());
                    insertStmt.setDate(2, e.getStartDate());
                    insertStmt.setDate(3, e.getEndDate());
                    insertStmt.setString(4, e.getType().name());

                    int rowsInserted = insertStmt.executeUpdate();
                    if (rowsInserted > 0) {
                        System.out.println("Cong� ajout� avec succ�s.");
                    }

                    // Mettre � jour le solde de cong�
                    try (PreparedStatement updateStmt = DBConnexion.getConnexion().prepareStatement(updateSoldeSql)) {
                        updateStmt.setInt(1, (int) daysBetween);
                        updateStmt.setInt(2, e.getId_employe());
                        updateStmt.executeUpdate();
                    }
                }
            } else {
                System.err.println("Employ� introuvable.");
            }
        } catch (SQLException | ClassNotFoundException exception) {
            System.err.println("Erreur lors de l'ajout du cong� : " + exception.getMessage());
            exception.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM holiday WHERE id = ?";
        try (PreparedStatement stmt = DBConnexion.getConnexion().prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Cong� supprim� avec succ�s.");
            }
        } catch (SQLException | ClassNotFoundException exception) {
            System.err.println("�chec de la suppression du cong� : " + exception.getMessage());
        }
    }

    @Override
    public void update(Holiday e) {
        String sql = "UPDATE holiday SET id_employe = ?, startdate = ?, enddate = ?, type = ? WHERE id = ?";
        try (PreparedStatement stmt = DBConnexion.getConnexion().prepareStatement(sql)) {
            stmt.setInt(1, e.getId_employe());
            stmt.setDate(2, e.getStartDate());
            stmt.setDate(3, e.getEndDate());
            stmt.setString(4, e.getType().name());
            stmt.setInt(5, e.getId_holiday());

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Cong� mis � jour avec succ�s.");
            }
        } catch (SQLException | ClassNotFoundException exception) {
            System.err.println("�chec de la mise � jour du cong� : " + exception.getMessage());
        }
    }

    @Override
    public List<Holiday> display() {
        String sql = "SELECT * FROM holiday";
        List<Holiday> holidays = new ArrayList<>();
        try (PreparedStatement stmt = DBConnexion.getConnexion().prepareStatement(sql)) {
            ResultSet re = stmt.executeQuery();
            while (re.next()) {
                int id = re.getInt("id");
                int id_employe = re.getInt("id_employe");
                Date startdate = re.getDate("startdate");
                Date enddate = re.getDate("enddate");
                String type = re.getString("type");
                Holiday holiday = new Holiday(id, id_employe, startdate, enddate, Type_holiday.valueOf(type));
                holidays.add(holiday);
            }

            if (holidays.isEmpty()) {
                System.out.println("Aucun cong� trouv�.");
            } else {
                System.out.println("Liste des cong�s :");
                for (Holiday holiday : holidays) {
                    System.out.println(holiday);
                }
            }

        } catch (SQLException | ClassNotFoundException ex) {
            System.err.println("�chec de la r�cup�ration des cong�s : " + ex.getMessage());
        }
        return holidays; // Retourne une liste vide si une erreur se produit
    }
}
