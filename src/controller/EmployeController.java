package controller;



import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import DAO.EmployeImpl;
import DAO.HolidayDAOImpl;
import Model.Employe;
import Model.Employe.Poste;
import Model.Employe.Role;
import Model.Holiday.HolidayType;
import Model.EmployeModel;
import Model.Holiday;
import Model.HolidayModel;
import View.EmployeView;

public class EmployeController {
    private EmployeModel model;
    private EmployeView view;
    private HolidayModel model2;

    public EmployeController(EmployeModel model,EmployeView view,HolidayModel model2) {
        this.model=model;
        this.view=view;
        this.model2=model2;

        this.view.btnAjouter.addActionListener(e->addEmploye());
        this.view.btnModifier.addActionListener(e->updateEmploye());
        this.view.btnAfficher.addActionListener(e -> afficherEmploye());
        this.view.btnSupprimer.addActionListener(e -> supprimerEmploye());

        this.view.btnAjouter2.addActionListener(e->ajouterHoliday());
        this.view.btnModifier2.addActionListener(e->modifierHoliday());
        this.view.btnAfficher2.addActionListener(e -> afficherHolidays());
        this.view.btnSupprimer2.addActionListener(e -> supprimerHoliday());
    }

    //Méthode pour ajouter un employé
    private void addEmploye() {
        String nom=view.getNom();
        String prenom=view.getPrenom();
        String email=view.getEmail();
        String telephone=view.getTelephone();
        double salaire =view.getSalaire();
        Poste poste=view.getPoste();
        Role role=view.getRole();


        boolean addEmploye=model.addEmploye(nom, prenom, email, telephone,salaire, role, poste);
        afficherEmploye();

        if(addEmploye) JOptionPane.showMessageDialog(null, "Employé Ajouté avec succès !");
        else JOptionPane.showMessageDialog(null, "Echec d'ajout d'employe !!!!!");
    }

    //Méthode pour modifier un employé
    private void updateEmploye() {


        int id =view.getId(view.table);

        String nom=view.getNom();
        String prenom=view.getPrenom();
        String email=view.getEmail();
        String telephone=view.getTelephone();
        double salaire =view.getSalaire();
        Poste poste=view.getPoste();
        Role role=view.getRole();



        boolean seccess= model.UpdateEmploye(id,nom, prenom, email, telephone, salaire, role, poste);
        afficherEmploye();


        if (seccess) JOptionPane.showMessageDialog(null, "Employé modifié avec succès !!!!");
        else JOptionPane.showMessageDialog(null, "modification echouee !");

    }

    // Méthode pour afficher tous les employés dans la table
    public void afficherEmploye() {
        EmployeImpl employeImpl = new EmployeImpl();
        List<Employe> employes = employeImpl.findAll();

        DefaultTableModel Tab = (DefaultTableModel) view.table.getModel();
        Tab.setRowCount(0);

        for (Employe employe : employes) {
            Tab.addRow(new Object[]{
                    employe.getId(),
                    employe.getNom(),
                    employe.getPrenom(),
                    employe.getEmail(),
                    employe.getTelephone(),
                    employe.getSalaire(),
                    employe.getRole(),
                    employe.getPoste()
            });
        }
        boolean success =model.afficherEmployes();
        if (success) JOptionPane.showMessageDialog(null, "Employées afficher avec succès !!!!");
        else JOptionPane.showMessageDialog(null, "Affichage echoue !");

    }

    //Méthode pour supprimer un employé
    public void supprimerEmploye() {

        int id =view.getId(view.table);


        model.deleteEmploye(id);
        afficherEmploye();
        JOptionPane.showMessageDialog(null, "Employé supprimé avec succès !");


    }



    private void ajouterHoliday() {
        try {
            // Récupérer les données depuis les champs de saisie
            int employe = view.getEmploye();
            HolidayType type = (HolidayType) view.cbType.getSelectedItem();
            String startDate = view.txtStartDate.getText();
            String endDate = view.txtEndDate.getText();
            // Validation des champs
            if (employe == 0) {
                JOptionPane.showMessageDialog(view, "Veuillez sélectionner un employé.");
                return;
            }
            if (startDate.isEmpty() || endDate.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Veuillez remplir les dates de début et de fin.");
                return;
            }

            boolean isAdded=model2.addHoliday(employe, startDate, endDate, type);

            if (isAdded) {
                JOptionPane.showMessageDialog(view, "Congé ajouté avec succès.");
                //afficherHolidays();
            } else {
                JOptionPane.showMessageDialog(view, "Échec de l'ajout du congé.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Erreur : " + ex.getMessage());
        }
    }


    private void modifierHoliday() {
        try {
            // Vérifier si une ligne est sélectionnée dans la table
            int selectedRow = view.table2.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(view, "Veuillez sélectionner un congé à modifier.");
                return;
            }/*

        // Récupérer l'ID du congé sélectionné (depuis la table)
      //  int holidayId = (int) view.table2.getValueAt(selectedRow, 0);
*/
            // Récupérer les nouvelles données depuis les champs de saisie
            int id = (int) view.table2.getValueAt(selectedRow, 0); // Employé sélectionné
            int employe = view.getEmploye();
            String startDate = view.txtStartDate.getText().trim(); // Nouvelle date de début
            String endDate = view.txtEndDate.getText().trim(); // Nouvelle date de fin
            HolidayType type = (HolidayType) view.cbType.getSelectedItem(); // Nouveau type de congé

            // Appeler la méthode de modification dans le modèle
            boolean isUpdated = model2.updateHoliday(id,employe, type, startDate, endDate);

            if (isUpdated) {
                JOptionPane.showMessageDialog(view, "Congé modifié avec succès.");
                // Actualiser la vue si nécessaire
                // afficherHolidays();
            } else {
                JOptionPane.showMessageDialog(view, "Échec de la modification du congé.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Erreur : " + ex.getMessage());
        }
    }


    public void afficherHolidays() {
        HolidayDAOImpl dao = new HolidayDAOImpl();
        List<Holiday> Holidays = dao.findAll();

        DefaultTableModel Tab = (DefaultTableModel) view.table2.getModel();
        Tab.setRowCount(0);

        for (Holiday H : Holidays) {
            Tab.addRow(new Object[]{
                    H.getIdC(),
                    H.getIdE(),
                    H.getType(),
                    H.getStartDate(),
                    H.getEndDate()


            });
        }
		 /* boolean success =model2.findAllHoliday();
		  if (success) JOptionPane.showMessageDialog(null, "Employées afficher avec succès !!!!");
	      else JOptionPane.showMessageDialog(null, "Affichage echoue !");
	  */
    }

    public void supprimerHoliday() {

        int id =view.getId(view.table2);


        model2.deleteHoliday(id);
        JOptionPane.showMessageDialog(null, "Employé supprimé avec succès !");


    }

}
