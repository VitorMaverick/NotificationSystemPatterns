import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { ValidatedField, ValidatedForm, isNumber } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getAgendamentos } from 'app/entities/notificationSystem/agendamento/agendamento.reducer';
import { createEntity, getEntity, reset, updateEntity } from './segmento.reducer';

export const SegmentoUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const agendamentos = useAppSelector(state => state.notificationsystem.agendamento.entities);
  const segmentoEntity = useAppSelector(state => state.notificationsystem.segmento.entity);
  const loading = useAppSelector(state => state.notificationsystem.segmento.loading);
  const updating = useAppSelector(state => state.notificationsystem.segmento.updating);
  const updateSuccess = useAppSelector(state => state.notificationsystem.segmento.updateSuccess);

  const handleClose = () => {
    navigate(`/notificationsystem/segmento${location.search}`);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getAgendamentos({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }
    if (values.agendamentoId !== undefined && typeof values.agendamentoId !== 'number') {
      values.agendamentoId = Number(values.agendamentoId);
    }

    const entity = {
      ...segmentoEntity,
      ...values,
      agendamento: agendamentos.find(it => it.id.toString() === values.agendamento?.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...segmentoEntity,
          agendamento: segmentoEntity?.agendamento?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="notificationSystemApp.notificationSystemSegmento.home.createOrEditLabel" data-cy="SegmentoCreateUpdateHeading">
            Criar ou editar Segmento
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="segmento-id" label="Código" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Nome"
                id="segmento-nome"
                name="nome"
                data-cy="nome"
                type="text"
                validate={{
                  required: { value: true, message: 'O campo é obrigatório.' },
                }}
              />
              <ValidatedField label="Descricao" id="segmento-descricao" name="descricao" data-cy="descricao" type="text" />
              <ValidatedField
                label="Canal Envio"
                id="segmento-canalEnvio"
                name="canalEnvio"
                data-cy="canalEnvio"
                type="text"
                validate={{
                  required: { value: true, message: 'O campo é obrigatório.' },
                }}
              />
              <ValidatedField
                label="Agendamento Id"
                id="segmento-agendamentoId"
                name="agendamentoId"
                data-cy="agendamentoId"
                type="text"
                validate={{
                  required: { value: true, message: 'O campo é obrigatório.' },
                  validate: v => isNumber(v) || 'Este campo é do tipo numérico.',
                }}
              />
              <ValidatedField id="segmento-agendamento" name="agendamento" data-cy="agendamento" label="Agendamento" type="select">
                <option value="" key="0" />
                {agendamentos
                  ? agendamentos.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/notificationsystem/segmento" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Voltar</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Salvar
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default SegmentoUpdate;
