import React, { Component } from "react";
import { withRouter } from "react-router-dom";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import AdminStores from "./AdminStores";
import PoolerStores from "./PoolerStores";

class Stores extends Component {
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
    var storesComponent;

    if(user.role === "admin"){
        storesComponent = <AdminStores />
      } else {
        storesComponent = <PoolerStores />
      }

    return (
      <div>
        {storesComponent}
      </div>
    );
  }
}

Stores.propTypes = {
  auth: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
  auth: state.auth
});


export default connect(mapStateToProps, {})(withRouter(Stores));