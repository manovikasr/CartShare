import React, { Component } from "react";
import { withRouter } from "react-router-dom";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import AdminHome from "./AdminHome";
import PoolerHome from "./PoolerHome";
import homeImage from "../../images/CartShare3.png"

class Home extends Component {
  constructor(props) {
    super(props);
    this.state = {};
  }

  componentDidMount() {
    if (!this.props.auth.isAuthenticated) {
      this.props.history.push("/");
    }
    else {
      if (!this.props.auth.user.email_verified) {
        this.props.history.push("/verify");
      }
    }
  }

  render() {
    const { user } = this.props.auth;
    var homeComponent;

    if (user.role === "admin") {
      homeComponent = <AdminHome />
    } else {
      homeComponent = <PoolerHome />
    }

    return (
      <div className="container" style={{ width: "75%" }}>
        <h2 className="p-4">Welcome to CartShare!</h2>
        <img
          className="d-block w-100"
          src={homeImage}
          alt="Cart Share"
        />
      </div>
    );
  }
}

Home.propTypes = {
  auth: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
  auth: state.auth
});


export default connect(mapStateToProps, {})(withRouter(Home));