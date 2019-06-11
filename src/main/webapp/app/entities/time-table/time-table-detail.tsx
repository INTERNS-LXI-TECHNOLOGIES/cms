import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './time-table.reducer';
import { ITimeTable } from 'app/shared/model/time-table.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITimeTableDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class TimeTableDetail extends React.Component<ITimeTableDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { timeTableEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            TimeTable [<b>{timeTableEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="semester">Semester</span>
            </dt>
            <dd>{timeTableEntity.semester}</dd>
            <dt>
              <span id="department">Department</span>
            </dt>
            <dd>{timeTableEntity.department}</dd>
          </dl>
          <Button tag={Link} to="/entity/time-table" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/time-table/${timeTableEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ timeTable }: IRootState) => ({
  timeTableEntity: timeTable.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(TimeTableDetail);
