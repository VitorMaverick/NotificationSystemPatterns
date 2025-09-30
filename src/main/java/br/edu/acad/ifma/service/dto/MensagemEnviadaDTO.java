package br.edu.acad.ifma.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link br.edu.acad.ifma.domain.MensagemEnviada} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MensagemEnviadaDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant dataEnvio;

    private String status;

    @NotNull
    private Long clienteId;

    @NotNull
    private Long templateId;

    private ClienteDTO cliente;

    private TemplateDTO template;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDataEnvio() {
        return dataEnvio;
    }

    public void setDataEnvio(Instant dataEnvio) {
        this.dataEnvio = dataEnvio;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public ClienteDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
    }

    public TemplateDTO getTemplate() {
        return template;
    }

    public void setTemplate(TemplateDTO template) {
        this.template = template;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MensagemEnviadaDTO)) {
            return false;
        }

        MensagemEnviadaDTO mensagemEnviadaDTO = (MensagemEnviadaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, mensagemEnviadaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MensagemEnviadaDTO{" +
            "id=" + getId() +
            ", dataEnvio='" + getDataEnvio() + "'" +
            ", status='" + getStatus() + "'" +
            ", clienteId=" + getClienteId() +
            ", templateId=" + getTemplateId() +
            ", cliente=" + getCliente() +
            ", template=" + getTemplate() +
            "}";
    }
}
