import React, { Component } from "react";
import { withRouter } from "react-router-dom";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { Button, Modal, Form, Col, Alert } from "react-bootstrap";
import Select from "react-select";
import axios from "axios";

class AddEditProductModal extends Component {
    constructor(props) {
        super(props);
        this.state = {
            product_name: "",
            product_brand: "",
            sku: "",
            price: "",
            unit_type: "",
            product_desc: "",
            stores: [],
            selected_stores: []
        };
    }

    componentDidMount() {
        if (this.props.addToMultiStore) {
            var stores_data = [];
            axios.get("store")
                .then(res => {
                    if (res.data) {
                        res.data.stores.forEach(store => {
                            let option = {
                                label: store.store_name,
                                value: store.id
                            }
                            stores_data.push(option);
                        })
                        this.setState({
                            stores: stores_data
                        });
                    }
                })
                .catch(e => {
                    console.log(e);
                });
        }
        if (this.props.product && this.props.product.product_brand) {
            this.setState({
                product_brand: this.props.product.product_brand
            });
        }
    }

    onChange = (e) => {
        this.setState({
            [e.target.name]: e.target.value
        });
    }

    onStoresSelect = options => {
        this.setState({
            selected_stores: options
        });
    };

    onHide = (e) => {
        this.setState({
            error_message: ""
        });
        this.props.onHide();
    }

    addProduct = (e) => {
        e.preventDefault();
        var store_ids = "";
        if (this.props.store) {
            store_ids = this.props.store.id
        } else {
            if (this.state.selected_stores.length) {
                this.state.selected_stores.forEach(option => {
                    store_ids += option.value + ",";
                });
                store_ids = store_ids.substring(0, store_ids.length - 1);
            }
            else {
                this.setState({
                    error_message: "Please select a store."
                });
                return;
            }
        }

        const productData = {
            product_name: this.state.product_name,
            product_brand: this.state.product_brand,
            sku: this.state.sku,
            product_desc: this.state.product_desc,
            unit_type: this.state.unit_type,
            price: this.state.price
        };

        axios.post(`/product?store_ids=${store_ids}`, productData)
            .then(res => {
                if (res.status === 200) {
                    this.onHide();
                    this.props.getProducts();
                }
            })
            .catch(e => {
                console.log(e.response);
                if (e.response && e.response.data)
                    this.setState({
                        error_message: e.response.data.message
                    });
            })
    }

    updateProduct = (e) => {
        e.preventDefault();
        var id, product_name, product_brand, sku, store_id, product_desc, product_img, unit_type, price;

        if (this.props.product) {
            id = this.props.product.id;
            store_id = this.props.product.store_id;
            product_name = this.state.product_name || this.props.product.product_name;
            product_brand = this.state.product_brand;
            sku = this.state.sku || this.props.product.sku;
            product_desc = this.state.product_desc || this.props.product.product_desc;
            unit_type = this.state.unit_type || this.props.product.unit_type;
            price = this.state.price || this.props.product.price;
            product_img = this.props.product.product_img;
        }
        const productData = {
            id, product_name, store_id, product_brand, product_desc, sku, unit_type, price, product_img
        };

        axios.put(`/product/${id}`, productData)
            .then(res => {
                if (res.status === 200) {
                    this.onHide();
                    this.props.getProducts();
                }
            })
            .catch(e => {
                console.log(e.response);
                if (e.response && e.response.data)
                    this.setState({
                        error_message: e.response.data.message
                    });
            })

    }

    render() {
        var title = "Add Product", onSubmit = this.addProduct, storesField;
        var errorMessage, product_name, product_brand, sku, product_desc, unit_type, price;
        if (this.state.error_message) {
            errorMessage = (
                <Alert variant="warning">{this.state.error_message}</Alert>
            );
        }

        if (this.props.product) {
            title = "Update Product";
            onSubmit = this.updateProduct;
            product_name = this.props.product.product_name;
            product_brand = this.props.product.product_brand;
            sku = this.props.product.sku;
            product_desc = this.props.product.product_desc;
            unit_type = this.props.product.unit_type;
            price = this.props.product.price;
        }

        if (!this.props.store && !this.props.product) {
            storesField = (
                <Form.Row>
                    <Form.Group as={Col} controlId="stores">
                        <Form.Label><b>Stores</b></Form.Label>
                        <Select isMulti
                            onChange={this.onStoresSelect}
                            options={this.state.stores}
                            value={this.state.selected_stores}
                            placeholder="Select the stores"
                            required
                        />
                    </Form.Group>
                </Form.Row>
            );
        }

        return (
            <Modal show={this.props.showModal} onHide={this.onHide}>
                <Modal.Header closeButton>
                    <Modal.Title><b>{title}</b></Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form onSubmit={onSubmit}>
                        {errorMessage}
                        <Form.Row>
                            <Form.Group as={Col} controlId="product_name">
                                <Form.Label><b>Product Name</b></Form.Label>
                                <Form.Control name="product_name"
                                    type="text"
                                    onChange={this.onChange}
                                    defaultValue={product_name}
                                    placeholder="Enter the product name"
                                    pattern="^[A-Za-z0-9.,- ]+$"
                                    required
                                />
                            </Form.Group>
                        </Form.Row>

                        <Form.Row>
                            <Form.Group as={Col} controlId="product_brand">
                                <Form.Label><b>Product Brand</b></Form.Label>
                                <Form.Control name="product_brand"
                                    type="text"
                                    onChange={this.onChange}
                                    defaultValue={product_brand}
                                    placeholder="Enter the Product Brand"
                                    pattern="^[A-Za-z0-9.,- ]+$"
                                />
                            </Form.Group>
                        </Form.Row>

                        <Form.Row>
                            <Form.Group as={Col} controlId="sku">
                                <Form.Label><b>Product SKU</b></Form.Label>
                                <Form.Control name="sku"
                                    type="text"
                                    onChange={this.onChange}
                                    defaultValue={sku}
                                    placeholder="Enter the Product SKU"
                                    pattern="^[A-Za-z0-9]+$"
                                    required />
                            </Form.Group>
                        </Form.Row>

                        <Form.Row>
                            <Form.Group as={Col} controlId="product_desc">
                                <Form.Label><b>Description</b></Form.Label>
                                <Form.Control name="product_desc"
                                    as="textarea"
                                    onChange={this.onChange}
                                    defaultValue={product_desc}
                                    placeholder="Enter the description"
                                    pattern="^[A-Za-z., ]+$"
                                    required />
                            </Form.Group>
                        </Form.Row>

                        <Form.Row>
                            <Form.Group as={Col} controlId="unit_type">
                                <Form.Label><b>Unit Type</b></Form.Label>
                                <Form.Control name="unit_type"
                                    type="text"
                                    onChange={this.onChange}
                                    defaultValue={unit_type}
                                    placeholder="Enter the unit type"
                                    pattern="^[A-Za-z0-9 ]+$"
                                    required />
                            </Form.Group>
                        </Form.Row>

                        <Form.Row>
                            <Form.Group as={Col} controlId="price">
                                <Form.Label><b>Price per unit (in $)</b></Form.Label>
                                <Form.Control name="price"
                                    type="text"
                                    onChange={this.onChange}
                                    defaultValue={price}
                                    placeholder="Enter the price"
                                    pattern="^[0-9](\.[0-9]+)?$"
                                    required
                                />
                            </Form.Group>
                        </Form.Row>

                        {storesField}

                        <center>
                            <Button variant="primary" type="submit">
                                <b>{title}</b>
                            </Button> &nbsp; &nbsp;
                            <Button variant="secondary" onClick={this.onHide}>
                                <b>Cancel</b>
                            </Button>
                        </center>
                    </Form>
                </Modal.Body>
            </Modal>
        );
    }
}

AddEditProductModal.propTypes = {
    auth: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
    auth: state.auth
});

export default connect(mapStateToProps, {})(withRouter(AddEditProductModal));