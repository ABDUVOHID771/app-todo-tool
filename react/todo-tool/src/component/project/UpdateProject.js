import React, { Component } from "react";
import { getProject, putProject } from "../../action/ProjectAction";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import classnames from "classnames";
import { Link } from "react-router-dom";

class UpdateProject extends Component {
  constructor() {
    super();
    this.state = {
      uuid: "",
      projectName: "",
      projectIdentifier: "",
      description: "",
      startedDate: "",
      endedDate: "",
      errors: {},
    };
  }

  componentWillReceiveProps(nextProps) {
    if (nextProps.errors) {
      this.setState({ errors: nextProps.errors });
    }
    const {
      uuid,
      projectName,
      projectIdentifier,
      description,
      startedDate,
      endedDate,
    } = nextProps.project;
    this.setState({
      uuid,
      projectName,
      projectIdentifier,
      description,
      startedDate,
      endedDate,
    });

    this.onChange = this.onChange.bind(this);
    this.onSubmit = this.onSubmit.bind(this);
  }

  componentDidMount() {
    const { id } = this.props.match.params;
    this.props.getProject(id, this.props.history);
  }

  onChange(e) {
    this.setState({ [e.target.name]: e.target.value });
  }

  onSubmit(e) {
    e.preventDefault();

    const updateProject = {
      uuid: this.state.uuid,
      projectName: this.state.projectName,
      projectIdentifier: this.state.projectIdentifier,
      description: this.state.description,
      startedDate: this.state.startedDate,
      endedDate: this.state.endedDate,
    };

    this.props.putProject(this.state.uuid, updateProject, this.props.history);
  }

  render() {
    const { errors } = this.state;
    return (
      // Start of Project FORM //
      <div className="project">
        <div className="container">
          <div className="row">
            <div className="col-md-8 m-auto">
              <h5 className="display-4 text-center">Edit Project</h5>
              <hr />
              <form onSubmit={this.onSubmit}>
                <div className="form-group">
                  <input
                    type="text"
                    className={classnames("form-control form-control-lg ", {
                      "is-invalid": errors.projectName,
                    })}
                    placeholder="Project Name"
                    name="projectName"
                    value={this.state.projectName}
                    onChange={this.onChange}
                  />
                  {errors.projectName && (
                    <div className="invalid-feedback">{errors.projectName}</div>
                  )}
                </div>
                <div className="form-group">
                  <input
                    type="text"
                    className="form-control form-control-lg"
                    placeholder="Unique Project ID"
                    value={this.state.projectIdentifier}
                    disabled
                  />
                </div>
                <div className="form-group">
                  <textarea
                    className={classnames("form-control form-control-lg", {
                      "is-invalid": errors.description,
                    })}
                    placeholder="Project Description"
                    name="description"
                    value={this.state.description}
                    onChange={this.onChange}
                  ></textarea>
                  {errors.description && (
                    <div className="invalid-feedback">{errors.description}</div>
                  )}
                </div>
                <h6>Start Date</h6>
                <div className="form-group">
                  <input
                    type="date"
                    className={classnames("form-control form-control-lg", {
                      "is-invalid": errors.startedDate,
                    })}
                    name="startedDate"
                    value={this.state.startedDate}
                    onChange={this.onChange}
                  />
                  {errors.startedDate && (
                    <div className="invalid-feedback">{errors.startedDate}</div>
                  )}
                </div>
                <h6>Estimated End Date</h6>
                <div className="form-group">
                  <input
                    type="date"
                    className={classnames("form-control form-control-lg", {
                      "is-invalid": errors.endedDate,
                    })}
                    name="endedDate"
                    value={this.state.endedDate}
                    onChange={this.onChange}
                  />
                  {errors.endedDate && (
                    <div className="invalid-feedback">{errors.endedDate}</div>
                  )}
                </div>

                <Link to={`/dashboard`} className="btn btn-secondary">
                  Back to Dashboard
                </Link>
                <input type="submit" className="btn btn-primary float-right" />
              </form>
            </div>
          </div>
        </div>
      </div>
      // END OF PROJECT FORM //
    );
  }
}

UpdateProject.propTypes = {
  getProject: PropTypes.func.isRequired,
  putProject: PropTypes.func.isRequired,
  project: PropTypes.object.isRequired,
  errors: PropTypes.object.isRequired,
};

const mapStateToProps = (state) => ({
  project: state.project.project,
  errors: state.errors,
});

export default connect(mapStateToProps, { getProject, putProject })(
  UpdateProject
);
