import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import UserDomain from './user-domain';
import UserDomainDetail from './user-domain-detail';
import UserDomainUpdate from './user-domain-update';
import UserDomainDeleteDialog from './user-domain-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={UserDomainUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={UserDomainUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={UserDomainDetail} />
      <ErrorBoundaryRoute path={match.url} component={UserDomain} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={UserDomainDeleteDialog} />
  </>
);

export default Routes;
