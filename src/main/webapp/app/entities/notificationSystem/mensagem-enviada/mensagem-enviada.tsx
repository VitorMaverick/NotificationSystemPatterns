import React, { useEffect, useState } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { JhiItemCount, JhiPagination, TextFormat, getPaginationState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortDown, faSortUp } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './mensagem-enviada.reducer';

export const MensagemEnviada = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );

  const mensagemEnviadaList = useAppSelector(state => state.notificationsystem.mensagemEnviada.entities);
  const loading = useAppSelector(state => state.notificationsystem.mensagemEnviada.loading);
  const totalItems = useAppSelector(state => state.notificationsystem.mensagemEnviada.totalItems);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      }),
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (pageLocation.search !== endURL) {
      navigate(`${pageLocation.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(pageLocation.search);
    const page = params.get('page');
    const sort = params.get(SORT);
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [pageLocation.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const handleSyncList = () => {
    sortEntities();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = paginationState.sort;
    const order = paginationState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    }
    return order === ASC ? faSortUp : faSortDown;
  };

  return (
    <div>
      <h2 id="mensagem-enviada-heading" data-cy="MensagemEnviadaHeading">
        Mensagem Enviadas
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Atualizar lista
          </Button>
          <Link
            to="/notificationsystem/mensagem-enviada/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp; Criar novo Mensagem Enviada
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {mensagemEnviadaList && mensagemEnviadaList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('dataEnvio')}>
                  Data Envio <FontAwesomeIcon icon={getSortIconByFieldName('dataEnvio')} />
                </th>
                <th className="hand" onClick={sort('status')}>
                  Status <FontAwesomeIcon icon={getSortIconByFieldName('status')} />
                </th>
                <th className="hand" onClick={sort('clienteId')}>
                  Cliente Id <FontAwesomeIcon icon={getSortIconByFieldName('clienteId')} />
                </th>
                <th className="hand" onClick={sort('templateId')}>
                  Template Id <FontAwesomeIcon icon={getSortIconByFieldName('templateId')} />
                </th>
                <th>
                  Cliente <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Template <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {mensagemEnviadaList.map((mensagemEnviada, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/notificationsystem/mensagem-enviada/${mensagemEnviada.id}`} color="link" size="sm">
                      {mensagemEnviada.id}
                    </Button>
                  </td>
                  <td>
                    {mensagemEnviada.dataEnvio ? (
                      <TextFormat type="date" value={mensagemEnviada.dataEnvio} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{mensagemEnviada.status}</td>
                  <td>{mensagemEnviada.clienteId}</td>
                  <td>{mensagemEnviada.templateId}</td>
                  <td>
                    {mensagemEnviada.cliente ? (
                      <Link to={`/notificationsystem/cliente/${mensagemEnviada.cliente.id}`}>{mensagemEnviada.cliente.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {mensagemEnviada.template ? (
                      <Link to={`/notificationsystem/template/${mensagemEnviada.template.id}`}>{mensagemEnviada.template.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/notificationsystem/mensagem-enviada/${mensagemEnviada.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">Visualizar</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/notificationsystem/mensagem-enviada/${mensagemEnviada.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
                      </Button>
                      <Button
                        onClick={() =>
                          (window.location.href = `/notificationsystem/mensagem-enviada/${mensagemEnviada.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`)
                        }
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Excluir</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">Nenhum Mensagem Enviada encontrado</div>
        )}
      </div>
      {totalItems ? (
        <div className={mensagemEnviadaList && mensagemEnviadaList.length > 0 ? '' : 'd-none'}>
          <div className="justify-content-center d-flex">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} />
          </div>
          <div className="justify-content-center d-flex">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={totalItems}
            />
          </div>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

export default MensagemEnviada;
