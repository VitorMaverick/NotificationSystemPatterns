import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { ValidatedField, ValidatedForm, isNumber } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getSegmentos } from 'app/entities/notificationSystem/segmento/segmento.reducer';
import { createEntity, getEntity, reset, updateEntity } from './cliente.reducer';

export const ClienteUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const segmentos = useAppSelector(state => state.notificationsystem.segmento.entities);
  const clienteEntity = useAppSelector(state => state.notificationsystem.cliente.entity);
  const loading = useAppSelector(state => state.notificationsystem.cliente.loading);
  const updating = useAppSelector(state => state.notificationsystem.cliente.updating);
  const updateSuccess = useAppSelector(state => state.notificationsystem.cliente.updateSuccess);

  const handleClose = () => {
    navigate(`/notificationsystem/cliente${location.search}`);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getSegmentos({}));
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
    if (values.segmentoId !== undefined && typeof values.segmentoId !== 'number') {
      values.segmentoId = Number(values.segmentoId);
    }

    const entity = {
      ...clienteEntity,
      ...values,
      segmento: segmentos.find(it => it.id.toString() === values.segmento?.toString()),
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
          ...clienteEntity,
          segmento: clienteEntity?.segmento?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="notificationSystemApp.notificationSystemCliente.home.createOrEditLabel" data-cy="ClienteCreateUpdateHeading">
            Criar ou editar Cliente
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="cliente-id" label="Código" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Nome"
                id="cliente-nome"
                name="nome"
                data-cy="nome"
                type="text"
                validate={{
                  required: { value: true, message: 'O campo é obrigatório.' },
                }}
              />
              <ValidatedField
                label="Email"
                id="cliente-email"
                name="email"
                data-cy="email"
                type="text"
                validate={{
                  required: { value: true, message: 'O campo é obrigatório.' },
                }}
              />
              <ValidatedField label="Telefone" id="cliente-telefone" name="telefone" data-cy="telefone" type="text" />
              <ValidatedField
                label="Segmento Id"
                id="cliente-segmentoId"
                name="segmentoId"
                data-cy="segmentoId"
                type="text"
                validate={{
                  required: { value: true, message: 'O campo é obrigatório.' },
                  validate: v => isNumber(v) || 'Este campo é do tipo numérico.',
                }}
              />
              <ValidatedField id="cliente-segmento" name="segmento" data-cy="segmento" label="Segmento" type="select">
                <option value="" key="0" />
                {segmentos
                  ? segmentos.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/notificationsystem/cliente" replace color="info">
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

export default ClienteUpdate;
