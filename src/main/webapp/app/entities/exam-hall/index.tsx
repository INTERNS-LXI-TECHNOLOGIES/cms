import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ExamHall from './exam-hall';
import ExamHallDetail from './exam-hall-detail';
import ExamHallUpdate from './exam-hall-update';
import ExamHallDeleteDialog from './exam-hall-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ExamHallUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ExamHallUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ExamHallDetail} />
      <ErrorBoundaryRoute path={match.url} component={ExamHall} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={ExamHallDeleteDialog} />
  </>
);

export default Routes;
