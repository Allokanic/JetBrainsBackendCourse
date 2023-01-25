package cinema;

public class SeatInfoRequest {
    private int row;
    private int column;

    public SeatInfoRequest(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public SeatInfoRequest() {
        row = column = 0;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }
}

