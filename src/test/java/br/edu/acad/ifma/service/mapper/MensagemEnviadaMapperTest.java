package br.edu.acad.ifma.service.mapper;

import static br.edu.acad.ifma.domain.MensagemEnviadaAsserts.*;
import static br.edu.acad.ifma.domain.MensagemEnviadaTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MensagemEnviadaMapperTest {

    private MensagemEnviadaMapper mensagemEnviadaMapper;

    @BeforeEach
    void setUp() {
        mensagemEnviadaMapper = new MensagemEnviadaMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getMensagemEnviadaSample1();
        var actual = mensagemEnviadaMapper.toEntity(mensagemEnviadaMapper.toDto(expected));
        assertMensagemEnviadaAllPropertiesEquals(expected, actual);
    }
}
