import React, { Component } from "react";
import { withRouter } from "react-router-dom";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { Button, Modal, Form, Col, Alert, Table } from "react-bootstrap";
import axios from "axios";

class OrderProductsModal extends Component {
    constructor(props) {
        super(props);
        this.state = {

        };
    }

    render() {
        var products_list, products;
        if (this.props.order.order_details && this.props.order.order_details.length) {
            products_list = this.props.order.order_details.map(product => {
                return (
                    <tr>
                        <td align="center">{product.sku}</td>
                        <td align="center">{product.product_name}</td>
                        <td align="center">{product.product_brand}</td>
                        <td align="center">{product.product_price}</td>
                        <td align="center">{product.quantity}</td>
                        <td align="center">{product.total_price}</td>
                    </tr>
                )
            });
        }
        return (
            <Modal show={this.props.showModal} onHide={this.props.onHide} size="lg">
                <Modal.Header closeButton>
                    <Modal.Title>Order Details</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Table borderless striped>
                        <thead align="center">
                            <th>SKU</th>
                            <th>Product Name</th>
                            <th>Product Brand</th>
                            <th>Product Price</th>
                            <th>Quantity</th>
                            <th>Total Price</th>
                        </thead>
                        <tbody>
                            {products_list}
                        </tbody>
                    </Table>
                    <center>
                        <Button variant="secondary" onClick={this.props.onHide}>
                            <b>Close</b>
                        </Button>
                    </center>
                </Modal.Body>
            </Modal>
        );
    }
}

OrderProductsModal.propTypes = {
    auth: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
    auth: state.auth
});

export default connect(mapStateToProps, {})(withRouter(OrderProductsModal));