package pt.psoft.g1.psoftg1.readermanagement.model;

import org.springframework.data.annotation.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import pt.psoft.g1.psoftg1.genremanagement.model.GenreNoSQL;
import pt.psoft.g1.psoftg1.shared.services.IDGeneratorService;
import pt.psoft.g1.psoftg1.usermanagement.model.ReaderNoSQL;

import java.util.List;

@Getter
@Setter
@Document(collection = "reader_details")
public class ReaderDetailsNoSQL {

    @Id
    private String id;
    private ReaderNoSQL reader;

    private String readerNumber;
    private String birthDate;
    private String phoneNumber;
    private boolean gdprConsent;
    private boolean marketingConsent;
    private boolean thirdPartySharingConsent;
    private List<GenreNoSQL> interestList;

    public ReaderDetailsNoSQL(ReaderDetails readerDetails) {
        this.id = IDGeneratorService.generateIdNoSQL();
        this.reader = ReaderNoSQL.fromDomain(readerDetails.getReader());
        this.readerNumber = readerDetails.getReaderNumber();
        this.birthDate = readerDetails.getBirthDate().toString();
        this.phoneNumber = readerDetails.getPhoneNumber();
        this.gdprConsent = readerDetails.isGdprConsent();
        this.marketingConsent = readerDetails.isMarketingConsent();
        this.thirdPartySharingConsent = readerDetails.isThirdPartySharingConsent();
        this.interestList = readerDetails.getInterestList() != null
                ? readerDetails.getInterestList().stream().map(GenreNoSQL::fromDomain).toList()
                : List.of();
    }

    protected ReaderDetailsNoSQL() {}

    public ReaderDetails toDomain() {
        return new ReaderDetails(
                Integer.parseInt(this.readerNumber.split("/")[1]),
                this.reader != null ? this.reader.toDomain() : null,
                this.birthDate,
                this.phoneNumber,
                this.gdprConsent,
                this.marketingConsent,
                this.thirdPartySharingConsent,
                null,
                this.interestList != null ? this.interestList.stream().map(GenreNoSQL::toDomain).toList() : List.of()
        );
    }
    public static ReaderDetailsNoSQL fromDomain(ReaderDetails readerDetails) {
        return new ReaderDetailsNoSQL(readerDetails);
    }
}
