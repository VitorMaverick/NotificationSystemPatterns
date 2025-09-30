package br.edu.acad.ifma.service.mapper;

import br.edu.acad.ifma.domain.Agendamento;
import br.edu.acad.ifma.domain.Segmento;
import br.edu.acad.ifma.service.dto.AgendamentoDTO;
import br.edu.acad.ifma.service.dto.SegmentoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Segmento} and its DTO {@link SegmentoDTO}.
 */
@Mapper(componentModel = "spring")
public interface SegmentoMapper extends EntityMapper<SegmentoDTO, Segmento> {
    @Mapping(target = "agendamento", source = "agendamento", qualifiedByName = "agendamentoId")
    SegmentoDTO toDto(Segmento s);

    @Named("agendamentoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AgendamentoDTO toDtoAgendamentoId(Agendamento agendamento);
}
