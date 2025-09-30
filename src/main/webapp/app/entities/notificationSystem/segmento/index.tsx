import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Segmento from './segmento';
import SegmentoDetail from './segmento-detail';
import SegmentoUpdate from './segmento-update';
import SegmentoDeleteDialog from './segmento-delete-dialog';

const SegmentoRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Segmento />} />
    <Route path="new" element={<SegmentoUpdate />} />
    <Route path=":id">
      <Route index element={<SegmentoDetail />} />
      <Route path="edit" element={<SegmentoUpdate />} />
      <Route path="delete" element={<SegmentoDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SegmentoRoutes;
