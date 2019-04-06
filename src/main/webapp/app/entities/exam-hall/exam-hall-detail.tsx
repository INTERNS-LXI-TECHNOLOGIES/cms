import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './exam-hall.reducer';
import { IExamHall } from 'app/shared/model/exam-hall.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IExamHallDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ExamHallDetail extends React.Component<IExamHallDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { examHallEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            ExamHall [<b>{examHallEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="hallNumber">Hall Number</span>
            </dt>
            <dd>{examHallEntity.hallNumber}</dd>
            <dt>
              <span id="batch">Batch</span>
            </dt>
            <dd>{examHallEntity.batch}</dd>
            <dt>
              <span id="rollNumFrom">Roll Num From</span>
            </dt>
            <dd>{examHallEntity.rollNumFrom}</dd>
            <dt>
              <span id="rollNumTo">Roll Num To</span>
            </dt>
            <dd>{examHallEntity.rollNumTo}</dd>
            <dt>
              <span id="invigialtor">Invigialtor</span>
            </dt>
            <dd>{examHallEntity.invigialtor}</dd>
          </dl>
          <Button tag={Link} to="/entity/exam-hall" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/exam-hall/${examHallEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ examHall }: IRootState) => ({
  examHallEntity: examHall.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ExamHallDetail);
