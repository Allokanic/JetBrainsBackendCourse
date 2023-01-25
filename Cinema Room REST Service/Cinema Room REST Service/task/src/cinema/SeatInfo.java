package cinema;

public class SeatInfo {
    private int row;
    private int column;
    private int price;

    public SeatInfo(int row, int column, int price) {
        this.row = row;
        this.column = column;
        this.price = price;
    }

    public SeatInfo(SeatInfoRequest object) {
        this.row = object.getRow();
        this.column = object.getColumn();
    }

    public void setRow(int row) {
        this.row = row;
    }
    public void setColumn(int column) {
        this.column = column;
    }

    public int getRow() {
        return row;
    }
    public int getColumn() {
        return column;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}

