package br.edu.acad.ifma.domain;

import static br.edu.acad.ifma.domain.AgendamentoTestSamples.*;
import static br.edu.acad.ifma.domain.SegmentoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import br.edu.acad.ifma.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SegmentoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Segmento.class);
        Segmento segmento1 = getSegmentoSample1();
        Segmento segmento2 = new Segmento();
        assertThat(segmento1).isNotEqualTo(segmento2);

        segmento2.setId(segmento1.getId());
        assertThat(segmento1).isEqualTo(segmento2);

        segmento2 = getSegmentoSample2();
        assertThat(segmento1).isNotEqualTo(segmento2);
    }

    @Test
    void agendamentoTest() {
        Segmento segmento = getSegmentoRandomSampleGenerator();
        Agendamento agendamentoBack = getAgendamentoRandomSampleGenerator();

        segmento.setAgendamento(agendamentoBack);
        assertThat(segmento.getAgendamento()).isEqualTo(agendamentoBack);

        segmento.agendamento(null);
        assertThat(segmento.getAgendamento()).isNull();
    }
}
