import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './study-material.reducer';
import { IStudyMaterial } from 'app/shared/model/study-material.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IStudyMaterialDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class StudyMaterialDetail extends React.Component<IStudyMaterialDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { studyMaterialEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            StudyMaterial [<b>{studyMaterialEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="title">Title</span>
            </dt>
            <dd>{studyMaterialEntity.title}</dd>
            <dt>
              <span id="module">Module</span>
            </dt>
            <dd>{studyMaterialEntity.module}</dd>
            <dt>
              <span id="file">File</span>
            </dt>
            <dd>
              {studyMaterialEntity.file ? (
                <div>
                  <a onClick={openFile(studyMaterialEntity.fileContentType, studyMaterialEntity.file)}>Open&nbsp;</a>
                  <span>
                    {studyMaterialEntity.fileContentType}, {byteSize(studyMaterialEntity.file)}
                  </span>
                </div>
              ) : null}
            </dd>
            <dt>Subject</dt>
            <dd>{studyMaterialEntity.subjectId ? studyMaterialEntity.subjectId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/study-material" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/study-material/${studyMaterialEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ studyMaterial }: IRootState) => ({
  studyMaterialEntity: studyMaterial.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(StudyMaterialDetail);
