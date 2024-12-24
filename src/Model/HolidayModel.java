package Model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import javax.swing.text.View;

import DAO.EmployeImpl;
import DAO.HolidayDAOImpl;
import Model.Holiday.HolidayType;

public class HolidayModel {
    private HolidayDAOImpl dao;

    public HolidayModel(HolidayDAOImpl dao) {
        this.dao=dao;
    }
	/*
private boolean ValidateUpdateBalance(Employe E, long DifferenceDay) {
	Employe SelectedOne= EmployeImpl.findById(E.getId());
	if(SelectedOne==null) {
	return false;
	}
	int currentBalance = SelectedOne.getHolidayBalance();
	if(DifferenceDay>0) {
		if(currentBalance<DifferenceDay)
			System.out.println("Insuffisant holidayBalance");
		return false;
	}
	SelectedOne.setHolidayBalance(currentBalance-(int)DifferenceDay);

	if(DifferenceDay<0) {
	  System.out.println("Invalide number");
	  //return false;
    }
  // EmployeImpl.update(SelectedOne, E.getId());
   return true;
}

/*
public boolean addHoliday(int E,String StrartDate,String EndDate,HolidayType type) {
	long nbrOfDays= daysBetween(StrartDate,EndDate);

	if(!ValidateUpdateBalance(E,nbrOfDays)) return false;

	Holiday NvHoliday=new Holiday( E,StrartDate,EndDate,type);
	dao.add(NvHoliday);
	return true;
}
public static long daysBetween(String startDate, String endDate) {
    return ChronoUnit.DAYS.between(LocalDate.parse(startDate), LocalDate.parse(endDate));
}
/*
public boolean updateHoliday(int E, String StartDate, String EndDate, HolidayType type) {
    long nbrOfDays = daysBetween(StartDate, EndDate);

    // Vérifier si le solde de congé est suffisant avant de procéder à la mise à jour
    if (!ValidateUpdateBalance(E, nbrOfDays)) return false;

    // Créer un objet Holiday avec les nouvelles informations
    Holiday updatedHoliday = new Holiday(E, StartDate, EndDate, type);

    try {
        // Appeler la méthode update du DAO pour mettre à jour l'objet Holiday dans la base de données
      //  dao.update(updatedHoliday, E.getId());

        // Si tout se passe bien, retourner true
        return true;
    } catch (Exception ex) {
        // En cas d'échec de la mise à jour, afficher un message d'erreur et retourner false
        System.out.println("Erreur lors de la mise à jour du congé : " + ex.getMessage());
        return false;
    }
}
*/

    public boolean addHoliday(int employe, String strartDate, String endDate, HolidayType type) {

        Holiday NvHoliday=new Holiday( employe,type,strartDate,endDate);
        dao.add(NvHoliday);
        return true;

    }



    public boolean updateHoliday(int idC,int employe, HolidayType type, String strartDate, String endDate) {
        Holiday H=new Holiday( idC,employe,type,strartDate,endDate);
        dao.update(H);
        return true;
    }

    public boolean deleteHoliday(int idC) {
        dao.delete(idC);
        return true;
    }


    public boolean findAllHoliday() {

        dao.findAll();
        return true;
    }


    public Holiday getHolidayById(int idC) {
        return dao.findById(idC);
    }




}


