import React, { Component } from "react";
import { Link, withRouter } from "react-router-dom";
import { connect } from "react-redux";
import PropTypes from "prop-types";
import { logoutUser } from "../../redux/actions/authActions";
import { Navbar, Nav } from 'react-bootstrap';
import cartLogo from "../../images/cart.png";

import firebase from "firebase";

class Navigationbar extends Component {
  constructor(props) {
    super(props);
    this.state = {};
  }

  onLogoutClick = e => {
    e.preventDefault();
    this.props.logoutUser(this.props.history);
  };

  render() {
    const { user, isAuthenticated } = this.props.auth;

    var menuButtons;
    var loginButton, logoutButton;

    loginButton = (
      <div class="collapse navbar-collapse navbar-right" id="navbarNav">
        <Nav className="mr-auto">
        </Nav>
        <Link to="/login" class="nav-link text-dark t-font-size-14"><i class="fas fa-user"></i>&nbsp;Login</Link>
      </div>
    );

    logoutButton = (
      <div class="collapse navbar-collapse navbar-right" id="navbarNav">
        <Nav className="mr-auto">
        </Nav>
        <Link className="nav-link text-dark t-font-size-14" to="/" onClick={this.onLogoutClick}><i className="fas fa-sign-out-alt pr-2"></i>Logout</Link>
      </div>
    );

    if (isAuthenticated && user.role !== 'admin') {
      menuButtons = (
        <div class="collapse navbar-collapse navbar-right" id="navbarNav">
          <Nav className="mr-auto">
          </Nav>
          {/* <Nav.Link>{projects}</Nav.Link> */}
        </div>
      );
    }
    else if (isAuthenticated && user.role === 'admin') {
      menuButtons = (
        <div class="collapse navbar-collapse navbar-right" id="navbarNav">
          <Nav className="mr-auto">
          </Nav>
          <Nav.Link>{logoutButton}</Nav.Link>
        </div>
      );;
    }
    else {
      menuButtons = (
        <>
          <Link className="nav-link text-dark t-font-size-14" to="/" onClick={() => { firebase.auth().signOut() }}>Logout</Link>
          {loginButton}
        </>
      )
    }

    return (
      <div>
        <nav className="navbar navbar-expand-lg navbar-dark bg-white border">
          <Navbar.Brand>
            <Link to='/home' className="nav-link" href="#">
              <img src={cartLogo} width="30" height="auto" alt="Cart Share" /> &nbsp;&nbsp;
            <font color="red"><b>Cart Share</b></font>
            </Link>
          </Navbar.Brand>
          {menuButtons}
        </nav>
      </div>
    );
  }
}

Navigationbar.propTypes = {
  logoutUser: PropTypes.func.isRequired,
  auth: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
  auth: state.auth
});

export default connect(mapStateToProps, { logoutUser })(withRouter(Navigationbar));