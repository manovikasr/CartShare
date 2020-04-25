import React, { Component } from "react";
import { Redirect } from "react-router-dom";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { loginUser } from "../../redux/actions/authActions";
import firebase from "firebase";
import StyledFirebaseAuth from "react-firebaseui/StyledFirebaseAuth";
import { firebaseConfig } from "../../config/firebase";

class Login extends Component {
    constructor() {
        super();
        this.state = {
            isSignedIn: false
        };
    }

    componentDidMount() {
        firebase.auth().onAuthStateChanged(user => {
            this.setState({ isSignedIn: !!user })
            
            if (!!user) {
                
                const userData = { email: user.email};
                this.props.loginUser(userData);
            }
        })
    }

    render() {
        var loginComponent;

        if (this.state.isSignedIn) {
            loginComponent = (
                <div>
                    <h1>
                        {firebase.auth().currentUser.displayName}
                    </h1>
                    <button onClick={() => firebase.auth().signOut()}>Sign out!</button>
                </div>
            );
        } else {
            loginComponent = (
                <div className="container p-4">
                    <div className="row justify-content-center align-items-center h-100">
                        <div className="col-md-5 mx-auto bg-white p-3 border rounded">
                            <StyledFirebaseAuth
                                uiConfig={firebaseConfig}
                                firebaseAuth={firebase.auth()}
                            />
                        </div>
                    </div>
                </div>
            );
        }

        return (
            <div>
                {loginComponent}
            </div>
        );
    }
}

Login.propTypes = {
    loginUser: PropTypes.func.isRequired,
    auth: PropTypes.object.isRequired,
    errors: PropTypes.object.isRequired,
    success: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
    auth: state.auth,
    errors: state.errors,
    success: state.success
});

export default connect(mapStateToProps, { loginUser })(Login);