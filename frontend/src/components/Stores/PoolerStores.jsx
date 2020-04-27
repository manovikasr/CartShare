import React, { Component } from "react";
import { withRouter } from "react-router-dom";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import axios from "axios";
import { Alert, Button, Col, Row, InputGroup, FormControl } from "react-bootstrap";
import StoreCard from "./StoreCard";

class PoolerStores extends Component {
  constructor(props) {
    super(props);
    this.state = {
      stores: [],
      search_input: ""
    };
  }

  componentDidMount() {
    this.getStores();
  }

  componentWillUnmount(){
    this.setState({
      search_input: "",
      message: ""
    });
  };

  onChange = (e) => {
    this.setState({
      [e.target.name]: e.target.value
    });
  }

  getStores = () => {
    axios.get("store")
      .then(res => {
        if (res.data) {
          this.setState({
            stores: res.data.stores
          });
        }
      })
      .catch(e => {
        console.log(e);
      });
  };

  render() {
    const { user } = this.props.auth;
    var stores;

    if (this.state.stores.length) {
      const filteredStores = this.state.search_input.length ? this.state.stores.filter(
        store => store.store_name.toLowerCase().includes(this.state.search_input.toLowerCase())
        || store.city.toLowerCase().includes(this.state.search_input.toLowerCase())
        || store.zip.toString().includes(this.state.search_input.toLowerCase())
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
              <StoreCard store={store} />
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
                placeholder="Search Stores by name, city or zip.."
                aria-label="Search Stores"
                aria-describedby="basic-addon2"
                style={{ margin: "3%" }}
                name="search_input"
                onChange={this.onChange}
              />
            </InputGroup>
          </Col>
        </Row>
        <br/>
        <div>
          <Row>{stores}</Row>
        </div>
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