import React, { Component } from "react";
import { withRouter } from "react-router-dom";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { Col, Form, Button, ButtonGroup, Alert } from 'react-bootstrap';

class Message extends Component {
    constructor(props) {
        super(props);
        this.state = {};
    }

    componentDidMount() {
        if (!this.props.auth.isAuthenticated && this.props.auth.user.role === "admin" ) {
            this.props.history.push("/");
        }
        else {
            if (!this.props.auth.user.email_verified) {
                this.props.history.push("/verify");
            }
        }
    }

    render() {
        const { user } = this.props.auth;

        return (
            <React.Fragment>
                <div className="container p-4">
                    <div className="row justify-content-center align-items-center h-100">
                        <div className="col-md-12">
                            <div className="col-md-6 mx-auto bg-white p-3 border rounded">
                                <center>
                                    <h2>Send a message</h2>
                                </center>
                                <br />
                                <Form autoComplete="off">
                                    <Form.Row>
                                        <Form.Group as={Col} controlId="to_email">
                                            <Form.Label><b>To</b></Form.Label>
                                            <Form.Control nameto_email
                                                type="email"
                                                placeholder="Enter the email address"
                                                pattern="^[A-Za-z0-9 ]+$"
                                                required />
                                        </Form.Group>
                                    </Form.Row>
                                    <Form.Row>
                                        <Form.Group as={Col} controlId="message">
                                            <Form.Label><b>Enter your message</b></Form.Label>
                                            <Form.Control as="textarea" rows="3" columns="3"/>
                                        </Form.Group>
                                    </Form.Row>

                                    <div className="row">
                                        <div className="col-md-12 text-center p-2">
                                            <ButtonGroup aria-label="Third group">
                                                <Button type="submit" variant="success">Send</Button>
                                            </ButtonGroup>
                                        </div>
                                    </div>
                                </Form>
                            </div>
                        </div>
                    </div>
                </div>
            </React.Fragment>
        );
    }
}

Message.propTypes = {
    auth: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
    auth: state.auth
});


export default connect(mapStateToProps, {})(withRouter(Message));