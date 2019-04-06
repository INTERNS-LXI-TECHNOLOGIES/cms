import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, setFileData, openFile, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ISubject } from 'app/shared/model/subject.model';
import { getEntities as getSubjects } from 'app/entities/subject/subject.reducer';
import { getEntity, updateEntity, createEntity, setBlob, reset } from './study-material.reducer';
import { IStudyMaterial } from 'app/shared/model/study-material.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IStudyMaterialUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IStudyMaterialUpdateState {
  isNew: boolean;
  subjectId: string;
}

export class StudyMaterialUpdate extends React.Component<IStudyMaterialUpdateProps, IStudyMaterialUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      subjectId: '0',
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

    this.props.getSubjects();
  }

  onBlobChange = (isAnImage, name) => event => {
    setFileData(event, (contentType, data) => this.props.setBlob(name, data, contentType), isAnImage);
  };

  clearBlob = name => () => {
    this.props.setBlob(name, undefined, undefined);
  };

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { studyMaterialEntity } = this.props;
      const entity = {
        ...studyMaterialEntity,
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
    this.props.history.push('/entity/study-material');
  };

  render() {
    const { studyMaterialEntity, subjects, loading, updating } = this.props;
    const { isNew } = this.state;

    const { file, fileContentType } = studyMaterialEntity;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="smartAcademicSystemApp.studyMaterial.home.createOrEditLabel">Create or edit a StudyMaterial</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : studyMaterialEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="study-material-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="titleLabel" for="title">
                    Title
                  </Label>
                  <AvField id="study-material-title" type="text" name="title" />
                </AvGroup>
                <AvGroup>
                  <Label id="moduleLabel" for="module">
                    Module
                  </Label>
                  <AvField id="study-material-module" type="string" className="form-control" name="module" />
                </AvGroup>
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
                  <Label for="subject.id">Subject</Label>
                  <AvInput id="study-material-subject" type="select" className="form-control" name="subjectId">
                    <option value="" key="0" />
                    {subjects
                      ? subjects.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/study-material" replace color="info">
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
  subjects: storeState.subject.entities,
  studyMaterialEntity: storeState.studyMaterial.entity,
  loading: storeState.studyMaterial.loading,
  updating: storeState.studyMaterial.updating,
  updateSuccess: storeState.studyMaterial.updateSuccess
});

const mapDispatchToProps = {
  getSubjects,
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
)(StudyMaterialUpdate);
