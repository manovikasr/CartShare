import React, { Component } from "react";
import { Link, withRouter } from "react-router-dom";
import { connect } from "react-redux";
import PropTypes from "prop-types";
import { logoutUser } from "../../redux/actions/authActions";
import { Navbar, Nav, Dropdown } from 'react-bootstrap';
import cartLogo from "../../images/cart.png";
import firebase from "firebase";

class Navigationbar extends Component {
  constructor(props) {
    super(props);
    this.state = {};
  }

  onLogoutClick = e => {
    e.preventDefault();
    firebase.auth().signOut()
    this.props.logoutUser(this.props.history);
  };

  render() {
    const { user, isAuthenticated } = this.props.auth;

    var menu, menuOptions;
    var login, logout, userDropdown, verifyEmail;

    login = (
      <div className="collapse navbar-collapse navbar-right" id="navbarNav">
        <Nav className="mr-auto">
        </Nav>
        <Link to="/login" className="nav-link text-dark t-font-size-14"><i className="fas fa-user"></i>&nbsp;Login</Link>
      </div>
    );

    logout = (
      <Link className="nav-link text-dark t-font-size-14" to="/" onClick={this.onLogoutClick}><i className="fas fa-sign-out-alt pr-2"></i>Logout</Link>
    );

    if (!user.email_verified) {
      verifyEmail = (
        <Dropdown.Item><Link to="/verify" className="nav-link text-dark t-font-size-14"><i className="far fa-envelope pr-2"></i>Verify Email</Link></Dropdown.Item>
      );
    }

    userDropdown = (
      <Dropdown>
        <Dropdown.Toggle variant="link" className="nav-link text-dark t-font-size-14" id="dropdown-basic">
          Hi, {user.screen_name}
        </Dropdown.Toggle>
        <Dropdown.Menu>
          <Dropdown.Item><Link to="/profile" className="nav-link text-dark t-font-size-14"><i className="far fa-address-card pr-2"></i>Profile</Link></Dropdown.Item>
          {verifyEmail}
          <Dropdown.Item>{logout}</Dropdown.Item>
        </Dropdown.Menu>
      </Dropdown>
    );

    if (isAuthenticated && user.email_verified) {
      if (user.role === "pooler") {
        menuOptions = (
          <div className="collapse navbar-collapse navbar-right" id="navbarNav">
            <Nav className="mr-auto">
            </Nav>
            <Nav.Link>
              <Link className="nav-link text-dark t-font-size-14" to="/stores"><i className="fas fa-store pr-2"></i>Stores</Link>
            </Nav.Link>
            <Nav.Link>
              <Link className="nav-link text-dark t-font-size-14" to="/pools/mypool"><i className="fas fa-users pr-2"></i>Pool</Link>
            </Nav.Link>
            <Nav.Link>
              <Link className="nav-link text-dark t-font-size-14" to="/orders"><i className="fas fa-clipboard pr-2"></i>Orders</Link>
            </Nav.Link>
            <Nav.Link>
              <Link className="nav-link text-dark t-font-size-14" to="/message"><i className="fas fa-envelope pr-2"></i>Message</Link>
            </Nav.Link>
            <Nav.Link>
              <Link className="nav-link text-dark t-font-size-14" to="/cart"><i className="fas fa-shopping-cart pr-2"></i>Cart</Link>
            </Nav.Link>
          </div>
        );
      } else if (user.role === "admin") {
        menuOptions = (
          <div className="collapse navbar-collapse navbar-right" id="navbarNav">
            <Nav className="mr-auto">
            </Nav>
            <Nav.Link>
              <Link className="nav-link text-dark t-font-size-14" to="/stores"><i className="fas fa-store pr-2"></i>Stores</Link>
            </Nav.Link>
            <Nav.Link>
              <Link className="nav-link text-dark t-font-size-14" to="/products"><i className="fas fa-box-open pr-2"></i>Products</Link>
            </Nav.Link>
          </div>
        );
      }
    }

    if (isAuthenticated) {
      menu = (
        <div className="collapse navbar-collapse navbar-right" id="navbarNav">
          <Nav className="mr-auto">
          </Nav>
          {menuOptions}
          <Nav.Link>{userDropdown}</Nav.Link>
        </div>
      );;
    }
    else {
      menu = (
        <>
          {login}
        </>
      );
    }

    return (
      <div>
        <nav className="navbar navbar-expand-lg navbar-dark bg-white border">
          <Navbar.Brand>
            <Link to='/' className="nav-link" href="#">
              <img src={cartLogo} width="30" height="auto" alt="Cart Share" /> &nbsp;&nbsp;
            <font color="red"><b>Cart Share</b></font>
            </Link>
          </Navbar.Brand>
          {menu}
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