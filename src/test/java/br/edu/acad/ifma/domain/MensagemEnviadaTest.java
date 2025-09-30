package br.edu.acad.ifma.domain;

import static br.edu.acad.ifma.domain.ClienteTestSamples.*;
import static br.edu.acad.ifma.domain.MensagemEnviadaTestSamples.*;
import static br.edu.acad.ifma.domain.TemplateTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import br.edu.acad.ifma.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MensagemEnviadaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MensagemEnviada.class);
        MensagemEnviada mensagemEnviada1 = getMensagemEnviadaSample1();
        MensagemEnviada mensagemEnviada2 = new MensagemEnviada();
        assertThat(mensagemEnviada1).isNotEqualTo(mensagemEnviada2);

        mensagemEnviada2.setId(mensagemEnviada1.getId());
        assertThat(mensagemEnviada1).isEqualTo(mensagemEnviada2);

        mensagemEnviada2 = getMensagemEnviadaSample2();
        assertThat(mensagemEnviada1).isNotEqualTo(mensagemEnviada2);
    }

    @Test
    void clienteTest() {
        MensagemEnviada mensagemEnviada = getMensagemEnviadaRandomSampleGenerator();
        Cliente clienteBack = getClienteRandomSampleGenerator();

        mensagemEnviada.setCliente(clienteBack);
        assertThat(mensagemEnviada.getCliente()).isEqualTo(clienteBack);

        mensagemEnviada.cliente(null);
        assertThat(mensagemEnviada.getCliente()).isNull();
    }

    @Test
    void templateTest() {
        MensagemEnviada mensagemEnviada = getMensagemEnviadaRandomSampleGenerator();
        Template templateBack = getTemplateRandomSampleGenerator();

        mensagemEnviada.setTemplate(templateBack);
        assertThat(mensagemEnviada.getTemplate()).isEqualTo(templateBack);

        mensagemEnviada.template(null);
        assertThat(mensagemEnviada.getTemplate()).isNull();
    }
}
