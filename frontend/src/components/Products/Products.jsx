import React, { Component } from "react";
import { withRouter } from "react-router-dom";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import axios from "axios";
import { Alert, Card, Row, Col, InputGroup, FormControl, Button } from "react-bootstrap";
import ProductCard from "./ProductCard";
import storeImage from "../../images/StoreImage.png";
import AddEditProductModal from "./AddEditProductModal";

class Products extends Component {
    constructor(props) {
        super(props);
        this.state = {
            products: [],
            search_input: ""
        };
    }

    async componentDidMount() {
        this.getAllProducts();
    }

    getAllProducts = () => {
        axios.get(`/products`)
            .then(res => {
                if (res.data) {
                    this.setState({
                        products: res.data.products
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
            this.getAllProducts();
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
        var alertMessage;
        if (this.state.error_message) {
            alertMessage = (
              <Alert variant="warning">{this.state.error_message}</Alert>
            );
          }

        if (this.state.products.length) {
            const filteredProducts = this.state.search_input.length ? this.state.products.filter(
                product => product.product_name.toLowerCase().includes(this.state.search_input.toLowerCase()) 
                || product.sku.toLowerCase().includes(this.state.search_input.toLowerCase())
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
                            <ProductCard product={product} deleteProduct={this.deleteProduct} getProducts={this.getAllProducts} />
                        </Col>
                    )
                });
            }
        } else {
            products = (
                <Alert variant="warning">
                    There are no products in any store.
                </Alert>
            )
        }
        return (
            <div style={{ height: "75vh" }} className="container valign-wrapper">
                <br/>
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
                <AddEditProductModal showModal={this.state.showModal} onHide={this.handleToggle} getProducts={this.getProducts} />
            </div>
        );
    }
}

Products.propTypes = {
    auth: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
    auth: state.auth
});


export default connect(mapStateToProps, {})(withRouter(Products));