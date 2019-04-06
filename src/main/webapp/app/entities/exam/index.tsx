import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Exam from './exam';
import ExamDetail from './exam-detail';
import ExamUpdate from './exam-update';
import ExamDeleteDialog from './exam-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ExamUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ExamUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ExamDetail} />
      <ErrorBoundaryRoute path={match.url} component={Exam} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={ExamDeleteDialog} />
  </>
);

export default Routes;
