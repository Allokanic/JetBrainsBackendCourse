package cinema;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
class CinemaService {

    private final CinemaRepository rep = new CinemaRepository();

    public List<SeatInfo> getSeats() {
        return rep.getAvailableSeats();
    }

    public List<SeatInfo> getAllSeats() {
        return rep.getAllSeats();
    }

    public int getRowsNumber(){
        return rep.getRowsNumber();
    }

    public int getColumnsNumber() {
        return rep.getColumnsNumber();
    }

    public boolean checkSeat(int row, int col) {
        return rep.checkSeat(row, col);
    }

    public int getSeatPrice(int row, int col) {
        return rep.getSeatPrice(row, col);
    }

    public String bookSeat(SeatInfo position) {
        String uuid = UUID.randomUUID().toString();
        rep.setToken(uuid, position);
        rep.bookSeat(position);
        return uuid;
    }

    public SeatInfo returnTicket(String token) {
        return rep.returnTicket(token);
    }

    public boolean checkPassword(String password) {
        return rep.checkPassword(password);
    }

    public Map<String, Integer> getStatistics() {
        return rep.getStatistics();
    }
}
