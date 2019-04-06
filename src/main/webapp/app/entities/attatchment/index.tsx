import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Attatchment from './attatchment';
import AttatchmentDetail from './attatchment-detail';
import AttatchmentUpdate from './attatchment-update';
import AttatchmentDeleteDialog from './attatchment-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={AttatchmentUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={AttatchmentUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={AttatchmentDetail} />
      <ErrorBoundaryRoute path={match.url} component={Attatchment} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={AttatchmentDeleteDialog} />
  </>
);

export default Routes;
