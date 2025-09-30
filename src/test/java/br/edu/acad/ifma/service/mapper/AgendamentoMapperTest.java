package br.edu.acad.ifma.service.mapper;

import static br.edu.acad.ifma.domain.AgendamentoAsserts.*;
import static br.edu.acad.ifma.domain.AgendamentoTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AgendamentoMapperTest {

    private AgendamentoMapper agendamentoMapper;

    @BeforeEach
    void setUp() {
        agendamentoMapper = new AgendamentoMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getAgendamentoSample1();
        var actual = agendamentoMapper.toEntity(agendamentoMapper.toDto(expected));
        assertAgendamentoAllPropertiesEquals(expected, actual);
    }
}
