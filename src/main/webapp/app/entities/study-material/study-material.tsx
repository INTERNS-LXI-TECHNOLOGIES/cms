import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import {
  openFile,
  byteSize,
  ICrudGetAllAction,
  getSortState,
  IPaginationBaseState,
  getPaginationItemsNumber,
  JhiPagination
} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './study-material.reducer';
import { IStudyMaterial } from 'app/shared/model/study-material.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface IStudyMaterialProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export type IStudyMaterialState = IPaginationBaseState;

export class StudyMaterial extends React.Component<IStudyMaterialProps, IStudyMaterialState> {
  state: IStudyMaterialState = {
    ...getSortState(this.props.location, ITEMS_PER_PAGE)
  };

  componentDidMount() {
    this.getEntities();
  }

  sort = prop => () => {
    this.setState(
      {
        order: this.state.order === 'asc' ? 'desc' : 'asc',
        sort: prop
      },
      () => this.sortEntities()
    );
  };

  sortEntities() {
    this.getEntities();
    this.props.history.push(`${this.props.location.pathname}?page=${this.state.activePage}&sort=${this.state.sort},${this.state.order}`);
  }

  handlePagination = activePage => this.setState({ activePage }, () => this.sortEntities());

  getEntities = () => {
    const { activePage, itemsPerPage, sort, order } = this.state;
    this.props.getEntities(activePage - 1, itemsPerPage, `${sort},${order}`);
  };

  render() {
    const { studyMaterialList, match, totalItems } = this.props;
    return (
      <div>
        <h2 id="study-material-heading">
          Study Materials
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Study Material
          </Link>
        </h2>
        <div className="table-responsive">
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={this.sort('id')}>
                  ID <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('title')}>
                  Title <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('module')}>
                  Module <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('file')}>
                  File <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Subject <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {studyMaterialList.map((studyMaterial, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${studyMaterial.id}`} color="link" size="sm">
                      {studyMaterial.id}
                    </Button>
                  </td>
                  <td>{studyMaterial.title}</td>
                  <td>{studyMaterial.module}</td>
                  <td>
                    {studyMaterial.file ? (
                      <div>
                        <a onClick={openFile(studyMaterial.fileContentType, studyMaterial.file)}>Open &nbsp;</a>
                        <span>
                          {studyMaterial.fileContentType}, {byteSize(studyMaterial.file)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>{studyMaterial.subjectId ? <Link to={`subject/${studyMaterial.subjectId}`}>{studyMaterial.subjectId}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${studyMaterial.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${studyMaterial.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${studyMaterial.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        </div>
        <Row className="justify-content-center">
          <JhiPagination
            items={getPaginationItemsNumber(totalItems, this.state.itemsPerPage)}
            activePage={this.state.activePage}
            onSelect={this.handlePagination}
            maxButtons={5}
          />
        </Row>
      </div>
    );
  }
}

const mapStateToProps = ({ studyMaterial }: IRootState) => ({
  studyMaterialList: studyMaterial.entities,
  totalItems: studyMaterial.totalItems
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(StudyMaterial);
