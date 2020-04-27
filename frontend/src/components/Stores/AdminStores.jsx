import React, { Component } from "react";
import { withRouter } from "react-router-dom";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import axios from "axios";
import { Alert, Button, Col, Row, InputGroup, FormControl } from "react-bootstrap";
import StoreCard from "./StoreCard";
import AddEditStoreModal from "./AddEditStoreModal";

class AdminStores extends Component {
  constructor(props) {
    super(props);
    this.state = {
      stores: [],
      showModal: false,
      search_input: "",
      message: "",
      error_message: ""
    };
  };

  componentDidMount() {
    this.getStores();
  };

  componentWillUnmount(){
    this.setState({
      search_input: "",
      showModal: false,
      message: "",
      error_message: ""
    });
  };

  onChange = (e) => {
    this.setState({
      [e.target.name]: e.target.value
    });
  }

  handleToggle = () => {
    this.setState({
      showModal: !this.state.showModal
    });
  };

  getStores = () => {
    axios.get("store")
      .then(res => {
        if (res.data.length) {
          this.setState({
            stores: res.data
          });
        }
      })
      .catch(e => {
        console.log(e);
      });
  };

  deleteStore = (store_id) => {
    axios.post(`store/delete/${store_id}`)
      .then(res => {
        if (res.status === 200) {
          this.setState({
            message: res.data.message
          })
          this.getStores();
        }
      })
      .catch(e => {
        if (e.response && e.response.data) {
          this.setState({
            error_message: e.response.data.message
          });
        }
      });
  }

  render() {
    const { user } = this.props.auth;
    var alertMessage, stores;
    if (this.state.error_message) {
      alertMessage = (
        <Alert variant="warning">{this.state.error_message}</Alert>
      );
    }
    if (this.state.message) {
      alertMessage = (
        <Alert variant="success">{this.state.message}</Alert>
      );
    }

    if (this.state.stores.length) {
      const filteredStores = this.state.search_input.length ? this.state.stores.filter(
        store => store.store_name.toLowerCase().includes(this.state.search_input.toLowerCase())
      ) : this.state.stores;
      if (filteredStores.length === 0 && this.state.search_input.length) {
        stores = (
          <Alert variant="warning">
            There are no stores matching your search.
          </Alert>
        );
      } else {
        stores = filteredStores.map(store => {
          return (
            <Col sm={3}>
              <StoreCard store={store} deleteStore={this.deleteStore} getStores={this.getStores} />
            </Col>
          )
        });
      }
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
          <Col sm={9}>
            <InputGroup style={{ width: '50%' }} size="lg">
              <FormControl
                placeholder="Search Stores.."
                aria-label="Search Stores"
                aria-describedby="basic-addon2"
                style={{ margin: "3%" }}
                name="search_input"
                onChange={this.onChange}
              />
            </InputGroup>
          </Col>
          <Col>
            <Button variant="success" style={{ margin: "3%" }} onClick={this.handleToggle}>Add Store</Button>
          </Col>
        </Row>
        <br/>
        <AddEditStoreModal showModal={this.state.showModal} onHide={this.handleToggle} getStores={this.getStores} />
        {alertMessage}
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