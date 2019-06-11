import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import DayOfWeek from './day-of-week';
import DayOfWeekDetail from './day-of-week-detail';
import DayOfWeekUpdate from './day-of-week-update';
import DayOfWeekDeleteDialog from './day-of-week-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={DayOfWeekUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={DayOfWeekUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={DayOfWeekDetail} />
      <ErrorBoundaryRoute path={match.url} component={DayOfWeek} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={DayOfWeekDeleteDialog} />
  </>
);

export default Routes;
