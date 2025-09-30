package br.edu.acad.ifma.service.mapper;

import br.edu.acad.ifma.domain.Cliente;
import br.edu.acad.ifma.domain.MensagemEnviada;
import br.edu.acad.ifma.domain.Template;
import br.edu.acad.ifma.service.dto.ClienteDTO;
import br.edu.acad.ifma.service.dto.MensagemEnviadaDTO;
import br.edu.acad.ifma.service.dto.TemplateDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MensagemEnviada} and its DTO {@link MensagemEnviadaDTO}.
 */
@Mapper(componentModel = "spring")
public interface MensagemEnviadaMapper extends EntityMapper<MensagemEnviadaDTO, MensagemEnviada> {
    @Mapping(target = "cliente", source = "cliente", qualifiedByName = "clienteId")
    @Mapping(target = "template", source = "template", qualifiedByName = "templateId")
    MensagemEnviadaDTO toDto(MensagemEnviada s);

    @Named("clienteId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ClienteDTO toDtoClienteId(Cliente cliente);

    @Named("templateId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TemplateDTO toDtoTemplateId(Template template);
}
