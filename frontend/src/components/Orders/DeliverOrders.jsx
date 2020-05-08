import React, { Component } from "react";
import { withRouter } from "react-router-dom";
import { Row, Col, Alert } from "react-bootstrap";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import axios from "axios";
import OrderCard from "./OrderCard";

class DeliveredOrders extends Component {
    constructor(props) {
        super(props);
        this.state = {
            delivery_orders: []
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
        this.getDeliveryOrders();
    }

    getDeliveryOrders = () => {
        const { user } = this.props.auth;
        axios.get("/order/delivery")
            .then(async res => {
                if (res.data) {
                    console.log(res.data);
                    let orders = res.data.orders.filter(order => order.user_id != user.id)
                    this.setState({
                        delivery_orders: orders
                    });
                }
            })
            .catch(e => {
                if (e.response) {
                    console.log(e.response.data);
                }
            });
    }

    handleToggle = () => {
        this.setState({
            showModal: !this.state.showModal
        });
    }

    render() {
        var order_cards, message;
        if (this.state.delivery_orders.length) {
            order_cards = this.state.delivery_orders.map(order => {
                return (
                    <Col sm={3}>
                        <OrderCard order={order} showAddress={true} getOrders={this.getDeliveryOrders}/>
                    </Col>
                )
            });
        }
        else {
            message = (
                <div className="py-4">
                    <Alert variant="info">You don't have any orders to deliver.</Alert>
                </div>
            );
        }


        return (
            <div className="container" style={{ width: "75%" }}>
                <h2 className="p-4">Deliver Orders</h2>
                {message}
                <Row>{order_cards}</Row>
                <br /><br />
            </div>
        );
    }
}

DeliveredOrders.propTypes = {
    auth: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
    auth: state.auth
});


export default connect(mapStateToProps, {})(withRouter(DeliveredOrders));