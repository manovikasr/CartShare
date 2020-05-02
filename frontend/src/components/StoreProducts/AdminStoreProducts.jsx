import React, { Component } from "react";
import { withRouter } from "react-router-dom";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import axios from "axios";
import { Alert, Card, Row, Col, InputGroup, FormControl, Button } from "react-bootstrap";
import ProductCard from "../Products/ProductCard";
import storeImage from "../../images/StoreImage.png";
import AddEditProductModal from "../Products/AddEditProductModal";

class AdminStoreProducts extends Component {
    constructor(props) {
        super(props);
        this.state = {
            store: null,
            products: [],
            search_input: ""
        };
    }

    async componentDidMount() {
        if (this.props.location.state) {
            await this.setState({
                store: this.props.location.state.store,
                products: this.props.location.state.store.store_products
            });
        } else {
            this.props.history.push("/stores");
        }
    }

    getProducts = () => {
        const store_id = this.state.store.id;
        axios.get(`${store_id}`)
            .then(res => {
                if (res.data) {
                    this.setState({
                        products: res.data.store.store_products
                    });
                }
            })
            .catch(e => {
                if (e.response) {
                    console.log(e.response.data);
                }
            });
    };

    handleToggle = (e) => {
        this.setState({
            showModal: !this.state.showModal
        });
    }

    onChange = (e) => {
        this.setState({
            [e.target.name]: e.target.value
        });
    }

    deleteProduct = product_id => {
        axios.post(`product/delete/${product_id}`)
        .then(res => {
          if (res.status === 200) {
            this.getProducts();
          }
        })
        .catch(e => {
          if (e.response && e.response.data) {
            this.setState({
              error_message: e.response.data.message
            });
          }
        });
    };

    render() {
        var products;
        var store_name, address, address2, alertMessage;
        if (this.state.error_message) {
            alertMessage = (
              <Alert variant="warning">{this.state.error_message}</Alert>
            );
          }
        if (this.state.store) {
            store_name = this.state.store.store_name;
            address = this.state.store.address;
            address2 = this.state.store.city + ", " + this.state.store.state + " - " + this.state.store.zip;
        }
        if (this.state.products.length) {
            const filteredProducts = this.state.search_input.length ? this.state.products.filter(
                product => product.product_name.toLowerCase().includes(this.state.search_input.toLowerCase()) || product.sku.toLowerCase().includes(this.state.search_input.toLowerCase())
            ) : this.state.products;
            if (filteredProducts.length === 0 && this.state.search_input.length) {
                products = (
                    <Alert variant="warning">
                        There are no products matching your search.
                    </Alert>
                );
            } else {
                products = filteredProducts.map(product => {
                    return (
                        <Col sm={3}>
                            <ProductCard store={this.state.store} product={product} deleteProduct={this.deleteProduct} getProducts={this.getProducts} />
                        </Col>
                    )
                });
            }
        } else {
            products = (
                <Alert variant="warning">
                    There are no products in this store.
                </Alert>
            )
        }
        return (
            <div style={{ height: "75vh" }} className="container valign-wrapper">
                <Card bg="info" text="white" style={{ width: "70rem", height: "15rem", margin: "2%" }}>
                    <Row>
                        <Col>
                            <Card.Img style={{ width: "18rem", height: "15rem" }} src={storeImage} />
                        </Col>
                        <Card.Body>
                            <Card.Title><h1>{store_name}</h1></Card.Title>
                            <br />
                            <Card.Text>
                                <h4>
                                    {address}<br />
                                    {address2}
                                </h4>
                            </Card.Text>
                        </Card.Body>
                    </Row>
                </Card>
                <Row>
                    <Col sm={9}>
                        <InputGroup style={{ width: '50%' }} size="lg">
                            <FormControl
                                placeholder="Search Products.."
                                aria-label="Search Products"
                                aria-describedby="basic-addon2"
                                style={{ margin: "3%" }}
                                name="search_input"
                                onChange={this.onChange}
                            />
                        </InputGroup>
                    </Col>
                    <Col>
                        <Button variant="success" style={{ margin: "3%" }} onClick={this.handleToggle}>Add Product</Button>
                    </Col>
                </Row>
                {alertMessage}<br/>
                {products}
                <AddEditProductModal store={this.state.store} showModal={this.state.showModal} onHide={this.handleToggle} getProducts={this.getProducts} />
            </div>
        );
    }
}

AdminStoreProducts.propTypes = {
    auth: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
    auth: state.auth
});


export default connect(mapStateToProps, {})(withRouter(AdminStoreProducts));