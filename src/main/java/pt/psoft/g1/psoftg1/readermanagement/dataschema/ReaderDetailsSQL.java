package pt.psoft.g1.psoftg1.readermanagement.dataschema;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pt.psoft.g1.psoftg1.genremanagement.dataschema.GenreSQL;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetails;
import pt.psoft.g1.psoftg1.shared.services.IDGeneratorService;
import pt.psoft.g1.psoftg1.usermanagement.dataschema.ReaderSQL;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Table(name = "READER_DETAILS")
public class ReaderDetailsSQL {

    @Id
    private String id;

    @OneToOne
    @JoinColumn(name = "reader_id")
    private ReaderSQL reader;

    private String readerNumber;
    private String birthDate;
    private String phoneNumber;
    private boolean gdprConsent;
    private boolean marketingConsent;
    private boolean thirdPartySharingConsent;

    @ManyToMany
    private List<GenreSQL> interestList;

    public ReaderDetailsSQL(ReaderDetails readerDetails) {
        this.id = IDGeneratorService.generateIdSQL();
        this.reader = ReaderSQL.fromDomain(readerDetails.getReader());
        this.readerNumber = readerDetails.getReaderNumber();
        this.birthDate = readerDetails.getBirthDate().toString();
        this.phoneNumber = readerDetails.getPhoneNumber();
        this.gdprConsent = readerDetails.isGdprConsent();
        this.marketingConsent = readerDetails.isMarketingConsent();
        this.thirdPartySharingConsent = readerDetails.isThirdPartySharingConsent();
        this.interestList = readerDetails.getInterestList().stream().map(GenreSQL::fromDomain).collect(Collectors.toList());
    }

    // JPA
    protected ReaderDetailsSQL() { }

    public ReaderDetails toDomain() {
        return new ReaderDetails(
                Integer.parseInt(this.readerNumber.split("/")[1]),
                this.reader.toDomain(),
                this.birthDate,
                this.phoneNumber,
                this.gdprConsent,
                this.marketingConsent,
                this.thirdPartySharingConsent,
                null,
                this.interestList.stream().map(GenreSQL::toDomain).collect(Collectors.toList())
        );
    }

    public static ReaderDetailsSQL fromDomain(ReaderDetails readerDetails) {
        return new ReaderDetailsSQL(readerDetails);
    }
}
