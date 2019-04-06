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
import { getEntity, updateEntity, createEntity, reset } from './event.reducer';
import { IEvent } from 'app/shared/model/event.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IEventUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IEventUpdateState {
  isNew: boolean;
  idscordinators: any[];
}

export class EventUpdate extends React.Component<IEventUpdateProps, IEventUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      idscordinators: [],
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
    values.eventDate = convertDateTimeToServer(values.eventDate);

    if (errors.length === 0) {
      const { eventEntity } = this.props;
      const entity = {
        ...eventEntity,
        ...values,
        cordinators: mapIdList(values.cordinators)
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/event');
  };

  render() {
    const { eventEntity, userDomains, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="smartAcademicSystemApp.event.home.createOrEditLabel">Create or edit a Event</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : eventEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="event-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="eventCategoryLabel">Event Category</Label>
                  <AvInput
                    id="event-eventCategory"
                    type="select"
                    className="form-control"
                    name="eventCategory"
                    value={(!isNew && eventEntity.eventCategory) || 'ARTS'}
                  >
                    <option value="ARTS">ARTS</option>
                    <option value="SPORTS">SPORTS</option>
                    <option value="OTHER">OTHER</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="eventNameLabel" for="eventName">
                    Event Name
                  </Label>
                  <AvField id="event-eventName" type="text" name="eventName" />
                </AvGroup>
                <AvGroup>
                  <Label id="eventDateLabel" for="eventDate">
                    Event Date
                  </Label>
                  <AvInput
                    id="event-eventDate"
                    type="datetime-local"
                    className="form-control"
                    name="eventDate"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.eventEntity.eventDate)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="eventVenueLabel" for="eventVenue">
                    Event Venue
                  </Label>
                  <AvField id="event-eventVenue" type="text" name="eventVenue" />
                </AvGroup>
                <AvGroup>
                  <Label id="activeLabel" check>
                    <AvInput id="event-active" type="checkbox" className="form-control" name="active" />
                    Active
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label for="userDomains">Cordinators</Label>
                  <AvInput
                    id="event-cordinators"
                    type="select"
                    multiple
                    className="form-control"
                    name="cordinators"
                    value={eventEntity.cordinators && eventEntity.cordinators.map(e => e.id)}
                  >
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
                <Button tag={Link} id="cancel-save" to="/entity/event" replace color="info">
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
  eventEntity: storeState.event.entity,
  loading: storeState.event.loading,
  updating: storeState.event.updating,
  updateSuccess: storeState.event.updateSuccess
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
)(EventUpdate);
