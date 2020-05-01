import React, { Component } from "react";
import { withRouter, BrowserRouter, NavLink, Route } from "react-router-dom";
import { Nav } from "react-bootstrap";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import MyOrders from "./MyOrders";
import PickUpOrders from "./PickUpOrders";

class Orders extends Component {
    constructor(props) {
        super(props);
        this.state = {};
    }

    componentDidMount() {
        if (!this.props.auth.isAuthenticated || this.props.auth.user.role === "admin") {
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
            <div>
                <BrowserRouter>
                    <Nav variant="tabs" >
                        <Nav.Item>
                            <Nav.Link eventKey="1" active as={NavLink} to="/orders/myorders">
                                My Orders
                            </Nav.Link>
                        </Nav.Item>
                        <Nav.Item>
                            <Nav.Link eventKey="2" as={NavLink} to="/orders/pickup">
                                Pickup Orders
                            </Nav.Link>
                        </Nav.Item>
                    </Nav>
                    <Route path="/orders/myorders" component={MyOrders} exact />
                    <Route path="/orders/pickup" component={PickUpOrders} exact />
                </BrowserRouter>
            </div>
        );
    }
}

Orders.propTypes = {
    auth: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
    auth: state.auth
});


export default connect(mapStateToProps, {})(withRouter(Orders));