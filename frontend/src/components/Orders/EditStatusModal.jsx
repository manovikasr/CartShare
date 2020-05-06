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
            state:""    
        };
    }

    componentDidMount() {

    }

    onHide = (e) => {
        this.props.onHide();
    }

    onChange = (e) => {
        this.setState({
            [e.target.name]: e.target.value
        });
    }

    editOrder = () => {
        axios.put(`/order/change_order_status/${this.props.order.id}/${this.state.status}`)
        .then(res => {
            if (res.status === 200) {
                this.props.onHide();
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
            <Modal show={this.props.showModal} onHide={this.onHide}>
                <Modal.Header closeButton>
                    <Modal.Title><b>Edit Status of your Order - {this.props.order.id}</b></Modal.Title>
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
                                    <option>Placed</option>
                                    <option>Self-Pickedup</option>
                                    <option>Pickedup</option>
                                    <option>Delivered</option>
                                    <option>Delivered-Not-Received</option>
                                </Form.Control>
                            </Form.Group>
                        </Form.Row>

                        <center>
                            <Button variant="success" onClick = {this.editOrder}>
                                <b>Edit</b>
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