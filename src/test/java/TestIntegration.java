import com.tmaze.repo.DaneWejsciowe;
import org.junit.jupiter.api.Test;
import com.tmaze.model.Bank;

import java.io.*;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TestIntegration {
    @Test
    void repositoryShouldContain12Objects() {
        DaneWejsciowe daneWejsciowe = new DaneWejsciowe();
        Map<String, Object> storage = DaneWejsciowe.allObj;

        assertEquals(12, storage.size());
        assertTrue(storage.containsKey("bank_1"));
        assertTrue(storage.containsKey("klient_4"));
        assertTrue(storage.containsKey("konto_3"));
    }

    @Test
    void shouldReturn4Cats() {
        DaneWejsciowe daneWejsciowe = new DaneWejsciowe();

        List<?> banks = daneWejsciowe.findByClassName("Bank");
        assertEquals(4, banks.size());
        assertTrue(banks.get(0) instanceof Bank);
    }

    @Test
    void shouldReturnFallbackObjectForUnknownClass() {
        DaneWejsciowe daneWejsciowe = new DaneWejsciowe();

        List<?> result = daneWejsciowe.findByClassName("Osoba");
        assertEquals(1, result.size());
        assertNotNull(result.get(0));
    }
}