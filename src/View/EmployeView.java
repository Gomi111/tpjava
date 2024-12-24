package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import DAO.EmployeImpl;
import DAO.HolidayDAOImpl;
import Model.Employe.Role;
import Model.Holiday.HolidayType;
import Model.Employe;
import Model.Holiday;
import Model.Employe.Poste;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EmployeView extends JFrame {

    private EmployeImpl dao;


    // private static final String Int = null;
    // Déclaration des panneaux et composants de l'interface utilisateur
    private JPanel mainPanel, topPanel, centerPanel, bottomPanel;
    private JLabel lblNom, lblPrenom, lblEmail, lblTelephone, lblSalaire, lblPoste, lblRole;
    private JTextField txtNom, txtPrenom, txtEmail, txtTelephone, txtSalaire;
    private JComboBox<Poste> cbPoste;
    private JComboBox<Role> cbRole;
    public JTable table;

    public JButton btnAjouter;
    public JButton btnModifier;
    public JButton btnSupprimer;
    public JButton btnAfficher;

    public JButton btnAjouter2;
    public JButton btnModifier2;
    public JButton btnSupprimer2;
    public JButton btnAfficher2;

    private JPanel mainPanel2, topPanel2, centerPanel2, bottomPanel2;
    private JLabel lblEmploye, lblType, lblStartDate, lblEndDate;
    public JTextField txtStartDate;
    public JTextField txtEndDate;
    public JTextField idEmploye;

    public JComboBox<String> cbEmploye;
    public JComboBox<HolidayType> cbType;
    //public JList<Employe> listE;
    public JTable table2;

    public EmployeView() {

        // Configuration de la fenêtre principale
        setTitle("Gestion des Employés");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialisation des panneaux
        mainPanel = new JPanel(new BorderLayout());
        mainPanel2 = new JPanel(new BorderLayout());

        topPanel = new JPanel(new GridLayout(8, 2, 10, 10));
        centerPanel = new JPanel(new BorderLayout());
        bottomPanel = new JPanel(new GridLayout(1, 4, 10, 10));

        topPanel2 = new JPanel(new GridLayout(5, 2, 10, 10));
        centerPanel2 = new JPanel(new BorderLayout());
        bottomPanel2 = new JPanel(new GridLayout(1, 4, 10, 10));

        // Initialisation des étiquettes
        lblNom = new JLabel("Nom:");
        lblPrenom = new JLabel("Prénom:");
        lblEmail = new JLabel("Email:");
        lblTelephone = new JLabel("Téléphone:");
        lblSalaire = new JLabel("Salaire:");
        lblPoste = new JLabel("Poste:");
        lblRole = new JLabel("Rôle:");

        lblEmploye = new JLabel("Nom Employé:");
        lblType = new JLabel("Type:");
        lblStartDate = new JLabel("Date de début:");
        lblEndDate = new JLabel("Date de fin:");

        // Initialisation des champs d'entrée
        txtNom = new JTextField();
        txtPrenom = new JTextField();
        txtEmail = new JTextField();
        txtTelephone = new JTextField();
        txtSalaire = new JTextField();
        txtStartDate = new JTextField();
        txtEndDate = new JTextField();
        //List<Employe> EmployeN =new ArrayList <>();
        //EmployeN= EmployeImpl.findAll();
        // idEmploye=new JTextField();
        //listE = new JList<>(EmployeN);


        cbRole = new JComboBox<>(Role.values());
        cbPoste = new JComboBox<>(Poste.values());

        cbEmploye = new JComboBox<>(this.findAllE());
        cbType = new JComboBox<>(HolidayType.values());

        table = new JTable(new DefaultTableModel(new Object[]{"ID", "Nom", "Prénom", "Email", "Téléphone", "Salaire", "Poste", "Rôle"}, 0));
        JScrollPane scrollPane = new JScrollPane(table);

        table2 = new JTable(new DefaultTableModel(new Object[]{"ID", "nom du Employe", "Type", "Date de début", "Date de fin"}, 0));
        JScrollPane scrollPane2 = new JScrollPane(table2);

        btnAjouter = new JButton("Ajouter");
        btnModifier = new JButton("Modifier");
        btnSupprimer = new JButton("Supprimer");
        btnAfficher = new JButton("Afficher");

        btnAjouter2 = new JButton("Ajouter");
        btnModifier2 = new JButton("Modifier");
        btnSupprimer2 = new JButton("Supprimer");
        btnAfficher2 = new JButton("Afficher");

        topPanel.add(lblNom);
        topPanel.add(txtNom);
        topPanel.add(lblPrenom);
        topPanel.add(txtPrenom);
        topPanel.add(lblEmail);
        topPanel.add(txtEmail);
        topPanel.add(lblTelephone);
        topPanel.add(txtTelephone);
        topPanel.add(lblSalaire);
        topPanel.add(txtSalaire);
        topPanel.add(lblRole);
        topPanel.add(cbRole);
        topPanel.add(lblPoste);
        topPanel.add(cbPoste);
        topPanel.add(new JLabel("Listes des Employes :"));


        topPanel2.add(lblEmploye);
        topPanel2.add(cbEmploye);
        topPanel2.add(lblType);
        topPanel2.add(cbType);
        topPanel2.add(lblStartDate);
        topPanel2.add(txtStartDate);
        topPanel2.add(lblEndDate);
        topPanel2.add(txtEndDate);
        topPanel2.add(new JLabel("Listes des Conges :"));


        centerPanel.add(scrollPane, BorderLayout.CENTER);
        centerPanel2.add(scrollPane2, BorderLayout.CENTER);

        bottomPanel.add(btnAjouter);
        bottomPanel.add(btnModifier);
        bottomPanel.add(btnSupprimer);
        bottomPanel.add(btnAfficher);

        bottomPanel2.add(btnAjouter2);
        bottomPanel2.add(btnModifier2);
        bottomPanel2.add(btnSupprimer2);
        bottomPanel2.add(btnAfficher2);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        mainPanel2.add(topPanel2, BorderLayout.NORTH);
        mainPanel2.add(centerPanel2, BorderLayout.CENTER);
        mainPanel2.add(bottomPanel2, BorderLayout.SOUTH);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Employé", mainPanel);
        tabbedPane.addTab("Congés", mainPanel2);

        add(tabbedPane);
        setVisible(true);
    }

    public int getId(JTable table) {
        int selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Veuillez sélectionner une ligne !");
            return -1;
        }
        return (int) table.getValueAt(selectedRow, 0);

    }


    public String[] findAllE() {
        dao= new EmployeImpl();
        List<Employe> EmployeE = new ArrayList<>();
        EmployeE = dao.findAll();
        System.out.println("");
        String[] tab = new String[EmployeE.size()];
        for (int i=0;i<EmployeE.size();i++) {

            Employe emp = EmployeE.get(i);
            System.out.println("emp"+emp.getNom());
            String nomPrenom = emp.getNom() + " " + emp.getPrenom();
            tab[i]=nomPrenom;
        }
        return  tab;
    }
    /*
    public List<String> findAllE() {
        List<String> EmployeE = new ArrayList<>();
        EmployeE = dao.findNOM();

        for (String E : EmployeE) {
            String nomPrenom = E.getNom();
            cbEmploye.addItem(nomPrenom);
        }
        return EmployeE;
    }
    */
    public String getNom() {
        return txtNom.getText();
    }

    public String getPrenom() {
        return txtPrenom.getText();
    }
    public String getEmail() {
        return txtEmail.getText();
    }
    public String getTelephone() {
        return txtTelephone.getText();
    }
    public double getSalaire() {
        return Double.parseDouble(txtSalaire.getText());
    }
    public Role getRole() {
        Role r=(Role) cbRole.getSelectedItem();
        return r;
    }
    public Poste getPoste() {
        Poste p=(Poste) cbPoste.getSelectedItem();
        return p ;
    }


    public int getEmploye() {
        try {
            return Integer.parseInt(idEmploye.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "L'ID de l'employé n'est pas valide.");
            return -1;
        }
    }




    public String getStartDate() {
        return txtStartDate.getText();
    }

    public String getEndDate() {
        return txtEndDate.getText();
    }


    public static void main(String[] args) {
        new EmployeView();
    }
}
