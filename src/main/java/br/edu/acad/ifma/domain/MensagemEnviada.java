package br.edu.acad.ifma.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * A MensagemEnviada.
 */
@Entity
@Table(name = "mensagem_enviada")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MensagemEnviada implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "data_envio", nullable = false)
    private Instant dataEnvio;

    @Column(name = "status")
    private String status;

    @NotNull
    @Column(name = "cliente_id", nullable = false)
    private Long clienteId;

    @NotNull
    @Column(name = "template_id", nullable = false)
    private Long templateId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "segmento" }, allowSetters = true)
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    private Template template;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MensagemEnviada id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDataEnvio() {
        return this.dataEnvio;
    }

    public MensagemEnviada dataEnvio(Instant dataEnvio) {
        this.setDataEnvio(dataEnvio);
        return this;
    }

    public void setDataEnvio(Instant dataEnvio) {
        this.dataEnvio = dataEnvio;
    }

    public String getStatus() {
        return this.status;
    }

    public MensagemEnviada status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getClienteId() {
        return this.clienteId;
    }

    public MensagemEnviada clienteId(Long clienteId) {
        this.setClienteId(clienteId);
        return this;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public Long getTemplateId() {
        return this.templateId;
    }

    public MensagemEnviada templateId(Long templateId) {
        this.setTemplateId(templateId);
        return this;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public Cliente getCliente() {
        return this.cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public MensagemEnviada cliente(Cliente cliente) {
        this.setCliente(cliente);
        return this;
    }

    public Template getTemplate() {
        return this.template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

    public MensagemEnviada template(Template template) {
        this.setTemplate(template);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MensagemEnviada)) {
            return false;
        }
        return getId() != null && getId().equals(((MensagemEnviada) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MensagemEnviada{" +
            "id=" + getId() +
            ", dataEnvio='" + getDataEnvio() + "'" +
            ", status='" + getStatus() + "'" +
            ", clienteId=" + getClienteId() +
            ", templateId=" + getTemplateId() +
            "}";
    }
}
