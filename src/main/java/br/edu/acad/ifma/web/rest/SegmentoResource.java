package br.edu.acad.ifma.web.rest;

import br.edu.acad.ifma.repository.SegmentoRepository;
import br.edu.acad.ifma.service.SegmentoService;
import br.edu.acad.ifma.service.dto.SegmentoDTO;
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
 * REST controller for managing {@link br.edu.acad.ifma.domain.Segmento}.
 */
@RestController
@RequestMapping("/api/segmentos")
public class SegmentoResource {

    private static final Logger LOG = LoggerFactory.getLogger(SegmentoResource.class);

    private static final String ENTITY_NAME = "notificationSystemSegmento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SegmentoService segmentoService;

    private final SegmentoRepository segmentoRepository;

    public SegmentoResource(SegmentoService segmentoService, SegmentoRepository segmentoRepository) {
        this.segmentoService = segmentoService;
        this.segmentoRepository = segmentoRepository;
    }

    /**
     * {@code POST  /segmentos} : Create a new segmento.
     *
     * @param segmentoDTO the segmentoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new segmentoDTO, or with status {@code 400 (Bad Request)} if the segmento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<SegmentoDTO> createSegmento(@Valid @RequestBody SegmentoDTO segmentoDTO) throws URISyntaxException {
        LOG.debug("REST request to save Segmento : {}", segmentoDTO);
        if (segmentoDTO.getId() != null) {
            throw new BadRequestAlertException("A new segmento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        segmentoDTO = segmentoService.save(segmentoDTO);
        return ResponseEntity.created(new URI("/api/segmentos/" + segmentoDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, segmentoDTO.getId().toString()))
            .body(segmentoDTO);
    }

    /**
     * {@code PUT  /segmentos/:id} : Updates an existing segmento.
     *
     * @param id the id of the segmentoDTO to save.
     * @param segmentoDTO the segmentoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated segmentoDTO,
     * or with status {@code 400 (Bad Request)} if the segmentoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the segmentoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<SegmentoDTO> updateSegmento(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SegmentoDTO segmentoDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Segmento : {}, {}", id, segmentoDTO);
        if (segmentoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, segmentoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!segmentoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        segmentoDTO = segmentoService.update(segmentoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, segmentoDTO.getId().toString()))
            .body(segmentoDTO);
    }

    /**
     * {@code PATCH  /segmentos/:id} : Partial updates given fields of an existing segmento, field will ignore if it is null
     *
     * @param id the id of the segmentoDTO to save.
     * @param segmentoDTO the segmentoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated segmentoDTO,
     * or with status {@code 400 (Bad Request)} if the segmentoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the segmentoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the segmentoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SegmentoDTO> partialUpdateSegmento(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SegmentoDTO segmentoDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Segmento partially : {}, {}", id, segmentoDTO);
        if (segmentoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, segmentoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!segmentoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SegmentoDTO> result = segmentoService.partialUpdate(segmentoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, segmentoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /segmentos} : get all the segmentos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of segmentos in body.
     */
    @GetMapping("")
    public ResponseEntity<List<SegmentoDTO>> getAllSegmentos(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of Segmentos");
        Page<SegmentoDTO> page = segmentoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /segmentos/:id} : get the "id" segmento.
     *
     * @param id the id of the segmentoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the segmentoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SegmentoDTO> getSegmento(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Segmento : {}", id);
        Optional<SegmentoDTO> segmentoDTO = segmentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(segmentoDTO);
    }

    /**
     * {@code DELETE  /segmentos/:id} : delete the "id" segmento.
     *
     * @param id the id of the segmentoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSegmento(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Segmento : {}", id);
        segmentoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
