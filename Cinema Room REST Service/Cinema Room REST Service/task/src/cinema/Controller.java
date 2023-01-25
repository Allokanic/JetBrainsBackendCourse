package cinema;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
class CinemaController {
    private final CinemaService service = new CinemaService();

    @GetMapping("/seats")
    public Map<Object, Object> getFullData() {
        return Map.of(
                "total_rows", service.getRowsNumber(),
                "total_columns", service.getColumnsNumber(),
                "available_seats", service.getAllSeats());
    }

    @PostMapping("/purchase")
    public ResponseEntity<Object> checkSeat(@RequestBody SeatInfoRequest request) {
        if (request.getRow() > service.getRowsNumber() ||
                request.getColumn() > service.getColumnsNumber() ||
                request.getColumn() < 1 || request.getRow() < 1) {
            return new ResponseEntity<>(Map.of("error", "The number of a row or a column is out of bounds!"), HttpStatus.BAD_REQUEST);
        } else if (!service.checkSeat(request.getRow(), request.getColumn())) {
            return new ResponseEntity<>(Map.of("error", "The ticket has been already purchased!"), HttpStatus.BAD_REQUEST);
        } else {
            SeatInfo result = new SeatInfo(request);
            result.setPrice(service.getSeatPrice(request.getRow(), request.getColumn()));
            String token = service.bookSeat(result);
            Map<String, Object> response = Map.of("token", token, "ticket", result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @PostMapping("/return")
    public ResponseEntity<Object> returnTicket(@RequestBody Token token) {
        SeatInfo returned = service.returnTicket(token.getToken());
        if (returned != null) {
            Map<Object, Object> response = Map.of("returned_ticket", returned);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Map.of("error", "Wrong token!"), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/stats")
    public ResponseEntity<Object> getStatistic(@RequestParam Optional<String> password) {
        if (password.isPresent() && service.checkPassword(password.get())) {
            return new ResponseEntity<>(service.getStatistics(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Map.of("error", "The password is wrong!"), HttpStatus.UNAUTHORIZED);
        }
    }
}
