import React, { Component } from "react";
import { withRouter } from "react-router-dom";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { Button, Modal, Form, Col, Alert } from "react-bootstrap";
import axios from "axios";

class OrderProductsModal extends Component {
    constructor(props) {
        super(props);
        this.state = {
            
        };
    }


    onHide = (e) => {
        this.props.onHide();
    }

    render() {
        return (
            <Modal show={this.props.showModal} onHide={this.onHide}>
                <Modal.Header closeButton>
                    <Modal.Title><b>List of Products</b></Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form>
                        <Form.Row>
                            <Form.Group as={Col} controlId="produt_id">
                                <Form.Label><b>Product id</b></Form.Label>
                                <Form.Control name="product_id"
                                    type="text"
                                    defaultValue="123"
                                    readOnly/>
                            </Form.Group>
                        </Form.Row>

                        <Form.Row>
                            <Form.Group as={Col} controlId="product_name">
                                <Form.Label><b>Product Name</b></Form.Label>
                                <Form.Control name="product_name"
                                    type="text"
                                    defaultValue="Milk"
                                    readOnly/>
                            </Form.Group>
                        </Form.Row>

                        <Form.Row>
                            <Form.Group as={Col} controlId="product_description">
                                <Form.Label><b>Product Description</b></Form.Label>
                                <Form.Control name="product_description"
                                    type="text"
                                    defaultValue="Reduced Fat"
                                    readOnly/>
                            </Form.Group>
                        </Form.Row>

                        <center>
                            <Button variant="secondary" onClick={this.props.onHide}>
                                <b>Close</b>
                            </Button>
                        </center>
                    </Form>
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