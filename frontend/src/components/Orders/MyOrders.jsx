import React, { Component } from "react";
import { withRouter, BrowserRouter, NavLink, Route } from "react-router-dom";
import { Nav, Container, Row, Col, Alert, Table, Form, Button } from "react-bootstrap";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import OrderCard from "./OrderCard";
import axios from "axios";

class MyOrders extends Component {
    constructor(props) {
        super(props);
        this.state = {
            orders: []
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
        axios.get("/order/myorders")
            .then(res => {
                if (res.data.orders) {
                    var orders = res.data.orders;
                    var cancelled_orders = [];
                    orders.forEach(order => {
                        if(Math.ceil((new Date() - new Date(order.created_on))/(1000*60*60*24)) > 2){
                            order.status = "ORDER_CANCELLED";
                            cancelled_orders.push(order.id);
                        }
                    });
                    this.setState({
                        orders
                    });
                    this.cancelOrders(cancelled_orders);
                }
            })
            .catch(e => {
                if (e.response) {
                    console.log(e.response.data);
                }
            })
    }

    cancelOrders = async orders => {
        orders.forEach(async order_id => {
            await axios.put(`/order/status/${order_id}/ORDER_CANCELLED`)
                .then(res => {
                })
                .catch(e => {
                    if (e.response && e.response.data)
                        console.log(e.response);
                });
        });
    }

    render() {
        var orders_list, message;
        if (this.state.orders.length) {
            orders_list = this.state.orders.map(order => {
                return (
                    <Col sm={3}>
                        <OrderCard order={order} />
                    </Col>
                )
            });
        }
        else {
            message = (
                <div>
                    <Alert variant="info">You don't have any orders yet.</Alert>
                </div>
            );
        }


        return (
            <div className="container" style={{ width: "75%" }}>
                <h2 className="p-4">My Orders</h2>
                {message}
                <Row>{orders_list}</Row>
                <br /><br />
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