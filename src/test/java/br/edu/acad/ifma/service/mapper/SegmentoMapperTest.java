package br.edu.acad.ifma.service.mapper;

import static br.edu.acad.ifma.domain.SegmentoAsserts.*;
import static br.edu.acad.ifma.domain.SegmentoTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SegmentoMapperTest {

    private SegmentoMapper segmentoMapper;

    @BeforeEach
    void setUp() {
        segmentoMapper = new SegmentoMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getSegmentoSample1();
        var actual = segmentoMapper.toEntity(segmentoMapper.toDto(expected));
        assertSegmentoAllPropertiesEquals(expected, actual);
    }
}
