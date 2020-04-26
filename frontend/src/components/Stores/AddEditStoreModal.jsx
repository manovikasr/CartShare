import React, { Component } from "react";
import { Button, Modal, Form, Col } from "react-bootstrap";

class AddEditStoreModal extends Component {
    constructor(props) {
        super(props);
        this.state = {
            name: "",
            address: "",
            city: "",
            state: "",
            zip: ""
        };
    }

    onChange = (e) => {
        this.setState({
            [e.target.name]: e.target.value
        });
    }

    onSubmit = (e) => {
        e.preventDefault();

        const storeData = {
            name: this.state.name,
            address: this.state.address,
            city: this.state.city,
            state: this.state.state,
            zip: this.state.zip
        };
    }

    render() {
        var title = "Add Store";

        if (this.props.store) {
            title = "Update Store";

        }
        return (
            <Modal show={this.props.showModal} onHide={this.props.onHide}>
                <Modal.Header closeButton>
                    <Modal.Title><b>{title}</b></Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form onSubmit={this.onSubmit}>

                        <Form.Row>
                            <Form.Group as={Col} controlId="name">
                                <Form.Label><b>Store Name</b></Form.Label>
                                <Form.Control name="name"
                                    type="text"
                                    onChange={this.onChange}
                                    defaultValue={this.state.name}
                                    placeholder="Enter the store name"
                                    pattern="^[A-Za-z0-9 ]+$"
                                    required />
                            </Form.Group>
                        </Form.Row>

                        <Form.Row>
                            <Form.Group as={Col} controlId="address">
                                <Form.Label><b>Street Address</b></Form.Label>
                                <Form.Control name="address"
                                    type="text"
                                    onChange={this.onChange}
                                    defaultValue={this.state.address}
                                    placeholder="Enter the street address"
                                    pattern="^[A-Za-z0-9 ]+$"
                                    required />
                            </Form.Group>
                        </Form.Row>

                        <Form.Row>
                            <Form.Group as={Col} controlId="city">
                                <Form.Label><b>City</b></Form.Label>
                                <Form.Control name="city"
                                    type="text"
                                    onChange={this.onChange}
                                    defaultValue={this.state.city}
                                    placeholder="Enter the city"
                                    pattern="^[A-Za-z ]+$"
                                    required />
                            </Form.Group>

                            <Form.Group as={Col} controlId="state">
                                <Form.Label><b>State</b></Form.Label>
                                <Form.Control name="state"
                                    type="text"
                                    onChange={this.onChange}
                                    defaultValue={this.state.state}
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
                                    defaultValue={this.state.zip}
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

export default AddEditStoreModal;