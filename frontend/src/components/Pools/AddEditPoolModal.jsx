import React, { Component } from "react";
import { Button, Modal, Form, Col, Alert } from "react-bootstrap";
import axios from "axios";

class AddEditPoolModal extends Component {
    constructor(props) {
        super(props);
        this.state = {
            pool_id: "",
            pool_name: "",
            neighbourhood: "",
            pool_description: "",
            zip: "",
            error_message: ""
        };
    }

    onChange = (e) => {
        this.setState({
            [e.target.name]: e.target.value
        });
    }

    addPool = (e) => {
        e.preventDefault();

        const storeData = {
            store_name: this.state.store_name,
            address: this.state.address,
            city: this.state.city,
            state: this.state.state,
            zip: this.state.zip
        };

        axios.post("store", storeData)
            .then(res => {
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

    updatePool = (e) => {
        e.preventDefault();
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
        var title = "Add Pool", onSubmit = this.addPool, updateMode = false;
        var errorMessage, pool_id, pool_name, neighbourhood, pool_description, state, zip;
        if (this.state.error_message) {
            errorMessage = (
                <Alert variant="warning">{this.state.error_message}</Alert>
            );
        }

        if (this.props.store) {
            updateMode = true;
            title = "Update Pool";
            onSubmit = this.updatePool;

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
                            <Form.Group as={Col} controlId="pool_id">
                                <Form.Label><b>Pool ID</b></Form.Label>
                                <Form.Control name="pool_id"
                                    type="text"
                                    onChange={this.onChange}
                                    defaultValue={pool_id}
                                    placeholder="Enter the pool id"
                                    pattern="^[A-Za-z0-9 ]+$"
                                    required
                                    readOnly={updateMode} />
                            </Form.Group>
                        </Form.Row>

                        <Form.Row>
                            <Form.Group as={Col} controlId="pool_name">
                                <Form.Label><b>Pool Name</b></Form.Label>
                                <Form.Control name="pool_name"
                                    type="text"
                                    onChange={this.onChange}
                                    defaultValue={pool_name}
                                    placeholder="Enter the Pool name"
                                    pattern="^[A-Za-z0-9 ]+$"
                                    required />
                            </Form.Group>
                        </Form.Row>

                        <Form.Row>
                            <Form.Group as={Col} controlId="neighbourhood">
                                <Form.Label><b>Neighbourhood</b></Form.Label>
                                <Form.Control name="neighbourhood"
                                    type="text"
                                    onChange={this.onChange}
                                    defaultValue={neighbourhood}
                                    placeholder="Enter the neighbourhood name"
                                    pattern="^[A-Za-z0-9,# ]+$"
                                    required />
                            </Form.Group>
                        </Form.Row>

                        <Form.Row>
                            <Form.Group as={Col} controlId="pool_description">
                                <Form.Label><b>Description</b></Form.Label>
                                <Form.Control name="pool_description"
                                    as="textarea"
                                    onChange={this.onChange}
                                    defaultValue={pool_description}
                                    placeholder="Enter the description"
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
                                    pattern="^[0-9 ]+$"
                                    required />
                            </Form.Group>
                        </Form.Row>
                        <center>
                            <Button variant="primary" type="submit">
                                <b>{title}</b>
                            </Button> &nbsp; &nbsp;
                            <Button variant="secondary" onClick={this.props.onHide}>
                                <b>Cancel</b>
                            </Button>
                        </center>
                    </Form>
                </Modal.Body>
            </Modal>
        );
    }
}

export default AddEditPoolModal;