package br.edu.acad.ifma.domain;

import static br.edu.acad.ifma.domain.AgendamentoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import br.edu.acad.ifma.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AgendamentoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Agendamento.class);
        Agendamento agendamento1 = getAgendamentoSample1();
        Agendamento agendamento2 = new Agendamento();
        assertThat(agendamento1).isNotEqualTo(agendamento2);

        agendamento2.setId(agendamento1.getId());
        assertThat(agendamento1).isEqualTo(agendamento2);

        agendamento2 = getAgendamentoSample2();
        assertThat(agendamento1).isNotEqualTo(agendamento2);
    }
}
