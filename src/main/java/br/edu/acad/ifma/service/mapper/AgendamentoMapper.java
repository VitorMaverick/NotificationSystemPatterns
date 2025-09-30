package br.edu.acad.ifma.service.mapper;

import br.edu.acad.ifma.domain.Agendamento;
import br.edu.acad.ifma.service.dto.AgendamentoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Agendamento} and its DTO {@link AgendamentoDTO}.
 */
@Mapper(componentModel = "spring")
public interface AgendamentoMapper extends EntityMapper<AgendamentoDTO, Agendamento> {}
