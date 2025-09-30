import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import MensagemEnviada from './mensagem-enviada';
import MensagemEnviadaDetail from './mensagem-enviada-detail';
import MensagemEnviadaUpdate from './mensagem-enviada-update';
import MensagemEnviadaDeleteDialog from './mensagem-enviada-delete-dialog';

const MensagemEnviadaRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<MensagemEnviada />} />
    <Route path="new" element={<MensagemEnviadaUpdate />} />
    <Route path=":id">
      <Route index element={<MensagemEnviadaDetail />} />
      <Route path="edit" element={<MensagemEnviadaUpdate />} />
      <Route path="delete" element={<MensagemEnviadaDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default MensagemEnviadaRoutes;
