package br.edu.acad.ifma.domain;

import static br.edu.acad.ifma.domain.ClienteTestSamples.*;
import static br.edu.acad.ifma.domain.SegmentoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import br.edu.acad.ifma.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ClienteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cliente.class);
        Cliente cliente1 = getClienteSample1();
        Cliente cliente2 = new Cliente();
        assertThat(cliente1).isNotEqualTo(cliente2);

        cliente2.setId(cliente1.getId());
        assertThat(cliente1).isEqualTo(cliente2);

        cliente2 = getClienteSample2();
        assertThat(cliente1).isNotEqualTo(cliente2);
    }

    @Test
    void segmentoTest() {
        Cliente cliente = getClienteRandomSampleGenerator();
        Segmento segmentoBack = getSegmentoRandomSampleGenerator();

        cliente.setSegmento(segmentoBack);
        assertThat(cliente.getSegmento()).isEqualTo(segmentoBack);

        cliente.segmento(null);
        assertThat(cliente.getSegmento()).isNull();
    }
}
