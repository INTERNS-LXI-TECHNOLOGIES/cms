import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Assignment from './assignment';
import AssignmentDetail from './assignment-detail';
import AssignmentUpdate from './assignment-update';
import AssignmentDeleteDialog from './assignment-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={AssignmentUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={AssignmentUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={AssignmentDetail} />
      <ErrorBoundaryRoute path={match.url} component={Assignment} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={AssignmentDeleteDialog} />
  </>
);

export default Routes;
