import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './qualification.reducer';
import { IQualification } from 'app/shared/model/qualification.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IQualificationDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class QualificationDetail extends React.Component<IQualificationDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { qualificationEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Qualification [<b>{qualificationEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="grade">Grade</span>
            </dt>
            <dd>{qualificationEntity.grade}</dd>
            <dt>
              <span id="year">Year</span>
            </dt>
            <dd>{qualificationEntity.year}</dd>
            <dt>
              <span id="university">University</span>
            </dt>
            <dd>{qualificationEntity.university}</dd>
            <dt>
              <span id="marks">Marks</span>
            </dt>
            <dd>{qualificationEntity.marks}</dd>
            <dt>
              <span id="percentage">Percentage</span>
            </dt>
            <dd>{qualificationEntity.percentage}</dd>
            <dt>User Domain</dt>
            <dd>{qualificationEntity.userDomainId ? qualificationEntity.userDomainId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/qualification" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/qualification/${qualificationEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ qualification }: IRootState) => ({
  qualificationEntity: qualification.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(QualificationDetail);
