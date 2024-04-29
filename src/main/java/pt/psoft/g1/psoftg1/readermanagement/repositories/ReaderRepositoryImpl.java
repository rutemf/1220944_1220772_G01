package pt.psoft.g1.psoftg1.readermanagement.repositories;

import org.springframework.stereotype.Service;
import pt.psoft.g1.psoftg1.readermanagement.model.EmailAddress;
import pt.psoft.g1.psoftg1.readermanagement.model.PhoneNumber;
import pt.psoft.g1.psoftg1.readermanagement.model.Reader;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderNumber;
import pt.psoft.g1.psoftg1.readermanagement.services.CreateReaderRequest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class ReaderRepositoryImpl implements ReaderRepository{
    private List<Reader> readerList = new ArrayList<>();

    @Override
    public Reader findByPhoneNumber(PhoneNumber phoneNumber) {
        for(int i = 0; i < readerList.size(); i++) {
            Reader reader = readerList.get(i);
            PhoneNumber readerPhoneNumber = reader.getPhoneNumber();

            if(phoneNumber.toString().equals(readerPhoneNumber.toString())) {
                return reader;
            }
        }

        return null;
    }

    @Override
    public Reader findByReaderNumber(ReaderNumber readerNumber) {
        for(int i = 0; i < readerList.size(); i++) {
            Reader reader = readerList.get(i);
            ReaderNumber readerReaderNumber = reader.getReaderNumber();

            if(readerNumber.toString().equals(readerReaderNumber.toString())) {
                return reader;
            }
        }
        return null;
    }

    @Override
    public Reader save(Reader reader) throws Exception {
        try {
            if(findByReaderNumber(reader.getReaderNumber()) != null) {
                throw new Exception("A reader with provided reader number is already registered");
            }

            if(findByPhoneNumber(reader.getPhoneNumber()) != null) {
                throw new Exception("A reader with provided phone number is already registered");
            }

            readerList.add(reader);
        } catch(Exception e) {
            throw new Exception("Unable to save given Reader: " + e.getMessage());
        }

        return reader;
    }

    @Override
    public List<Reader> findAll() {
        return readerList;
    }
}
