import React, { Component } from "react";
import { Link } from "react-router-dom";
import Backlog from "./Backlog";
import { connect } from "react-redux";
import PropTypes from "prop-types";
import { getBacklog } from "../../action/BacklogAction";

class ProjectBoard extends Component {
  constructor() {
    super();
    this.state = { errors: {} };
  }

  componentDidMount() {
    const { id } = this.props.match.params;
    this.props.getBacklog(id);
  }

  componentWillReceiveProps(nextProps) {
    if (nextProps.errors) {
      this.setState({ errors: nextProps.errors });
    }
  }

  render() {
    const { id } = this.props.match.params;
    const { project_tasks } = this.props.backlog;
    const { errors } = this.state;
    if (project_tasks.length < 1) {
      if (errors.projectIdentifier) {
        return (
          <div className="alert alert-danger text-center" role="alert">
            {errors.projectIdentifier}
          </div>
        );
      } else {
        return (
          <div className="container">
            <Link
              to={`/add-project-task/${id}`}
              className="btn btn-primary mb-3"
            >
              <i className="fas fa-plus-circle"> Create Project Task</i>
            </Link>
            <br />
            <hr />
            <div className="alert alert-info text-center" role="aler">
              No project tasks on this board
            </div>
          </div>
        );
      }
    }
    return (
      <div className="container">
        <Link to={`/add-project-task/${id}`} className="btn btn-primary mb-3">
          <i className="fas fa-plus-circle"> Create Project Task</i>
        </Link>
        <br />
        <hr />
        <Backlog tasks={project_tasks} />
      </div>
    );
  }
}

ProjectBoard.propTypes = {
  backlog: PropTypes.object.isRequired,
  getBacklog: PropTypes.func.isRequired,
  errors: PropTypes.object.isRequired,
};

const mapStateToProps = (state) => ({
  backlog: state.backlog,
  errors: state.errors,
});

export default connect(mapStateToProps, { getBacklog })(ProjectBoard);
