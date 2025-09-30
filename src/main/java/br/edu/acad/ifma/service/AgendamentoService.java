package br.edu.acad.ifma.service;

import br.edu.acad.ifma.domain.Agendamento;
import br.edu.acad.ifma.repository.AgendamentoRepository;
import br.edu.acad.ifma.service.dto.AgendamentoDTO;
import br.edu.acad.ifma.service.mapper.AgendamentoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link br.edu.acad.ifma.domain.Agendamento}.
 */
@Service
@Transactional
public class AgendamentoService {

    private static final Logger LOG = LoggerFactory.getLogger(AgendamentoService.class);

    private final AgendamentoRepository agendamentoRepository;

    private final AgendamentoMapper agendamentoMapper;

    public AgendamentoService(AgendamentoRepository agendamentoRepository, AgendamentoMapper agendamentoMapper) {
        this.agendamentoRepository = agendamentoRepository;
        this.agendamentoMapper = agendamentoMapper;
    }

    /**
     * Save a agendamento.
     *
     * @param agendamentoDTO the entity to save.
     * @return the persisted entity.
     */
    public AgendamentoDTO save(AgendamentoDTO agendamentoDTO) {
        LOG.debug("Request to save Agendamento : {}", agendamentoDTO);
        Agendamento agendamento = agendamentoMapper.toEntity(agendamentoDTO);
        agendamento = agendamentoRepository.save(agendamento);
        return agendamentoMapper.toDto(agendamento);
    }

    /**
     * Update a agendamento.
     *
     * @param agendamentoDTO the entity to save.
     * @return the persisted entity.
     */
    public AgendamentoDTO update(AgendamentoDTO agendamentoDTO) {
        LOG.debug("Request to update Agendamento : {}", agendamentoDTO);
        Agendamento agendamento = agendamentoMapper.toEntity(agendamentoDTO);
        agendamento = agendamentoRepository.save(agendamento);
        return agendamentoMapper.toDto(agendamento);
    }

    /**
     * Partially update a agendamento.
     *
     * @param agendamentoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AgendamentoDTO> partialUpdate(AgendamentoDTO agendamentoDTO) {
        LOG.debug("Request to partially update Agendamento : {}", agendamentoDTO);

        return agendamentoRepository
            .findById(agendamentoDTO.getId())
            .map(existingAgendamento -> {
                agendamentoMapper.partialUpdate(existingAgendamento, agendamentoDTO);

                return existingAgendamento;
            })
            .map(agendamentoRepository::save)
            .map(agendamentoMapper::toDto);
    }

    /**
     * Get all the agendamentos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AgendamentoDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Agendamentos");
        return agendamentoRepository.findAll(pageable).map(agendamentoMapper::toDto);
    }

    /**
     * Get one agendamento by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AgendamentoDTO> findOne(Long id) {
        LOG.debug("Request to get Agendamento : {}", id);
        return agendamentoRepository.findById(id).map(agendamentoMapper::toDto);
    }

    /**
     * Delete the agendamento by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Agendamento : {}", id);
        agendamentoRepository.deleteById(id);
    }
}
