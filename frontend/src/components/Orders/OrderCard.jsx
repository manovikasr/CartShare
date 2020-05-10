import React, { Component } from "react";
import { withRouter } from "react-router-dom";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import axios from "axios";
import { Card, Button } from "react-bootstrap";
import OrderProductsModal from "./OrderProductsModal";

class OrderCard extends Component {
    constructor(props) {
        super(props);
        this.state = {
            showModal: false,
            order: {}
        };
    }

    componentDidMount() {
        console.log(this.props.order)
        this.setState({
            order: this.props.order
        });
    }

    handleToggle = () => {
        this.setState({
            showModal: !this.state.showModal
        });
    }

    markNotDelivered = (e) => {
        if (window.confirm("Are you sure your order was not delivered? We will check with the assigned pooler.")) {
            axios.put(`/order/status/${this.props.order.id}/ORDER_NOT_DELIVERED`)
                .then(res => {
                    if (res.status === 200) {
                        this.props.getOrders();
                    }
                })
                .catch(e => {
                    if (e.response && e.response.data)
                        console.log(e.response);
                });
        }
    }

    markDelivered = (e) => {
        axios.put(`/order/status/${this.props.order.id}/ORDER_DELIVERED`)
        .then(res => {
            if (res.status === 200) {
                this.props.getOrders();
            }
        })
        .catch(e => {
            if (e.response && e.response.data)
                console.log(e.response);
        });
    }

    render() {
        var actionButton, address;
        const { user } = this.props.auth;

        if ((this.props.order.status === "ORDER_PICKEDUP" || this.props.order.status === "ORDER_NOT_DELIVERED") && user.id !== this.props.order.user.id) {
            address = (
                <>
                    <b>Delivery Address: </b>{this.props.order.user.address}<br />
                    {this.props.order.user.city}, {this.props.order.user.state} - {this.props.order.user.zip}
                </>
            );
            actionButton = (
                <Button variant="info" size="sm" onClick={this.markDelivered}>Mark Delivered</Button>
            );
        }
        if (this.props.order.status === "ORDER_DELIVERED") {
            actionButton = (
                <Button variant="link" size="sm" onClick={this.markNotDelivered}>Order Not Received?</Button>
            );
        }
        return (
            <div>
                <Card bg="white" style={{ width: "18rem", margin: "5%" }}>
                    <Card.Body>
                        <Card.Title><b>Order #{this.state.order.id}</b></Card.Title>
                        <Card.Text>
                            <b>Store:</b> {this.props.order.store_name} <br />
                            <b>Order Date:</b> {(new Date(this.props.order.created_on)).toLocaleDateString("en-US")} <br />
                            <b>Status:</b> {this.props.order.status}<br />
                            {address}
                        </Card.Text>
                        <Button variant="success" size="sm" onClick={this.handleToggle}>View Products</Button>
                    &nbsp;&nbsp;&nbsp; {actionButton}
                    </Card.Body>
                </Card>
                <OrderProductsModal showModal={this.state.showModal} onHide={this.handleToggle} order={this.state.order} />
            </div>
        );
    }
}

OrderCard.propTypes = {
    auth: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
    auth: state.auth
});


export default connect(mapStateToProps, {})(withRouter(OrderCard));
