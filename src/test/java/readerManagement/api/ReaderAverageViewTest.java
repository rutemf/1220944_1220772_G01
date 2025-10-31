package readerManagement.api;

import org.junit.jupiter.api.Test;
import pt.psoft.g1.psoftg1.readermanagement.api.ReaderAverageView;
import pt.psoft.g1.psoftg1.readermanagement.api.ReaderView;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class ReaderAverageViewTest {

    // Black Box Test
    @Test
    void testCreationAndGetters() {
        ReaderView reader = createValidReaderView("test@example.com");
        Long count = 42L;

        ReaderAverageView view = new ReaderAverageView();
        view.setReaderView(reader);
        view.setLendingCount(count);

        assertNotNull(view, "**A view não deve ser nula.**");
        assertEquals(reader, view.getReaderView(), "**O ReaderView deve ser o setado.**");
        assertEquals(count, view.getLendingCount(), "**A contagem de empréstimos deve ser a setada.**");
    }

    // Black Box Test
    @Test
    void testLendingCountCanBeNull() {
        ReaderView reader = createValidReaderView("another@example.com");

        ReaderAverageView view = new ReaderAverageView();
        view.setReaderView(reader);
        view.setLendingCount(null);

        assertNull(view.getLendingCount(), "**O lendingCount deve ser nulo.**");
        assertNotNull(view.getReaderView(), "**O ReaderView deve ser não nulo.**");
    }

    // Black Box Test
    @Test
    void testEqualityAndHashCodeSuccess() {
        ReaderView reader1 = createValidReaderView("a@test.com");
        ReaderView reader2 = createValidReaderView("a@test.com");

        ReaderAverageView view1 = new ReaderAverageView();
        view1.setReaderView(reader1);
        view1.setLendingCount(10L);

        ReaderAverageView view2 = new ReaderAverageView();
        view2.setReaderView(reader2);
        view2.setLendingCount(10L);

        assertEquals(view1, view2, "**Objetos com o mesmo conteúdo devem ser iguais.**");
        assertEquals(view1.hashCode(), view2.hashCode(), "**Hash codes devem ser iguais para objetos iguais.**");
    }

    // Black Box Test
    @Test
    void testEqualityAndHashCodeDifference() {
        ReaderView reader = createValidReaderView("b@test.com");

        ReaderAverageView view1 = new ReaderAverageView();
        view1.setReaderView(reader);
        view1.setLendingCount(10L);

        ReaderAverageView view2 = new ReaderAverageView();
        view2.setReaderView(reader);
        view2.setLendingCount(20L);

        assertNotEquals(view1, view2, "**Objetos com conteúdo diferente não devem ser iguais.**");
        assertNotEquals(view1.hashCode(), view2.hashCode(), "**Hash codes devem ser diferentes.**");

        view2.setReaderView(createValidReaderView("c@test.com"));
        assertNotEquals(view1, view2, "**Objetos com ReaderView diferente não devem ser iguais.**");
    }

    // Black Box Test
    @Test
    void testToStringContainsAllFields() {
        ReaderView reader = createValidReaderView("d@test.com");
        ReaderAverageView view = new ReaderAverageView();
        view.setReaderView(reader);
        view.setLendingCount(55L);

        String viewString = view.toString();

        assertTrue(viewString.contains("readerView="), "**O toString deve conter readerView.**");
        assertTrue(viewString.contains("lendingCount=55"), "**O toString deve conter lendingCount.**");
        assertTrue(viewString.contains("d@test.com"), "**O toString deve conter o email do ReaderView.**");
    }

    private ReaderView createValidReaderView(String email) {
        ReaderView view = new ReaderView();
        view.setReaderNumber("R12345");
        view.setEmail(email);
        view.setFullName("Teste Reader");
        view.setInterestList(Collections.emptyList());
        return view;
    }
}
