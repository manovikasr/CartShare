import React, { Component } from "react";
import { withRouter } from "react-router-dom";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { Button, Modal, Form, Col, Alert } from "react-bootstrap";
import axios from "axios";

class ConfirmOrderModal extends Component {
    constructor(props) {
        super(props);
        this.state = {
            delivery: false,
            error_message: ""
        };
    }

    onCheck = (e) => {
        let delivery = e.target.value === "true";
        this.setState({
            delivery
        });
    }

    onChange = (e) => {
        this.setState({
            [e.target.name]: e.target.value
        });
    }

    onHide = (e) => {
        this.setState({
            self_pickup: null
        });
        this.props.onHide();
    }

    onSubmit = (e) => {
        e.preventDefault();
        var placeOrder = true;
        if(this.state.delivery && this.props.credits <= -4){
            placeOrder = window.confirm("You have only " + this.props.credits + " Contribution credits. Do you still want your fellow poolers to deliver your order?");
        }
        if(placeOrder){
            this.props.placeOrder(this.state.delivery);
        } else {
            return;
        }
    }

    render() {
        var message;
        if (this.state.delivery) {
            message = (
                <Alert variant="success">You have {this.props.credits} contribution credits.</Alert>
            );
            if (this.props.credits <= -4) {
                message = (
                    <Alert variant="warning">You have only {this.props.credits} contribution credits.</Alert>
                );
            }
            if (this.props.credits <= -6) {
                message = (
                    <Alert variant="danger">You have only {this.props.credits} contribution credits.</Alert>
                );
            }
        }

        return (
            <Modal show={this.props.showModal} onHide={this.onHide}>
                <Modal.Header closeButton>
                    <Modal.Title><b>Confirm Order</b></Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form onSubmit={this.onSubmit}>

                        <Form.Row>
                            <Form.Group as={Col} controlId="delivery">
                                <Form.Label><b>How do you want this order to be delivered?</b></Form.Label>
                                <Form.Check name="delivery"
                                    type="radio"
                                    label="I want a fellow pooler to pickup and deliver."
                                    id="delivery"
                                    value={true}
                                    onChange={this.onCheck}
                                    required
                                />
                                <Form.Check name="delivery"
                                    type="radio"
                                    label="I will pickup the order myself."
                                    id="pickup"
                                    value={false}
                                    onChange={this.onCheck}
                                    required
                                />
                            </Form.Group>
                        </Form.Row>

                        {message}

                        <center>
                            <Button variant="primary" type="submit">
                                <b>Confirm Order</b>
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

ConfirmOrderModal.propTypes = {
    auth: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
    auth: state.auth
});

export default connect(mapStateToProps, {})(withRouter(ConfirmOrderModal));