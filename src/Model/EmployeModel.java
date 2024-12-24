package Model;

import java.sql.SQLException;

import javax.management.relation.Role;
import javax.swing.JOptionPane;

import DAO.EmployeImpl;
import Model.Employe.Poste;
public class  EmployeModel  {
    private EmployeImpl dao;

    public EmployeModel(EmployeImpl dao) {
        this.dao=dao;
    }

    //logique Metier
    public boolean addEmploye(String nom, String pronom, String email, String telephone, double salaire, Employe.Role role, Poste poste) {

        if(salaire<=0) {
            JOptionPane.showMessageDialog(null, "Salaire invalide il est infèrieure de zéro !!!!!!!");

            return false;}

        if (email == null || !email.contains("@")) {
            JOptionPane.showMessageDialog(null, "L'email n'est pas valide Ajouter '@' !!!!!!!");


            return false;
        }

        Employe NvEmploye = new Employe(nom,pronom,email,telephone,salaire,role,poste);
        dao.add(NvEmploye);
        return true;

    }

    public boolean UpdateEmploye(int id , String nom, String pronom, String email, String telephone, double salaire, Employe.Role role, Poste poste) {
        //Employe E=dao.findById(id);

        if(salaire<=0) {
            JOptionPane.showMessageDialog(null, "Salaire invalide il est infèrieure de zéro !!!!!!!");

            return false;}

        if (email == null || !email.contains("@")) {
            JOptionPane.showMessageDialog(null, "L'email n'est pas valide Ajouter '@' !!!!!!!");


            return false;
        }

        Employe E = new Employe(id,nom,pronom,email,telephone,salaire,role,poste);
        dao.update(E);
        return true;
    }


    public boolean deleteEmploye(int id) {
        //Employe E=dao.findById(id);

        dao.delete(id);
        return true;

    }

    public boolean afficherEmployes() {

        dao.findAll();
        return true;
    }


}
