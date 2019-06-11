import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './day-of-week.reducer';
import { IDayOfWeek } from 'app/shared/model/day-of-week.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IDayOfWeekDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class DayOfWeekDetail extends React.Component<IDayOfWeekDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { dayOfWeekEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            DayOfWeek [<b>{dayOfWeekEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="sub1">Sub 1</span>
            </dt>
            <dd>{dayOfWeekEntity.sub1}</dd>
            <dt>
              <span id="sub2">Sub 2</span>
            </dt>
            <dd>{dayOfWeekEntity.sub2}</dd>
            <dt>
              <span id="sub3">Sub 3</span>
            </dt>
            <dd>{dayOfWeekEntity.sub3}</dd>
            <dt>
              <span id="sub4">Sub 4</span>
            </dt>
            <dd>{dayOfWeekEntity.sub4}</dd>
            <dt>
              <span id="sub5">Sub 5</span>
            </dt>
            <dd>{dayOfWeekEntity.sub5}</dd>
            <dt>
              <span id="sub6">Sub 6</span>
            </dt>
            <dd>{dayOfWeekEntity.sub6}</dd>
            <dt>
              <span id="sub7">Sub 7</span>
            </dt>
            <dd>{dayOfWeekEntity.sub7}</dd>
            <dt>Time Table</dt>
            <dd>{dayOfWeekEntity.timeTableId ? dayOfWeekEntity.timeTableId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/day-of-week" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/day-of-week/${dayOfWeekEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ dayOfWeek }: IRootState) => ({
  dayOfWeekEntity: dayOfWeek.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(DayOfWeekDetail);
