import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, setFileData, openFile, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ILeaveApplication } from 'app/shared/model/leave-application.model';
import { getEntities as getLeaveApplications } from 'app/entities/leave-application/leave-application.reducer';
import { getEntity, updateEntity, createEntity, setBlob, reset } from './attatchment.reducer';
import { IAttatchment } from 'app/shared/model/attatchment.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IAttatchmentUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IAttatchmentUpdateState {
  isNew: boolean;
  leaveApplicationId: string;
}

export class AttatchmentUpdate extends React.Component<IAttatchmentUpdateProps, IAttatchmentUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      leaveApplicationId: '0',
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

    this.props.getLeaveApplications();
  }

  onBlobChange = (isAnImage, name) => event => {
    setFileData(event, (contentType, data) => this.props.setBlob(name, data, contentType), isAnImage);
  };

  clearBlob = name => () => {
    this.props.setBlob(name, undefined, undefined);
  };

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { attatchmentEntity } = this.props;
      const entity = {
        ...attatchmentEntity,
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
    this.props.history.push('/entity/attatchment');
  };

  render() {
    const { attatchmentEntity, leaveApplications, loading, updating } = this.props;
    const { isNew } = this.state;

    const { file, fileContentType } = attatchmentEntity;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="smartInformationSystemApp.attatchment.home.createOrEditLabel">Create or edit a Attatchment</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : attatchmentEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="attatchment-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <AvGroup>
                    <Label id="fileLabel" for="file">
                      File
                    </Label>
                    <br />
                    {file ? (
                      <div>
                        <a onClick={openFile(fileContentType, file)}>Open</a>
                        <br />
                        <Row>
                          <Col md="11">
                            <span>
                              {fileContentType}, {byteSize(file)}
                            </span>
                          </Col>
                          <Col md="1">
                            <Button color="danger" onClick={this.clearBlob('file')}>
                              <FontAwesomeIcon icon="times-circle" />
                            </Button>
                          </Col>
                        </Row>
                      </div>
                    ) : null}
                    <input id="file_file" type="file" onChange={this.onBlobChange(false, 'file')} />
                    <AvInput type="hidden" name="file" value={file} />
                  </AvGroup>
                </AvGroup>
                <AvGroup>
                  <Label for="leaveApplication.id">Leave Application</Label>
                  <AvInput id="attatchment-leaveApplication" type="select" className="form-control" name="leaveApplicationId">
                    <option value="" key="0" />
                    {leaveApplications
                      ? leaveApplications.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/attatchment" replace color="info">
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
  leaveApplications: storeState.leaveApplication.entities,
  attatchmentEntity: storeState.attatchment.entity,
  loading: storeState.attatchment.loading,
  updating: storeState.attatchment.updating,
  updateSuccess: storeState.attatchment.updateSuccess
});

const mapDispatchToProps = {
  getLeaveApplications,
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(AttatchmentUpdate);
