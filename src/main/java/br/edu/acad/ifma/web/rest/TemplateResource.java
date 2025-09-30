package br.edu.acad.ifma.web.rest;

import br.edu.acad.ifma.repository.TemplateRepository;
import br.edu.acad.ifma.service.TemplateService;
import br.edu.acad.ifma.service.dto.TemplateDTO;
import br.edu.acad.ifma.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link br.edu.acad.ifma.domain.Template}.
 */
@RestController
@RequestMapping("/api/templates")
public class TemplateResource {

    private static final Logger LOG = LoggerFactory.getLogger(TemplateResource.class);

    private static final String ENTITY_NAME = "notificationSystemTemplate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TemplateService templateService;

    private final TemplateRepository templateRepository;

    public TemplateResource(TemplateService templateService, TemplateRepository templateRepository) {
        this.templateService = templateService;
        this.templateRepository = templateRepository;
    }

    /**
     * {@code POST  /templates} : Create a new template.
     *
     * @param templateDTO the templateDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new templateDTO, or with status {@code 400 (Bad Request)} if the template has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TemplateDTO> createTemplate(@Valid @RequestBody TemplateDTO templateDTO) throws URISyntaxException {
        LOG.debug("REST request to save Template : {}", templateDTO);
        if (templateDTO.getId() != null) {
            throw new BadRequestAlertException("A new template cannot already have an ID", ENTITY_NAME, "idexists");
        }
        templateDTO = templateService.save(templateDTO);
        return ResponseEntity.created(new URI("/api/templates/" + templateDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, templateDTO.getId().toString()))
            .body(templateDTO);
    }

    /**
     * {@code PUT  /templates/:id} : Updates an existing template.
     *
     * @param id the id of the templateDTO to save.
     * @param templateDTO the templateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated templateDTO,
     * or with status {@code 400 (Bad Request)} if the templateDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the templateDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TemplateDTO> updateTemplate(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TemplateDTO templateDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Template : {}, {}", id, templateDTO);
        if (templateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, templateDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!templateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        templateDTO = templateService.update(templateDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, templateDTO.getId().toString()))
            .body(templateDTO);
    }

    /**
     * {@code PATCH  /templates/:id} : Partial updates given fields of an existing template, field will ignore if it is null
     *
     * @param id the id of the templateDTO to save.
     * @param templateDTO the templateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated templateDTO,
     * or with status {@code 400 (Bad Request)} if the templateDTO is not valid,
     * or with status {@code 404 (Not Found)} if the templateDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the templateDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TemplateDTO> partialUpdateTemplate(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TemplateDTO templateDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Template partially : {}, {}", id, templateDTO);
        if (templateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, templateDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!templateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TemplateDTO> result = templateService.partialUpdate(templateDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, templateDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /templates} : get all the templates.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of templates in body.
     */
    @GetMapping("")
    public ResponseEntity<List<TemplateDTO>> getAllTemplates(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of Templates");
        Page<TemplateDTO> page = templateService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /templates/:id} : get the "id" template.
     *
     * @param id the id of the templateDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the templateDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TemplateDTO> getTemplate(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Template : {}", id);
        Optional<TemplateDTO> templateDTO = templateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(templateDTO);
    }

    /**
     * {@code DELETE  /templates/:id} : delete the "id" template.
     *
     * @param id the id of the templateDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTemplate(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Template : {}", id);
        templateService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
