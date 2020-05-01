import React, { Component } from "react";
import { withRouter } from "react-router-dom";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { Button, Modal, Form, Col, Alert } from "react-bootstrap";
import axios from "axios";

class AddEditPoolModal extends Component {
    constructor(props) {
        super(props);
        this.state = {
            pool_id: "",
            pool_name: "",
            neighbourhood_name: "",
            pool_desc: "",
            pool_zip: "",
            error_message: ""
        };
    }

    onChange = (e) => {
        this.setState({
            [e.target.name]: e.target.value
        });
    }

    onHide = (e) => {
        this.setState({
            error_message: ""
        });
        this.props.onHide();
    }

    addPool = (e) => {
        e.preventDefault();
        const { user } = this.props.auth;

        const poolData = {
            pool_leader_id: user.id,
            pool_id: this.state.pool_id,
            pool_name: this.state.pool_name,
            neighbourhood_name: this.state.neighbourhood_name,
            pool_desc: this.state.pool_desc,
            pool_zip: this.state.pool_zip
        };

        axios.post("/pool", poolData)
            .then(res => {
                if (res.status === 200) {
                    this.props.onHide();
                    this.props.getPoolData();
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

    updatePool = (e) => {
        e.preventDefault();
        var id, pool_id, pool_name, neighbourhood_name, pool_desc;
        if (this.props.pool) {
            id = this.props.pool.id;
            pool_id = this.props.pool.pool_id;
            pool_name = this.state.pool_name || this.props.pool.pool_name;
            neighbourhood_name = this.state.neighbourhood_name || this.props.pool.neighbourhood_name;
            pool_desc = this.state.pool_desc || this.props.pool.pool_desc;
        }
        const poolData = {
            pool_name, pool_id, neighbourhood_name, pool_desc
        };

        axios.put(`${id}`, poolData)
            .then(res => {
                if (res.status === 200) {
                    this.props.onHide();
                    this.props.getPoolData();
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
        var errorMessage, pool_id, pool_name, neighbourhood_name, pool_desc, state, pool_zip;
        if (this.state.error_message) {
            errorMessage = (
                <Alert variant="warning">{this.state.error_message}</Alert>
            );
        }

        if (this.props.pool) {
            updateMode = true;
            title = "Update Pool";
            onSubmit = this.updatePool;
            pool_id = this.props.pool.pool_id;
            pool_name = this.props.pool.pool_name;
            pool_desc = this.props.pool.pool_desc;
            neighbourhood_name = this.props.pool.neighbourhood_name;
            pool_zip = this.props.pool.pool_zip;
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
                            <Form.Group as={Col} controlId="pool_desc">
                                <Form.Label><b>Description</b></Form.Label>
                                <Form.Control name="pool_desc"
                                    as="textarea"
                                    onChange={this.onChange}
                                    defaultValue={pool_desc}
                                    placeholder="Enter the description"
                                    pattern="^[A-Za-z ]+$"
                                    required />
                            </Form.Group>
                        </Form.Row>

                        <Form.Row>
                            <Form.Group as={Col} controlId="neighbourhood_name">
                                <Form.Label><b>Neighbourhood Name</b></Form.Label>
                                <Form.Control name="neighbourhood_name"
                                    type="text"
                                    onChange={this.onChange}
                                    defaultValue={neighbourhood_name}
                                    placeholder="Enter the neighbourhood name"
                                    pattern="^[A-Za-z0-9,# ]+$"
                                    required />
                            </Form.Group>
                        </Form.Row>

                        <Form.Row>
                            <Form.Group as={Col} controlId="pool_zip">
                                <Form.Label><b>Zip Code</b></Form.Label>
                                <Form.Control name="pool_zip"
                                    type="text"
                                    onChange={this.onChange}
                                    defaultValue={pool_zip}
                                    placeholder="Enter the zip code"
                                    pattern="^[0-9 ]+$"
                                    required
                                    readOnly={updateMode} />
                            </Form.Group>
                        </Form.Row>
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

AddEditPoolModal.propTypes = {
    auth: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
    auth: state.auth
});

export default connect(mapStateToProps, {})(withRouter(AddEditPoolModal));