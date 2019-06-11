import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ITimeTable } from 'app/shared/model/time-table.model';
import { getEntities as getTimeTables } from 'app/entities/time-table/time-table.reducer';
import { getEntity, updateEntity, createEntity, reset } from './day-of-week.reducer';
import { IDayOfWeek } from 'app/shared/model/day-of-week.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IDayOfWeekUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IDayOfWeekUpdateState {
  isNew: boolean;
  timeTableId: string;
}

export class DayOfWeekUpdate extends React.Component<IDayOfWeekUpdateProps, IDayOfWeekUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      timeTableId: '0',
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

    this.props.getTimeTables();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { dayOfWeekEntity } = this.props;
      const entity = {
        ...dayOfWeekEntity,
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
    this.props.history.push('/entity/day-of-week');
  };

  render() {
    const { dayOfWeekEntity, timeTables, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="smartAcademicSystemApp.dayOfWeek.home.createOrEditLabel">Create or edit a DayOfWeek</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : dayOfWeekEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="day-of-week-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="sub1Label" for="sub1">
                    Sub 1
                  </Label>
                  <AvField id="day-of-week-sub1" type="text" name="sub1" />
                </AvGroup>
                <AvGroup>
                  <Label id="sub2Label" for="sub2">
                    Sub 2
                  </Label>
                  <AvField id="day-of-week-sub2" type="text" name="sub2" />
                </AvGroup>
                <AvGroup>
                  <Label id="sub3Label" for="sub3">
                    Sub 3
                  </Label>
                  <AvField id="day-of-week-sub3" type="text" name="sub3" />
                </AvGroup>
                <AvGroup>
                  <Label id="sub4Label" for="sub4">
                    Sub 4
                  </Label>
                  <AvField id="day-of-week-sub4" type="text" name="sub4" />
                </AvGroup>
                <AvGroup>
                  <Label id="sub5Label" for="sub5">
                    Sub 5
                  </Label>
                  <AvField id="day-of-week-sub5" type="text" name="sub5" />
                </AvGroup>
                <AvGroup>
                  <Label id="sub6Label" for="sub6">
                    Sub 6
                  </Label>
                  <AvField id="day-of-week-sub6" type="text" name="sub6" />
                </AvGroup>
                <AvGroup>
                  <Label id="sub7Label" for="sub7">
                    Sub 7
                  </Label>
                  <AvField id="day-of-week-sub7" type="text" name="sub7" />
                </AvGroup>
                <AvGroup>
                  <Label for="timeTable.id">Time Table</Label>
                  <AvInput id="day-of-week-timeTable" type="select" className="form-control" name="timeTableId">
                    <option value="" key="0" />
                    {timeTables
                      ? timeTables.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/day-of-week" replace color="info">
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
  timeTables: storeState.timeTable.entities,
  dayOfWeekEntity: storeState.dayOfWeek.entity,
  loading: storeState.dayOfWeek.loading,
  updating: storeState.dayOfWeek.updating,
  updateSuccess: storeState.dayOfWeek.updateSuccess
});

const mapDispatchToProps = {
  getTimeTables,
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
)(DayOfWeekUpdate);
