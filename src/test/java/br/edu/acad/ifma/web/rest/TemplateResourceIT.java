package br.edu.acad.ifma.web.rest;

import static br.edu.acad.ifma.domain.TemplateAsserts.*;
import static br.edu.acad.ifma.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.edu.acad.ifma.IntegrationTest;
import br.edu.acad.ifma.domain.Template;
import br.edu.acad.ifma.repository.TemplateRepository;
import br.edu.acad.ifma.service.dto.TemplateDTO;
import br.edu.acad.ifma.service.mapper.TemplateMapper;
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
 * Integration tests for the {@link TemplateResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TemplateResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_TITULO_MENSAGEM = "AAAAAAAAAA";
    private static final String UPDATED_TITULO_MENSAGEM = "BBBBBBBBBB";

    private static final String DEFAULT_CORPO_MENSAGEM = "AAAAAAAAAA";
    private static final String UPDATED_CORPO_MENSAGEM = "BBBBBBBBBB";

    private static final String DEFAULT_TIPO_MENSAGEM = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_MENSAGEM = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/templates";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TemplateRepository templateRepository;

    @Autowired
    private TemplateMapper templateMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTemplateMockMvc;

    private Template template;

    private Template insertedTemplate;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Template createEntity() {
        return new Template()
            .nome(DEFAULT_NOME)
            .tituloMensagem(DEFAULT_TITULO_MENSAGEM)
            .corpoMensagem(DEFAULT_CORPO_MENSAGEM)
            .tipoMensagem(DEFAULT_TIPO_MENSAGEM);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Template createUpdatedEntity() {
        return new Template()
            .nome(UPDATED_NOME)
            .tituloMensagem(UPDATED_TITULO_MENSAGEM)
            .corpoMensagem(UPDATED_CORPO_MENSAGEM)
            .tipoMensagem(UPDATED_TIPO_MENSAGEM);
    }

    @BeforeEach
    void initTest() {
        template = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedTemplate != null) {
            templateRepository.delete(insertedTemplate);
            insertedTemplate = null;
        }
    }

    @Test
    @Transactional
    void createTemplate() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Template
        TemplateDTO templateDTO = templateMapper.toDto(template);
        var returnedTemplateDTO = om.readValue(
            restTemplateMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(templateDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            TemplateDTO.class
        );

        // Validate the Template in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedTemplate = templateMapper.toEntity(returnedTemplateDTO);
        assertTemplateUpdatableFieldsEquals(returnedTemplate, getPersistedTemplate(returnedTemplate));

        insertedTemplate = returnedTemplate;
    }

    @Test
    @Transactional
    void createTemplateWithExistingId() throws Exception {
        // Create the Template with an existing ID
        template.setId(1L);
        TemplateDTO templateDTO = templateMapper.toDto(template);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTemplateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(templateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Template in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        template.setNome(null);

        // Create the Template, which fails.
        TemplateDTO templateDTO = templateMapper.toDto(template);

        restTemplateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(templateDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTituloMensagemIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        template.setTituloMensagem(null);

        // Create the Template, which fails.
        TemplateDTO templateDTO = templateMapper.toDto(template);

        restTemplateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(templateDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCorpoMensagemIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        template.setCorpoMensagem(null);

        // Create the Template, which fails.
        TemplateDTO templateDTO = templateMapper.toDto(template);

        restTemplateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(templateDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTemplates() throws Exception {
        // Initialize the database
        insertedTemplate = templateRepository.saveAndFlush(template);

        // Get all the templateList
        restTemplateMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(template.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].tituloMensagem").value(hasItem(DEFAULT_TITULO_MENSAGEM)))
            .andExpect(jsonPath("$.[*].corpoMensagem").value(hasItem(DEFAULT_CORPO_MENSAGEM)))
            .andExpect(jsonPath("$.[*].tipoMensagem").value(hasItem(DEFAULT_TIPO_MENSAGEM)));
    }

    @Test
    @Transactional
    void getTemplate() throws Exception {
        // Initialize the database
        insertedTemplate = templateRepository.saveAndFlush(template);

        // Get the template
        restTemplateMockMvc
            .perform(get(ENTITY_API_URL_ID, template.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(template.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.tituloMensagem").value(DEFAULT_TITULO_MENSAGEM))
            .andExpect(jsonPath("$.corpoMensagem").value(DEFAULT_CORPO_MENSAGEM))
            .andExpect(jsonPath("$.tipoMensagem").value(DEFAULT_TIPO_MENSAGEM));
    }

    @Test
    @Transactional
    void getNonExistingTemplate() throws Exception {
        // Get the template
        restTemplateMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTemplate() throws Exception {
        // Initialize the database
        insertedTemplate = templateRepository.saveAndFlush(template);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the template
        Template updatedTemplate = templateRepository.findById(template.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTemplate are not directly saved in db
        em.detach(updatedTemplate);
        updatedTemplate
            .nome(UPDATED_NOME)
            .tituloMensagem(UPDATED_TITULO_MENSAGEM)
            .corpoMensagem(UPDATED_CORPO_MENSAGEM)
            .tipoMensagem(UPDATED_TIPO_MENSAGEM);
        TemplateDTO templateDTO = templateMapper.toDto(updatedTemplate);

        restTemplateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, templateDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(templateDTO))
            )
            .andExpect(status().isOk());

        // Validate the Template in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTemplateToMatchAllProperties(updatedTemplate);
    }

    @Test
    @Transactional
    void putNonExistingTemplate() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        template.setId(longCount.incrementAndGet());

        // Create the Template
        TemplateDTO templateDTO = templateMapper.toDto(template);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTemplateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, templateDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(templateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Template in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTemplate() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        template.setId(longCount.incrementAndGet());

        // Create the Template
        TemplateDTO templateDTO = templateMapper.toDto(template);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTemplateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(templateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Template in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTemplate() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        template.setId(longCount.incrementAndGet());

        // Create the Template
        TemplateDTO templateDTO = templateMapper.toDto(template);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTemplateMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(templateDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Template in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTemplateWithPatch() throws Exception {
        // Initialize the database
        insertedTemplate = templateRepository.saveAndFlush(template);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the template using partial update
        Template partialUpdatedTemplate = new Template();
        partialUpdatedTemplate.setId(template.getId());

        partialUpdatedTemplate.tituloMensagem(UPDATED_TITULO_MENSAGEM).tipoMensagem(UPDATED_TIPO_MENSAGEM);

        restTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTemplate.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTemplate))
            )
            .andExpect(status().isOk());

        // Validate the Template in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTemplateUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedTemplate, template), getPersistedTemplate(template));
    }

    @Test
    @Transactional
    void fullUpdateTemplateWithPatch() throws Exception {
        // Initialize the database
        insertedTemplate = templateRepository.saveAndFlush(template);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the template using partial update
        Template partialUpdatedTemplate = new Template();
        partialUpdatedTemplate.setId(template.getId());

        partialUpdatedTemplate
            .nome(UPDATED_NOME)
            .tituloMensagem(UPDATED_TITULO_MENSAGEM)
            .corpoMensagem(UPDATED_CORPO_MENSAGEM)
            .tipoMensagem(UPDATED_TIPO_MENSAGEM);

        restTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTemplate.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTemplate))
            )
            .andExpect(status().isOk());

        // Validate the Template in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTemplateUpdatableFieldsEquals(partialUpdatedTemplate, getPersistedTemplate(partialUpdatedTemplate));
    }

    @Test
    @Transactional
    void patchNonExistingTemplate() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        template.setId(longCount.incrementAndGet());

        // Create the Template
        TemplateDTO templateDTO = templateMapper.toDto(template);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, templateDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(templateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Template in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTemplate() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        template.setId(longCount.incrementAndGet());

        // Create the Template
        TemplateDTO templateDTO = templateMapper.toDto(template);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(templateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Template in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTemplate() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        template.setId(longCount.incrementAndGet());

        // Create the Template
        TemplateDTO templateDTO = templateMapper.toDto(template);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTemplateMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(templateDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Template in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTemplate() throws Exception {
        // Initialize the database
        insertedTemplate = templateRepository.saveAndFlush(template);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the template
        restTemplateMockMvc
            .perform(delete(ENTITY_API_URL_ID, template.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return templateRepository.count();
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

    protected Template getPersistedTemplate(Template template) {
        return templateRepository.findById(template.getId()).orElseThrow();
    }

    protected void assertPersistedTemplateToMatchAllProperties(Template expectedTemplate) {
        assertTemplateAllPropertiesEquals(expectedTemplate, getPersistedTemplate(expectedTemplate));
    }

    protected void assertPersistedTemplateToMatchUpdatableProperties(Template expectedTemplate) {
        assertTemplateAllUpdatablePropertiesEquals(expectedTemplate, getPersistedTemplate(expectedTemplate));
    }
}
