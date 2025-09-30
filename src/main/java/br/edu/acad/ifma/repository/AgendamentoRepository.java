package br.edu.acad.ifma.repository;

import br.edu.acad.ifma.domain.Agendamento;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Agendamento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {}
