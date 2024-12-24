package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Model.Employe.Role;

import Model.Employe;
import Model.Employe.Poste;


public class EmployeImpl implements GenericDAOI<Employe> {
    private static Connection conn;

    public EmployeImpl() {
        this.conn = connexion.getConnexion();
    }

    // Ajouter un nouvel employé dans la base de données
    @Override
    public void add(Employe E) {
        String Query = "INSERT INTO Employe(nom, prenom, email, telephone, salaire, role, poste) VALUES(?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(Query)) {
            stmt.setString(1, E.getNom());
            stmt.setString(2, E.getPrenom());
            stmt.setString(3, E.getEmail());
            stmt.setString(4, E.getTelephone());
            stmt.setDouble(5, E.getSalaire());
            stmt.setString(6, E.getRole().name());
            stmt.setString(7, E.getPoste().name());
            stmt.executeUpdate();
            System.out.println("Employé ajouté avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de l'employé !");


        }
    }


    // Récupérer la liste de tous les employés dans la base de données
    @Override
    public  List<Employe> findAll() {
        List<Employe> employes = new ArrayList<>();
        String query = "SELECT * FROM Employe";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                employes.add(new Employe(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("telephone"),
                        rs.getDouble("salaire"),
                        Role.valueOf(rs.getString("role")),
                        Poste.valueOf(rs.getString("poste"))
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération de tous les employés !!!!!");

        }
        return employes;
    }

    // Mettre à jour les informations d'un employé existant
    @Override
    public void update(Employe E) {
        String query = "UPDATE Employe SET nom = ?, prenom = ?, email = ?, telephone = ?, salaire = ?, role = ?, poste = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, E.getNom());
            stmt.setString(2, E.getPrenom());
            stmt.setString(3, E.getEmail());
            stmt.setString(4, E.getTelephone());
            stmt.setDouble(5, E.getSalaire());
            stmt.setString(6, E.getRole().name());
            stmt.setString(7, E.getPoste().name());
            stmt.setInt(8, E.getId());
            stmt.executeUpdate();

            System.out.println("Employe modifier avec succès !!!");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modefication de l'employe !!!!!");

        }
    }

    // Supprimer un employé de la base de données par son ID
    @Override
    public void delete(int id) {
        String query = "DELETE FROM Employe WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1,id);
            stmt.executeUpdate();
            System.out.println("Employe supprimé avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de l'employe !!!!");

        }
    }

    public Employe findById(int id) {
        String query = "SELECT * FROM Employe WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1,id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Employe(
                            rs.getInt("id"),
                            rs.getString("nom"),
                            rs.getString("prenom"),
                            rs.getString("email"),
                            rs.getString("telephone"),
                            rs.getDouble("salaire"),
                            Role.valueOf(rs.getString("role")),
                            Poste.valueOf(rs.getString("poste"))
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  null;

    }


    public  List<String> findNOM() {
        List<String> employes = new ArrayList<>();
        String query = "SELECT Nom FROM Employe";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            for(String E: employes) {
                employes.add(rs.getString("nom"));
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération de tous les employés !!!!!");

        }
        return employes;
    }



}
