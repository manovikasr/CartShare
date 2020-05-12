import React, { Component } from "react";
import { withRouter } from "react-router-dom";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import axios from "axios";
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
            profile: {},
            success_message: "",
            error_message: "",
            disableButton: false
        };
    }

    componentDidMount() {
        if (!this.props.auth.isAuthenticated) {
            this.props.history.push("/");
        }
        this.getProfile();
    }

    componentWillReceiveProps(props) {
        console.log(props)
        if (props.errors.message) {
            this.setState({
                error_message: props.errors.message,
                success_message: "",
                disableButton: false
            });
        } if (props.success.message) {
            this.setState({
                success_message: "Profile updated",
                error_message: "",
                disableButton: false
            });
        }
    }

    getProfile = () => {
        axios.get("/profile")
            .then(res => {
                if (res.data) {
                    this.setState({
                        profile: res.data.user
                    });
                }
            })
            .catch(e => {
                console.log(e);
            });
    };

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
        this.setState({
            disableButton: true
        });
        const { user } = this.props.auth;
        const userData = {
            user_id: user.id,
            contribution_credits: this.state.profile.contribution_credits,
            email: this.state.profile.email,
            screen_name: this.state.profile.screen_name,
            nick_name: this.state.nick_name || this.state.profile.nick_name,
            address: this.state.address || this.state.profile.address,
            city: this.state.city || this.state.profile.city,
            state: this.state.state || this.state.profile.state,
            zip: this.state.zip || this.state.profile.zip,
        };

        this.props.updateProfile(userData);
    };

    render() {
        var errorMessage, successMessage, contri_status = "", contri_status_color = "";
        var user = this.state.profile;
        if (this.state.error_message) {
            errorMessage = (
                <Alert variant="warning">{this.state.error_message}</Alert>
            );
        }
        if (this.state.success_message) {
            successMessage = (
                <Alert variant="success">{this.state.success_message}</Alert>
            );
        }

        if (this.state.profile.id) {
            let contri_credits = user.contribution_credits;
            if (contri_credits >= -4) {
                contri_status = "GREEN";
                contri_status_color = "#03fc6b";
            }
            else if (contri_credits > -6) {
                contri_status = "YELLOW";
                contri_status_color = "#fcfc03";
            }
            else {
                contri_status = "RED";
                contri_status_color = "#e66722";
            }
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
                                {successMessage}

                                <Form onSubmit={this.onSubmit}>
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
                                                value={user.email}
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

                                    {(user.role === "pooler") &&
                                        <Form.Row>
                                            <Form.Group as={Col} controlId="credits">
                                                <Form.Label><b>Credits</b></Form.Label>
                                                <Form.Control name="credits"
                                                    type="text"
                                                    value={user.contribution_credits || 0}
                                                    readOnly />
                                            </Form.Group>
                                            <Form.Group as={Col} controlId="status">
                                                <Form.Label><b>Status</b></Form.Label>
                                                <Form.Control name="status"
                                                    type="text"
                                                    style={{ backgroundColor: contri_status_color }}
                                                    value={contri_status}
                                                    readOnly />
                                            </Form.Group>
                                        </Form.Row>
                                    }

                                    <Form.Row>
                                        <Form.Group as={Col} controlId="address">
                                            <Form.Label><b>Street Address</b></Form.Label>
                                            <Form.Control name="address"
                                                type="text"
                                                onChange={this.onChange}
                                                defaultValue={user.address}
                                                placeholder="Enter your street address"
                                                pattern="^[A-Za-z0-9,.#- ]+$"
                                                required />
                                        </Form.Group>
                                    </Form.Row>

                                    <Form.Row>
                                        <Form.Group as={Col} controlId="city">
                                            <Form.Label><b>City</b></Form.Label>
                                            <Form.Control name="city"
                                                type="text"
                                                onChange={this.onChange}
                                                defaultValue={user.city}
                                                placeholder="Enter your city"
                                                pattern="^[A-Za-z ]+$"
                                                required />
                                        </Form.Group>

                                        <Form.Group as={Col} controlId="state">
                                            <Form.Label><b>State</b></Form.Label>
                                            <Form.Control name="state"
                                                type="text"
                                                onChange={this.onChange}
                                                defaultValue={user.state}
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
                                                defaultValue={user.zip}
                                                placeholder="Enter your zip code"
                                                pattern="^[0-9]{5}(?:-[0-9]{4})?$"
                                                required />
                                        </Form.Group>
                                    </Form.Row>

                                    <div className="row">
                                        <div className="col-md-12 text-center p-2">
                                            <ButtonGroup aria-label="Third group">
                                                <Button type="submit" variant="success" disabled={this.state.disableButton}>Update</Button>&nbsp;&nbsp;
                                                <Button variant="secondary" onClick={() => { this.props.history.push("/"); }} disabled={this.state.disableButton}>Cancel</Button>
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