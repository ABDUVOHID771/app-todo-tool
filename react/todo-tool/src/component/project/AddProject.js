import React, { Component } from "react";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { createProject } from "../../action/ProjectAction";
import classnames from "classnames";
import { Link } from "react-router-dom";

class AddProject extends Component {
  constructor() {
    super();
    this.state = {
      projectName: "",
      description: "",
      projectIdentifier: "",
      startedDate: "",
      endedDate: "",
      errors: {},
    };
    this.onChange = this.onChange.bind(this);
    this.onSubmit = this.onSubmit.bind(this);
  }
  onChange(e) {
    this.setState({ [e.target.name]: e.target.value });
  }

  onSubmit(e) {
    e.preventDefault();
    const newState = {
      projectName: this.state.projectName,
      description: this.state.description,
      projectIdentifier: this.state.projectIdentifier,
      startedDate: this.state.startedDate,
      endedDate: this.state.endedDate,
    };
    this.props.createProject(newState, this.props.history);
  }

  componentWillReceiveProps(nextProps) {
    if (nextProps.errors) {
      this.setState({ errors: nextProps.errors });
    }
  }

  render() {
    const { errors } = this.state;
    return (
      <div className="project">
        <div className="container">
          <div className="row">
            <div className="col-md-8 m-auto">
              <h5 className="display-4 text-center">Create Project form</h5>
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
                    className={classnames("form-control form-control-lg", {
                      "is-invalid": errors.projectIdentifier,
                    })}
                    placeholder="Unique Project ID"
                    value={this.state.projectIdentifier}
                    name="projectIdentifier"
                    onChange={this.onChange}
                  />
                  {errors.projectIdentifier && (
                    <div className="invalid-feedback">
                      {errors.projectIdentifier}
                    </div>
                  )}
                </div>
                <div className="form-group">
                  <textarea
                    className={classnames("form-control form-control-lg ", {
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
    );
  }
}

const mapStateToProps = (state) => ({
  errors: state.errors,
});

AddProject.propTypes = {
  createProject: PropTypes.func.isRequired,
  errors: PropTypes.object.isRequired,
};

export default connect(mapStateToProps, { createProject })(AddProject);
