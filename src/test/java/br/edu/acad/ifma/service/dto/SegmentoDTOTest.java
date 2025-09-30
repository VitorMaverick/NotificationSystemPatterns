package br.edu.acad.ifma.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import br.edu.acad.ifma.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SegmentoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SegmentoDTO.class);
        SegmentoDTO segmentoDTO1 = new SegmentoDTO();
        segmentoDTO1.setId(1L);
        SegmentoDTO segmentoDTO2 = new SegmentoDTO();
        assertThat(segmentoDTO1).isNotEqualTo(segmentoDTO2);
        segmentoDTO2.setId(segmentoDTO1.getId());
        assertThat(segmentoDTO1).isEqualTo(segmentoDTO2);
        segmentoDTO2.setId(2L);
        assertThat(segmentoDTO1).isNotEqualTo(segmentoDTO2);
        segmentoDTO1.setId(null);
        assertThat(segmentoDTO1).isNotEqualTo(segmentoDTO2);
    }
}
