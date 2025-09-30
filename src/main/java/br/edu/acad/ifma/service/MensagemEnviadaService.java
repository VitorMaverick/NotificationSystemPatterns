package br.edu.acad.ifma.service;

import br.edu.acad.ifma.domain.MensagemEnviada;
import br.edu.acad.ifma.repository.MensagemEnviadaRepository;
import br.edu.acad.ifma.service.dto.MensagemEnviadaDTO;
import br.edu.acad.ifma.service.mapper.MensagemEnviadaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link br.edu.acad.ifma.domain.MensagemEnviada}.
 */
@Service
@Transactional
public class MensagemEnviadaService {

    private static final Logger LOG = LoggerFactory.getLogger(MensagemEnviadaService.class);

    private final MensagemEnviadaRepository mensagemEnviadaRepository;

    private final MensagemEnviadaMapper mensagemEnviadaMapper;

    public MensagemEnviadaService(MensagemEnviadaRepository mensagemEnviadaRepository, MensagemEnviadaMapper mensagemEnviadaMapper) {
        this.mensagemEnviadaRepository = mensagemEnviadaRepository;
        this.mensagemEnviadaMapper = mensagemEnviadaMapper;
    }

    /**
     * Save a mensagemEnviada.
     *
     * @param mensagemEnviadaDTO the entity to save.
     * @return the persisted entity.
     */
    public MensagemEnviadaDTO save(MensagemEnviadaDTO mensagemEnviadaDTO) {
        LOG.debug("Request to save MensagemEnviada : {}", mensagemEnviadaDTO);
        MensagemEnviada mensagemEnviada = mensagemEnviadaMapper.toEntity(mensagemEnviadaDTO);
        mensagemEnviada = mensagemEnviadaRepository.save(mensagemEnviada);
        return mensagemEnviadaMapper.toDto(mensagemEnviada);
    }

    /**
     * Update a mensagemEnviada.
     *
     * @param mensagemEnviadaDTO the entity to save.
     * @return the persisted entity.
     */
    public MensagemEnviadaDTO update(MensagemEnviadaDTO mensagemEnviadaDTO) {
        LOG.debug("Request to update MensagemEnviada : {}", mensagemEnviadaDTO);
        MensagemEnviada mensagemEnviada = mensagemEnviadaMapper.toEntity(mensagemEnviadaDTO);
        mensagemEnviada = mensagemEnviadaRepository.save(mensagemEnviada);
        return mensagemEnviadaMapper.toDto(mensagemEnviada);
    }

    /**
     * Partially update a mensagemEnviada.
     *
     * @param mensagemEnviadaDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<MensagemEnviadaDTO> partialUpdate(MensagemEnviadaDTO mensagemEnviadaDTO) {
        LOG.debug("Request to partially update MensagemEnviada : {}", mensagemEnviadaDTO);

        return mensagemEnviadaRepository
            .findById(mensagemEnviadaDTO.getId())
            .map(existingMensagemEnviada -> {
                mensagemEnviadaMapper.partialUpdate(existingMensagemEnviada, mensagemEnviadaDTO);

                return existingMensagemEnviada;
            })
            .map(mensagemEnviadaRepository::save)
            .map(mensagemEnviadaMapper::toDto);
    }

    /**
     * Get all the mensagemEnviadas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MensagemEnviadaDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all MensagemEnviadas");
        return mensagemEnviadaRepository.findAll(pageable).map(mensagemEnviadaMapper::toDto);
    }

    /**
     * Get one mensagemEnviada by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MensagemEnviadaDTO> findOne(Long id) {
        LOG.debug("Request to get MensagemEnviada : {}", id);
        return mensagemEnviadaRepository.findById(id).map(mensagemEnviadaMapper::toDto);
    }

    /**
     * Delete the mensagemEnviada by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete MensagemEnviada : {}", id);
        mensagemEnviadaRepository.deleteById(id);
    }
}
