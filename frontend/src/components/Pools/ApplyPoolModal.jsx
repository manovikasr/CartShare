import React, { Component } from "react";
import { withRouter } from "react-router-dom";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { Button, Modal, Form, Col, Alert } from "react-bootstrap";
import axios from "axios";

class ApplyPoolModal extends Component {
    constructor(props) {
        super(props);
        this.state = {
            ref_name: "",
            know_pool_leader: true,
            error_message: ""
        };
    }

    onCheck = (e) => {
        let know_pool_leader = e.target.value === "true";
        let ref_name = know_pool_leader ? "" : this.state.ref_name;
        this.setState({
            know_pool_leader,
            ref_name
        });
    }

    onChange = (e) => {
        this.setState({
            [e.target.name]: e.target.value
        });
    }

    onHide = (e) => {
        this.setState({
            know_pool_leader: true,
            ref_name: "",
            error_message: ""
        });
        this.props.onHide();
    }

    onApply = (e) => {
        e.preventDefault();
        const { user } = this.props.auth;
        const pool = this.props.pool;
        var knows_leader = this.state.know_pool_leader;
        var ref_name = this.state.ref_name;
        var pool_leader_id = pool.pool_leader_id;
        var pool_leader = pool.user.find(member => member.id === pool_leader_id);

        if (knows_leader) {
            ref_name = pool_leader ? pool_leader.screen_name : this.state.ref_name;
        } else {
            if(pool_leader.screen_name === ref_name)
                knows_leader = true;
        }

        axios.post(`/pool/apply?user_id=${user.id}&pool_id=${pool.id}&knows_leader=${knows_leader}&ref_name=${ref_name}`)
            .then(res => {
                if (res.status === 200) {
                    this.onHide();
                    this.props.getUserApplications();
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
        var errorMessage, referenceNameField, pool = this.props.pool;
        if (this.state.error_message) {
            errorMessage = (
                <Alert variant="warning">{this.state.error_message}</Alert>
            );
        }

        if (this.state.know_pool_leader === false) {
            referenceNameField = (
                <Form.Row>
                    <Form.Group as={Col} controlId="ref_name">
                        <Form.Label><b>Enter the name of a reference to join the group</b></Form.Label>
                        <Form.Control name="ref_name"
                            type="text"
                            onChange={this.onChange}
                            placeholder="Enter the reference name"
                            pattern="^[A-Za-z0-9 ]+$"
                            required
                        />
                    </Form.Group>
                </Form.Row>
            );
        }

        return (
            <Modal show={this.props.showModal} onHide={this.onHide}>
                <Modal.Header closeButton>
                    <Modal.Title><b>Join Pool</b></Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form onSubmit={this.onApply}>
                        {errorMessage}
                        <Form.Row>
                            <Form.Group as={Col} controlId="pool_id">
                                <Form.Label><b>Pool Name</b></Form.Label>
                                <Form.Control name="pool_name"
                                    type="text"
                                    defaultValue={pool.pool_name}
                                    readOnly />
                            </Form.Group>
                        </Form.Row>

                        <Form.Row>
                            <Form.Group as={Col} controlId="know_pool_leader">
                                <Form.Label><b>Do you know the leader of this pool?</b></Form.Label>
                                <Form.Check name="know_pool_leader"
                                    type="radio"
                                    label="Yes"
                                    id="yes"
                                    value={true}
                                    onChange={this.onCheck}
                                    required
                                />
                                <Form.Check name="know_pool_leader"
                                    type="radio"
                                    label="No"
                                    id="no"
                                    value={false}
                                    onChange={this.onCheck}
                                    required
                                />
                            </Form.Group>
                        </Form.Row>

                        {referenceNameField}

                        <center>
                            <Button variant="primary" type="submit">
                                <b>Apply</b>
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

ApplyPoolModal.propTypes = {
    auth: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
    auth: state.auth
});

export default connect(mapStateToProps, {})(withRouter(ApplyPoolModal));