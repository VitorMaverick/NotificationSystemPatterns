package br.edu.acad.ifma.web.rest;

import static br.edu.acad.ifma.domain.AgendamentoAsserts.*;
import static br.edu.acad.ifma.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.edu.acad.ifma.IntegrationTest;
import br.edu.acad.ifma.domain.Agendamento;
import br.edu.acad.ifma.repository.AgendamentoRepository;
import br.edu.acad.ifma.service.dto.AgendamentoDTO;
import br.edu.acad.ifma.service.mapper.AgendamentoMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AgendamentoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AgendamentoResourceIT {

    private static final String DEFAULT_PERIODICIDADE = "AAAAAAAAAA";
    private static final String UPDATED_PERIODICIDADE = "BBBBBBBBBB";

    private static final String DEFAULT_HORA_ENVIO = "AAAAAAAAAA";
    private static final String UPDATED_HORA_ENVIO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/agendamentos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Autowired
    private AgendamentoMapper agendamentoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAgendamentoMockMvc;

    private Agendamento agendamento;

    private Agendamento insertedAgendamento;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Agendamento createEntity() {
        return new Agendamento().periodicidade(DEFAULT_PERIODICIDADE).horaEnvio(DEFAULT_HORA_ENVIO);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Agendamento createUpdatedEntity() {
        return new Agendamento().periodicidade(UPDATED_PERIODICIDADE).horaEnvio(UPDATED_HORA_ENVIO);
    }

    @BeforeEach
    void initTest() {
        agendamento = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedAgendamento != null) {
            agendamentoRepository.delete(insertedAgendamento);
            insertedAgendamento = null;
        }
    }

    @Test
    @Transactional
    void createAgendamento() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Agendamento
        AgendamentoDTO agendamentoDTO = agendamentoMapper.toDto(agendamento);
        var returnedAgendamentoDTO = om.readValue(
            restAgendamentoMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(agendamentoDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AgendamentoDTO.class
        );

        // Validate the Agendamento in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedAgendamento = agendamentoMapper.toEntity(returnedAgendamentoDTO);
        assertAgendamentoUpdatableFieldsEquals(returnedAgendamento, getPersistedAgendamento(returnedAgendamento));

        insertedAgendamento = returnedAgendamento;
    }

    @Test
    @Transactional
    void createAgendamentoWithExistingId() throws Exception {
        // Create the Agendamento with an existing ID
        agendamento.setId(1L);
        AgendamentoDTO agendamentoDTO = agendamentoMapper.toDto(agendamento);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAgendamentoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(agendamentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Agendamento in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPeriodicidadeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        agendamento.setPeriodicidade(null);

        // Create the Agendamento, which fails.
        AgendamentoDTO agendamentoDTO = agendamentoMapper.toDto(agendamento);

        restAgendamentoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(agendamentoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAgendamentos() throws Exception {
        // Initialize the database
        insertedAgendamento = agendamentoRepository.saveAndFlush(agendamento);

        // Get all the agendamentoList
        restAgendamentoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(agendamento.getId().intValue())))
            .andExpect(jsonPath("$.[*].periodicidade").value(hasItem(DEFAULT_PERIODICIDADE)))
            .andExpect(jsonPath("$.[*].horaEnvio").value(hasItem(DEFAULT_HORA_ENVIO)));
    }

    @Test
    @Transactional
    void getAgendamento() throws Exception {
        // Initialize the database
        insertedAgendamento = agendamentoRepository.saveAndFlush(agendamento);

        // Get the agendamento
        restAgendamentoMockMvc
            .perform(get(ENTITY_API_URL_ID, agendamento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(agendamento.getId().intValue()))
            .andExpect(jsonPath("$.periodicidade").value(DEFAULT_PERIODICIDADE))
            .andExpect(jsonPath("$.horaEnvio").value(DEFAULT_HORA_ENVIO));
    }

    @Test
    @Transactional
    void getNonExistingAgendamento() throws Exception {
        // Get the agendamento
        restAgendamentoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAgendamento() throws Exception {
        // Initialize the database
        insertedAgendamento = agendamentoRepository.saveAndFlush(agendamento);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the agendamento
        Agendamento updatedAgendamento = agendamentoRepository.findById(agendamento.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAgendamento are not directly saved in db
        em.detach(updatedAgendamento);
        updatedAgendamento.periodicidade(UPDATED_PERIODICIDADE).horaEnvio(UPDATED_HORA_ENVIO);
        AgendamentoDTO agendamentoDTO = agendamentoMapper.toDto(updatedAgendamento);

        restAgendamentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, agendamentoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(agendamentoDTO))
            )
            .andExpect(status().isOk());

        // Validate the Agendamento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAgendamentoToMatchAllProperties(updatedAgendamento);
    }

    @Test
    @Transactional
    void putNonExistingAgendamento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        agendamento.setId(longCount.incrementAndGet());

        // Create the Agendamento
        AgendamentoDTO agendamentoDTO = agendamentoMapper.toDto(agendamento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAgendamentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, agendamentoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(agendamentoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Agendamento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAgendamento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        agendamento.setId(longCount.incrementAndGet());

        // Create the Agendamento
        AgendamentoDTO agendamentoDTO = agendamentoMapper.toDto(agendamento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAgendamentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(agendamentoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Agendamento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAgendamento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        agendamento.setId(longCount.incrementAndGet());

        // Create the Agendamento
        AgendamentoDTO agendamentoDTO = agendamentoMapper.toDto(agendamento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAgendamentoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(agendamentoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Agendamento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAgendamentoWithPatch() throws Exception {
        // Initialize the database
        insertedAgendamento = agendamentoRepository.saveAndFlush(agendamento);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the agendamento using partial update
        Agendamento partialUpdatedAgendamento = new Agendamento();
        partialUpdatedAgendamento.setId(agendamento.getId());

        partialUpdatedAgendamento.periodicidade(UPDATED_PERIODICIDADE);

        restAgendamentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAgendamento.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAgendamento))
            )
            .andExpect(status().isOk());

        // Validate the Agendamento in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAgendamentoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAgendamento, agendamento),
            getPersistedAgendamento(agendamento)
        );
    }

    @Test
    @Transactional
    void fullUpdateAgendamentoWithPatch() throws Exception {
        // Initialize the database
        insertedAgendamento = agendamentoRepository.saveAndFlush(agendamento);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the agendamento using partial update
        Agendamento partialUpdatedAgendamento = new Agendamento();
        partialUpdatedAgendamento.setId(agendamento.getId());

        partialUpdatedAgendamento.periodicidade(UPDATED_PERIODICIDADE).horaEnvio(UPDATED_HORA_ENVIO);

        restAgendamentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAgendamento.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAgendamento))
            )
            .andExpect(status().isOk());

        // Validate the Agendamento in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAgendamentoUpdatableFieldsEquals(partialUpdatedAgendamento, getPersistedAgendamento(partialUpdatedAgendamento));
    }

    @Test
    @Transactional
    void patchNonExistingAgendamento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        agendamento.setId(longCount.incrementAndGet());

        // Create the Agendamento
        AgendamentoDTO agendamentoDTO = agendamentoMapper.toDto(agendamento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAgendamentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, agendamentoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(agendamentoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Agendamento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAgendamento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        agendamento.setId(longCount.incrementAndGet());

        // Create the Agendamento
        AgendamentoDTO agendamentoDTO = agendamentoMapper.toDto(agendamento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAgendamentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(agendamentoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Agendamento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAgendamento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        agendamento.setId(longCount.incrementAndGet());

        // Create the Agendamento
        AgendamentoDTO agendamentoDTO = agendamentoMapper.toDto(agendamento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAgendamentoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(agendamentoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Agendamento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAgendamento() throws Exception {
        // Initialize the database
        insertedAgendamento = agendamentoRepository.saveAndFlush(agendamento);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the agendamento
        restAgendamentoMockMvc
            .perform(delete(ENTITY_API_URL_ID, agendamento.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return agendamentoRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Agendamento getPersistedAgendamento(Agendamento agendamento) {
        return agendamentoRepository.findById(agendamento.getId()).orElseThrow();
    }

    protected void assertPersistedAgendamentoToMatchAllProperties(Agendamento expectedAgendamento) {
        assertAgendamentoAllPropertiesEquals(expectedAgendamento, getPersistedAgendamento(expectedAgendamento));
    }

    protected void assertPersistedAgendamentoToMatchUpdatableProperties(Agendamento expectedAgendamento) {
        assertAgendamentoAllUpdatablePropertiesEquals(expectedAgendamento, getPersistedAgendamento(expectedAgendamento));
    }
}
