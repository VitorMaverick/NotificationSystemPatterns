package br.edu.acad.ifma.service.mapper;

import br.edu.acad.ifma.domain.Template;
import br.edu.acad.ifma.service.dto.TemplateDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Template} and its DTO {@link TemplateDTO}.
 */
@Mapper(componentModel = "spring")
public interface TemplateMapper extends EntityMapper<TemplateDTO, Template> {}
