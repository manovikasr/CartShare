import React, { Component } from "react";
import { Button, Modal, Form, Col, Alert } from "react-bootstrap";
import axios from "axios";

class AddEditStoreModal extends Component {
    constructor(props) {
        super(props);
        this.state = {
            store_name: "",
            address: "",
            city: "",
            state: "",
            zip: "",
            error_message: "",
            disableButton: false
        };
    }

    onChange = (e) => {
        this.setState({
            [e.target.name]: e.target.value
        });
    }

    addStore = (e) => {
        e.preventDefault();
        this.setState({
            disableButton: true
        });

        const storeData = {
            store_name: this.state.store_name,
            address: this.state.address,
            city: this.state.city,
            state: this.state.state,
            zip: this.state.zip
        };

        axios.post("store", storeData)
            .then(res => {
                this.setState({
                    disableButton: false
                });
                this.props.onHide();
                this.props.getStores();
            })
            .catch(e => {
                console.log(e.response);
                if (e.response && e.response.data)
                    this.setState({
                        error_message: e.response.data.message
                    });
            })
    }

    updateStore = (e) => {
        e.preventDefault();
        this.setState({
            disableButton: true
        });
        var store_id, store_name, address, city, state, zip;
        if (this.props.store) {
            store_id = this.props.store.id;
            store_name = this.state.store_name || this.props.store.store_name;
            address = this.state.address || this.props.store.address;
            city = this.state.city || this.props.store.city;
            state = this.state.state || this.props.store.state;
            zip = this.state.zip || this.props.store.zip;
        }
        const storeData = {
            store_name, address, city, state, zip
        };

        axios.put(`store/${store_id}`, storeData)
            .then(res => {
                if (res.status === 200) {
                    this.setState({
                        disableButton: false
                    });
                    this.props.onHide();
                    this.props.getStores();
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
        var title = "Add Store", onSubmit = this.addStore;
        var errorMessage, store_name, address, city, state, zip;
        if (this.state.error_message) {
            errorMessage = (
                <Alert variant="warning">{this.state.error_message}</Alert>
            );
        }

        if (this.props.store) {
            title = "Update Store";
            onSubmit = this.updateStore;
            store_name = this.props.store.store_name;
            address = this.props.store.address;
            city = this.props.store.city;
            state = this.props.store.state;
            zip = this.props.store.zip;
        }
        return (
            <Modal show={this.props.showModal} onHide={this.props.onHide}>
                <Modal.Header closeButton>
                    <Modal.Title><b>{title}</b></Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form onSubmit={onSubmit}>
                        {errorMessage}
                        <Form.Row>
                            <Form.Group as={Col} controlId="store_name">
                                <Form.Label><b>Store Name</b></Form.Label>
                                <Form.Control name="store_name"
                                    type="text"
                                    onChange={this.onChange}
                                    defaultValue={store_name}
                                    placeholder="Enter the store name"
                                    pattern="^[A-Za-z0-9., ]+$"
                                    required />
                            </Form.Group>
                        </Form.Row>

                        <Form.Row>
                            <Form.Group as={Col} controlId="address">
                                <Form.Label><b>Street Address</b></Form.Label>
                                <Form.Control name="address"
                                    type="text"
                                    onChange={this.onChange}
                                    defaultValue={address}
                                    placeholder="Enter the street address"
                                    pattern="^[A-Za-z0-9.,# ]+$"
                                    required />
                            </Form.Group>
                        </Form.Row>

                        <Form.Row>
                            <Form.Group as={Col} controlId="city">
                                <Form.Label><b>City</b></Form.Label>
                                <Form.Control name="city"
                                    type="text"
                                    onChange={this.onChange}
                                    defaultValue={city}
                                    placeholder="Enter the city"
                                    pattern="^[A-Za-z ]+$"
                                    required />
                            </Form.Group>

                            <Form.Group as={Col} controlId="state">
                                <Form.Label><b>State</b></Form.Label>
                                <Form.Control name="state"
                                    type="text"
                                    onChange={this.onChange}
                                    defaultValue={state}
                                    placeholder="Enter the state"
                                    pattern="^[A-Za-z ]+$"
                                    required />
                            </Form.Group>
                        </Form.Row>

                        <Form.Row>
                            <Form.Group as={Col} controlId="zip">
                                <Form.Label><b>Zip Code</b></Form.Label>
                                <Form.Control name="zip"
                                    type="text"
                                    onChange={this.onChange}
                                    defaultValue={zip}
                                    placeholder="Enter the zip code"
                                    pattern="^[0-9]{5}(?:-[0-9]{4})?$"
                                    required />
                            </Form.Group>
                        </Form.Row>
                        <center>
                            <Button variant="primary" type="submit" disabled={this.state.disableButton}>
                                <b>{title}</b>
                            </Button> &nbsp; &nbsp;
                            <Button variant="secondary" onClick={this.props.onHide} disabled={this.state.disableButton}>
                                <b>Cancel</b>
                            </Button>
                        </center>
                    </Form>
                </Modal.Body>
            </Modal>
        );
    }
}

export default AddEditStoreModal;