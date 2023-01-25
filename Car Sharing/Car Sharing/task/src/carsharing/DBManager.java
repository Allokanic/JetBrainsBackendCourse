package carsharing;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class DBManager {
    private static final Scanner sc = new Scanner(System.in);

    public static ArrayList<String> getAllDataFromCompany() {
        ArrayList<String> result = new ArrayList<>();
        try {
            ResultSet allData = DBRep.getAllDataFromCompany();
            while (allData.next()) {
                result.add(allData.getInt("id") + ". " + allData.getString("name"));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public static void addCompany() {
        System.out.println("Enter the company name:");
        DBRep.addCompany(sc.nextLine());
        System.out.println("The company was created!\n");
    }

    public static void addCar(int company_id) {
        System.out.println("Enter the car name:");
        DBRep.addCar(sc.nextLine(), company_id);
        System.out.println("The car was added!\n");
    }

    public static ArrayList<String> getCarList(int company_id) {
        ArrayList<String> result = new ArrayList<>();
        try {
            ResultSet allData = DBRep.getAllDataFromCars(company_id);
            int i = 1;
            while (allData.next()) {
                result.add(i + ". " + allData.getString("name"));
                i++;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
