package br.edu.acad.ifma.web.rest;

import static br.edu.acad.ifma.domain.MensagemEnviadaAsserts.*;
import static br.edu.acad.ifma.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.edu.acad.ifma.IntegrationTest;
import br.edu.acad.ifma.domain.MensagemEnviada;
import br.edu.acad.ifma.repository.MensagemEnviadaRepository;
import br.edu.acad.ifma.service.dto.MensagemEnviadaDTO;
import br.edu.acad.ifma.service.mapper.MensagemEnviadaMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link MensagemEnviadaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MensagemEnviadaResourceIT {

    private static final Instant DEFAULT_DATA_ENVIO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_ENVIO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Long DEFAULT_CLIENTE_ID = 1L;
    private static final Long UPDATED_CLIENTE_ID = 2L;

    private static final Long DEFAULT_TEMPLATE_ID = 1L;
    private static final Long UPDATED_TEMPLATE_ID = 2L;

    private static final String ENTITY_API_URL = "/api/mensagem-enviadas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MensagemEnviadaRepository mensagemEnviadaRepository;

    @Autowired
    private MensagemEnviadaMapper mensagemEnviadaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMensagemEnviadaMockMvc;

    private MensagemEnviada mensagemEnviada;

    private MensagemEnviada insertedMensagemEnviada;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MensagemEnviada createEntity() {
        return new MensagemEnviada()
            .dataEnvio(DEFAULT_DATA_ENVIO)
            .status(DEFAULT_STATUS)
            .clienteId(DEFAULT_CLIENTE_ID)
            .templateId(DEFAULT_TEMPLATE_ID);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MensagemEnviada createUpdatedEntity() {
        return new MensagemEnviada()
            .dataEnvio(UPDATED_DATA_ENVIO)
            .status(UPDATED_STATUS)
            .clienteId(UPDATED_CLIENTE_ID)
            .templateId(UPDATED_TEMPLATE_ID);
    }

    @BeforeEach
    void initTest() {
        mensagemEnviada = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedMensagemEnviada != null) {
            mensagemEnviadaRepository.delete(insertedMensagemEnviada);
            insertedMensagemEnviada = null;
        }
    }

    @Test
    @Transactional
    void createMensagemEnviada() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the MensagemEnviada
        MensagemEnviadaDTO mensagemEnviadaDTO = mensagemEnviadaMapper.toDto(mensagemEnviada);
        var returnedMensagemEnviadaDTO = om.readValue(
            restMensagemEnviadaMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(mensagemEnviadaDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            MensagemEnviadaDTO.class
        );

        // Validate the MensagemEnviada in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedMensagemEnviada = mensagemEnviadaMapper.toEntity(returnedMensagemEnviadaDTO);
        assertMensagemEnviadaUpdatableFieldsEquals(returnedMensagemEnviada, getPersistedMensagemEnviada(returnedMensagemEnviada));

        insertedMensagemEnviada = returnedMensagemEnviada;
    }

    @Test
    @Transactional
    void createMensagemEnviadaWithExistingId() throws Exception {
        // Create the MensagemEnviada with an existing ID
        mensagemEnviada.setId(1L);
        MensagemEnviadaDTO mensagemEnviadaDTO = mensagemEnviadaMapper.toDto(mensagemEnviada);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMensagemEnviadaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(mensagemEnviadaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MensagemEnviada in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDataEnvioIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        mensagemEnviada.setDataEnvio(null);

        // Create the MensagemEnviada, which fails.
        MensagemEnviadaDTO mensagemEnviadaDTO = mensagemEnviadaMapper.toDto(mensagemEnviada);

        restMensagemEnviadaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(mensagemEnviadaDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkClienteIdIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        mensagemEnviada.setClienteId(null);

        // Create the MensagemEnviada, which fails.
        MensagemEnviadaDTO mensagemEnviadaDTO = mensagemEnviadaMapper.toDto(mensagemEnviada);

        restMensagemEnviadaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(mensagemEnviadaDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTemplateIdIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        mensagemEnviada.setTemplateId(null);

        // Create the MensagemEnviada, which fails.
        MensagemEnviadaDTO mensagemEnviadaDTO = mensagemEnviadaMapper.toDto(mensagemEnviada);

        restMensagemEnviadaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(mensagemEnviadaDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMensagemEnviadas() throws Exception {
        // Initialize the database
        insertedMensagemEnviada = mensagemEnviadaRepository.saveAndFlush(mensagemEnviada);

        // Get all the mensagemEnviadaList
        restMensagemEnviadaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mensagemEnviada.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataEnvio").value(hasItem(DEFAULT_DATA_ENVIO.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].clienteId").value(hasItem(DEFAULT_CLIENTE_ID.intValue())))
            .andExpect(jsonPath("$.[*].templateId").value(hasItem(DEFAULT_TEMPLATE_ID.intValue())));
    }

    @Test
    @Transactional
    void getMensagemEnviada() throws Exception {
        // Initialize the database
        insertedMensagemEnviada = mensagemEnviadaRepository.saveAndFlush(mensagemEnviada);

        // Get the mensagemEnviada
        restMensagemEnviadaMockMvc
            .perform(get(ENTITY_API_URL_ID, mensagemEnviada.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mensagemEnviada.getId().intValue()))
            .andExpect(jsonPath("$.dataEnvio").value(DEFAULT_DATA_ENVIO.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.clienteId").value(DEFAULT_CLIENTE_ID.intValue()))
            .andExpect(jsonPath("$.templateId").value(DEFAULT_TEMPLATE_ID.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingMensagemEnviada() throws Exception {
        // Get the mensagemEnviada
        restMensagemEnviadaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMensagemEnviada() throws Exception {
        // Initialize the database
        insertedMensagemEnviada = mensagemEnviadaRepository.saveAndFlush(mensagemEnviada);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the mensagemEnviada
        MensagemEnviada updatedMensagemEnviada = mensagemEnviadaRepository.findById(mensagemEnviada.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMensagemEnviada are not directly saved in db
        em.detach(updatedMensagemEnviada);
        updatedMensagemEnviada
            .dataEnvio(UPDATED_DATA_ENVIO)
            .status(UPDATED_STATUS)
            .clienteId(UPDATED_CLIENTE_ID)
            .templateId(UPDATED_TEMPLATE_ID);
        MensagemEnviadaDTO mensagemEnviadaDTO = mensagemEnviadaMapper.toDto(updatedMensagemEnviada);

        restMensagemEnviadaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, mensagemEnviadaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(mensagemEnviadaDTO))
            )
            .andExpect(status().isOk());

        // Validate the MensagemEnviada in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedMensagemEnviadaToMatchAllProperties(updatedMensagemEnviada);
    }

    @Test
    @Transactional
    void putNonExistingMensagemEnviada() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mensagemEnviada.setId(longCount.incrementAndGet());

        // Create the MensagemEnviada
        MensagemEnviadaDTO mensagemEnviadaDTO = mensagemEnviadaMapper.toDto(mensagemEnviada);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMensagemEnviadaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, mensagemEnviadaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(mensagemEnviadaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MensagemEnviada in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMensagemEnviada() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mensagemEnviada.setId(longCount.incrementAndGet());

        // Create the MensagemEnviada
        MensagemEnviadaDTO mensagemEnviadaDTO = mensagemEnviadaMapper.toDto(mensagemEnviada);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMensagemEnviadaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(mensagemEnviadaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MensagemEnviada in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMensagemEnviada() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mensagemEnviada.setId(longCount.incrementAndGet());

        // Create the MensagemEnviada
        MensagemEnviadaDTO mensagemEnviadaDTO = mensagemEnviadaMapper.toDto(mensagemEnviada);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMensagemEnviadaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(mensagemEnviadaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MensagemEnviada in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMensagemEnviadaWithPatch() throws Exception {
        // Initialize the database
        insertedMensagemEnviada = mensagemEnviadaRepository.saveAndFlush(mensagemEnviada);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the mensagemEnviada using partial update
        MensagemEnviada partialUpdatedMensagemEnviada = new MensagemEnviada();
        partialUpdatedMensagemEnviada.setId(mensagemEnviada.getId());

        partialUpdatedMensagemEnviada
            .dataEnvio(UPDATED_DATA_ENVIO)
            .status(UPDATED_STATUS)
            .clienteId(UPDATED_CLIENTE_ID)
            .templateId(UPDATED_TEMPLATE_ID);

        restMensagemEnviadaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMensagemEnviada.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMensagemEnviada))
            )
            .andExpect(status().isOk());

        // Validate the MensagemEnviada in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMensagemEnviadaUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedMensagemEnviada, mensagemEnviada),
            getPersistedMensagemEnviada(mensagemEnviada)
        );
    }

    @Test
    @Transactional
    void fullUpdateMensagemEnviadaWithPatch() throws Exception {
        // Initialize the database
        insertedMensagemEnviada = mensagemEnviadaRepository.saveAndFlush(mensagemEnviada);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the mensagemEnviada using partial update
        MensagemEnviada partialUpdatedMensagemEnviada = new MensagemEnviada();
        partialUpdatedMensagemEnviada.setId(mensagemEnviada.getId());

        partialUpdatedMensagemEnviada
            .dataEnvio(UPDATED_DATA_ENVIO)
            .status(UPDATED_STATUS)
            .clienteId(UPDATED_CLIENTE_ID)
            .templateId(UPDATED_TEMPLATE_ID);

        restMensagemEnviadaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMensagemEnviada.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMensagemEnviada))
            )
            .andExpect(status().isOk());

        // Validate the MensagemEnviada in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMensagemEnviadaUpdatableFieldsEquals(
            partialUpdatedMensagemEnviada,
            getPersistedMensagemEnviada(partialUpdatedMensagemEnviada)
        );
    }

    @Test
    @Transactional
    void patchNonExistingMensagemEnviada() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mensagemEnviada.setId(longCount.incrementAndGet());

        // Create the MensagemEnviada
        MensagemEnviadaDTO mensagemEnviadaDTO = mensagemEnviadaMapper.toDto(mensagemEnviada);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMensagemEnviadaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, mensagemEnviadaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(mensagemEnviadaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MensagemEnviada in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMensagemEnviada() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mensagemEnviada.setId(longCount.incrementAndGet());

        // Create the MensagemEnviada
        MensagemEnviadaDTO mensagemEnviadaDTO = mensagemEnviadaMapper.toDto(mensagemEnviada);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMensagemEnviadaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(mensagemEnviadaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MensagemEnviada in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMensagemEnviada() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mensagemEnviada.setId(longCount.incrementAndGet());

        // Create the MensagemEnviada
        MensagemEnviadaDTO mensagemEnviadaDTO = mensagemEnviadaMapper.toDto(mensagemEnviada);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMensagemEnviadaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(mensagemEnviadaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MensagemEnviada in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMensagemEnviada() throws Exception {
        // Initialize the database
        insertedMensagemEnviada = mensagemEnviadaRepository.saveAndFlush(mensagemEnviada);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the mensagemEnviada
        restMensagemEnviadaMockMvc
            .perform(delete(ENTITY_API_URL_ID, mensagemEnviada.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return mensagemEnviadaRepository.count();
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

    protected MensagemEnviada getPersistedMensagemEnviada(MensagemEnviada mensagemEnviada) {
        return mensagemEnviadaRepository.findById(mensagemEnviada.getId()).orElseThrow();
    }

    protected void assertPersistedMensagemEnviadaToMatchAllProperties(MensagemEnviada expectedMensagemEnviada) {
        assertMensagemEnviadaAllPropertiesEquals(expectedMensagemEnviada, getPersistedMensagemEnviada(expectedMensagemEnviada));
    }

    protected void assertPersistedMensagemEnviadaToMatchUpdatableProperties(MensagemEnviada expectedMensagemEnviada) {
        assertMensagemEnviadaAllUpdatablePropertiesEquals(expectedMensagemEnviada, getPersistedMensagemEnviada(expectedMensagemEnviada));
    }
}
