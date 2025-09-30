package br.edu.acad.ifma.service.mapper;

import br.edu.acad.ifma.domain.Cliente;
import br.edu.acad.ifma.domain.Segmento;
import br.edu.acad.ifma.service.dto.ClienteDTO;
import br.edu.acad.ifma.service.dto.SegmentoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Cliente} and its DTO {@link ClienteDTO}.
 */
@Mapper(componentModel = "spring")
public interface ClienteMapper extends EntityMapper<ClienteDTO, Cliente> {
    @Mapping(target = "segmento", source = "segmento", qualifiedByName = "segmentoId")
    ClienteDTO toDto(Cliente s);

    @Named("segmentoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SegmentoDTO toDtoSegmentoId(Segmento segmento);
}
