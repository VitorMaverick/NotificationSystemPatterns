package br.edu.acad.ifma.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import br.edu.acad.ifma.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MensagemEnviadaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MensagemEnviadaDTO.class);
        MensagemEnviadaDTO mensagemEnviadaDTO1 = new MensagemEnviadaDTO();
        mensagemEnviadaDTO1.setId(1L);
        MensagemEnviadaDTO mensagemEnviadaDTO2 = new MensagemEnviadaDTO();
        assertThat(mensagemEnviadaDTO1).isNotEqualTo(mensagemEnviadaDTO2);
        mensagemEnviadaDTO2.setId(mensagemEnviadaDTO1.getId());
        assertThat(mensagemEnviadaDTO1).isEqualTo(mensagemEnviadaDTO2);
        mensagemEnviadaDTO2.setId(2L);
        assertThat(mensagemEnviadaDTO1).isNotEqualTo(mensagemEnviadaDTO2);
        mensagemEnviadaDTO1.setId(null);
        assertThat(mensagemEnviadaDTO1).isNotEqualTo(mensagemEnviadaDTO2);
    }
}
