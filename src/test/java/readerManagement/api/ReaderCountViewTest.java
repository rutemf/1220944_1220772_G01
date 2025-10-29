package readerManagement.api;

import org.junit.jupiter.api.Test;
import pt.psoft.g1.psoftg1.readermanagement.api.ReaderCountView;
import pt.psoft.g1.psoftg1.readermanagement.api.ReaderView;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class ReaderCountViewTest {

    // Black Box Test
    @Test
    void testCreationAndGetters() {
        ReaderView reader = createValidReaderView();
        Long count = 5L;

        ReaderCountView view = new ReaderCountView();
        view.setReaderView(reader);
        view.setLendingCount(count);

        assertNotNull(view, "**A view não deve ser nula.**");
        assertEquals(reader, view.getReaderView(), "**O ReaderView deve ser o setado.**");
        assertEquals(count, view.getLendingCount(), "**A contagem de empréstimos deve ser a setada.**");
    }

    // Black Box Test
    @Test
    void testEqualityAndHashCode() {
        ReaderView reader1 = createValidReaderView();
        ReaderView reader2 = createValidReaderView();

        ReaderCountView view1 = new ReaderCountView();
        view1.setReaderView(reader1);
        view1.setLendingCount(10L);

        ReaderCountView view2 = new ReaderCountView();
        view2.setReaderView(reader2);
        view2.setLendingCount(10L);

        assertEquals(view1, view2, "**Objetos com o mesmo conteúdo devem ser iguais.**");
        assertEquals(view1.hashCode(), view2.hashCode(), "**Hash codes devem ser iguais para objetos iguais.**");

        view2.setLendingCount(20L);
        assertNotEquals(view1, view2, "**Objetos com conteúdo diferente não devem ser iguais.**");
    }

    // Black Box Test
    @Test
    void testEqualsAndHashCodeEdgeCases() {
        ReaderView reader = createValidReaderView();

        ReaderCountView view1 = new ReaderCountView();
        view1.setReaderView(reader);
        view1.setLendingCount(5L);

        assertNotEquals(null, view1, "O objeto não deve ser igual a null.");

        ReaderCountView view2 = new ReaderCountView();
        view2.setReaderView(reader);
        view2.setLendingCount(5L);

        assertEquals(view1, view2, "Teste de Simetria: view1 deve ser igual a view2.");
        assertEquals(view2, view1, "Teste de Simetria: view2 deve ser igual a view1.");

        int initialHashCode = view1.hashCode();

        view1.setLendingCount(10L);
        assertNotEquals(initialHashCode, view1.hashCode(), "O HashCode deve mudar após modificação.");

        view1.setLendingCount(5L); // Volta ao estado original
        assertEquals(initialHashCode, view1.hashCode(), "O HashCode deve retornar ao valor original.");
    }

    private ReaderView createValidReaderView() {
        ReaderView view = new ReaderView();
        view.setReaderNumber("R12345");
        view.setEmail("test@example.com");
        view.setFullName("Teste Reader");
        view.setBirthDate("1990-01-01");
        view.setPhoneNumber("912345678");
        view.setPhoto("photo.jpg");
        view.setGdprConsent(true);
        view.setMarketingConsent(false);
        view.setThirdPartySharingConsent(false);
        view.setInterestList(Collections.emptyList());
        return view;
    }
}
