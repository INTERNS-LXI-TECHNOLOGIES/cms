import React from 'react';
import { Switch } from 'react-router-dom';

// tslint:disable-next-line:no-unused-variable
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import UserDomain from './user-domain';
import UserRole from './user-role';
import Address from './address';
import Event from './event';
import Qualification from './qualification';
import LeaveApplication from './leave-application';
import Attatchment from './attatchment';
import ExamSchedule from './exam-schedule';
import Exam from './exam';
import StudyMaterial from './study-material';
import ExamHall from './exam-hall';
import Subject from './subject';
import Assignment from './assignment';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}/user-domain`} component={UserDomain} />
      <ErrorBoundaryRoute path={`${match.url}/user-role`} component={UserRole} />
      <ErrorBoundaryRoute path={`${match.url}/address`} component={Address} />
      <ErrorBoundaryRoute path={`${match.url}/event`} component={Event} />
      <ErrorBoundaryRoute path={`${match.url}/qualification`} component={Qualification} />
      <ErrorBoundaryRoute path={`${match.url}/leave-application`} component={LeaveApplication} />
      <ErrorBoundaryRoute path={`${match.url}/attatchment`} component={Attatchment} />
      <ErrorBoundaryRoute path={`${match.url}/exam-schedule`} component={ExamSchedule} />
      <ErrorBoundaryRoute path={`${match.url}/exam`} component={Exam} />
      <ErrorBoundaryRoute path={`${match.url}/study-material`} component={StudyMaterial} />
      <ErrorBoundaryRoute path={`${match.url}/exam-hall`} component={ExamHall} />
      <ErrorBoundaryRoute path={`${match.url}/subject`} component={Subject} />
      <ErrorBoundaryRoute path={`${match.url}/assignment`} component={Assignment} />
      {/* jhipster-needle-add-route-path - JHipster will routes here */}
    </Switch>
  </div>
);

export default Routes;
