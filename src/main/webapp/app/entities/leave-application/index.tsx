import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import LeaveApplication from './leave-application';
import LeaveApplicationDetail from './leave-application-detail';
import LeaveApplicationUpdate from './leave-application-update';
import LeaveApplicationDeleteDialog from './leave-application-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={LeaveApplicationUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={LeaveApplicationUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={LeaveApplicationDetail} />
      <ErrorBoundaryRoute path={match.url} component={LeaveApplication} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={LeaveApplicationDeleteDialog} />
  </>
);

export default Routes;
