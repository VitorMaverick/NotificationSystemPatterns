package br.edu.acad.ifma.web.rest;

import br.edu.acad.ifma.repository.MensagemEnviadaRepository;
import br.edu.acad.ifma.service.MensagemEnviadaService;
import br.edu.acad.ifma.service.dto.MensagemEnviadaDTO;
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
 * REST controller for managing {@link br.edu.acad.ifma.domain.MensagemEnviada}.
 */
@RestController
@RequestMapping("/api/mensagem-enviadas")
public class MensagemEnviadaResource {

    private static final Logger LOG = LoggerFactory.getLogger(MensagemEnviadaResource.class);

    private static final String ENTITY_NAME = "notificationSystemMensagemEnviada";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MensagemEnviadaService mensagemEnviadaService;

    private final MensagemEnviadaRepository mensagemEnviadaRepository;

    public MensagemEnviadaResource(MensagemEnviadaService mensagemEnviadaService, MensagemEnviadaRepository mensagemEnviadaRepository) {
        this.mensagemEnviadaService = mensagemEnviadaService;
        this.mensagemEnviadaRepository = mensagemEnviadaRepository;
    }

    /**
     * {@code POST  /mensagem-enviadas} : Create a new mensagemEnviada.
     *
     * @param mensagemEnviadaDTO the mensagemEnviadaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mensagemEnviadaDTO, or with status {@code 400 (Bad Request)} if the mensagemEnviada has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<MensagemEnviadaDTO> createMensagemEnviada(@Valid @RequestBody MensagemEnviadaDTO mensagemEnviadaDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save MensagemEnviada : {}", mensagemEnviadaDTO);
        if (mensagemEnviadaDTO.getId() != null) {
            throw new BadRequestAlertException("A new mensagemEnviada cannot already have an ID", ENTITY_NAME, "idexists");
        }
        mensagemEnviadaDTO = mensagemEnviadaService.save(mensagemEnviadaDTO);
        return ResponseEntity.created(new URI("/api/mensagem-enviadas/" + mensagemEnviadaDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, mensagemEnviadaDTO.getId().toString()))
            .body(mensagemEnviadaDTO);
    }

    /**
     * {@code PUT  /mensagem-enviadas/:id} : Updates an existing mensagemEnviada.
     *
     * @param id the id of the mensagemEnviadaDTO to save.
     * @param mensagemEnviadaDTO the mensagemEnviadaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mensagemEnviadaDTO,
     * or with status {@code 400 (Bad Request)} if the mensagemEnviadaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mensagemEnviadaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MensagemEnviadaDTO> updateMensagemEnviada(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody MensagemEnviadaDTO mensagemEnviadaDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update MensagemEnviada : {}, {}", id, mensagemEnviadaDTO);
        if (mensagemEnviadaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mensagemEnviadaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mensagemEnviadaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        mensagemEnviadaDTO = mensagemEnviadaService.update(mensagemEnviadaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, mensagemEnviadaDTO.getId().toString()))
            .body(mensagemEnviadaDTO);
    }

    /**
     * {@code PATCH  /mensagem-enviadas/:id} : Partial updates given fields of an existing mensagemEnviada, field will ignore if it is null
     *
     * @param id the id of the mensagemEnviadaDTO to save.
     * @param mensagemEnviadaDTO the mensagemEnviadaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mensagemEnviadaDTO,
     * or with status {@code 400 (Bad Request)} if the mensagemEnviadaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the mensagemEnviadaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the mensagemEnviadaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MensagemEnviadaDTO> partialUpdateMensagemEnviada(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody MensagemEnviadaDTO mensagemEnviadaDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update MensagemEnviada partially : {}, {}", id, mensagemEnviadaDTO);
        if (mensagemEnviadaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mensagemEnviadaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mensagemEnviadaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MensagemEnviadaDTO> result = mensagemEnviadaService.partialUpdate(mensagemEnviadaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, mensagemEnviadaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /mensagem-enviadas} : get all the mensagemEnviadas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mensagemEnviadas in body.
     */
    @GetMapping("")
    public ResponseEntity<List<MensagemEnviadaDTO>> getAllMensagemEnviadas(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of MensagemEnviadas");
        Page<MensagemEnviadaDTO> page = mensagemEnviadaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /mensagem-enviadas/:id} : get the "id" mensagemEnviada.
     *
     * @param id the id of the mensagemEnviadaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mensagemEnviadaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MensagemEnviadaDTO> getMensagemEnviada(@PathVariable("id") Long id) {
        LOG.debug("REST request to get MensagemEnviada : {}", id);
        Optional<MensagemEnviadaDTO> mensagemEnviadaDTO = mensagemEnviadaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mensagemEnviadaDTO);
    }

    /**
     * {@code DELETE  /mensagem-enviadas/:id} : delete the "id" mensagemEnviada.
     *
     * @param id the id of the mensagemEnviadaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMensagemEnviada(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete MensagemEnviada : {}", id);
        mensagemEnviadaService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
