import React, { Component } from "react";
import { withRouter } from "react-router-dom";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { Button, Modal, Form, Col, Alert } from "react-bootstrap";
import axios from "axios";

class EditStatusModal extends Component {
    constructor(props) {
        super(props);
        this.state = {
            status: ""
        };
    }

    onChange = (e) => {
        this.setState({
            [e.target.name]: e.target.value
        });
    }

    updateOrderStatus = () => {
        axios.put(`/order/status/${this.props.order.id}/${this.state.status}`)
            .then(res => {
                if (res.status === 200) {
                    this.props.onHide();
                    this.props.getOrders();
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
        return (
            <Modal show={this.props.showModal} onHide={this.props.onHide}>
                <Modal.Header closeButton>
                    <Modal.Title><b>Update Status for Order #{this.props.order.id}</b></Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form>
                        <Form.Row>
                            <Form.Group as={Col} controlId="status">
                                <Form.Label><b>Status</b></Form.Label>
                                <Form.Control name="status"
                                    as="select"
                                    defaultValue={this.props.order.status}
                                    onChange={this.onChange}>
                                    <option value="ORDER_PICKED_UP">Picked up</option>
                                    <option value="DELIVERED">Delivered</option>
                                </Form.Control>
                            </Form.Group>
                        </Form.Row>

                        <center>
                            <Button variant="success" onClick={this.updateOrderStatus}>
                                <b>Update</b>
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

EditStatusModal.propTypes = {
    auth: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
    auth: state.auth
});

export default connect(mapStateToProps, {})(withRouter(EditStatusModal));