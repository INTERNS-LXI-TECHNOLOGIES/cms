import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ExamSchedule from './exam-schedule';
import ExamScheduleDetail from './exam-schedule-detail';
import ExamScheduleUpdate from './exam-schedule-update';
import ExamScheduleDeleteDialog from './exam-schedule-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ExamScheduleUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ExamScheduleUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ExamScheduleDetail} />
      <ErrorBoundaryRoute path={match.url} component={ExamSchedule} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={ExamScheduleDeleteDialog} />
  </>
);

export default Routes;
