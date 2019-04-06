import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './leave-application.reducer';
import { ILeaveApplication } from 'app/shared/model/leave-application.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ILeaveApplicationDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class LeaveApplicationDetail extends React.Component<ILeaveApplicationDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { leaveApplicationEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            LeaveApplication [<b>{leaveApplicationEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="reason">Reason</span>
            </dt>
            <dd>{leaveApplicationEntity.reason}</dd>
            <dt>
              <span id="fromDate">From Date</span>
            </dt>
            <dd>
              <TextFormat value={leaveApplicationEntity.fromDate} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="toDate">To Date</span>
            </dt>
            <dd>
              <TextFormat value={leaveApplicationEntity.toDate} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>Applied By</dt>
            <dd>{leaveApplicationEntity.appliedById ? leaveApplicationEntity.appliedById : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/leave-application" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/leave-application/${leaveApplicationEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ leaveApplication }: IRootState) => ({
  leaveApplicationEntity: leaveApplication.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(LeaveApplicationDetail);
