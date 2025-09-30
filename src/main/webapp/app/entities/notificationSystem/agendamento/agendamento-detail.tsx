import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './agendamento.reducer';

export const AgendamentoDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const agendamentoEntity = useAppSelector(state => state.notificationsystem.agendamento.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="agendamentoDetailsHeading">Agendamento</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">Código</span>
          </dt>
          <dd>{agendamentoEntity.id}</dd>
          <dt>
            <span id="periodicidade">Periodicidade</span>
          </dt>
          <dd>{agendamentoEntity.periodicidade}</dd>
          <dt>
            <span id="horaEnvio">Hora Envio</span>
          </dt>
          <dd>{agendamentoEntity.horaEnvio}</dd>
        </dl>
        <Button tag={Link} to="/notificationsystem/agendamento" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Voltar</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/notificationsystem/agendamento/${agendamentoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AgendamentoDetail;
