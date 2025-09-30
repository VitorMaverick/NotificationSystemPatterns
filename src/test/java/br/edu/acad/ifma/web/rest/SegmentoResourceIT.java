package br.edu.acad.ifma.web.rest;

import static br.edu.acad.ifma.domain.SegmentoAsserts.*;
import static br.edu.acad.ifma.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.edu.acad.ifma.IntegrationTest;
import br.edu.acad.ifma.domain.Segmento;
import br.edu.acad.ifma.repository.SegmentoRepository;
import br.edu.acad.ifma.service.dto.SegmentoDTO;
import br.edu.acad.ifma.service.mapper.SegmentoMapper;
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
 * Integration tests for the {@link SegmentoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SegmentoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_CANAL_ENVIO = "AAAAAAAAAA";
    private static final String UPDATED_CANAL_ENVIO = "BBBBBBBBBB";

    private static final Long DEFAULT_AGENDAMENTO_ID = 1L;
    private static final Long UPDATED_AGENDAMENTO_ID = 2L;

    private static final String ENTITY_API_URL = "/api/segmentos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SegmentoRepository segmentoRepository;

    @Autowired
    private SegmentoMapper segmentoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSegmentoMockMvc;

    private Segmento segmento;

    private Segmento insertedSegmento;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Segmento createEntity() {
        return new Segmento()
            .nome(DEFAULT_NOME)
            .descricao(DEFAULT_DESCRICAO)
            .canalEnvio(DEFAULT_CANAL_ENVIO)
            .agendamentoId(DEFAULT_AGENDAMENTO_ID);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Segmento createUpdatedEntity() {
        return new Segmento()
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .canalEnvio(UPDATED_CANAL_ENVIO)
            .agendamentoId(UPDATED_AGENDAMENTO_ID);
    }

    @BeforeEach
    void initTest() {
        segmento = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedSegmento != null) {
            segmentoRepository.delete(insertedSegmento);
            insertedSegmento = null;
        }
    }

    @Test
    @Transactional
    void createSegmento() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Segmento
        SegmentoDTO segmentoDTO = segmentoMapper.toDto(segmento);
        var returnedSegmentoDTO = om.readValue(
            restSegmentoMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(segmentoDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            SegmentoDTO.class
        );

        // Validate the Segmento in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedSegmento = segmentoMapper.toEntity(returnedSegmentoDTO);
        assertSegmentoUpdatableFieldsEquals(returnedSegmento, getPersistedSegmento(returnedSegmento));

        insertedSegmento = returnedSegmento;
    }

    @Test
    @Transactional
    void createSegmentoWithExistingId() throws Exception {
        // Create the Segmento with an existing ID
        segmento.setId(1L);
        SegmentoDTO segmentoDTO = segmentoMapper.toDto(segmento);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSegmentoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(segmentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Segmento in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        segmento.setNome(null);

        // Create the Segmento, which fails.
        SegmentoDTO segmentoDTO = segmentoMapper.toDto(segmento);

        restSegmentoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(segmentoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCanalEnvioIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        segmento.setCanalEnvio(null);

        // Create the Segmento, which fails.
        SegmentoDTO segmentoDTO = segmentoMapper.toDto(segmento);

        restSegmentoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(segmentoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAgendamentoIdIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        segmento.setAgendamentoId(null);

        // Create the Segmento, which fails.
        SegmentoDTO segmentoDTO = segmentoMapper.toDto(segmento);

        restSegmentoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(segmentoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSegmentos() throws Exception {
        // Initialize the database
        insertedSegmento = segmentoRepository.saveAndFlush(segmento);

        // Get all the segmentoList
        restSegmentoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(segmento.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].canalEnvio").value(hasItem(DEFAULT_CANAL_ENVIO)))
            .andExpect(jsonPath("$.[*].agendamentoId").value(hasItem(DEFAULT_AGENDAMENTO_ID.intValue())));
    }

    @Test
    @Transactional
    void getSegmento() throws Exception {
        // Initialize the database
        insertedSegmento = segmentoRepository.saveAndFlush(segmento);

        // Get the segmento
        restSegmentoMockMvc
            .perform(get(ENTITY_API_URL_ID, segmento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(segmento.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.canalEnvio").value(DEFAULT_CANAL_ENVIO))
            .andExpect(jsonPath("$.agendamentoId").value(DEFAULT_AGENDAMENTO_ID.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingSegmento() throws Exception {
        // Get the segmento
        restSegmentoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSegmento() throws Exception {
        // Initialize the database
        insertedSegmento = segmentoRepository.saveAndFlush(segmento);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the segmento
        Segmento updatedSegmento = segmentoRepository.findById(segmento.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSegmento are not directly saved in db
        em.detach(updatedSegmento);
        updatedSegmento
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .canalEnvio(UPDATED_CANAL_ENVIO)
            .agendamentoId(UPDATED_AGENDAMENTO_ID);
        SegmentoDTO segmentoDTO = segmentoMapper.toDto(updatedSegmento);

        restSegmentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, segmentoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(segmentoDTO))
            )
            .andExpect(status().isOk());

        // Validate the Segmento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSegmentoToMatchAllProperties(updatedSegmento);
    }

    @Test
    @Transactional
    void putNonExistingSegmento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        segmento.setId(longCount.incrementAndGet());

        // Create the Segmento
        SegmentoDTO segmentoDTO = segmentoMapper.toDto(segmento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSegmentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, segmentoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(segmentoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Segmento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSegmento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        segmento.setId(longCount.incrementAndGet());

        // Create the Segmento
        SegmentoDTO segmentoDTO = segmentoMapper.toDto(segmento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSegmentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(segmentoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Segmento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSegmento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        segmento.setId(longCount.incrementAndGet());

        // Create the Segmento
        SegmentoDTO segmentoDTO = segmentoMapper.toDto(segmento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSegmentoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(segmentoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Segmento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSegmentoWithPatch() throws Exception {
        // Initialize the database
        insertedSegmento = segmentoRepository.saveAndFlush(segmento);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the segmento using partial update
        Segmento partialUpdatedSegmento = new Segmento();
        partialUpdatedSegmento.setId(segmento.getId());

        partialUpdatedSegmento.descricao(UPDATED_DESCRICAO).canalEnvio(UPDATED_CANAL_ENVIO).agendamentoId(UPDATED_AGENDAMENTO_ID);

        restSegmentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSegmento.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSegmento))
            )
            .andExpect(status().isOk());

        // Validate the Segmento in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSegmentoUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedSegmento, segmento), getPersistedSegmento(segmento));
    }

    @Test
    @Transactional
    void fullUpdateSegmentoWithPatch() throws Exception {
        // Initialize the database
        insertedSegmento = segmentoRepository.saveAndFlush(segmento);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the segmento using partial update
        Segmento partialUpdatedSegmento = new Segmento();
        partialUpdatedSegmento.setId(segmento.getId());

        partialUpdatedSegmento
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .canalEnvio(UPDATED_CANAL_ENVIO)
            .agendamentoId(UPDATED_AGENDAMENTO_ID);

        restSegmentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSegmento.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSegmento))
            )
            .andExpect(status().isOk());

        // Validate the Segmento in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSegmentoUpdatableFieldsEquals(partialUpdatedSegmento, getPersistedSegmento(partialUpdatedSegmento));
    }

    @Test
    @Transactional
    void patchNonExistingSegmento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        segmento.setId(longCount.incrementAndGet());

        // Create the Segmento
        SegmentoDTO segmentoDTO = segmentoMapper.toDto(segmento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSegmentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, segmentoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(segmentoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Segmento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSegmento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        segmento.setId(longCount.incrementAndGet());

        // Create the Segmento
        SegmentoDTO segmentoDTO = segmentoMapper.toDto(segmento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSegmentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(segmentoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Segmento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSegmento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        segmento.setId(longCount.incrementAndGet());

        // Create the Segmento
        SegmentoDTO segmentoDTO = segmentoMapper.toDto(segmento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSegmentoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(segmentoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Segmento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSegmento() throws Exception {
        // Initialize the database
        insertedSegmento = segmentoRepository.saveAndFlush(segmento);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the segmento
        restSegmentoMockMvc
            .perform(delete(ENTITY_API_URL_ID, segmento.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return segmentoRepository.count();
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

    protected Segmento getPersistedSegmento(Segmento segmento) {
        return segmentoRepository.findById(segmento.getId()).orElseThrow();
    }

    protected void assertPersistedSegmentoToMatchAllProperties(Segmento expectedSegmento) {
        assertSegmentoAllPropertiesEquals(expectedSegmento, getPersistedSegmento(expectedSegmento));
    }

    protected void assertPersistedSegmentoToMatchUpdatableProperties(Segmento expectedSegmento) {
        assertSegmentoAllUpdatablePropertiesEquals(expectedSegmento, getPersistedSegmento(expectedSegmento));
    }
}
