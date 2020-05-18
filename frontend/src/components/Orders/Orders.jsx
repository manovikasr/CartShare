import React, { Component } from "react";
import { withRouter, BrowserRouter, NavLink, Route } from "react-router-dom";
import { Nav } from "react-bootstrap";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import MyOrders from "./MyOrders";
import PickupOrders from "./PickUpOrders";
import DeliverOrders from './DeliverOrders';
import PoolersOrders from './PoolersOrders';

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
        return (
            <div>
                <BrowserRouter>
                    <Nav variant="tabs" >
                        <Nav.Item>
                            <Nav.Link eventKey="1" as={NavLink} to="/orders/myorders">
                                My Orders
                            </Nav.Link>
                        </Nav.Item>
                        <Nav.Item>
                            <Nav.Link eventKey="2" as={NavLink} to="/orders/pickup">
                                Pickup Orders
                            </Nav.Link>
                        </Nav.Item>
                        <Nav.Item>
                            <Nav.Link eventKey="3" as={NavLink} to="/orders/deliver">
                                Deliver Orders
                            </Nav.Link>
                        </Nav.Item>
                    </Nav>
                    <Route path="/orders/myorders" component={MyOrders} exact />
                    <Route path="/orders/pickup" component={PickupOrders} exact />
                    <Route path="/orders/deliver" component={DeliverOrders} exact />
                    <Route path="/orders/poolorders" component={PoolersOrders} exact />
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