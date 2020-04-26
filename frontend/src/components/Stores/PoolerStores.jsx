import React, { Component } from "react";
import { withRouter } from "react-router-dom";
import PropTypes from "prop-types";
import { connect } from "react-redux";

class PoolerStores extends Component {
  constructor(props) {
    super(props);
    this.state = {};
  }

  componentDidMount() {

  }

  render() {
    return (
      <div>
        <b>Pooler Stores</b>
      </div>
    );
  }
}

PoolerStores.propTypes = {
  auth: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
  auth: state.auth
});


export default connect(mapStateToProps, {})(withRouter(PoolerStores));