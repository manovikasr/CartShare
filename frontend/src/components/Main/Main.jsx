import React from 'react';
import { Route, Switch } from "react-router";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import Navbar from '../Navbar/Navbar';
import LandingPage from '../LandingPage/LandingPage';
import Home from '../Home/Home';
import Login from '../Login/Login';
import jwt_decode from "jwt-decode";
import setAuthToken from "../../config/setAuthToken";
import { setCurrentUser, logoutUser } from "../../redux/actions/authActions";
import store from "../../store";

// Check for token to keep user logged in
if (localStorage.jwtToken) {
    // Set auth token header auth
    const token = localStorage.jwtToken;

    setAuthToken(token);
    // Decode token and get user info and exp
    const decoded = jwt_decode(token);
    // Set user and isAuthenticated
    store.dispatch(setCurrentUser(decoded));// Check for expired token
    const currentTime = Date.now() / 1000; // to get in milliseconds

    if (decoded.exp < currentTime) {
        // Logout user
        store.dispatch(logoutUser());    // Redirect to login
        window.location.href = "./login";
    }
}

class Main extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            loading: true
        }
    }

    render() {
        return (
            <React.Fragment>
                <Navbar />
                <Switch>
                    <Route exact path="/" component={LandingPage} />
                    <Route exact path="/login" component={Login} />
                    <Route exact path="/home" component={Home} />
                </Switch>
            </React.Fragment>
        )
    }
}

Main.propTypes = {
    auth: PropTypes.object.isRequired,
    errors: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
    auth: state.auth,
    errors: state.errors,
    success: state.success
});

export default connect(mapStateToProps, {})(Main);
