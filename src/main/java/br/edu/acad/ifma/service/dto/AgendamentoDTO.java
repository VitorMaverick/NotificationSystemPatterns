package br.edu.acad.ifma.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link br.edu.acad.ifma.domain.Agendamento} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AgendamentoDTO implements Serializable {

    private Long id;

    @NotNull
    private String periodicidade;

    private String horaEnvio;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPeriodicidade() {
        return periodicidade;
    }

    public void setPeriodicidade(String periodicidade) {
        this.periodicidade = periodicidade;
    }

    public String getHoraEnvio() {
        return horaEnvio;
    }

    public void setHoraEnvio(String horaEnvio) {
        this.horaEnvio = horaEnvio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AgendamentoDTO)) {
            return false;
        }

        AgendamentoDTO agendamentoDTO = (AgendamentoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, agendamentoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AgendamentoDTO{" +
            "id=" + getId() +
            ", periodicidade='" + getPeriodicidade() + "'" +
            ", horaEnvio='" + getHoraEnvio() + "'" +
            "}";
    }
}
