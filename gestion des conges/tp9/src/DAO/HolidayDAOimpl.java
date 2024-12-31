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
            // Récupérer le solde de congé de l'employé
            checkStmt.setInt(1, e.getId_employe());
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                int solde = rs.getInt("solde");

                // Calculer le nombre de jours demandés
                long daysBetween = java.time.temporal.ChronoUnit.DAYS.between(
                    e.getStartDate().toLocalDate(),
                    e.getEndDate().toLocalDate()
                );

                if (daysBetween > solde) {
                    System.err.println("Le solde de congé est insuffisant.");
                    return;
                }

                // Insérer la demande de congé
                try (PreparedStatement insertStmt = DBConnexion.getConnexion().prepareStatement(insertHolidaySql)) {
                    insertStmt.setInt(1, e.getId_employe());
                    insertStmt.setDate(2, e.getStartDate());
                    insertStmt.setDate(3, e.getEndDate());
                    insertStmt.setString(4, e.getType().name());

                    int rowsInserted = insertStmt.executeUpdate();
                    if (rowsInserted > 0) {
                        System.out.println("Congé ajouté avec succès.");
                    }

                    // Mettre à jour le solde de congé
                    try (PreparedStatement updateStmt = DBConnexion.getConnexion().prepareStatement(updateSoldeSql)) {
                        updateStmt.setInt(1, (int) daysBetween);
                        updateStmt.setInt(2, e.getId_employe());
                        updateStmt.executeUpdate();
                    }
                }
            } else {
                System.err.println("Employé introuvable.");
            }
        } catch (SQLException | ClassNotFoundException exception) {
            System.err.println("Erreur lors de l'ajout du congé : " + exception.getMessage());
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
                System.out.println("Congé supprimé avec succès.");
            }
        } catch (SQLException | ClassNotFoundException exception) {
            System.err.println("Échec de la suppression du congé : " + exception.getMessage());
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
                System.out.println("Congé mis à jour avec succès.");
            }
        } catch (SQLException | ClassNotFoundException exception) {
            System.err.println("Échec de la mise à jour du congé : " + exception.getMessage());
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
                System.out.println("Aucun congé trouvé.");
            } else {
                System.out.println("Liste des congés :");
                for (Holiday holiday : holidays) {
                    System.out.println(holiday);
                }
            }

        } catch (SQLException | ClassNotFoundException ex) {
            System.err.println("Échec de la récupération des congés : " + ex.getMessage());
        }
        return holidays; // Retourne une liste vide si une erreur se produit
    }
}
