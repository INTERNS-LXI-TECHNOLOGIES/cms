import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import StudyMaterial from './study-material';
import StudyMaterialDetail from './study-material-detail';
import StudyMaterialUpdate from './study-material-update';
import StudyMaterialDeleteDialog from './study-material-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={StudyMaterialUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={StudyMaterialUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={StudyMaterialDetail} />
      <ErrorBoundaryRoute path={match.url} component={StudyMaterial} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={StudyMaterialDeleteDialog} />
  </>
);

export default Routes;
