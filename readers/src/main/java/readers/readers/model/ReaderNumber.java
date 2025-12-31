package readers.readers.model;

import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
public class ReaderNumber implements Serializable {

    private String readerNumber;

    public ReaderNumber(int year, int number) {
        this.readerNumber = year + "/" + number;
    }

    public ReaderNumber(int number) {
        this.readerNumber = LocalDate.now().getYear() + "/" + number;
    }

    protected ReaderNumber() { }

    @Override
    public String toString() {
        return this.readerNumber;
    }
}
