package br.edu.acad.ifma.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link br.edu.acad.ifma.domain.Segmento} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SegmentoDTO implements Serializable {

    private Long id;

    @NotNull
    private String nome;

    private String descricao;

    @NotNull
    private String canalEnvio;

    @NotNull
    private Long agendamentoId;

    private AgendamentoDTO agendamento;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCanalEnvio() {
        return canalEnvio;
    }

    public void setCanalEnvio(String canalEnvio) {
        this.canalEnvio = canalEnvio;
    }

    public Long getAgendamentoId() {
        return agendamentoId;
    }

    public void setAgendamentoId(Long agendamentoId) {
        this.agendamentoId = agendamentoId;
    }

    public AgendamentoDTO getAgendamento() {
        return agendamento;
    }

    public void setAgendamento(AgendamentoDTO agendamento) {
        this.agendamento = agendamento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SegmentoDTO)) {
            return false;
        }

        SegmentoDTO segmentoDTO = (SegmentoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, segmentoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SegmentoDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", canalEnvio='" + getCanalEnvio() + "'" +
            ", agendamentoId=" + getAgendamentoId() +
            ", agendamento=" + getAgendamento() +
            "}";
    }
}
