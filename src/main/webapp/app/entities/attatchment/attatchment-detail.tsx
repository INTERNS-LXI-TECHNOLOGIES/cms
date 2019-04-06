import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './attatchment.reducer';
import { IAttatchment } from 'app/shared/model/attatchment.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IAttatchmentDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class AttatchmentDetail extends React.Component<IAttatchmentDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { attatchmentEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Attatchment [<b>{attatchmentEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="file">File</span>
            </dt>
            <dd>
              {attatchmentEntity.file ? (
                <div>
                  <a onClick={openFile(attatchmentEntity.fileContentType, attatchmentEntity.file)}>Open&nbsp;</a>
                  <span>
                    {attatchmentEntity.fileContentType}, {byteSize(attatchmentEntity.file)}
                  </span>
                </div>
              ) : null}
            </dd>
            <dt>Leave Application</dt>
            <dd>{attatchmentEntity.leaveApplicationId ? attatchmentEntity.leaveApplicationId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/attatchment" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/attatchment/${attatchmentEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ attatchment }: IRootState) => ({
  attatchmentEntity: attatchment.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(AttatchmentDetail);
