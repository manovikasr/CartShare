import React, { Component } from "react";
import { withRouter } from "react-router-dom";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { Button, Modal, Form, Col, Alert } from "react-bootstrap";
import axios from "axios";

class BarcodeModal extends Component {
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
                    <Modal.Title><b>Confirm</b></Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form>
                        <Form.Row>
                            <Form.Group as={Col} controlId="produt_id">
                                <Form.Label><b>Barcode</b></Form.Label>
                            </Form.Group>
                        </Form.Row>

                        <center>
                            <Button variant="success" onClick={this.props.onHide}>
                                <b>Checkout</b>
                            </Button>&nbsp;&nbsp;
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

BarcodeModal.propTypes = {
    auth: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
    auth: state.auth
});

export default connect(mapStateToProps, {})(withRouter(BarcodeModal));