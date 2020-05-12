import React, { Component } from "react";
import { withRouter } from "react-router-dom";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { Form, Col, ButtonGroup, Button, Alert } from "react-bootstrap";
import axios from "axios";

class Message extends Component {
    constructor(props) {
        super(props);
        this.state = {
            sender_screen_name: "",
            receiver_screen_name: "",
            message: "",
            alert_message: "",
            disableButton: false
        };
    }

    componentDidMount() {
        if (!this.props.auth.isAuthenticated && this.props.auth.user.role === "admin") {
            this.props.history.push("/");
        }
        else {
            if (!this.props.auth.user.email_verified) {
                this.props.history.push("/verify");
            }
        }
        const sender_screen_name = this.props.auth.user.screen_name;
        this.setState({
            sender_screen_name
        });
    }

    componentWillUnmount() {
        this.setState({
            receiver_screen_name: "",
            message: "",
            alert_message: ""
        });
    }

    onChange = (e) => {
        this.setState({
            [e.target.name]: e.target.value
        });
    }

    onSubmit = (e) => {
        e.preventDefault();

        this.setState({
            disableButton: true
        });
        const messageData = {
            sender_screen_name: this.state.sender_screen_name,
            receiver_screen_name: this.state.receiver_screen_name,
            message: this.state.message
        };

        axios.post("message", messageData)
            .then(res => {
                if (res.status === 200) {
                    this.setState({
                        receiver_screen_name: "",
                        message: "",
                        alert_message: "Message Sent Successfully!",
                        disableButton: false
                    });
                }
            })
            .catch(e => {
                if (e.response && e.response.data) {
                    this.setState({
                        alert_message: e.response.data.message
                    });
                }
            });

    }

    render() {
        var alertMessage;

        if (this.state.alert_message) {
            alertMessage = (
                <Alert variant="success">{this.state.alert_message}</Alert>
            );
        }

        return (
            <div className="container p-4">
                <div className="row justify-content-center align-items-center h-100">
                    <div className="col-md-12">
                        <div className="col-md-6 mx-auto bg-white p-3 border rounded">
                            <h2 align="center">Send a Message</h2>
                            <br />
                            {alertMessage}
                            <Form onSubmit={this.onSubmit} autoComplete="off">
                                <Form.Row>
                                    <Form.Group as={Col} controlId="sender_screen_name">
                                        <Form.Label><b>Sender Name</b></Form.Label>
                                        <Form.Control name="sender_screen_name"
                                            type="text"
                                            defaultValue={this.state.sender_screen_name}
                                            readOnly />
                                    </Form.Group>
                                </Form.Row>

                                <Form.Row>
                                    <Form.Group as={Col} controlId="receiver_screen_name">
                                        <Form.Label><b>Receiver's Name</b></Form.Label>
                                        <Form.Control name="receiver_screen_name"
                                            type="text"
                                            onChange={this.onChange}
                                            value={this.state.receiver_screen_name}
                                            placeholder="Enter receiver's name"
                                            pattern="^[A-Za-z0-9 ]+$"
                                            required />
                                    </Form.Group>
                                </Form.Row>

                                <Form.Row>
                                    <Form.Group as={Col} controlId="message">
                                        <Form.Label><b>Message</b></Form.Label>
                                        <Form.Control name="message"
                                            as="textarea"
                                            rows={10}
                                            onChange={this.onChange}
                                            value={this.state.message}
                                            placeholder="Enter your message"
                                            pattern="^[A-Za-z0-9.,- ]+$"
                                            required />
                                    </Form.Group>
                                </Form.Row>
                                <br/>


                                <div className="row">
                                    <div className="col-md-12 text-center p-2">
                                        <ButtonGroup aria-label="Third group">
                                            <Button type="submit" variant="success" disabled={this.state.disableButton}>Send</Button>&nbsp;&nbsp;
                                            <Button variant="secondary" onClick={() => { this.props.history.push("/"); }} disabled={this.state.disableButton}>Cancel</Button>
                                        </ButtonGroup>
                                    </div>
                                </div>
                            </Form>
                        </div>
                    </div>
                </div>
            </div>
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