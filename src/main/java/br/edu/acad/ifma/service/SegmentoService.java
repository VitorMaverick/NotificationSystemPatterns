package br.edu.acad.ifma.service;

import br.edu.acad.ifma.domain.Segmento;
import br.edu.acad.ifma.repository.SegmentoRepository;
import br.edu.acad.ifma.service.dto.SegmentoDTO;
import br.edu.acad.ifma.service.mapper.SegmentoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link br.edu.acad.ifma.domain.Segmento}.
 */
@Service
@Transactional
public class SegmentoService {

    private static final Logger LOG = LoggerFactory.getLogger(SegmentoService.class);

    private final SegmentoRepository segmentoRepository;

    private final SegmentoMapper segmentoMapper;

    public SegmentoService(SegmentoRepository segmentoRepository, SegmentoMapper segmentoMapper) {
        this.segmentoRepository = segmentoRepository;
        this.segmentoMapper = segmentoMapper;
    }

    /**
     * Save a segmento.
     *
     * @param segmentoDTO the entity to save.
     * @return the persisted entity.
     */
    public SegmentoDTO save(SegmentoDTO segmentoDTO) {
        LOG.debug("Request to save Segmento : {}", segmentoDTO);
        Segmento segmento = segmentoMapper.toEntity(segmentoDTO);
        segmento = segmentoRepository.save(segmento);
        return segmentoMapper.toDto(segmento);
    }

    /**
     * Update a segmento.
     *
     * @param segmentoDTO the entity to save.
     * @return the persisted entity.
     */
    public SegmentoDTO update(SegmentoDTO segmentoDTO) {
        LOG.debug("Request to update Segmento : {}", segmentoDTO);
        Segmento segmento = segmentoMapper.toEntity(segmentoDTO);
        segmento = segmentoRepository.save(segmento);
        return segmentoMapper.toDto(segmento);
    }

    /**
     * Partially update a segmento.
     *
     * @param segmentoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SegmentoDTO> partialUpdate(SegmentoDTO segmentoDTO) {
        LOG.debug("Request to partially update Segmento : {}", segmentoDTO);

        return segmentoRepository
            .findById(segmentoDTO.getId())
            .map(existingSegmento -> {
                segmentoMapper.partialUpdate(existingSegmento, segmentoDTO);

                return existingSegmento;
            })
            .map(segmentoRepository::save)
            .map(segmentoMapper::toDto);
    }

    /**
     * Get all the segmentos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SegmentoDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Segmentos");
        return segmentoRepository.findAll(pageable).map(segmentoMapper::toDto);
    }

    /**
     * Get one segmento by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SegmentoDTO> findOne(Long id) {
        LOG.debug("Request to get Segmento : {}", id);
        return segmentoRepository.findById(id).map(segmentoMapper::toDto);
    }

    /**
     * Delete the segmento by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Segmento : {}", id);
        segmentoRepository.deleteById(id);
    }
}
