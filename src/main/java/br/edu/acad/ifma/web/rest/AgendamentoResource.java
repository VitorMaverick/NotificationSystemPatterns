package br.edu.acad.ifma.web.rest;

import br.edu.acad.ifma.repository.AgendamentoRepository;
import br.edu.acad.ifma.service.AgendamentoService;
import br.edu.acad.ifma.service.dto.AgendamentoDTO;
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
 * REST controller for managing {@link br.edu.acad.ifma.domain.Agendamento}.
 */
@RestController
@RequestMapping("/api/agendamentos")
public class AgendamentoResource {

    private static final Logger LOG = LoggerFactory.getLogger(AgendamentoResource.class);

    private static final String ENTITY_NAME = "notificationSystemAgendamento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AgendamentoService agendamentoService;

    private final AgendamentoRepository agendamentoRepository;

    public AgendamentoResource(AgendamentoService agendamentoService, AgendamentoRepository agendamentoRepository) {
        this.agendamentoService = agendamentoService;
        this.agendamentoRepository = agendamentoRepository;
    }

    /**
     * {@code POST  /agendamentos} : Create a new agendamento.
     *
     * @param agendamentoDTO the agendamentoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new agendamentoDTO, or with status {@code 400 (Bad Request)} if the agendamento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AgendamentoDTO> createAgendamento(@Valid @RequestBody AgendamentoDTO agendamentoDTO) throws URISyntaxException {
        LOG.debug("REST request to save Agendamento : {}", agendamentoDTO);
        if (agendamentoDTO.getId() != null) {
            throw new BadRequestAlertException("A new agendamento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        agendamentoDTO = agendamentoService.save(agendamentoDTO);
        return ResponseEntity.created(new URI("/api/agendamentos/" + agendamentoDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, agendamentoDTO.getId().toString()))
            .body(agendamentoDTO);
    }

    /**
     * {@code PUT  /agendamentos/:id} : Updates an existing agendamento.
     *
     * @param id the id of the agendamentoDTO to save.
     * @param agendamentoDTO the agendamentoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated agendamentoDTO,
     * or with status {@code 400 (Bad Request)} if the agendamentoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the agendamentoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AgendamentoDTO> updateAgendamento(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AgendamentoDTO agendamentoDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Agendamento : {}, {}", id, agendamentoDTO);
        if (agendamentoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, agendamentoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!agendamentoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        agendamentoDTO = agendamentoService.update(agendamentoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, agendamentoDTO.getId().toString()))
            .body(agendamentoDTO);
    }

    /**
     * {@code PATCH  /agendamentos/:id} : Partial updates given fields of an existing agendamento, field will ignore if it is null
     *
     * @param id the id of the agendamentoDTO to save.
     * @param agendamentoDTO the agendamentoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated agendamentoDTO,
     * or with status {@code 400 (Bad Request)} if the agendamentoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the agendamentoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the agendamentoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AgendamentoDTO> partialUpdateAgendamento(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AgendamentoDTO agendamentoDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Agendamento partially : {}, {}", id, agendamentoDTO);
        if (agendamentoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, agendamentoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!agendamentoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AgendamentoDTO> result = agendamentoService.partialUpdate(agendamentoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, agendamentoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /agendamentos} : get all the agendamentos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of agendamentos in body.
     */
    @GetMapping("")
    public ResponseEntity<List<AgendamentoDTO>> getAllAgendamentos(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of Agendamentos");
        Page<AgendamentoDTO> page = agendamentoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /agendamentos/:id} : get the "id" agendamento.
     *
     * @param id the id of the agendamentoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the agendamentoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AgendamentoDTO> getAgendamento(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Agendamento : {}", id);
        Optional<AgendamentoDTO> agendamentoDTO = agendamentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(agendamentoDTO);
    }

    /**
     * {@code DELETE  /agendamentos/:id} : delete the "id" agendamento.
     *
     * @param id the id of the agendamentoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAgendamento(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Agendamento : {}", id);
        agendamentoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
