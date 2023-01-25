package cinema;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CinemaRepository {
    private final String password = "super_secret";
    private int currentIncome = 0;
    private int availableSeats = 81;
    private int numberOfBookedSeats = 0;
    private final int rowsNumber = 9;
    private final int columnsNumber = 9;
    private final char[][] seats = new char[rowsNumber][columnsNumber];
    private final int[][] prices = new int[rowsNumber][columnsNumber];
    private final List<SeatInfo> allSeats = new ArrayList<>();
    private final Map<String, SeatInfo> tokenContainer = new ConcurrentHashMap<>();

    public CinemaRepository() {
        for(int i = 0; i < 9; ++i) {
            for(int j = 0; j < 9; ++j){
                seats[i][j] = 'C';
                if (i < 4) {
                    prices[i][j] = 10;
                } else {
                    prices[i][j] = 8;
                }
                allSeats.add(new SeatInfo(i+1, j+1, prices[i][j]));
            }
        }
    }

    public List<SeatInfo> getAvailableSeats() {
        List<SeatInfo> result = new ArrayList<>();
        for(int i = 0; i < 9; ++i) {
            for(int j = 0; j < 9; ++j) {
                if (seats[i][j] == 'C') {
                    result.add(new SeatInfo(i+1, j+1, prices[i][j]));
                }
            }
        }
        return result;
    }

    public List<SeatInfo> getAllSeats() {
        return allSeats;
    }

    public int getRowsNumber() {

        return rowsNumber;
    }

    public int getColumnsNumber() {

        return columnsNumber;
    }

    public boolean checkSeat(int row, int col) {

        return seats[row-1][col-1] == 'C';
    }

    public int getSeatPrice(int row, int col) {

        return row > 4 ? 8 : 10;
    }

    public void bookSeat(SeatInfo position) {

        seats[position.getRow()-1][position.getColumn()-1] = 'B';
        currentIncome += position.getPrice();
        availableSeats--;
        numberOfBookedSeats++;
    }

    public void setToken(String uuid, SeatInfo position) {

        tokenContainer.put(uuid, position);
    }

    public SeatInfo returnTicket(String token) {
        SeatInfo result = tokenContainer.get(token);
        tokenContainer.remove(token);
        if (result != null) {
            seats[result.getRow()-1][result.getColumn()-1] = 'C';
            currentIncome -= result.getPrice();
            availableSeats++;
            numberOfBookedSeats--;
        }
        return result;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    public Map<String, Integer> getStatistics() {
        return Map.of(
                "current_income", currentIncome,
                "number_of_available_seats", availableSeats,
                "number_of_purchased_tickets", numberOfBookedSeats);
    }
}
