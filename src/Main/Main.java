package Main;

import DAO.EmployeImpl;
import DAO.HolidayDAOImpl;
import Model.EmployeModel;
import Model.HolidayModel;
import View.EmployeView;
import controller.EmployeController;

public class Main {

    public static void main(String[] args) {
        EmployeView View = new EmployeView();
        EmployeImpl DAO = new EmployeImpl();
        HolidayDAOImpl DAO2 = new HolidayDAOImpl();
        EmployeModel Model = new EmployeModel(DAO);
        HolidayModel Model2 = new HolidayModel(DAO2);

        new EmployeController(Model, View, Model2);

        View.setVisible(true);

    }


}