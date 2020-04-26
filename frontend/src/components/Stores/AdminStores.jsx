import React, { Component } from "react";
import { withRouter } from "react-router-dom";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { Alert, Button, InputGroup, FormControl, Col, Row } from "react-bootstrap";
import StoreCard from "./StoreCard";
import AddEditStoreModal from "./AddEditStoreModal";

class AdminStores extends Component {
  constructor(props) {
    super(props);
    this.state = {
        stores: [],
        showModal: false
    };
  }

  componentDidMount() {

  }

  handleToggle = () => {
    this.setState({
      showModal: !this.state.showModal
    });
  }

  render() {
    const { user } = this.props.auth;
    var stores;

    if (this.state.stores.length) {
      stores = this.state.stores.map(store => {
        return (
          <Col sm={3}>
            <StoreCard store={store} role={user.role} />
          </Col>
        )
      }
      );
    }
    else {
      stores = (
        <Alert variant="warning">
          There are no stores available. Please add a store.
        </Alert>
      );
    }

    return (
        <div style={{ height: "75vh" }} className="container valign-wrapper">
        <br />
        <Row>
            <Col sm={9}></Col>
          <Col>
            <Button variant="success" style={{ margin: "3%" }} onClick={this.handleToggle}>Add Store</Button>
          </Col>
        </Row>
        <AddEditStoreModal showModal={this.state.showModal} onHide={this.handleToggle} />
        <div>
          <Row>{stores}</Row>
        </div>
      </div>
   
    );
  }
}

AdminStores.propTypes = {
  auth: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
  auth: state.auth
});


export default connect(mapStateToProps, {})(withRouter(AdminStores));