import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IUserDomain } from 'app/shared/model/user-domain.model';
import { getEntities as getUserDomains } from 'app/entities/user-domain/user-domain.reducer';
import { getEntity, updateEntity, createEntity, reset } from './qualification.reducer';
import { IQualification } from 'app/shared/model/qualification.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IQualificationUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IQualificationUpdateState {
  isNew: boolean;
  userDomainId: string;
}

export class QualificationUpdate extends React.Component<IQualificationUpdateProps, IQualificationUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      userDomainId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getUserDomains();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { qualificationEntity } = this.props;
      const entity = {
        ...qualificationEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/qualification');
  };

  render() {
    const { qualificationEntity, userDomains, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="smartInformationSystemApp.qualification.home.createOrEditLabel">Create or edit a Qualification</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : qualificationEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="qualification-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="gradeLabel" for="grade">
                    Grade
                  </Label>
                  <AvField id="qualification-grade" type="text" name="grade" />
                </AvGroup>
                <AvGroup>
                  <Label id="yearLabel" for="year">
                    Year
                  </Label>
                  <AvField id="qualification-year" type="string" className="form-control" name="year" />
                </AvGroup>
                <AvGroup>
                  <Label id="universityLabel" for="university">
                    University
                  </Label>
                  <AvField id="qualification-university" type="text" name="university" />
                </AvGroup>
                <AvGroup>
                  <Label id="marksLabel" for="marks">
                    Marks
                  </Label>
                  <AvField id="qualification-marks" type="string" className="form-control" name="marks" />
                </AvGroup>
                <AvGroup>
                  <Label id="percentageLabel" for="percentage">
                    Percentage
                  </Label>
                  <AvField id="qualification-percentage" type="string" className="form-control" name="percentage" />
                </AvGroup>
                <AvGroup>
                  <Label for="userDomain.id">User Domain</Label>
                  <AvInput id="qualification-userDomain" type="select" className="form-control" name="userDomainId">
                    <option value="" key="0" />
                    {userDomains
                      ? userDomains.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/qualification" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">Back</span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />
                  &nbsp; Save
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  userDomains: storeState.userDomain.entities,
  qualificationEntity: storeState.qualification.entity,
  loading: storeState.qualification.loading,
  updating: storeState.qualification.updating,
  updateSuccess: storeState.qualification.updateSuccess
});

const mapDispatchToProps = {
  getUserDomains,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(QualificationUpdate);
