import React, { Component } from "react";
import { withRouter } from "react-router-dom";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { updateProfile } from "../../redux/actions/authActions";
import { Col, Form, Button, ButtonGroup, Alert } from 'react-bootstrap';

class Profile extends Component {
    constructor() {
        super();
        this.state = {
            email: "",
            nick_name: "",
            screen_name: "",
            address: "",
            city: "",
            state: "",
            zip: "",
            message: ""
        };
    }

    componentDidMount() {
        if (!this.props.auth.isAuthenticated) {
            this.props.history.push("/");
        }
    }

    componentWillReceiveProps(props) {

    }

    componentWillUnmount() {
        this.setState({
            message: ""
        });
    }

    onChange = e => {
        this.setState({
            [e.target.id]: e.target.value
        });
    };

    onSubmit = e => {
        e.preventDefault();

        const userData = {
            email: this.state.email,
            screen_name: this.state.screen_name,
            nick_name: this.state.nick_name,
            address: this.state.address,
            city: this.state.city,
            state: this.state.state,
            zip: this.state.zip
        };

        this.props.updateProfile(userData);
    };

    render() {
        var errorMessage;
        var { user } = this.props.auth;
        if (this.state.message) {
            errorMessage = (
                <Alert variant="warning">{this.state.message}</Alert>
            );
        }
        return (
            <React.Fragment>
                <div className="container p-4">
                    <div className="row justify-content-center align-items-center h-100">
                        <div className="col-md-12">
                            <div className="col-md-6 mx-auto bg-white p-3 border rounded">
                                <center>
                                    <h2>Profile</h2>
                                </center>
                                <br />
                                {errorMessage}

                                <Form onSubmit={this.onSubmit} autoComplete="off">
                                    <Form.Row>
                                        <Form.Group as={Col} controlId="screen_name">
                                            <Form.Label><b>Screen Name</b></Form.Label>
                                            <Form.Control name="screen_name"
                                                type="text"
                                                defaultValue={user.screen_name}
                                                readOnly />
                                        </Form.Group>
                                    </Form.Row>

                                    <Form.Row>
                                        <Form.Group as={Col} controlId="nick_name">
                                            <Form.Label><b>Nick Name</b></Form.Label>
                                            <Form.Control name="nick_name"
                                                type="text"
                                                onChange={this.onChange}
                                                defaultValue={user.nick_name}
                                                placeholder="Enter a nick name"
                                                pattern="^[A-Za-z0-9 ]+$"
                                                required />
                                        </Form.Group>
                                    </Form.Row>

                                    <Form.Row>
                                        <Form.Group as={Col} controlId="email">
                                            <Form.Label><b>Email Id</b></Form.Label>
                                            <Form.Control name="email"
                                                type="text"
                                                value={user.sub}
                                                readOnly />
                                        </Form.Group>
                                    </Form.Row>

                                    <Form.Row>
                                        <Form.Group as={Col} controlId="role">
                                            <Form.Label><b>Role</b></Form.Label>
                                            <Form.Control name="role"
                                                type="text"
                                                value={user.role === "admin" ? "Admin" : "Pooler"}
                                                readOnly />
                                        </Form.Group>
                                    </Form.Row>

                                    <Form.Row>
                                        <Form.Group as={Col} controlId="address">
                                            <Form.Label><b>Street Address</b></Form.Label>
                                            <Form.Control name="address"
                                                type="text"
                                                onChange={this.onChange}
                                                defaultValue={this.state.address}
                                                placeholder="Enter your street address"
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
                                                placeholder="Enter your city"
                                                pattern="^[A-Za-z ]+$"
                                                required />
                                        </Form.Group>

                                        <Form.Group as={Col} controlId="state">
                                            <Form.Label><b>State</b></Form.Label>
                                            <Form.Control name="state"
                                                type="text"
                                                onChange={this.onChange}
                                                defaultValue={this.state.state}
                                                placeholder="Enter your state"
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
                                                placeholder="Enter your zip code"
                                                pattern="^[0-9 ]+$"
                                                required />
                                        </Form.Group>
                                    </Form.Row>

                                    <div className="row">
                                        <div className="col-md-12 text-center p-2">
                                            <ButtonGroup aria-label="Third group">
                                                <Button type="submit" variant="success">Update</Button>&nbsp;&nbsp;
                                                <Button variant="secondary" onClick={() => { this.props.history.push("/"); }}>Cancel</Button>
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

Profile.propTypes = {
    registerUser: PropTypes.func.isRequired,
    auth: PropTypes.object.isRequired,
    errors: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
    auth: state.auth,
    errors: state.errors,
    success: state.success
});

export default connect(mapStateToProps, { updateProfile })(withRouter(Profile));