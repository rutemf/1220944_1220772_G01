package readers.readers.api;

import org.springframework.stereotype.Component;
import readers.readers.model.ReaderDetails;
import readers.readers.services.ReaderView;
import readers.genres.model.Genre;

import java.util.stream.Collectors;

@Component
public class ReaderViewMapper {
    public ReaderView toReaderView(ReaderDetails readerDetails, String email, String fullName) {
        if (readerDetails == null) {
            return null;
        }

        ReaderView view = new ReaderView();

        view.setReaderId(readerDetails.getReaderId());

        if (readerDetails.getReaderNumber() != null) {
            view.setReaderNumber(readerDetails.getReaderNumber().toString());
        }

        view.setBirthDate(readerDetails.getBirthDate().toString());

        if (readerDetails.getInterestList() != null) {
            view.setInterestList(readerDetails.getInterestList().stream()
            .map(Genre::getGenre).collect(Collectors.toList()));
        }

        view.setEmail(email);
        view.setFullName(fullName);

        return view;
    }
}