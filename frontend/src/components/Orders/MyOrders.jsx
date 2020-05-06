import React, { Component } from "react";
import { withRouter, BrowserRouter, NavLink, Route } from "react-router-dom";
import { Nav, Container, Row, Col, Alert, Table, Form, Button } from "react-bootstrap";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import OrderCard from "./OrdersCard";
import axios from "axios";

class MyOrders extends Component {
    constructor(props) {
        super(props);
        this.state = {
            my_orders:[]
        };
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
        this.getMyOrders();
    }

    getMyOrders = () => {
        axios.get("/order/my_orders")
        .then(res => {
            if(res.data.orders) {
                this.setState({
                    my_orders:res.data.orders
                });
            }
        })
        .catch(e => {
            if(e.response) {
                console.log(e.response.data);
            }
        })
    }

    render() {
        const { user } = this.props.auth;
        var orders_list, ordersTable, orders, actionButtons;
        if(this.state.my_orders.length) {
            orders_list = this.state.my_orders.map(order => {
                return (
                    <Col sm={3}>
                        <OrderCard order = {order}/>
                    </Col>
                ) 
            });
        }
        else {
            orders_list = (
                <div>
                    <Alert variant="info">You don't have any orders yet.</Alert>
                </div>
            );   
        }


        return (
            <div className = "container" style = {{ width: "75%"}}>
                <h2 className = "p-4">My Orders</h2>
                <Row>{orders_list}</Row> 
                <br/><br/>
            </div>
        );
    }
}

MyOrders.propTypes = {
    auth: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
    auth: state.auth
});


export default connect(mapStateToProps, {})(withRouter(MyOrders));