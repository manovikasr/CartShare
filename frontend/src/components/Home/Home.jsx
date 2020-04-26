import React, { Component } from "react";
import { withRouter } from "react-router-dom";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import AdminHome from "./AdminHome";
import PoolerHome from "./PoolerHome";

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
    const {user} = this.props.auth;
    var homeComponent;

    if(user.role === "admin"){
      homeComponent = <AdminHome />
    } else {
      homeComponent = <PoolerHome />
    }

    return (
      <div>
        {homeComponent}
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